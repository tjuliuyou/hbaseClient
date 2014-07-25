package byone.hbase.core

import byone.hbase.utils.{Args, Conf, DatePoint,ScanCovert}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.Cell
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import scala.collection.mutable.Map
import byone.hbase.uid.UniqueId
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.filter.ParseFilter


/**
 * Created by dream on 7/7/14.
 */
class RwRDD(table : String) extends java.io.Serializable {

  private val tablename = Conf.tablename

  private val uid = new UniqueId
  uid.readToCache("src/main/resources/test/eventuid.txt")


  private def ScanToString = (scan : Scan) => new ScanCovert(scan).coverToScan()

  private def hbaseFilter(in:String) = new ParseFilter().parseFilterString(in)
  /**
   *  get (startrow,stoprow) pairs
   */
  private def rowArea = (range: List[String], event: List[String]) => {
    val startTs =  DatePoint.toTs(range(1)) ++ DatePoint.Int2Byte(0)
    val stopTs = DatePoint.toTs(range(0)) ++ DatePoint.Int2Byte(500)
    if(event.isEmpty){
      for(p <- uid.getCached) yield {
        (p ++ startTs)->( p ++ stopTs)
      }
    }
    else {
      for(pre <- event) yield
      {
        val p = uid.id(pre)
        (p ++ startTs)->( p ++ stopTs)
      }
    }
  }

  /**
   *  get Scan list for scan
   */
  def scanList = (args: Args) => {
    require(!args.Range.isEmpty)
    val area = rowArea(args.Range,args.Events)
    val sl = if(args.Filter.equals("null")){
     area.map{rows =>
       val sn = new Scan(rows._1,rows._2)
      if(!args.Items.isEmpty)
        args.Items.foreach(item =>sn.addColumn("d".getBytes,item.getBytes))
      sn
    }}else
    {
      val fl = hbaseFilter(args.Filter)
      area.map{rows =>
        val sn = new Scan(rows._1,rows._2)
        sn.setFilter(fl)
        if(!args.Items.isEmpty)
          args.Items.foreach(item =>sn.addColumn("d".getBytes,item.getBytes))
        sn
    }}
    Vector() ++ sl //thans iterater to vector
  }

  /**
   *  map raw hbase date to (string,string) by grouplist
   */
  def gpBy = (raw: (ImmutableBytesWritable, Result), gp: List[String]) => {
    val retmap = Map[String, String]()
    var ky = ""
    for(kv:Cell<- raw._2.rawCells())
    {
      val key = new String(kv.getQualifier)
      val value = new String(kv.getValue)
      if(gp.contains(key)) {
        ky = value
      }
      else
        retmap += (key->value)
    }
    (ky,retmap)
  }

  /**
   *  get base hbase RDD with one Scan
   */
  def gethbaseRDD = (scan: Scan) =>  {
    Conf.conf.set(TableInputFormat.INPUT_TABLE, tablename)
    Conf.conf.set(TableInputFormat.SCAN,ScanToString(scan))
    val hBaseRDD = Conf.sc.newAPIHadoopRDD(Conf.conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    hBaseRDD
  }

  /**
   *  get base hbase RDD with Scan list
   */
  def gethbaseRDDs = (scan: Scan) =>  {
    Conf.conf.set(TableInputFormat.INPUT_TABLE, tablename)
    Conf.conf.set(TableInputFormat.SCAN,ScanToString(scan))
    val hBaseRDD = Conf.sc.newAPIHadoopRDD(Conf.conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    hBaseRDD
  }

  /**
   *  get and merge hbase RDD
   */
  def get = (args: Args)=>{
    val sl = scanList(args)
    val gp = if(args.Groupby.isEmpty) List("d") else args.Groupby
    var ret: RDD[(String, Map[String,String])] = Conf.sc.emptyRDD
    for (scan <- sl)  {
      val rdd =gethbaseRDD(scan).map(x =>gpBy(x,gp))
      rdd.collect()
      ret = ret ++ rdd
    }
    ret
  }

}

