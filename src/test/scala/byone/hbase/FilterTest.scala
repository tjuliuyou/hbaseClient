package byone.hbase

import byone.hbase.uid.UniqueId
import byone.hbase.util.{Constants, Converter}
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{HTable, Scan}
import org.apache.hadoop.hbase.mapreduce.TableInputFormat

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
    Constants.conf.set(TableInputFormat.INPUT_TABLE, Constants.dataTable)
    Constants.conf.set(TableInputFormat.SCAN,ScanToString(sn))
    val hbaseRDD = Constants.sc.newAPIHadoopRDD(Constants.conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    val tmp = hbaseRDD.count()
    println("hbaseRDD count: " + tmp)
  }

  def main(args: Array[String]) {
    val tablename ="log_data"
    val tb = new HTable(Constants.conf,tablename)
    //val keys = tb.getRegionLocations.navigableKeySet()

    val uid = new UniqueId
    uid.readToCache("hdfs://master1.dream:9000/spark/eventuid.txt")
    val range: List[String] = List("08/08/2014 14:45:53","08/08/2014 14:45:54")
    val startTs =  Converter.toTs(range(0))
    val stopTs = Converter.toTs(range(1))
    val pre = Converter.Int2Byte(16,1)
    val pre2 = Converter.Int2Byte(19,1)
    val startRow = pre ++ startTs
    val stoptRow = pre2 ++ stopTs

    val ts = Converter.toTs("05/08/2014 15:15:19")


    val sn = new Scan(startRow,stoptRow)
    sn.setCacheBlocks(true)
    sn.setCaching(10000)
    //sn.setReversed(true)

    val items = List("collectorId", "eventType", "relayDevIpAddr")
    items.foreach(item =>sn.addColumn("d".getBytes,item.getBytes))
    val ents = uid.getCached

    ents(2).foreach(x=>print(x+","))
    println()

//    val rowfilter1: Filter = new RowFilter(CompareOp.EQUAL,new EventComparator(ents(0)))
//    val rowfilter2: Filter = new RowFilter(CompareOp.EQUAL,new EventComparator(ents(1)))
//    val rowfilter3: Filter = new RowFilter(CompareOp.EQUAL,new EventComparator(ents(2)))
//    val rowfilter4: Filter = new RowFilter(CompareOp.EQUAL,new EventComparator(ents(4)))
//    val rowfl = List(rowfilter1,rowfilter2,rowfilter3,rowfilter4)
//    val jrowfl = rowfl.asJava
//
//    val fl:Filter  =new FilterList(FilterList.Operator.MUST_PASS_ONE,jrowfl)
//    val flist  =new FilterList(FilterList.Operator.MUST_PASS_ALL)
//    val colfilter = new ByParseFilter().parseFilterString("SingleColumnValueFilter ('d','collectorId',<,'number:89')")
//    flist.addFilter(fl)
//    flist.addFilter(colfilter)
//    sn.setFilter(flist)
//    val ss = tb.getScanner(sn)
//    var count =0
//    for(res:Result <- ss.asScala){
//      for(kv:Cell <- res.rawCells())
//        print(new String(kv.getQualifier) +"-> "+new String(kv.getValue)+ ", ")
//      println()
//      count += 1
//    }
//
//    ss.close()
//    // ret
//
//    println("count:" + count)
//
//
//    println("put to table successfully.")


    val hRdd = hbaseRdd(sn)
    println(hRdd.count())

    Constants.sc.stop()
  }

  def hbaseRdd(scan: Scan) = {
    Constants.conf.set(TableInputFormat.INPUT_TABLE, Constants.dataTable)
    val conf = HBaseConfiguration.create(Constants.conf)
    conf.set(TableInputFormat.SCAN,Converter.ScanToString(scan))
    val hBaseRDD = Constants.sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    hBaseRDD
  }


}
