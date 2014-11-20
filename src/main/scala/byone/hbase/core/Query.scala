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
import byone.hbase.protobuf.PreAggProtos
import byone.hbase.protobuf.PreAggProtos.MapEntry
import byone.hbase.util.{Constants, Converter}
import com.google.protobuf.ByteString
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.coprocessor.Batch
import org.apache.hadoop.hbase.client.{HTable, Result, Scan}
import org.apache.hadoop.hbase.filter.{Filter, FilterList}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.ipc.{BlockingRpcCallback, ServerRpcController}
import org.apache.hadoop.hbase.mapreduce.{MultiTableInputFormat, TableInputFormat}
import org.apache.hadoop.hbase.protobuf.ProtobufUtil
import org.apache.spark.rdd.RDD
import org.slf4j.LoggerFactory
import org.apache.spark.SparkContext._

import scala.collection.JavaConverters._

/**
 * Created by liu you on 14-8-13.
 *
 * Core Query class read Hbase/cached to local RDD
 * //@param queryAr args to retrieve data { @see QueryArgs}
 */
class Query(tablename: String, family: String) extends java.io.Serializable {

  private val logger = LoggerFactory.getLogger(getClass)



  def groupBy(raw: Map[String, String], groups: Seq[String])
  : (String, Map[String, String]) = {
    val keys = for (g <- groups) yield {
      raw(g)
    }
    (keys.mkString, raw)

  }

  /**
   * Get a list of Scan for scan hbase
   * @param scanFilter parsered scan filters { @see Query#hbaseFilter}
   * @param timeRange  time range area
   * @return scan list
   */
  def scanList(scanFilter: FilterList,
               timeRange: Seq[Array[Byte]],
               items: Set[String]) = {
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

  def getScan(scanFilter: FilterList,
              items: Set[String]) = {
    val cf = family.getBytes
    val tab = tablename.getBytes
    val scan = new Scan()
    scan.setCacheBlocks(false)
    scan.setCaching(2000)
    scan.setReversed(true)
    scan.setFilter(scanFilter)
    items.foreach(item => scan.addColumn(cf, item.getBytes))
    scan
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
    conf.set(TableInputFormat.INPUT_TABLE, tablename)
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
  def hbaseRdd(scans: List[Scan]):RDD[(ImmutableBytesWritable,Result)] = {
    val conf = HBaseConfiguration.create(Constants.conf)
    conf.set(TableInputFormat.INPUT_TABLE, tablename)
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
  private[Query] def normalize(raw: (ImmutableBytesWritable, Result))
  : Map[String, String] = {
    logger.debug("Normalize raw data to Map(k,v).")
    val eventPairs = raw._2.getNoVersionMap.firstEntry().getValue.asScala
    val retmap = eventPairs.map { case (x, y) =>
      (new String(x),new String(y))
    }
    //    val key = raw._1.get
    //    val subkey = key.slice(8, 12)
    //    subkey -> retmap.toMap
    retmap.toMap
  }



  /**
   * Check if event belongs to group
   * @param event one event
   * @return
   */
  def groupChecker(event: (Array[Byte], Map[String, String]),groups: Seq[String]): Boolean = {
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
    rdd
  }


  /**
   * raw Future rdd
   * @return RDD[Map[String,String]
   */
  def rawRdd(filters: String, events: Seq[String], range: Seq[String], items: Set[String])
  : RDD[Map[String, String]] = {
    logger.info("get rdds using newRawRdd")
    val scans = scanList(Query.hbaseFilter(filters, events), range.map(Converter.toTs),items)

    hbaseRdd(scans.toList).map(normalize).cache()

  }

  def resRdd(filters: String, events: Seq[String], range: Seq[String],
             items: Set[String], groups: Seq[String],arrges: Map[String, String])
  : RDD[(String, String)] = {

    val table = new HTable(Constants.conf, tablename)
    val scan = getScan(Query.hbaseFilter(filters,events),items)
    val protorange =range.map(Converter.toTs).map(ByteString.copyFrom).asJava

    val agg = for(ar <- arrges) yield {
      PreAggProtos.TupleEntry.newBuilder().setKey(ar._1).setValue(ar._2).build()
    }
    val request = PreAggProtos.Request.newBuilder()
      .setScan(ProtobufUtil.toScan(scan))
      .addAllRange(protorange)
      .addAllGroups(groups.asJava)
      .setAggre(PreAggProtos.MapEntry.newBuilder().addAllKv(agg.asJava).build())
      .build()

    val results =  table.coprocessorService(classOf[PreAggProtos.PreAggService],
      null, null,
      new Batch.Call[PreAggProtos.PreAggService, MapEntry]() {
        override def call(counter: PreAggProtos.PreAggService): MapEntry = {
          val controller = new ServerRpcController()
          val rpcCallback = new BlockingRpcCallback[PreAggProtos.Response]()
          counter.getPreData(controller, request, rpcCallback)
          val response = rpcCallback.get()
          //if(response != null && response.isInitialized)
          response.getData
        }
      })

    val data = results.values().asScala

    val temp = data.map(sub => {
      val kvList = sub.getKvList.asScala
      kvList.map(kv => {
        kv.getKey -> kv.getValue
      })
    }).flatten
   //val rdd = Constants.sc.emptyRDD[Map[String,String]]()
    val rdd = Constants.sc.makeRDD(temp.toSeq)
    rdd.map(x => {
      (x._1,(x._1.substring(0,3),x._2))
    }).reduceByKey(Query.aggre).map(x =>{
      (x._1,x._2._2)
    })
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
case class QueryArgs(Range: Option[Seq[String]],
                     Items: Option[Seq[String]],
                     Events: Option[Seq[String]],
                     Filter: Option[String],
                     Groups: Option[Seq[String]],
                     Aggres: Option[Seq[Seq[String]]],
                     Order: Option[String])


object Query {


  def aggre(lh:(String, String),rh: (String, String)): (String, String) = {
    if(lh._2.isEmpty ||rh._2.isEmpty)
      (lh._1,"")
    else {
      val lv = lh._2.toDouble
      val rv = rh._2.toDouble
      val ret = lh._1 match {
        case "avg" => (lv + rv)/2
        case "min" => Math.min(lv,rv)
        case "max" => Math.max(lv,rv)
        case "cou" => lv + rv
      }

      (lh._1,ret.toString)
    }
  }
  def create(tablename: String, family: String) = new Query(tablename,family)

  private val logger = LoggerFactory.getLogger(getClass)
  /**
   * parser filter args and events to filter
   * @param filterString   : filter args
   * @param events : list of events
   * @return Parsered filter list
   */
  def hbaseFilter(filterString: String, events: Seq[String]): FilterList = {
    val flist = new FilterList(FilterList.Operator.MUST_PASS_ALL)

    if (filterString.isEmpty && events.isEmpty) {
      logger.debug("filters&& event equals null, set Filter to null")
      null
    }

    else {
      if (events.nonEmpty) {
        logger.debug(" Parsering events to Filters.")
        val meaningful = events.map(Constants.uid.toId).filter(nullChecker)
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
      if (filterString.nonEmpty) {
        logger.debug(" Parsering filter string to Filters.")
        flist.addFilter(new ByParseFilter().parseFilterString(filterString))
      }
      flist
    }
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

}


//class Query(queryAr: String) extends java.io.Serializable {


//  def get(single: Boolean = true) = {
//    if (localCached) {
//      cached(new readArgs(range, events))
//    } else {
//      rdd = {
//        if (groups.isEmpty)
//          rawRdd().map(x => family -> x._2)
//        else
//          rawRdd().filter(groupChecker).map(groupBy)
//      }
//      if (aggres.nonEmpty) {
//        Aggre.doAggre(rdd, aggres)
//      } else {
//        rdd
//      }
//
//    }
//  }

//
//
//  def multiGet = {
//
//    logger.info("multi get rdds")
//    val retRdd = {
//      if (groups.isEmpty)
//        rawRdd().map(x => family -> x._2)
//      else
//       // rawRdd().filter(Query.groupChecker).map(groupBy)
//    }
//    if (aggres.nonEmpty) {
//      Aggre.doAggre(retRdd, aggres)
//    } else
//      retRdd
//  }
//
//  def groupBy(raw: (Array[Byte], Map[String, String]))
//  : (String, Map[String, String]) = {
//    val keys = for (g <- groups) yield {
//      raw._2(g)
//    }
//    (keys.mkString, raw._2)
//
//  }


//  case class readArgs(Range: Seq[String], Events: Seq[String])
//  val cached = Map[readArgs,RDD[(String, Map[String, String])]]()


//
//  /**
//   * raw Future rdd
//   * @return Future[RDD[(String,Map[String,String])]
//   */
//  def rawRdd(): RDD[(Array[Byte], Map[String, String])] = {
//    logger.info("get rdds using newRawRdd")
//    val scans = scanList(hbaseFilter(filters, events), range.map(Converter.toTs))
//    hbaseRdd(scans.toList).map(normalize).cache()
//
//  }




//}