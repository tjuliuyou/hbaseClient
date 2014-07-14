package byone.hbase

import byone.hbase.core.{Man}
import java.lang.String

import byone.hbase.utils.Conf
import org.apache.hadoop.hbase.client.Scan
import org.apache.hadoop.hbase.filter.{ParseFilter, KeyOnlyFilter}
import byone.hbase.uid.UniqueId

/**
 * Created by dream on 7/11/14.
 */
object test {

  def main(args: Array[String]){
    val pf = new ParseFilter()
    val x = pf.parseFilterString("PrefixFilter ('P') AND (SingleColumnValueFilter ('d','id',<,'binary:0005'))")
    val s = new Scan
    s.setFilter(x)
    val man = new Man
    man.scanV(s,"uid").foreach(println)
  }
}
