import byone.hbase.core.QueryArgs
import net.liftweb.json.JsonParser._

/**
 * Created by dream on 8/1/14.
 */
object test4 {



  def main(args: Array[String]) {
    val ar =
      """{
           "Range": ["12/10/2014 11:08:12","12/10/2014 19:08:15"],
           "Items": ["collectorId", "eventType", "relayDevIpAddr", "cpuUtil","hostIpAddr","eventSeverity"],
           "Events": ["PH_DEV_MON_SYS_MEM_UTIL","PH_DEV_MON_SYS_PER_CPU_UTIL"],
           "Filter": "SingleColumnValueFilter ('d','hostIpAddr',=,'binary:10.133.64.2')",
           "Groups": ["hostName"],
           "Aggres": null,
           "Order" : null
          }"""
    try {
      implicit val formats = net.liftweb.json.DefaultFormats
      val argss = parse(ar).extract[QueryArgs]
      if (argss.Range.get.length != 2) {
        println("range list size must be 2!")
      }
      if (argss.Range.get(0) > argss.Range.get(1)) {
        println("start time bigger than stop time.")

      }
      println(argss)
    } catch {
      case e: Exception => {
        println("Parser QueryArgs with liftweb json error: " + e.getMessage)

      }
    }
    finally {

    }


   // val a = parse(ar)



  }

}
