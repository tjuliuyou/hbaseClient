package byone.hbase.core

import org.apache.hadoop.hbase.{HColumnDescriptor, HTableDescriptor}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.conf.Configuration

/**
 * Created by dream on 7/7/14.
 */
class Man (conf : Configuration) {

  private val admin = new HBaseAdmin(conf)
  // create usual table
  def create(table : String, familys : Array[String]) {
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
    if(!admin.tableExists(table))
      println("table: '" + table + "' does not exists")
    else
    {
      admin.disableTable(table)
      admin.deleteTable(table)
      println("delete table: " + table + " successfully.")
    }
  }
}

object Man {
  //val conf =
  //def create(table : String) = new Manage(conf).create(table)
}