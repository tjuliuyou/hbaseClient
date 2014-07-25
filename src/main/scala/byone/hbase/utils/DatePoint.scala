package byone.hbase.utils

import java.text.SimpleDateFormat
import scala.collection.mutable.Map
import byone.hbase.uid.UniqueId

/**
 * Created by dream on 7/7/14.
 */
object DatePoint {

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

  def toTs(date : String) : Array[Byte] = {
    val df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    val time =df.parse(date).getTime
    num2Byte(Long.MaxValue-time)
  }
}
