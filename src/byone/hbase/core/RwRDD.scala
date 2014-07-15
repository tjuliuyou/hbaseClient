package byone.hbase.core

import byone.hbase.utils.{Args, Conf, DatePoint, ScanCovert}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.Cell
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import scala.collection.JavaConverters._
import SparkContext._
import org.apache.spark._
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

  private def ScanToString = (scan : Scan) => new ScanCovert(scan).coverToScan()

  private def hbaseFilter(in:String) = new ParseFilter().parseFilterString(in)
  /**
   *  get (startrow,stoprow) pairs
   */
  private def rowArea(range: List[String], event: List[String]): Map[String,String] = {
    val area = Map[String, String]()
    val startTs =  DatePoint.toTs(range(1)) + "0000"
    val stopTs = DatePoint.toTs(range(0)) + "0064"
    if(event.isEmpty){
      uid.ids.foreach( p =>
        area += ((p + startTs)->( p + stopTs))
      )
    }
    else {
      for(pre <- event)
      {
        val p = uid.id(pre)
        area += ((p + startTs)->( p + stopTs))
      }
    }
    area
  }

  /**
   *  get Scan list for scan
   */
  def scanList = (args: Args) => {
    require(!args.Range.isEmpty)
    val fl = hbaseFilter(args.Filter)
    val area = rowArea(args.Range,args.Events)
    val sl = area.map{rows =>
      val sn = new Scan(rows._1.getBytes,rows._2.getBytes)
      sn.setFilter(fl)
      if(!args.Items.isEmpty)
        args.Items.foreach(dis =>sn.addColumn("d".getBytes,dis.getBytes))
      sn
    }
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

//  def MergeRDD(sl: Vector[Scan]) : RDD[(ImmutableBytesWritable, Result)] = {
//    var ret: RDD[(ImmutableBytesWritable, Result)] = sc.emptyRDD
//    for (scan <- sl)  {
//      val rdd =gethbaseRDD(scan)
//      rdd.collect()
//      ret = ret ++ rdd
//    }
//    ret
//  }

  /**
   *  get merged hbase RDD
   */
  //def get = (sl: Vector[Scan], gp: List[String])=>{
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


object RwRDD {
  def ScanToString(scan : Scan) : String = new ScanCovert(scan).coverToScan()
  val conf = Conf.conf
  val tablename = Conf.tablename
  val sc = Conf.sc

  /**
   *  map raw hbase date to (string,string) by grouplist
   */
  def gpBy(raw: (ImmutableBytesWritable, Result), gp: Set[String]): (String,Map[String,String]) = {
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
  def gethbaseRDD(scan: Scan): RDD[(ImmutableBytesWritable, Result)] = {
    conf.set(TableInputFormat.INPUT_TABLE, tablename)
    conf.set(TableInputFormat.SCAN,ScanToString(scan))
    val hBaseRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    hBaseRDD
  }

  def MergeRDD(sl: Vector[Scan]) : RDD[(ImmutableBytesWritable, Result)] = {
    var ret: RDD[(ImmutableBytesWritable, Result)] = sc.emptyRDD
    for (scan <- sl)  {
      val rdd =gethbaseRDD(scan)
      rdd.collect()
      ret = ret ++ rdd
    }
    ret
  }

  /**
   *  get merged hbase RDD
   */
  def getRDD(sl: Vector[Scan], gp: Set[String]): RDD[(String, Map[String,String])] = {
    var ret: RDD[(String, Map[String,String])] = sc.emptyRDD
    for (scan <- sl)  {
      val rdd =gethbaseRDD(scan).map(x =>gpBy(x,gp))
      rdd.collect()
      ret = ret ++ rdd
    }
    ret
  }

}