package byone.hbase.core

import org.apache.hadoop.hbase.{Cell, HColumnDescriptor, HTableDescriptor}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.conf.Configuration
import java.lang.String
import byone.hbase.utils.Conf


/**
 * Created by dream on 7/7/14.
 */
class Man extends java.io.Serializable {
  private val table = "log_data"
  //private val admin = new HBaseAdmin(conf)
  // create usual table
  def create(table : String, familys : Array[String]) {
    val admin = new HBaseAdmin(Conf.conf)
    if(admin.tableExists(table))
      println("table '" + table + "' already exists")
    else
    {
      val tableDesc : HTableDescriptor = new HTableDescriptor(table)
      for(fc <- familys)
        tableDesc.addFamily(new HColumnDescriptor(fc))
      admin.createTable(tableDesc)
      println("create table: '" +table + "' successfully.")
    }
  }

  //delete table
  def delete(table : String) {
    val admin = new HBaseAdmin(Conf.conf)
    if(!admin.tableExists(table))
      println("table: '" + table + "' does not exists")
    else
    {
      admin.disableTable(table)
      admin.deleteTable(table)
      println("delete table: " + table + " successfully.")
    }
  }

  def add(row : String, fc : String, col : String, vl : String,tab:String = "") {
    val tablename: String = if(tab.isEmpty) table else tab
    val tb = new HTable(Conf.conf,tablename)
    val pt = new Put(row.getBytes)
    pt.add(fc.getBytes,col.getBytes,vl.getBytes)
    tb.put(pt)
    println("put " + row +" to table " + tablename + " successfully.")
  }

  def adds(){}

  //get row
  def get(row : String,tab:String = "") : String = {
    val tablename: String = if(tab.isEmpty) table else tab
    val tb = new HTable(Conf.conf,tablename)
    var s=""
    val gt = new Get(row.getBytes)
    val res = tb.get(gt)
    require(!res.isEmpty)
    for(kv: Cell<- res.rawCells())
      s += new String(kv.getQualifier) +"=" + new String(kv.getValue)
    s
  }

  def getV(row : String, col : String,tab:String = "") : String = {
    val tablename: String = if(tab.isEmpty) table else tab
    val tb = new HTable(Conf.conf,tablename)
    var s=""
    val gt = new Get(row.getBytes)
    gt.addColumn("d".getBytes,col.getBytes)
    val res = tb.get(gt)
    require(!res.isEmpty)
    for(kv:Cell <- res.rawCells())
      s = new String(kv.getValue)
    s
  }


}

object Man {
  //val conf =
  //def create(table : String) = new Manage(conf).create(table)
}