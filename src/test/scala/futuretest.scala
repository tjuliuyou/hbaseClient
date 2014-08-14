import byone.hbase.uid.UniqueId
import byone.hbase.util.{DatePoint, Constants}
import com.twitter.util.{Future, Promise}
import com.twitter.conversions.time._
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.{HBaseConfiguration, Cell}
import org.apache.hadoop.hbase.client.{Result, Scan, HTable}
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import scala.collection.JavaConverters._
/**
 * Created by dream on 14-8-12.
 */
object futuretest {
  val tablename ="log_data"
  val tb = new HTable(Constants.conf,tablename)
  val range: List[String] = List("08/08/2014 14:45:53","08/08/2014 14:45:54")
  val startTs =  DatePoint.toTs(range(0))
  val stopTs = DatePoint.toTs(range(1))
  val group = "d"
  def scanList: Seq[Scan] = {

    for(x <- 0 to 8) yield {
      val pre = DatePoint.Int2Byte(x,1)
      val startRow = pre ++ startTs
      val stoptRow = pre ++ stopTs

      val sn = new Scan(startRow,stoptRow)
      sn.setCacheBlocks(true)

      val items = List("collectorId", "eventType")
      items.foreach(item =>sn.addColumn("d".getBytes,item.getBytes))
      sn
    }
  }

  def get(sn: Scan) = {

    val ss = tb.getScanner(sn)
    val y = for(res:Result <- ss.asScala) yield {
      val x = for(kv:Cell <- res.rawCells()) yield
        (new String(kv.getQualifier) -> new String(kv.getValue))
      group -> x.toMap
    }
    ss.close()
    y.toMap
  }


  def printEvent(keyvalue: Map[String,Map[String, String]]) {
    keyvalue.foreach(println)
    println("count: "+keyvalue.size)
  }

  def main(args: Array[String]) {

//    val flist = for(sn <- scanList) yield {
//      Future(get(sn))
//    }
//
//    val Mer = Future.collect(flist)
//    for(f <- flist) {
//      printEvent(f.get())
//    }
//    Mer.onSuccess(println)


    val rdd = hbaseRDD(scanList(0))

   val xx = rdd.map(DatePoint.gpBy)

    xx.collect().foreach(println)
  }


  def hbaseRDD(scan: Scan) = {
    val tablename = Constants.tablename
    Constants.conf.set(TableInputFormat.INPUT_TABLE, tablename)
    val conf = HBaseConfiguration.create(Constants.conf)
    conf.set(TableInputFormat.SCAN,DatePoint.ScanToString(scan))
    val hBaseRDD = Constants.sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    hBaseRDD
  }
}
