package byone.hbase.core

import org.apache.hadoop.hbase.{Cell, HColumnDescriptor, HTableDescriptor}
import org.apache.hadoop.hbase.client._
import java.lang.String
import byone.hbase.utils.Conf
import scala.collection.JavaConverters._

/**
 * Created by dream on 7/7/14.
 */
class Table extends java.io.Serializable {
  private val tablename = "log_data"

  // create usual table
  def create(tablename : String, familys : Array[String]) {
    val admin = new HBaseAdmin(Conf.conf)
    if(admin.tableExists(tablename))
      println("table '" + tablename + "' already exists")
    else
    {
      val tableDesc : HTableDescriptor = new HTableDescriptor(tablename)
      for(fc <- familys)
        tableDesc.addFamily(new HColumnDescriptor(fc))
      admin.createTable(tableDesc)
      println("create table: '" +tablename + "' successfully.")
    }
  }

  //delete table
  def delete(tablename : String) {
    val admin = new HBaseAdmin(Conf.conf)
    if(!admin.tableExists(tablename))
      println("table: '" + tablename + "' does not exists")
    else
    {
      admin.disableTable(tablename)
      admin.deleteTable(tablename)
      println("delete table: " + tablename + " successfully.")
    }
  }

  def add(row : String, fc : String, col : String, vl : String,tab:String = tablename) {

    val tb = new HTable(Conf.conf,tab)
    val pt = new Put(row.getBytes)
    pt.add(fc.getBytes,col.getBytes,vl.getBytes)
    tb.put(pt)
    println("put " + row +" to table " + tab + " successfully.")
  }

//  def adds(row: String,kvs: Map[String, String],fc: String = "d", tab: String = tablename) {
//    val tb = new HTable(Conf.conf,tab)
//    val pl = for (kv <- kvs) yield {
//      val pt = new Put(row.getBytes)
//      pt.add(fc.getBytes,kv._1.getBytes,kv._2.getBytes)
//    }
//    pl
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
