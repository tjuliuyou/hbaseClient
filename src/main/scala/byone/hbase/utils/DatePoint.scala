package byone.hbase.utils

import java.text.SimpleDateFormat
import org.apache.hadoop.hbase.client.Scan

/**
 * Created by dream on 7/7/14.
 *
 * DataPoint holds a bunch of method used to covert value to other type
 */
object DatePoint {

  /**
   * Int2Byte covert int value to Array[Byte]
   * @param num int number
   * @param max Array[Byte] size default is 3
   * @return Array[Byte]
   */
  def Int2Byte(num: Int,max: Int = 3):Array[Byte] = {
    val ret = for(i <- 0 until max) yield {
      (num >>>(8*(max-i-1)) & 0xff).toByte
    }
    ret.toArray
  }

  /**
   * Num2Byte covert Long value to Array[Byte]
   * @param num Long number
   * @param max Array[Byte] size default is 8
   * @return Array[Byte]
   */
  def num2Byte(num: Long,max: Int = 8):Array[Byte] = {
    val ret = for(i <- 0 until max) yield {
      (num >>>(8*(max-i-1)) & 0xff).toByte
    }
    ret.toArray
  }

  /**
   * toTs covert date to timestamps
   * @param date format must be "dd/MM/yyyy HH:mm:ss"
   * @param delay reserved
   * @return timestamps Array[Byte]
   */
  def toTs(date : String, delay: Int = 0) : Array[Byte] = {
    val df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    val time =df.parse(date).getTime/1000
    num2Byte(time+delay,4)
  }

  /**
   * covert Scan to  Base64 encoded String {@see org.apache.hadoop.hbase.mapreduce
   * .TableMapReduceUtil#convertScanToString }
   * @return The scan saved in a Base64 encoded string.
   */
  def ScanToString = (scan : Scan) => new ScanCovert().coverToScan(scan)
}
