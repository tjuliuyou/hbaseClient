import byone.hbase.uid.{RandEvent, EventFactory, UniqueId}
import byone.hbase.utils.Conf
import java.lang.String
import net.liftweb.json.Formats
import net.liftweb.json.JsonParser.parse
import org.apache.hadoop.hbase.client._
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
//    val tablename ="log_data"
//    val admin = new HBaseAdmin(Conf.conf)
//    if(admin.tableExists(tablename)){
//      admin.disableTable(tablename)
//      admin.deleteTable(tablename)
//      println("drop table: '" +tablename + "' successfully.")
//    }
//
//      val desc : HTableDescriptor = new HTableDescriptor(tablename)
//      val hdes: HColumnDescriptor = new HColumnDescriptor("d".getBytes)
//      hdes.setInMemory(true)
//      hdes.setMaxVersions(1)
//
//      desc.addFamily(hdes)
//
//
//      admin.createTable(desc)
//      println("create table: '" +tablename + "' successfully.")


//    Conf.sc.stop()

//    def intToByte(num: Int): Array[Byte] ={
//      val byNum = new Array[Byte](4)
//     | for(i<- 0 to 4){
//        val offset = 32 - (i + 1)*8
//        byNum(i) =
//      }
//
//    }

    val tb = new HTable(Conf.conf,"log_data")

//    val a: Array[Byte] = Array(0.toByte, 0.toByte, 10.toByte, 6.toByte, -72.toByte, -103.toByte, -94.toByte, -114.toByte, 81.toByte,1.toByte)
//    val ts = Long.MaxValue - System.currentTimeMillis()
//    val row = RandEvent.Int2Byte(20) ++  RandEvent.num2Byte(ts)
//    println(row)
//    row.foreach(println)
//    val put = new Put(row)
//    put.add("d".getBytes,"d".getBytes,"d".getBytes)
//    tb.put(put)

    tb.setAutoFlush(false,true)
    tb.setWriteBufferSize(10*1024*1024)

    while (true){
      val plist = EventFactory.rand(500)
      tb.put(plist.asJava)
    }
//
//    val uid = new UniqueId
//
//    uid.readToCache("src/main/resources/test/eventuid.txt")
//    val x = uid.id("PH_DEV_MON_OSPF_NBR_STATUS")
//    x.foreach(println)
//    //x.foreach(println)
//
    val scan = new Scan()

    val ss = tb.getScanner(scan)
    for(res:Result <- ss.asScala)
      for(kv:Cell <- res.rawCells())
      {}
    ss.close()



  }

}
