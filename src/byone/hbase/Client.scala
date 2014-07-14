package byone.hbase

import byone.hbase.core.{RwRDD, Aggre}
import byone.hbase.utils.Conf
import scala.collection.mutable.Map

/**
 * Created by dream on 7/7/14.
 */
object Client {
  /**
    *  main func
    */
  def main(args: Array[String]) {

    //args
    val timerange = Vector("18/06/2014 14:47:11","18/06/2014 14:50:11")
    //val timerange = Vector("12/07/2014 11:33:11","12/07/2014 11:35:11")
    val display = Vector("collectorId", "eventType", "relayDevIpAddr","pollIntv","cpuUtil","envTempOffHighDegC")
    val eventType: Vector[String] = Vector.empty
    //val eventType = Vector("PH_DEV_MON_SYS_PER_CPU_UTIL","PH_DEV_MON_HW_TEMP")
    val filter = "SingleColumnValueFilter ('d','collectorId',=,'binary:10050')"
    //val gpbylist:Set[String] = Set.empty
    val gpbylist = Set("relayDevIpAddr")
    val aggitems = Vector("cpuUtil","envTempOffHighDegC","collectorId")
    val aggars = Map("avg" -> Set("cpuUtil","envTempOffHighDegC"))

    //parser args
    val scanCdn = Map("range"  -> timerange,
      "event"  -> eventType,
      "back"   -> display)

    //get hbase RDD and print it
    val rw = new RwRDD(Conf.tablename)
    val s = rw.scanList(scanCdn,filter)
    if(gpbylist.isEmpty){
      val hbaseRDD =rw.get(s,Set("d"))
      hbaseRDD.collect().foreach(x =>println(x._2))
      println("hbaseRDD count: " + hbaseRDD.count())
    }
    else {

      val hbaseRDD2 = rw.get(s,gpbylist)
      println("rw count: " + hbaseRDD2.count)
      val ag = new Aggre
      val tm = ag.avg(hbaseRDD2,aggitems)
      println("aggre: " + tm.count)
      tm.collect().foreach(println)

    val sort =tm.collect().sortBy(r =>
      (-r._2("collectorId"),-r._2("cpuUtil"))
    )
    sort.foreach(println)



    }
    Conf.sc.stop()
  }
}
