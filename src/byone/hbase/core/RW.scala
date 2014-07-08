package byone.hbase.core

import byone.hbase.utils.ScanCovert
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.{HBaseConfiguration, Cell}
import org.apache.hadoop.hbase.filter.FilterList
import org.apache.spark.SparkContext
import org.apache.spark.rdd.{RDD, HadoopRDD, NewHadoopRDD}
import scala.collection.JavaConverters._
import SparkContext._
import org.apache.spark._


/**
 * Created by dream on 7/7/14.
 */
class RW(table : String,conf : Configuration,sc : SparkContext) {
  private val tb = new HTable(conf,table)

  def add(row : String, fc : String, col : String, vl : String) {
    val pt = new Put(row.getBytes)
    pt.add(fc.getBytes,col.getBytes,vl.getBytes)
    tb.put(pt)
    println("put " + row +" to table " + table + " successfully.")
  }

  //put rows
  //def adds()

  //get row
  def get(row : String) : String = {
    var s=""
    val gt = new Get(row.getBytes)
    require(tb.exists(gt))
    val res = tb.get(gt)
    for(kv:Cell <- res.rawCells())
      s = new String(kv.getQualifier) +"=" + new String(kv.getValue)
    s
  }

  def get(row : String, col : String) : String = {
    var s=""
    val gt = new Get(row.getBytes)
    //gt.addColumn("d".getBytes,col.getBytes)
    require(tb.exists(gt))

    //require(gt.isCheckExistenceOnly)
    val res = tb.get(gt)
    for(kv:Cell <- res.rawCells())
      s = new String(kv.getValue)
    s
  }

  def scan(flist : FilterList,bklist : Array[String]) : ResultScanner = {
    val scan = new Scan()
    scan.setFilter(flist)
    tb.getScanner(scan)
  }

  //def group()


}

object RW {

  def ScanToString(scan : Scan) : String = new ScanCovert(scan).coverToScan()



}