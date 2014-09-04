package byone.hbase.uid

import byone.hbase.core.Table
import byone.hbase.util.{Constants, DatePoint}
import com.twitter.util.LruMap
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.filter.KeyOnlyFilter
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._

/**
 * Created by dream on 7/7/14.
 */
class UniqueId extends java.io.Serializable {

  private val logger = LoggerFactory.getLogger(classOf[UniqueId])
  private val serialVersionUID = 6529685098267757680L
  private val tableName = Constants.uidTable

  private val cached = new LruMap[Array[Byte], Array[Byte]](100)

  private val uidTable = Table(tableName)

  def toId(event : String) : Array[Byte] =
    convert(event.getBytes,Constants.uidfamily(0))

  def toName(id : Int) : Array[Byte] =
    convert(DatePoint.Int2Byte(id),Constants.uidfamily(1))

  /**
   * All uids stored in Hbase
   * @return
   */
  def ids : Seq[Array[Byte]] = {
    var ret: Seq[String] = List.empty
    val tb = new HTable(Constants.conf,tableName)
    val scan = new Scan()
    val fl = new KeyOnlyFilter ()
    scan.setFilter(fl)
    val ss = tb.getScanner(scan)
    val retdata = for(res:Result <- ss.asScala) yield {
      res.getRow
    }
    ss.close()
    tb.close()
    retdata.toSeq.filter(checker)
  }

  /**
   * Get cached uid list
   * @return
   */
  def getCached: List[Array[Byte]] = {
    val iter = for(x <- cached) yield {
      x._2
    }
    iter.toList
  }

  /**
   * Read uid/event type file to cached
   * @param file
   */
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

  /**
   * insert uid / event type to hbase
   * @param name uid or event
   */
  def insert(name : String) =
    cached.foreach{ case(a,b) =>
      uidTable.put(b,"name","name",a)
      uidTable.put(a,"id","id",b)
    }

  /**
   * Uid/event Converter
   * @param in event or uid
   * @return
   */
  private def convert(in: Array[Byte],flag: String) : Array[Byte] = {
    if(cached.contains(in))
      cached(in)
    else
    {
      val out = uidTable.get(in,flag)
      if(out== null){
        logger.error("Can not find "+new String(in)+" in uidTable or cache.")
      }
        else{
        cached(in) = out
        cached(out) = in
      }
      out
    }

  }

  /**
   * Uid checker using length
   * @return
   */
  private def checker = (in : Array[Byte]) => {
    in.length match {
      case 3 => true
      case _ => false
    }
  }
}
