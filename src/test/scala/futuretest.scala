import byone.hbase.uid.UniqueId
import byone.hbase.utils.{DatePoint, Constants}
import com.twitter.util.{Future, Promise}
import com.twitter.conversions.time._
import org.apache.hadoop.hbase.Cell
import org.apache.hadoop.hbase.client.{Result, Scan, HTable}
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
    val f = new Promise[Int]
    val g = f map{res => res +10}
    f.setValue(1)
    g.get(1.second)
    g respond( res => println(res))

    val xFuture = Future(1)
    val yFuture = Future(4)

    for (
      x <- xFuture;
      y <- yFuture
    ){
      print(x + y)
    }
    val flist = for(sn <- scanList) yield {
      Future(get(sn))
    }

    val Mer = Future.collect(flist)
    for(f <- flist) {
      printEvent(f.get())
    }
    //Me
    Mer.onSuccess(println)

  }
}
