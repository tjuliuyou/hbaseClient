package byone.hbase.core

import byone.hbase.util.{Constants, DatePoint}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm
import org.apache.hadoop.hbase.regionserver.BloomType
import org.apache.hadoop.hbase.{Cell, HColumnDescriptor, HTableDescriptor}
import org.slf4j.LoggerFactory
import scala.collection.JavaConverters._

/**
 * Created by dream on 7/7/14.
 */


class Table(tableName: String) extends java.io.Serializable {

  object Connect {
    val admin = new HBaseAdmin(Constants.conf)

    def close1 = admin.close()
  }

  private val logger = LoggerFactory.getLogger(classOf[Table])
  private val serialVersionUID = 6529685098267757691L
  //private val tablename = Constants.dataTable

  def close = Connect.close1

  // create usual table
  def create(familys: Seq[String], force: Boolean = false) {

    def doCreate = {
      val tableDesc: HTableDescriptor = new HTableDescriptor(tableName)
      for (fc <- familys)
        tableDesc.addFamily(new HColumnDescriptor(fc))
      Connect.admin.createTable(tableDesc)
      logger.info("create table: '" + tableName + "' successfully.")
    }
    if (Connect.admin.tableExists(tableName))
      if (force) {
        logger.info("Force to recreate table.")
        this.delete
        doCreate
      } else {
        logger.error("table '" + tableName + "' already exists.")
      }
    else
      doCreate

  }

  // create  table with regions
  def create(familys: Seq[String], startkey: Int, stopkey: Int, num: Int) {

    if (Connect.admin.tableExists(tableName))
      logger.error("table '" + tableName + "' already exists")
    else {
      val desc: HTableDescriptor = new HTableDescriptor(tableName)
      for (fc <- familys) {
        val hdes: HColumnDescriptor = new HColumnDescriptor(fc)
        hdes.setInMemory(true)
        hdes.setMaxVersions(1)
        hdes.setCompressionType(Algorithm.SNAPPY)
        hdes.setBloomFilterType(BloomType.ROW)
        desc.addFamily(hdes)
      }
      Connect.admin.createTable(desc, getSplits(startkey, stopkey, num))

      logger.info("create table: '" + tableName + "' successfully.")
    }
    //admin.close()
  }


  //delete table
  def delete: Boolean = {

    val success =
      if (!Connect.admin.tableExists(tableName)) {
        logger.error("table: '" + tableName + "' does not exists.")
        false
      }
      else {
        Connect.admin.disableTable(tableName)
        Connect.admin.deleteTable(tableName)
        logger.info("delete table: " + tableName + " successfully.")
        true
      }

    success
  }

  def put(row: Array[Byte], fc: String, col: String, vl: Array[Byte]) {

    val tb = new HTable(Constants.conf, tableName)
    val pt = new Put(row)
    pt.add(fc.getBytes, col.getBytes, vl)
    tb.put(pt)
    tb.close()
    logger.info("put " + new String(row) + " to table " + tableName + " successfully.")
  }

  def puts(htable: HTable, putlist: List[Put]) = HTableUtil.bucketRsPut(htable, putlist.asJava)

  //get row
  def get(row: String): String = {
    val tb = new HTable(Constants.conf, tableName)
    var s = ""
    val gt = new Get(row.getBytes)
    val res = tb.get(gt)
    require(!res.isEmpty)
    tb.close()
    for (kv: Cell <- res.rawCells())
      s += new String(kv.getQualifier) + "=" + new String(kv.getValue)
    s
  }


  def get(row: Array[Byte], col: String): Array[Byte] = {
    val tb = new HTable(Constants.conf, tableName)
    val gt = new Get(row)
    gt.addColumn(col.getBytes, col.getBytes)
    val res = tb.get(gt).getNoVersionMap

    val resValue = if (res == null) {
      logger.warn("Can not find Column: " + col + " in row: " + new String(row)
        + ". now return null")
      null
    }
    else {
      val temp = res.firstEntry().getValue.asScala
      temp(col.getBytes)
    }
    tb.close()
    resValue
  }


  private def getSplits(startkey: Int, stopkey: Int, num: Int): Array[Array[Byte]] = {
    val range = stopkey - startkey
    val rangeIncrement = range / (num - 1)
    val ret = for (i <- 0 until (num - 1)) yield {
      val key = startkey + rangeIncrement * i
      DatePoint.Int2Byte(key, Constants.PRELENGTH)
    }
    ret.toArray
  }
}

object Table {

  def mapToPut(cols: Map[String, String], row: Array[Byte], family: Array[Byte]): Put = {
    val put = new Put(row)
    put.setWriteToWAL(false)
    cols.foreach(x => put.add(family, x._1.getBytes, x._2.getBytes))
    put
  }

  def apply(tableName: String) = new Table(tableName)

}