package byone.hbase

import byone.hbase.uid.UniqueId
import org.apache.hadoop.hbase.{Cell, HBaseConfiguration}
import org.apache.hadoop.hbase.client.{Result, Scan}
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark._
import org.apache.spark.rdd.RDD
import byone.hbase.utils.{DatePoint, ScanCovert}
import scala.collection.mutable.Map
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._
import byone.hbase.core.Aggre

/**
 * Created by dream on 7/7/14.
 */
object Client {

  // scan to string
  def ScanToString(scan : Scan) : String = new ScanCovert(scan).coverToScan()
  /**
    *  main fun
    */
  def main(args: Array[String]) {

    // glable conf
    val tablename = "log_data"
    val sparkConf = new SparkConf().setAppName("HBaseTest").setMaster("local")
    val sc = new SparkContext(sparkConf)
    val conf = HBaseConfiguration.create()
    conf.addResource("/home/dream/workspace/scalahbaseClient/conf/hbase-site.xml")
    conf.addResource("/home/dream/workspace/scalahbaseClient/conf/yarn-site.xml")
    conf.addResource("/home/dream/workspace/scalahbaseClient/conf/mapred-site.xml")
    val uidtable = new UniqueId(conf,sc)

      /**
      *  get Scan list for scan
      */
    def getScan(cdn: Map[String,Vector[String]]) : Vector[Scan] = {
      require(cdn.contains("range"))
      //val sl =
      val area = DatePoint.getRowArea(cdn("range"),cdn("event"),uidtable)
      val sl = area.map{rows =>
        val sn = new Scan(rows._1.getBytes,rows._2.getBytes)
        val fl = new SingleColumnValueFilter("d".getBytes,"collectorId".getBytes,CompareOp.LESS,"10004".getBytes)
        sn.setFilter(fl)
        if(!cdn("back").isEmpty)
          cdn("back").foreach(dis =>sn.addColumn("d".getBytes,dis.getBytes))
        sn
      }
      Vector() ++ sl //thans iterater to vector
    }

      /**
     *  map raw hbase date to (string,string) by grouplist
     */
    def gpBy(raw: (ImmutableBytesWritable, Result), gp: Set[String]): (String,Map[String,String]) = {
      val retmap = Map[String, String]()
      var ky = ""
        for(kv:Cell<- raw._2.rawCells())
        {
          val key = new String(kv.getQualifier)
          val value = new String(kv.getValue)
          if(gp.contains(key)) {
            ky = value
          }
          else
            retmap += (key->value)
        }
      (ky,retmap)
    }

      /**
     *  get base hbase RDD with one Scan
     */
    def gethbaseRDD(scan: Scan): RDD[(ImmutableBytesWritable, Result)] = {
      conf.set(TableInputFormat.INPUT_TABLE, tablename)
      conf.set(TableInputFormat.SCAN,ScanToString(scan))
      val hBaseRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
        classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
        classOf[org.apache.hadoop.hbase.client.Result])
      hBaseRDD
    }

    def MergeRDD(sl: Vector[Scan]) : RDD[(ImmutableBytesWritable, Result)] = {
      var ret: RDD[(ImmutableBytesWritable, Result)] = sc.emptyRDD
      for (scan <- sl)  {
        val rdd =gethbaseRDD(scan)
        rdd.collect()
        ret = ret ++ rdd
      }
      ret
    }

      /**
     *  get merged hbase RDD
     */
    def getRDD(sl: Vector[Scan], gp: Set[String]): RDD[(String, Map[String,String])] = {
        var ret: RDD[(String, Map[String,String])] = sc.emptyRDD
        for (scan <- sl)  {
          val rdd =gethbaseRDD(scan).map(x =>gpBy(x,gp))
          rdd.collect()
          ret = ret ++ rdd
        }
        ret
    }
    //args

    val timerange = Vector("18/06/2014 14:47:11","18/06/2014 14:50:11")
    val display = Vector("collectorId", "eventType", "relayDevIpAddr","pollIntv","cpuUtil","envTempOffHighDegC")
    val eventType = Vector("PH_DEV_MON_SYS_PER_CPU_UTIL","PH_DEV_MON_HW_TEMP")
    val filters = Vector("collectorId","<","10050")
    //val gpbylist:Set[String] = Set.empty
    val gpbylist = Set("relayDevIpAddr")
    val aggitems = Vector("cpuUtil","envTempOffHighDegC","collectorId")
    val aggars = Map("avg" -> Set("cpuUtil","envTempOffHighDegC"))

    //parser args
    val scanCdn = Map("range"  -> timerange,
      "event"  -> eventType,
      "back"   -> display,
      "filter" -> filters)

    //get hbase RDD and print it
    val s = getScan(scanCdn)
    if(gpbylist.isEmpty){
      val hbaseRDD =getRDD(s,Set("d"))
      hbaseRDD.collect().foreach(x =>println(x._2))
      println("hbaseRDD count: " + hbaseRDD.count())
    }
    else {

    val hbaseRDD = getRDD(s,gpbylist)
    //hbaseRDD.collect().foreach(x =>println(x))
    //println("hbaseRDD count: " + hbaseRDD.count())


    //val aggr = new Aggre(hbaseRDD)
    val tm = Aggre.avg(hbaseRDD,aggitems)
      tm.collect().foreach(println)
    //aggr.avg(aggitems).collect().foreach(x =>println(x))
//    val last = hbaseRDD.map(x =>aggr.PreAggre(x,aggitems))
//
//    last.collect().foreach(x =>println(x))
//
//    val afterreduce = last.reduceByKey((x,y) => aggr.AggreRDD(x,y))
//
//    afterreduce.collect().foreach(x =>println(x))

//    val sort =afterreduce.mapValues(aggr.avg1).collect().sortBy(r =>
//      //r._2.foreach(x =>(x._2))
//      (r._2("collectorId"),r._2("cpuUtil"))
//    )
//      sort.foreach(x =>println(x))
    }
    sc.stop()
  }
}
