import byone.hbase.uid.EventFactory
import byone.hbase.util.Constants
import org.apache.hadoop.hbase.client._

import scala.collection.JavaConverters._
/**
 * Created by dream on 7/11/14.
 */
object puttest {
  //implicit formats = DefaultFormats
  def main(args: Array[String]) {

    val tablename = Constants.dataTable



    val tb = new HTable(Constants.conf,tablename)
    //val tbutil = new HTableUtil()

    tb.setAutoFlush(false)
    tb.setWriteBufferSize(10*1024*1024)
    var a: Int = 0
    while (a < 20000){
      a += 1
      val plist = EventFactory.rand(1000)
      if(a%10 == 0) println(a*1000)
      //tb.put(plist.asJava)
      HTableUtil.bucketRsPut(tb,plist.asJava)
    }



    Constants.sc.stop()

  }

}
