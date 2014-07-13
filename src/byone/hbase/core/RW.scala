package byone.hbase.core

import byone.hbase.utils.{Conf, DatePoint, ScanCovert}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.{KeyValue, HBaseConfiguration, Cell}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.{RDD, HadoopRDD, NewHadoopRDD}
import scala.collection.JavaConverters._
import SparkContext._
import org.apache.spark._
import scala.collection.mutable.Map
import byone.hbase.uid.UniqueId
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat


/**
 * Created by dream on 7/7/14.
 */
class RW(table : String) extends java.io.Serializable {

  val tablename = Conf.tablename

  private val uid = new UniqueId

  def ScanToString = (scan : Scan) => new ScanCovert(scan).coverToScan()
  /**
   *  get Scan list for scan
   */
  def getScan = (cdn: Map[String,Vector[String]]) => {
    require(cdn.contains("range"))
    //val sl =
    val area = DatePoint.getRowArea(cdn("range"),cdn("event"),uid)
    val f1 = cdn("filter")(0)
    //val fl = FilterParser.singleParser("collectorId","<","10004")
    val fl = FilterParser.singleParser(f1)
    val sl = area.map{rows =>
      val sn = new Scan(rows._1.getBytes,rows._2.getBytes)

      sn.setFilter(fl)
      if(!cdn("back").isEmpty)
        cdn("back").foreach(dis =>sn.addColumn("d".getBytes,dis.getBytes))
      sn
    }
    Vector() ++ sl //thans iterater to vector
  }

  /**
   *  map raw hbase date to (string,string) by grouplist
   */
  def gpBy = (raw: (ImmutableBytesWritable, Result), gp: Set[String]) => {
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
  def getRDD = (sl: Vector[Scan], gp: Set[String])=>{
    var ret: RDD[(String, Map[String,String])] = Conf.sc.emptyRDD
    for (scan <- sl)  {
      val rdd =gethbaseRDD(scan).map(x =>gpBy(x,gp))
      rdd.collect()
      ret = ret ++ rdd
    }
    ret
  }



//  /**
//   *  map raw hbase date to (string,string) by grouplist
//   */
//  def gpBy(raw: (ImmutableBytesWritable, Result), gp: Set[String]): (String,Map[String,String]) = {
//    val retmap = Map[String, String]()
//    var ky = ""
//    for(kv:Cell<- raw._2.rawCells())
//    {
//      val key = new String(kv.getQualifier)
//      val value = new String(kv.getValue)
//      if(gp.contains(key)) {
//        ky = value
//      }
//      else
//        retmap += (key->value)
//    }
//    (ky,retmap)
//  }
//
//  /**
//   *  get base hbase RDD with one Scan
//   */
//  def gethbaseRDD(scan: Scan): RDD[(ImmutableBytesWritable, Result)] = {
//    Conf.conf.set(TableInputFormat.INPUT_TABLE, tablename)
//    Conf.conf.set(TableInputFormat.SCAN,ScanToString(scan))
//    val hBaseRDD = Conf.sc.newAPIHadoopRDD(Conf.conf, classOf[TableInputFormat],
//      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
//      classOf[org.apache.hadoop.hbase.client.Result])
//    hBaseRDD
//  }
//
//  def MergeRDD(sl: Vector[Scan]) : RDD[(ImmutableBytesWritable, Result)] = {
//    var ret: RDD[(ImmutableBytesWritable, Result)] = Conf.sc.emptyRDD
//    for (scan <- sl)  {
//      val rdd =gethbaseRDD(scan)
//      rdd.collect()
//      ret = ret ++ rdd
//    }
//    ret
//  }
//
//  /**
//   *  get merged hbase RDD
//   */
//  def getRDD(sl: Vector[Scan], gp: Set[String]): RDD[(String, Map[String,String])] = {
//    var ret: RDD[(String, Map[String,String])] = Conf.sc.emptyRDD
//    for (scan <- sl)  {
//      val rdd =gethbaseRDD(scan).map(x =>gpBy(x,gp))
//      rdd.collect()
//      ret = ret ++ rdd
//    }
//    ret
//  }

}

object RW {
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