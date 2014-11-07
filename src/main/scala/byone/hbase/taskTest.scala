package byone.hbase

import com.twitter.util.Future

/**
 * Created by liuyou on 14/11/3.
 */
object taskTest {
  def main(args: Array[String]) {
//    val handle = RsyncClient.queryData(
//      """{
//           "Range": ["12/10/2014 11:08:12","12/10/2014 19:08:15"],
//           "Items": ["collectorId", "eventType", "relayDevIpAddr", "cpuUtil","hostIpAddr","eventSeverity"],
//           "Events": ["PH_DEV_MON_SYS_MEM_UTIL","PH_DEV_MON_SYS_PER_CPU_UTIL"],
//           "Filter": "SingleColumnValueFilter ('d','hostIpAddr',=,'binary:10.133.64.2')",
//           "Groups": ["hostName"],
//           "Aggres": null
//          }""")
//    println(handle)
//    val rdd = Future(handle.take)
//    rdd onSuccess(x => println("rdd already!"))
//    while(true){
//      println(handle.status)
//      Thread.sleep(5000)
//    }
    val handles = for(i <- 0 to 5) yield {
      RsyncClient.queryData("query"+ i.toString)
    }
//    for(i <- 1000 to 1100) {
//      RsyncClient.writeDataToHBase("data"+i.toString)
//      //    rdd onSuccess(x => println("rdd already!"))
//      //    while(true){
//      //      println(handle.status)
//      //      Thread.sleep(5000)
//      //    }
//    }
    handles.foreach{x =>
      Thread.sleep(6000)
      println("--------------------------------------------")
    }
  }
}
