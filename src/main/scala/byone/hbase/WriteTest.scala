package byone.hbase

import byone.hbase.util.{RandEvent, Constants}
import org.apache.hadoop.hbase.client.{HTableUtil, HTable}
import scala.collection.JavaConverters._
/**
 * Created by liuyou on 14/11/14.
 */
object WriteTest {

  def main(args: Array[String]) {
    val tablename = Constants.dataTable

    val tb = new HTable(Constants.conf, tablename)
    //val tbutil = new HTableUtil()

    tb.setAutoFlush(false, false)
    tb.setWriteBufferSize(10 * 1024 * 1024)
    var a: Int = 0
    while (a < 2) {
      a += 1
      val plist = RandEvent.rand(200)
      if (a % 10 == 0) println(a * 1000)
      //tb.put(plist.asJava)
      HTableUtil.bucketRsPut(tb, plist.asJava)
    }
    Constants.sc.stop()
  }

}
