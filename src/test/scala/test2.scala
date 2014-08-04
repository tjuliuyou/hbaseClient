import byone.hbase.uid.{RandEvent, EventFactory, UniqueId}
import byone.hbase.utils.{ScanCovert, Conf}
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
object test2 {
  def ScanToString = (scan : Scan) => new ScanCovert().coverToScan(scan)

  def main(args: Array[String]) {


    val tablename ="test"
//        val admin = new HBaseAdmin(Conf.conf)
//        if(admin.tableExists(tablename)){
//          admin.disableTable(tablename)
//          admin.deleteTable(tablename)
//          println("drop table: '" +tablename + "' successfully.")
//        }
//
//          val desc : HTableDescriptor = new HTableDescriptor(tablename)
//          val hdes: HColumnDescriptor = new HColumnDescriptor("d".getBytes)
//          hdes.setInMemory(true)
//          hdes.setMaxVersions(1)
//          hdes.setCompressionType(Algorithm.SNAPPY)
//          hdes.setBloomFilterType(BloomType.ROW)
//          desc.addFamily(hdes)
//
//
//
//          admin.createTable(desc)
//
//          println("create table: '" +tablename + "' successfully.")

    val uid = new UniqueId
    uid.readToCache("hdfs://master1.dream:9000/spark/eventuid.txt")
    val tb = new HTable(Conf.conf,tablename)

    val a = (129).toByte
    val b = (230).toByte
    val c = (5).toByte
    val x = Array(a,b,c)
    val y =for(sub <- x) yield {
      print(sub+",")
      sub.toChar
    }
    println()
    val value = y.mkString

    println(x.mkString +"   ---   "+ value)
    println()
    val xx = value.toList
    val yy = x.mkString.toList
    xx.foreach(sx=>print(sx+"--"))
    yy.map(x=>x.toByte).foreach(print)
    val pt = new Put(value.getBytes)
   uid.getCached.foreach(x=>{
     val vle = x.mkString
     pt.add("d".getBytes,vle.getBytes,x)
     pt.add("d".getBytes,vle.getBytes,vle.getBytes)
   })



    //pt.add("d".getBytes,"d".getBytes,x)


   // tb.put(pt)
    println("put to table successfully.")



  }
}
