import byone.hbase.uid.{RandEvent, EventFactory, UniqueId}
import byone.hbase.utils.Conf
import java.lang.String
import net.liftweb.json.Formats
import net.liftweb.json.JsonParser.parse
import org.apache.hadoop.hbase.client.{HTable, Put, HBaseAdmin}
import org.apache.hadoop.hbase.{HColumnDescriptor, HTableDescriptor}
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
//
//
//      admin.disableTable(tablename)
//      admin.deleteTable(tablename)
//    }
//    else
//    {
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
//    }
//
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
    //EventFactory.toCache
    val data1 = RandEvent.data1
    val data2 = RandEvent.data2
    val data3 = RandEvent.data3
    val data4 = RandEvent.data4
    val data5 = RandEvent.data5
    val data6 = RandEvent.data6
    val data7 = RandEvent.data7
    
//    val put = new Put("row".getBytes)
//    put.add("d".getBytes,"a".getBytes,"ee".getBytes)
//    put.add("d".getBytes,"b".getBytes,"edd".getBytes)
    val p1 = RandEvent.toPut(data1,"row1")
    val p2 = RandEvent.toPut(data2,"row2")
    val p3 = RandEvent.toPut(data3,"row3")
    val p4 = RandEvent.toPut(data4,"row4")
    val p5 = RandEvent.toPut(data5,"row5")
    val p6 = RandEvent.toPut(data6,"row6")
    val p7 = RandEvent.toPut(data7,"row7")




    val plist = List(p1,p2,p3,p4,p5,p6,p7)
    tb.put(plist.asJava)

  }

}
