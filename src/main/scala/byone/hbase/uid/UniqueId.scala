package byone.hbase.uid

import byone.hbase.core.Insert
import byone.hbase.util.{Constants, DatePoint}
import com.twitter.util.LruMap
import org.apache.hadoop.hbase.{HColumnDescriptor, HTableDescriptor, Cell}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.filter.KeyOnlyFilter
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._

/**
 * Created by dream on 7/7/14.
 */
class UniqueId extends java.io.Serializable {

  private val logger = LoggerFactory.getLogger(classOf[UniqueId])

  private val cached = new LruMap[Array[Byte], Array[Byte]](100)

  private val tableName = Constants.uidTable

  def get(row : Array[Byte], col : String) : Array[Byte] = {
    val tb = new HTable(Constants.conf,tableName)
    val gt = new Get(row)
    gt.addColumn(col.getBytes,col.getBytes)
    val res = tb.get(gt).getNoVersionMap
    require(!res.isEmpty)
    val resValue = res.firstEntry().getValue.asScala
    resValue(col.getBytes)
  }


  def toId(event : String) : Array[Byte] = {
    val et = event.getBytes
    if(cached.contains(et))
      cached(et)
    else
    {
      val uid = get(et,"id")
      cached += (et->uid)
      uid
    }
  }

  def toName(id : Array[Byte]) : Array[Byte] = {

    if(cached.contains(id))
      cached(id)
    else
    {
      val name = get(id,"name")
      cached += (id->name)
      name
    }
  }

  def ids : Seq[String] = {
    var ret: Seq[String] = List.empty
    val tb = new HTable(Constants.conf,tableName)
    val scan = new Scan()
    val fl = new KeyOnlyFilter ()
    scan.setFilter(fl)
    val ss = tb.getScanner(scan)
    for(res:Result <- ss.asScala)
      for(kv:Cell <- res.rawCells()) {
        val id = new String(kv.getRow)
        if(id.length.equals(Constants.UIDLENGTH))
        ret = ret :+ id
      }
    ss.close()
    ret.sorted
  }

  def getCached: List[Array[Byte]] = {
    val iter = for(x <- cached) yield {
      x._2
    }
    iter.toList
  }


  def readToCache (file : String) {
    val txtFile = Constants.sc.textFile(file)
    val txtFileMap = txtFile.map({lines =>
      val ev = lines.split(",")
      (ev(0),ev(1))
    }
    )
    txtFileMap.collect().foreach{case (event,id) =>{
      cached(event.getBytes) = DatePoint.Int2Byte(id.toInt)
      cached(DatePoint.Int2Byte(id.toInt)) = event.getBytes
    } }
  }

  def insert(name : String) =
    cached.foreach{ case(a,b) =>
      add(b,"name","name",a)
      add(a,"id","id",b)
    }


  def add(row : Array[Byte], fc : String, col : String, vl : Array[Byte]) {

    val tb = new HTable(Constants.conf,tableName)
    val pt = new Put(row)
    pt.add(fc.getBytes,col.getBytes,vl)
    tb.put(pt)
    logger.info("put " + new String(row) +" to table " + tableName + " successfully.")
  }

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

}
