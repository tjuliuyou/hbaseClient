import byone.hbase.uid.RandEvent
import byone.hbase.utils.{DatePoint, ScanCovert, Conf}
import java.lang.String
import org.apache.hadoop.hbase.client.{HTable, Scan}
import org.apache.hadoop.hbase.HRegionInfo
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import scala._
import scala.collection.JavaConverters._

/**
 * Created by dream on 7/31/14.
 */
object test3 {

  def ScanToString = (scan : Scan) => new ScanCovert().coverToScan(scan)
  def count ={


    val range: List[String] = List("31/07/2014 14:30:00","31/07/2014 17:39:10")

    val startTs =  DatePoint.toTs(range(0)) //add -1 mill seconds
    val stopTs = DatePoint.toTs(range(1))    //add 1 mill seconds
    print("stopTs: ")
    stopTs.foreach(x=>print(x.toInt + ","))
    println(" ")

    val ret = for(num <- 1 to 16) yield {
        val pre = DatePoint.Int2Byte(num*256/16,Conf.PRELENGTH)
        val pre2 = DatePoint.Int2Byte((num+1)*256/16,Conf.PRELENGTH)
        ( pre ++ startTs) -> (pre2 ++ stopTs)
      }

    val tablename ="log_data"
    val tb = new HTable(Conf.conf,tablename)
    val keys = tb.getRegionLocations.navigableKeySet()


    val starkey = keys.first().getStartKey //DatePoint.Int2Byte(0,1) //++ startTs
    val endkey = keys.first().getEndKey//DatePoint.Int2Byte(1,1) ++ stopTs

    val pair = for(k:HRegionInfo <- keys.asScala) yield {
//      if(k.getStartKey.isEmpty)
//        k.getStartKey -> k.getEndKey
//      if(k.getEndKey.isEmpty)
//        k.getStartKey -> k.getEndKey
//      else {
        val ktmep = k.getEndKey
        val temp = ktmep(0) - 1
        println("temp= "+ temp)
        val pre = DatePoint.Int2Byte(temp,1)
        pre.foreach(x=>print(x.toInt + ","))
        k.getStartKey -> (DatePoint.Int2Byte(temp,1) ++ stopTs)
  //    }
    }

    println(" -------------")
    starkey.foreach(x=>print(x + ","))
    print("     ")
    endkey.foreach(x=>print(x.toInt + ","))
    println(" ")

    var counts: Long = 0
    for(p <- pair) {
      val sn = new Scan(p._1,p._2)
      println(" -------------")
      p._1.foreach(x=>print(x + ","))
      print("     ")
      p._2.foreach(x=>print(x.toInt + ","))
      println(" ")
      sn.setCacheBlocks(false)
      sn.setCaching(10000)
      sn.setReversed(true)
      Conf.conf.set(TableInputFormat.INPUT_TABLE, Conf.tablename)
      Conf.conf.set(TableInputFormat.SCAN,ScanToString(sn))
      val hbaseRDD = Conf.sc.newAPIHadoopRDD(Conf.conf, classOf[TableInputFormat],
        classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
        classOf[org.apache.hadoop.hbase.client.Result])
      val tmp = hbaseRDD.count()
      counts += tmp
      println("hbaseRDD count: " + tmp)
    }
    println("counts: " + counts)
  }

  def totolCount = {
    val sn = new Scan()
    sn.setCacheBlocks(false)
    sn.setCaching(10000)
    sn.setReversed(true)
    Conf.conf.set(TableInputFormat.INPUT_TABLE, Conf.tablename)
    Conf.conf.set(TableInputFormat.SCAN,ScanToString(sn))
    val hbaseRDD = Conf.sc.newAPIHadoopRDD(Conf.conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    val tmp = hbaseRDD.count()
    println("hbaseRDD count: " + tmp)
  }

  def main(args: Array[String]) {
    val tablename ="log_data"
    val tb = new HTable(Conf.conf,tablename)
    val keys = tb.getRegionLocations.navigableKeySet()


    val keysseq = for(k: HRegionInfo <- keys.asScala) yield {
      k.getStartKey.foreach(x=>print(x + ","))
      print("    ")
      k.getEndKey.foreach(x=>print(x.toInt + ","))
      println()
      (k.getStartKey,k.getEndKey)
    }


    count


  }
}
