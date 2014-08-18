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

  private val logger = LoggerFactory.getLogger(classOf[Table])

  //private val tablename = Constants.dataTable

  // create usual table
  def create(familys : Seq[String]) {
    val admin = new HBaseAdmin(Constants.conf)
    if(admin.tableExists(tableName))
      logger.error("table '" + tableName + "' already exists")
    else
    {
      val tableDesc : HTableDescriptor = new HTableDescriptor(tableName)
      for(fc <- familys)
        tableDesc.addFamily(new HColumnDescriptor(fc))
      admin.createTable(tableDesc)
      logger.info("create table: '" +tableName + "' successfully.")
    }
  }

  // create  table with regions
  def create(tab : String,familys : Array[String], startkey: Int, stopkey: Int, num: Int) {
    val admin = new HBaseAdmin(Constants.conf)
    if(admin.tableExists(tab))
      logger.error("table '" + tab + "' already exists")
    else
    {
      val desc : HTableDescriptor = new HTableDescriptor(tab)
      for(fc <- familys){
        val hdes: HColumnDescriptor = new HColumnDescriptor(fc)
        hdes.setInMemory(true)
        hdes.setMaxVersions(1)
        hdes.setCompressionType(Algorithm.SNAPPY)
        hdes.setBloomFilterType(BloomType.ROW)
        desc.addFamily(hdes)
      }
      admin.createTable(desc,getSplits(startkey,stopkey,num))
      logger.info("create table: '" +tab + "' successfully.")
    }
  }


  //delete table
  def delete {
    val admin = new HBaseAdmin(Constants.conf)
    if(!admin.tableExists(tableName))
      logger.error("table: '" + tableName + "' does not exists")
    else
    {
      admin.disableTable(tableName)
      admin.deleteTable(tableName)
      logger.info("delete table: " + tableName + " successfully.")
    }
  }

  def put(row : Array[Byte], fc : String, col : String, vl : Array[Byte]) {

    val tb = new HTable(Constants.conf,tableName)
    val pt = new Put(row)
    pt.add(fc.getBytes,col.getBytes,vl)
    tb.put(pt)
    logger.info("put " + new String(row) +" to table " + tableName + " successfully.")
  }

  def mapToPut(cols: Map[String, String], row: Array[Byte]): Put = {
    val put = new Put(row)
    put.setWriteToWAL(false)
    val fc = "d".getBytes
    cols.foreach(x => put.add(fc, x._1.getBytes, x._2.getBytes))
    put
  }

  def puts(tb: HTable,pts: List[Put]) = HTableUtil.bucketRsPut(tb,pts.asJava)

  //get row
  def get(row : String): String = {
    val tb = new HTable(Constants.conf,tableName)
    var s=""
    val gt = new Get(row.getBytes)
    val res = tb.get(gt)
    require(!res.isEmpty)
    for(kv: Cell<- res.rawCells())
      s += new String(kv.getQualifier) +"=" + new String(kv.getValue)
    s
  }


  def get(row : Array[Byte], col : String) : Array[Byte] = {
    val tb = new HTable(Constants.conf,tableName)
    val gt = new Get(row)
    gt.addColumn(col.getBytes,col.getBytes)
    val res = tb.get(gt).getNoVersionMap
    require(!res.isEmpty)
    val resValue = res.firstEntry().getValue.asScala
    resValue(col.getBytes)
  }


  private def getSplits(startkey: Int, stopkey: Int, num: Int): Array[Array[Byte]] ={
    val range = stopkey - startkey
    val rangeIncrement = range/(num-1)
    val ret =for(i <- 0 until (num-1)) yield {
      val key = startkey + rangeIncrement*i
      DatePoint.Int2Byte(key,Constants.PRELENGTH)
    }
    ret.toArray
  }
}
