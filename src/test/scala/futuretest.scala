import byone.hbase.util.{Constants, Converter}
import org.apache.hadoop.hbase.client.{HTable, Result, Scan}
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.{Cell, HBaseConfiguration}

import scala.collection.JavaConverters._
/**
 * Created by dream on 14-8-12.
 */
object futuretest {
  val tablename ="log_data"
  val tb = new HTable(Constants.conf,tablename)
  val range: List[String] = List("05/09/2014 11:08:12","05/09/2014 11:08:15")
  val startTs =  Converter.toTs(range(0))
  val stopTs = Converter.toTs(range(1))
  val group = "d"
  def scanList: Seq[Scan] = {

    for(x <- 0 to 8) yield {
      val pre = Converter.Int2Byte(x,1)
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


  }


  def hbaseRDD(scan: Scan) = {
    val tablename = Constants.dataTable
    Constants.conf.set(TableInputFormat.INPUT_TABLE, tablename)
    val conf = HBaseConfiguration.create(Constants.conf)
    conf.set(TableInputFormat.SCAN,Converter.ScanToString(scan))
    val hBaseRDD = Constants.sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    hBaseRDD
  }
}
