package byone.hbase.util

import byone.hbase.core.Table

/**
 * Created by liuyou on 14/11/5.
 */
object TableTest {

  def main(args: Array[String]) {
    val tablename = "test1"
    val familys1 = Seq("d")
    val familys2 = Seq("d1","d2")

    val tb = Table(tablename)
//    tb.create(familys1)
//    Thread.sleep(10000)
//    tb.delete
//    Thread.sleep(10000)
    tb.create(familys1,1,9,9)
    //Thread.sleep(10000)
    //tb.delete

  }
}
