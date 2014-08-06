package byone.hbase.core

import org.apache.hadoop.hbase.{Cell, HColumnDescriptor, HTableDescriptor}
import org.apache.hadoop.hbase.client._
import java.lang.String
import byone.hbase.utils.{DatePoint, Conf}
import scala.collection.JavaConverters._
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm
import org.apache.hadoop.hbase.regionserver.BloomType
import scala.math.pow

/**
 * Created by dream on 7/7/14.
 */
class Table extends java.io.Serializable {
  private val tablename = Conf.tablename

  // create usual table
  def create(familys : Array[String],tab : String) {
    val admin = new HBaseAdmin(Conf.conf)
    if(admin.tableExists(tab))
      println("table '" + tab + "' already exists")
    else
    {
      val tableDesc : HTableDescriptor = new HTableDescriptor(tab)
      for(fc <- familys)
        tableDesc.addFamily(new HColumnDescriptor(fc))
      admin.createTable(tableDesc)
      println("create table: '" +tab + "' successfully.")
    }
  }

  // create  table with regions
  def create(familys : Array[String], startkey: Int, stopkey: Int, num: Int, tab : String) {
    val admin = new HBaseAdmin(Conf.conf)
    if(admin.tableExists(tab))
      println("table '" + tab + "' already exists")
    else
    {
      val desc : HTableDescriptor = new HTableDescriptor(tab)
      for(fc <- familys){
        val hdes: HColumnDescriptor = new HColumnDescriptor(fc)
        hdes.setInMemory(true)
        hdes.setMaxVersions(1)
        hdes.setCompressionType(Algorithm.SNAPPY)
        hdes.setBloomFilterType(BloomType.ROW)
        desc.addFamily(hdes)
      }
      admin.createTable(desc,getSplits(startkey,stopkey,num))
      println("create table: '" +tab + "' successfully.")
    }
  }


  //delete table
  def delete(tab : String  = tablename) {
    val admin = new HBaseAdmin(Conf.conf)
    if(!admin.tableExists(tab))
      println("table: '" + tab + "' does not exists")
    else
    {
      admin.disableTable(tab)
      admin.deleteTable(tab)
      println("delete table: " + tab + " successfully.")
    }
  }

  def add(row : String, fc : String, col : String, vl : String,tab:String = tablename) {

    val tb = new HTable(Conf.conf,tab)
    val pt = new Put(row.getBytes)
    pt.add(fc.getBytes,col.getBytes,vl.getBytes)
    tb.put(pt)
    println("put " + row +" to table " + tab + " successfully.")
  }

  def mapToPut(cols: Map[String, String], row: Array[Byte]): Put = {
    val put = new Put(row)
    put.setWriteToWAL(false)
    val fc = "d".getBytes
    cols.foreach(x => put.add(fc, x._1.getBytes, x._2.getBytes))
    put
  }


//  def adds(row: String,kvs: Map[String, String],fc: String = "d", tab: String = tablename) {
//    val tb = new HTable(Conf.conf,tab)
//    val put = mapToPut(kvs,row.getBytes)
//    tb.put(pl.toList.asJava)
//    println("put " + row +" to table " + tab + " successfully.")
//  }

  def batAdd(puts: List[Put], tab:String = tablename) {
    //val cf = Conf.conf.set
    val tb = new HTable(Conf.conf,tab)
    tb.setAutoFlush(false,true)

  }
  //get row
  def get(row : String,tab:String = tablename) : String = {
    val tb = new HTable(Conf.conf,tab)
    var s=""
    val gt = new Get(row.getBytes)
    val res = tb.get(gt)
    require(!res.isEmpty)
    for(kv: Cell<- res.rawCells())
      s += new String(kv.getQualifier) +"=" + new String(kv.getValue)
    s
  }

  def getSplits(num: Int, pre: Int): Array[Array[Byte]] ={
    val stop = pow(256,pre)
    getSplits(0,stop.toInt,num)
  }

  def getSplits(startkey: Int, stopkey: Int, num: Int): Array[Array[Byte]] ={
    val range = stopkey - startkey
    val rangeIncrement = range/num
    val ret =for(i <- 0 until  num) yield {
      val key = startkey + rangeIncrement*i
      DatePoint.Int2Byte(key,Conf.PRELENGTH)
     }
    ret.toArray
  }

  def getV(row : String, col : String,tab:String = tablename) : Array[Byte] = {
    val tb = new HTable(Conf.conf,tab)
    val gt = new Get(row.getBytes)
    var s = Array[Byte]()
    gt.addColumn("d".getBytes,col.getBytes)
    val res = tb.get(gt)
    require(!res.isEmpty)
    for(kv:Cell <- res.rawCells())
    { s ++= kv.getValue }
    s

  }

  def scanV(scan: Scan,tab:String = tablename):Set[String] = {
    var ret: Set[String] = Set.empty
    val tb = new HTable(Conf.conf,tab)
    val ss = tb.getScanner(scan)
    for(res:Result <- ss.asScala)
      for(kv:Cell <- res.rawCells())
        ret += (new String(kv.getRow))
    ss.close()
    ret
  }

}
