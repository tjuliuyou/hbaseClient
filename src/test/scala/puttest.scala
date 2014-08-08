import byone.hbase.uid.{RandEvent, EventFactory, UniqueId}
import byone.hbase.utils.{ScanCovert, Constants}
import java.lang.String
import net.liftweb.json.Formats
import net.liftweb.json.JsonParser.parse
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.regionserver.BloomType
import org.apache.hadoop.hbase.{Cell, HColumnDescriptor, HTableDescriptor}
import scala.collection.JavaConverters._
/**
 * Created by dream on 7/11/14.
 */
object puttest {
  //implicit formats = DefaultFormats
  def main(args: Array[String]) {

    val tablename = Constants.tablename



    val tb = new HTable(Constants.conf,tablename)
    //val tbutil = new HTableUtil()

    tb.setAutoFlush(false)
    tb.setWriteBufferSize(10*1024*1024)
    var a: Int = 0
    while (a < 1000){
      a += 1
      val plist = EventFactory.rand(1000)
      if(a%10 == 0) println(a*1000)
      //tb.put(plist.asJava)
      HTableUtil.bucketRsPut(tb,plist.asJava)
    }



    Constants.sc.stop()

  }

}
