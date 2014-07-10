package byone.hbase.utils

import java.text.SimpleDateFormat
import scala.collection.mutable.Map
import byone.hbase.uid.UniqueId

/**
 * Created by dream on 7/7/14.
 */
object DatePoint {
  def dateToTs(date : String) : String = {
    val df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    val time =df.parse(date).getTime
    (Long.MaxValue/1000000-time).toHexString
  }
  /**
   *  get (startrow,stoprow) pairs
   */
  def getRowArea(range: Vector[String], event: Vector[String], uid:UniqueId): Map[String,String] = {
    val area = Map[String, String]()
    if(event.isEmpty)
      area
    else {
      for(pre <- event)
      {
        val p = uid.getId(pre)
        area += ((p + dateToTs(range(1)) + "0000")->
          ( p + dateToTs(range(0)) + "0064"))
      }
      area
    }
  }

}
