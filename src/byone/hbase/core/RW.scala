package byone.hbase.core

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.Cell
import org.apache.hadoop.hbase.filter.FilterList
import org.apache.spark.rdd.{NewHadoopRDD, RDD}

/**
 * Created by dream on 7/7/14.
 */
class RW(conf : Configuration,table : String) {
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
    var s =""
    val res = tb.get(new Get(row.getBytes))
    for(kv:Cell <- res.rawCells())
      s = new String(kv.getQualifier) +"=" + new String(kv.getValue)
    s
  }

  def scan(flist : FilterList,bklist : Array[String]) : ResultScanner = {
    val scan = new Scan()
    scan.setFilter(flist)
    tb.getScanner(scan)
  }

}

object RW {
  def scanToRDD():RDD[NewHadoopRDD] = {

  }
}