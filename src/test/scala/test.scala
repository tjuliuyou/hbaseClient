import byone.hbase.uid.{RandEvent, EventFactory, UniqueId}
import byone.hbase.utils.{ScanCovert, Conf}
import java.lang.String
import net.liftweb.json.Formats
import net.liftweb.json.JsonParser.parse
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.{Cell, HColumnDescriptor, HTableDescriptor}
import scala.collection.JavaConverters._
/**
 * Created by dream on 7/11/14.
 */
object test {
  //implicit formats = DefaultFormats
  def main(args: Array[String]) {
//    val pf = new ParseFilter()
//    val x = pf.parseFilterString("PrefixFilter ('P') AND (SingleColumnValueFilter ('d','id',<,'binary:0005'))")
//    val s = new Scan
//    s.setFilter(x)
//    val man = new Man
//    man.scanV(s,"uid").foreach(println)
//    Conf.sc.stop()


      //Conf.conf.g
//   val uid = new UniqueId
//    uid.readToCache("src/main/resources/test/eventuid.txt")
//    uid.Insert("uid")
    val tablename ="log_data"
    val admin = new HBaseAdmin(Conf.conf)
    if(admin.tableExists(tablename)){
      admin.disableTable(tablename)
      admin.deleteTable(tablename)
      println("drop table: '" +tablename + "' successfully.")
    }

      val desc : HTableDescriptor = new HTableDescriptor(tablename)
      val hdes: HColumnDescriptor = new HColumnDescriptor("d".getBytes)
      hdes.setInMemory(true)
      hdes.setMaxVersions(1)
      hdes.setCompressionType(Algorithm.SNAPPY)
      desc.addFamily(hdes)

      admin.createTable(desc)
      println("create table: '" +tablename + "' successfully.")


    val tb = new HTable(Conf.conf,tablename)


    tb.setAutoFlush(false,true)
    tb.setWriteBufferSize(10*1024*1024)
    var a: Int = 0
    while (true){
      a += 1
      val plist = EventFactory.rand(500)
      if(a%20 == 0) println(a*500)
      tb.put(plist.asJava)
    }



    Conf.sc.stop()

  }

}
