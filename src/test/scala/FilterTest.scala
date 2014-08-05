import byone.hbase.filter.CompareFilter.CompareOp
import byone.hbase.filter.{BinaryPrefixComparator, BinaryComparator, RowFilter}
import byone.hbase.uid.{UniqueId, RandEvent}
import byone.hbase.utils.{DatePoint, ScanCovert, Conf}
import java.lang.String
import org.apache.hadoop.hbase.client.{Result, HTable, Scan}
import org.apache.hadoop.hbase.filter.FilterList
import org.apache.hadoop.hbase.{Cell, HRegionInfo}
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import scala._
import scala.collection.JavaConverters._

/**
 * Created by dream on 7/31/14.
 */
object FilterTest {

  def ScanToString = (scan : Scan) => new ScanCovert().coverToScan(scan)

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
    //val keys = tb.getRegionLocations.navigableKeySet()

    val uid = new UniqueId
    uid.readToCache("hdfs://master1.dream:9000/spark/eventuid.txt")
    val range: List[String] = List("05/08/2014 15:15:17","05/08/2014 15:15:22")
    val startTs =  DatePoint.toTs(range(0))
    val stopTs = DatePoint.toTs(range(1))
    val pre = DatePoint.Int2Byte(16,1)
    val startRow = pre ++ startTs
    val stoptRow = pre ++ stopTs

    val ts = DatePoint.toTs("05/08/2014 15:15:19")


    val sn = new Scan(startRow,stoptRow)
    sn.setCacheBlocks(true)
    sn.setCaching(10000)
    //sn.setReversed(true)

    val items = List("collectorId", "eventType", "relayDevIpAddr")
    items.foreach(item =>sn.addColumn("d".getBytes,item.getBytes))
    val ents = uid.getCached

    ents(0).foreach(x=>print(x+","))
    println()
    val fl =new FilterList(FilterList.Operator.MUST_PASS_ONE)
    val rowfilter = new RowFilter(CompareOp.EQUAL,new BinaryPrefixComparator(pre ++ ts,0))
    fl.addFilter(rowfilter)
    sn.setFilter(fl)
    val ss = tb.getScanner(sn)
    var count =0
    for(res:Result <- ss.asScala){
      for(kv:Cell <- res.rawCells())
        print(new String(kv.getQualifier) +"-> "+new String(kv.getValue)+ ", ")
      println()
      count += 1
    }

    ss.close()
    // ret

    println("count:" + count)


    println("put to table successfully.")




  }
}
