package byone.hbase

import org.apache.hadoop.hbase.client.Scan
import org.apache.spark._
import byone.hbase.utils.{Conf, ScanCovert}
import scala.collection.mutable.Map
import org.apache.spark.SparkContext._
import byone.hbase.core.{RwRDD, Aggre}

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

    //args

    val timerange = Vector("18/06/2014 14:47:11","18/06/2014 14:50:11")
    //val timerange = Vector("12/07/2014 11:33:11","12/07/2014 11:35:11")
    val display = Vector("collectorId", "eventType", "relayDevIpAddr","pollIntv","cpuUtil","envTempOffHighDegC")
    val eventType: Vector[String] = Vector.empty
    //val eventType = Vector("PH_DEV_MON_SYS_PER_CPU_UTIL","PH_DEV_MON_HW_TEMP")
    val filters = Vector("collectorId,<,10050")
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
    val rw = new RwRDD(Conf.tablename)
    val s = rw.scanList(scanCdn)
    if(gpbylist.isEmpty){
      val hbaseRDD =RwRDD.getRDD(s,Set("d"))
      hbaseRDD.collect().foreach(x =>println(x._2))
      println("hbaseRDD count: " + hbaseRDD.count())
    }
    else {

//      val hbaseRDD = RW.getRDD(s,gpbylist)
//      //hbaseRDD.collect().foreach(println)
//      println("RW count: " + hbaseRDD.count())

      val hbaseRDD2 = rw.get(s,gpbylist)
      //hbaseRDD.collect().foreach(println)
      println("rw count: " + hbaseRDD2.count())


//      val tm = Aggre.avg(hbaseRDD,aggitems)
//        tm.collect().foreach(println)
//
//      val sort =tm.collect().sortBy(r =>
//        (-r._2("collectorId"),-r._2("cpuUtil"))
//      )
//        sort.foreach(println)







    }
    Conf.sc.stop()
  }
}
