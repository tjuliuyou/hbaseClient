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
import byone.hbase.util.{Constants, Converter}
import com.twitter.util.{LruMap, Future}
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{HTable, Result, Scan}
import org.apache.hadoop.hbase.filter.{Filter, FilterList}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.{MultiTableInputFormat, TableInputFormat}
import org.apache.spark.rdd.RDD
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._


/**
 * Created by liu you on 14-8-13.
 *
 * Core Query class read Hbase/cached to local RDD
 * @param args args to retrieve data { @see QueryArgs}
 */
class Query(args: QueryArgs) extends java.io.Serializable {

  private val serialVersionUID = 6529685098267757690L
  private val logger = LoggerFactory.getLogger(classOf[Query])

  private val family = Constants.dataFamily(0)
  private val tablename = Constants.dataTable
  private val uid = new UniqueId

  private var range = args.Range
  private var events = args.Events
  private var filters = args.Filter

  private var groups = args.Groups

  private var aggres = if (args.Aggres.nonEmpty) {
    for (ar <- args.Aggres) yield {
      val cond = ar.head
      val item = ar.drop(1)
      (cond, item)
    }
  } else Seq[(String, Seq[String])]()

  private val aggitems = {
    val ilist = for (agg <- aggres) yield agg._2
    ilist.flatten.toList
  }

  private var items = (args.Items ++ args.Groups ++ aggitems).toSet

  case class readArgs(Range: Seq[String], Events: Seq[String])

  val cached = new LruMap[readArgs, RDD[(String, Map[String, String])]](10)

  var rdd: RDD[(String, Map[String, String])] = Constants.sc.emptyRDD

  Constants.conf.set(TableInputFormat.INPUT_TABLE, tablename)

  def setRange(rg: Seq[String]) {
    if (rg.size != 2)
      logger.error("range list size must be 2!")
    if (rg(0) > rg(1)) {
      logger.error("start time bigger than stop time")
    }
    range = rg
  }

  def setItems(it: Set[String]) {
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

  def setAggres(ag: Seq[Seq[String]]) {
    aggres = if (ag.nonEmpty) {
      for (ar <- ag) yield {
        val cond = ar.head
        val item = ar.drop(1)
        (cond, item)
      }
    } else Seq[(String, Seq[String])]()
  }


  def localCache: Boolean = {
    if (cached.isEmpty)
      false
    else
      true
  }

  def get(single: Boolean = true) = {
    if (localCache) {
      rdd = cached(new readArgs(range, events))
    } else {
      rdd = multiGet
    }
    rdd
  }


  //def groupchecker: ((Array[Byte], Map[String, String])) => Boolean = ???

  def multiGet = {

    logger.info("multi get rdds")
    val retRdd = {
      if (groups.isEmpty)
        rawRdd().map(x => family -> x._2)
      else
        rawRdd().filter(groupChecker).map(groupBy)
    }
    if (aggres.nonEmpty) {
      Aggre.doAggre(retRdd, aggres)
    } else
      retRdd
  }

  def groupBy(raw: (Array[Byte], Map[String, String]))
  : (String, Map[String, String]) = {
    val keys = for (g <- groups) yield {
      raw._2(g)
    }
    (keys.mkString, raw._2)

  }

  /**
   * raw Future rdd
   * @return Future[RDD[(String,Map[String,String])]
   */
  def rawRdd(): RDD[(Array[Byte], Map[String, String])] = {
    logger.info("get rdds using newRawRdd")
    val scans = scanList(hbaseFilter(filters, events), range.map(Converter.toTs))

    hbaseRdd(scans.toList).map(normalize).cache()

  }


  /**
   * parser filter args and events to filter
   * @param args   : filter args
   * @param events : list of events
   * @return Parsered filter list
   */
  private def hbaseFilter(args: String, events: Seq[String]): FilterList = {
    val flist = new FilterList(FilterList.Operator.MUST_PASS_ALL)

    if (filters.equals("null") && events.isEmpty) {
      logger.debug("filters&& event equals null, set Filter to null")
      //debug code
      //      val exfilter: Filter = new RowFilter(
      //        CompareFilter.CompareOp.EQUAL, new EventComparator(Converter.ip2Byte("10.133.64.2"),8))
      //      flist.addFilter(exfilter)
      //      val exfilter2: Filter = new RowFilter(
      //        CompareFilter.CompareOp.GREATER, new EventComparator(Converter.num2Byte(5,1),12))
      //      flist.addFilter(exfilter2)
      //      flist
      null
    }

    else {
      if (events.nonEmpty) {
        logger.debug(" Parsering events to Filters.")
        val meaningful = events.map(uid.toId).filter(nullChecker)
        if (meaningful.nonEmpty) {
          val ents = for (event <- meaningful) yield {
            val rowfilter: Filter = new RowFilter(
              CompareFilter.CompareOp.EQUAL, new EventComparator(event))
            rowfilter
          }
          val rowlist: Filter = new FilterList(FilterList.Operator.MUST_PASS_ONE, ents.asJava)
          flist.addFilter(rowlist)
        }
      }
      if (!args.equals("null")) {
        logger.debug(" Parsering filter string to Filters.")
        flist.addFilter(new ByParseFilter().parseFilterString(args))
      }
      flist
    }
  }

  /**
   * Get a list of Scan for scan hbase
   * @param scanFilter parsered scan filters { @see Query#hbaseFilter}
   * @param timeRange  time range area
   * @return scan list
   */
  private def scanList(scanFilter: FilterList, timeRange: Seq[Array[Byte]]) = {
    val cf = family.getBytes
    val tab = tablename.getBytes
    rowArea(timeRange).map { rows =>
      val scan = new Scan(rows._1, rows._2)
      scan.setCacheBlocks(false)
      scan.setCaching(2000)
      scan.setReversed(true)
      scan.setFilter(scanFilter)
      if (items.nonEmpty)
        items.foreach(item => scan.addColumn(cf, item.getBytes))
      scan.setAttribute(Scan.SCAN_ATTRIBUTES_TABLE_NAME, tab)
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
    for (num <- 0 until regionRange) yield {
      val pre = Converter.Int2Byte(num, length)
      (pre ++ timeRange(0)) -> (pre ++ timeRange(1))
    }
  }

  /**
   * Get single Hbase RDD with one Scan
   * @param scan { @see org.apache.hadoop.hbase.client.Scan}
   * @return table out rdd
   */
  def hbaseRdd(scan: Scan) = {
    val conf = HBaseConfiguration.create(Constants.conf)
    conf.set(TableInputFormat.SCAN, Converter.ScanToString(scan))
    val hbaseRDD = Constants.sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    hbaseRDD
  }

  /**
   * Get Hbase RDD with Scan list
   * @param scans { @see org.apache.hadoop.hbase.client.Scan}
   * @return table out multi rdd
   */
  def hbaseRdd(scans: List[Scan]) = {
    val conf = HBaseConfiguration.create(Constants.conf)
    conf.setStrings(MultiTableInputFormat.SCANS, Converter.ScanToString(scans): _*)
    val hbaseRDD = Constants.sc.newAPIHadoopRDD(conf, classOf[MultiTableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    hbaseRDD
  }


  /**
   * Normailzie raw date from Hbase to (rowkey,valuePairs)
   * @param raw get from hbase { @see newAPIHadoopRDD}
   * @return (rowkey array[byte], value map)
   */
  private def normalize(raw: (ImmutableBytesWritable, Result))
  : (Array[Byte], Map[String, String]) = {
    logger.debug("Normalize raw data to Map(k,v).")
    val eventPairs = raw._2.getNoVersionMap.firstEntry().getValue.asScala
    val retmap = eventPairs.map { case (x, y) =>
      new String(x) -> new String(y)
    }
    val key = raw._1.get
    val subkey = key.slice(8, 12)
    subkey -> retmap.toMap
  }

  /**
   * Check item equal null
   * @param any any type
   * @return
   */
  def nullChecker(any: AnyRef): Boolean = {
    any match {
      case null => false
      case _ => true
    }
  }

  /**
   * Check if event belongs to group
   * @param event one event
   * @return
   */
  def groupChecker(event: (Array[Byte], Map[String, String])): Boolean = {
    for (g <- groups) {
      if (!event._2.contains(g))
        return false
    }
    true
  }

  /**
   * Accumulator used to sum a Seq of RDDs
   * @param rddSeq a Seq of rdd
   * @return RDD
   */
  private def accumulator(rddSeq: Seq[RDD[(ImmutableBytesWritable, Result)]]) = {
    val ret: RDD[(ImmutableBytesWritable, Result)] = Constants.sc.emptyRDD
    val rdd = rddSeq.foldLeft(ret)((rhs, left) => rhs ++ left)
    Future.value(rdd)
  }

}

/**
 * Args holds a bunch of args parsed from test file (json)
 * @param Range   Time range Seq should be start time and stop time, to see the format { @see DatePoint#toTs}
 * @param Items   Items Seq that needed to take back for display
 * @param Events  Event type Seq explicit should be take back
 * @param Filter  Filter String will be used for Scans
 * @param Groups Group by Seq
 * @param Aggres  Aggreagte args
 */
case class QueryArgs(Range: Seq[String], Items: Seq[String]
                     , Events: Seq[String], Filter: String
                     , Groups: Seq[String], Aggres: Seq[Seq[String]])


object Query {


  private val constConnect = new HTable(Constants.conf, Constants.dataTable)

  def create(args: QueryArgs) = new Query(args)

  def close() = constConnect.close()

}