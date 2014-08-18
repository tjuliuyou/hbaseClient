/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package byone.hbase.core

import byone.hbase.filter.{ByParseFilter, CompareFilter, EventComparator, RowFilter}
import byone.hbase.uid.UniqueId
import byone.hbase.util.{Constants, DatePoint}
import com.twitter.util.{LruMap, Future}
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Result, Scan}
import org.apache.hadoop.hbase.filter.{Filter, FilterList}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark.rdd.RDD
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._

/**
 * Created by liuyou on 14-8-13.
 *
 * Core Query class read hbase/cached to local RDD
 * @param args args to retrieve data {@see QueryArgs}
 */
class Query(args: QueryArgs) extends java.io.Serializable {

  private val logger = LoggerFactory.getLogger(classOf[Query])
  private val family = Constants.FAMILY

  private var range = args.Range
  private var items = args.Items
  private var events = args.Events
  private var filters = args.Filter
  private var groups =  args.Groups
  private var aggres = if(args.Aggres.nonEmpty) {
    for (ar <- args.Aggres) yield {
      val cond = ar.head
      val item = ar.drop(1)
      (cond, item)
    }
  } else Seq[(String,Seq[String])]()

  case class readArgs(Range: Seq[String], Events: Seq[String])
  val cached = new LruMap[readArgs,RDD[(String,Map[String,String])]](10)
  //val rdd = new Promise[RDD[(String,Map[String,String])]]

  var rdd: RDD[(String,Map[String,String])] = Constants.sc.emptyRDD


  private val uid = new UniqueId
  uid.readToCache("hdfs://master1.dream:9000/spark/eventuid.txt")

  Constants.conf.set(TableInputFormat.INPUT_TABLE, Constants.tablename)

  def setRange(rg: Seq[String]) {
    if(rg.size != 2)
      logger.error("range list size must be 2!")
    if(rg(0)>rg(1)) {
      logger.error("start time bigger than stop time")
    }
    range = rg
  }

  def setItems(it: Seq[String]) {
    items = it
  }

  def setEvents(et: Seq[String]) {
    events = et
  }
  def setFilter(fr: String) {
    filters = fr
  }

  def setGroups(gp: Seq[String]) {
    groups = gp
  }

  def setAggres(ag: Seq[Seq[String]]){
    aggres = if(ag.nonEmpty) {
      for (ar <- ag) yield {
        val cond = ar.head
        val item = ar.drop(1)
        (cond, item)
      }
    } else Seq[(String,Seq[String])]()
  }


  def localCache: Boolean = {
    if(cached.isEmpty)
      false
    else
      true
  }

  def get()={
    if(localCache) {
      rdd = cached(new readArgs(range,events))
    }else {
      getFromHbase() onSuccess { raw =>
        cached(new readArgs(range,events)) = raw
        rdd = raw
      }
    }
   rdd
  }


  def getFromHbase(): Future[RDD[(String,Map[String,String])]] = {

    val retrdd = {
    if(groups.isEmpty)
      rawRdd().flatMap(raw => Future(raw.map(x => family->itemFilter(x._2))))
    else
      rawRdd().flatMap(raw => Future(raw.map(grouBy)))
    }
    if(aggres.nonEmpty) {
      val prerdd  = retrdd.flatMap(raw =>{
        Future(Aggre.doAggre(raw,aggres))})
    prerdd
    } else
      retrdd

  }

  private def itemFilter=(raw: Map[String, String]) => {
    items.map(x=> x -> raw.getOrElse(x,"null")).toMap
  }

  def grouBy(raw: (Array[Byte],Map[String,String]))
  : (String,Map[String,String]) = {

    val keys = for(g <- groups) yield {
      if(raw._2.contains(g)) "key"->raw._2(g)
      else "nul"->""
    }
    val key = keys.toMap
    (key("key"),itemFilter(raw._2))

  }

  /**
   * raw Future rdd
   * @return Future[RDD[(String,Map[String,String])]
   */
  def rawRdd(): Future[RDD[(Array[Byte],Map[String,String])]] = {

    logger.info("get future rdds")

    val scanFilter = {
      if(filters.equals("null") && events.isEmpty)
        null
      else
        hbaseFilter(filters,events)
    }
    val scans = scanList(scanFilter,range.map(DatePoint.toTs))

    val futureList = for(scan <- scans) yield Future(hbaseRdd(scan))

    Future.collect(futureList)
          .flatMap(accumulator)
          .flatMap(raw =>Future(raw.map(normalize)))

  }

  /**
   * Normailzie raw date from Hbase to (rowkey,valuePairs)
   * @param raw get from hbase {@see newAPIHadoopRDD}
   * @return (rowkey array[byte], value map)
   */
  private def normalize(raw: (ImmutableBytesWritable, Result))
  : (Array[Byte],Map[String,String]) = {
    val eventPairs = raw._2.getNoVersionMap.firstEntry().getValue.asScala
    val retmap = eventPairs.map{case (x,y) =>
      new String(x) -> new String(y)
    }
    raw._1.get -> retmap.toMap

  }



  /**
   * parser filter args and events to filter
   * @param args   : filter args
   * @param events : list of events
   * @return Parsered filter list
   */
  private def hbaseFilter(args:String,events: Seq[String]): FilterList = {
    val flist =new FilterList(FilterList.Operator.MUST_PASS_ALL)
    if(events.nonEmpty){
      val ents = for(event <- events) yield {
        val rowfilter: Filter = new RowFilter(
          CompareFilter.CompareOp.EQUAL,new EventComparator(uid.id(event)))
        rowfilter
      }
      val rowlist: Filter = new FilterList(FilterList.Operator.MUST_PASS_ONE,ents.asJava)
      flist.addFilter(rowlist)
    }

    if(!args.equals("null")){
      flist.addFilter(new ByParseFilter().parseFilterString(args))

    }
    flist
  }

  /**
   * Get a list of Scan for scan hbase
   * @param scanFilter parsered scan filters {@see Query#hbaseFilter}
   * @param timeRange  time range area
   * @return scan list
   */
  private def scanList(scanFilter: Filter,timeRange: Seq[Array[Byte]]) = {

    rowArea(timeRange).map{rows =>
      val scan = new Scan(rows._1,rows._2)
      scan.setCacheBlocks(false)
      scan.setCaching(10000)
      scan.setReversed(true)
      scan.setFilter(scanFilter)
      scan
    }
  }

  /**
   * Get all row area ( every region area)
   * @param timeRange starkey and stopkey
   * @return : map(startkey and stopkey)
   */
  private def rowArea(timeRange: Seq[Array[Byte]]) = {
    val length = Constants.PRELENGTH
    val regionRange = Constants.REGIONRANGE
    for(num <- 0 until regionRange) yield {
      val pre = DatePoint.Int2Byte(num,length)
      ( pre ++ timeRange(0)) -> (pre ++ timeRange(1))
    }
  }


  /**
   * Get single Hbase RDD with one Scan
   * @param scan {@see org.apache.hadoop.hbase.client.Scan}
   * @return table out rdd
   */
  def hbaseRdd(scan: Scan) = {
    val conf = HBaseConfiguration.create(Constants.conf)
    conf.set(TableInputFormat.SCAN,DatePoint.ScanToString(scan))
    val hBaseRDD = Constants.sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    hBaseRDD
  }

  /**
   * Accumulator used to sum a Seq of RDDs
   * @param rddSeq a Seq of rdd
   * @return RDD
   */
  private def accumulator(rddSeq: Seq[RDD[(ImmutableBytesWritable,Result)]]) ={
    val ret: RDD[(ImmutableBytesWritable,Result)] = Constants.sc.emptyRDD
    val rdd = rddSeq.foldLeft(ret)((rhs,left) => rhs ++ left)
    Future.value(rdd)
  }
}


/**
 * Args holds a bunch of args parsed from test file (json)
 * @param Range   Time range Seq should be start time and stop time, to see the format {@see DatePoint#toTs}
 * @param Items   Items Seq that needed to take back for display
 * @param Events  Event type Seq explicit should be take back
 * @param Filter  Filter String will be used for Scans
 * @param Groups Group by Seq
 * @param Aggres  Aggreagte args
 */
case class QueryArgs(Range: Seq[String], Items: Seq[String]
                     ,Events: Seq[String], Filter: String
                     ,Groups: Seq[String], Aggres: Seq[Seq[String]])