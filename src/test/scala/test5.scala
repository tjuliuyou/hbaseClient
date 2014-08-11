import byone.hbase.filter.{BinaryComparator, NumberComparator}

import scala.util.Random

/**
 * Created by liuyou on 2014/8/2.
 */
object test5 {

  def Int2Byte(num: Int,max: Int = 3):Array[Byte] = {
    val ret = for(i <- 0 until max) yield {
      (num >>>(8*(max-i-1)) & 0xff).toByte
    }
    ret.toArray
  }

  def num2Byte(num: Long,max: Int = 8):Array[Byte] = {
    val ret = for(i <- 0 until max) yield {
      (num >>>(8*(max-i-1)) & 0xff).toByte
    }
    ret.toArray
  }

  def alist: Iterable[Array[Byte]] = {
    for(i <- 0 to 100) yield {
      val ts = System.currentTimeMillis()
      val pre = Random.nextInt(256)
      Int2Byte(pre,1) ++ num2Byte(ts/1000,4) ++ Int2Byte(Random.nextInt(13)+1) ++ num2Byte(ts%1000,3)
    }
  }

  def main(args: Array[String]) {
    val comp = new NumberComparator("20".getBytes)
    val comp2 = new BinaryComparator("20".getBytes)
    for(i <- 0 to 50) {
      val c = Random.nextInt(200).toString
      print("current number: " + c)
      val b = c.getBytes
      val x = comp.compareTo(b,0,b.length)
      print(" number result: " + x)
      comp.compareTo(b,0,b.length)
      print(" -----------binary result: " + comp2.compareTo(b,0,b.length))
      println()
    }



  }
}
