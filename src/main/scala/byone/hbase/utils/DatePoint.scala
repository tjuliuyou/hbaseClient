package byone.hbase.utils

import java.text.SimpleDateFormat
import scala.collection.mutable.Map
import byone.hbase.uid.UniqueId

/**
 * Created by dream on 7/7/14.
 */
object DatePoint {
  def toTs(date : String) : String = {
    val df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    val time =df.parse(date).getTime
    (Long.MaxValue/1000000-time).toHexString
  }

}
