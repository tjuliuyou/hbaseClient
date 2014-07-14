package byone.hbase

import byone.hbase.core.{Man, FilterParser}
import java.lang.String

import byone.hbase.utils.Conf
import org.apache.hadoop.hbase.client.Scan
import org.apache.hadoop.hbase.filter.KeyOnlyFilter
import byone.hbase.uid.UniqueId

/**
 * Created by dream on 7/11/14.
 */
object test {

  def main(args: Array[String]){
    val uid = new UniqueId
    uid.ids.foreach(println)
  }
}
