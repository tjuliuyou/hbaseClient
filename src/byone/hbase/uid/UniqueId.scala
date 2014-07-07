package byone.hbase.uid

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.{ResultScanner, Scan, HTable}
import org.apache.hadoop.hbase.{Cell, KeyValue}
import scala.collection.JavaConverters._
/**
 * Created by dream on 7/7/14.
 */
class UniqueId(conf : Configuration) {
  private val cached = scala.collection.mutable.Map[String, String]()
  private val tb = new HTable(conf,"uid")
  val scan = new Scan()
  val ss : ResultScanner = tb.getScanner(scan)

  def getName(id : String) : String = {
    if(cached.contains(id))
      cached(id)
    else
      for(res <- ss.asScala)
        for(kv:Cell<- res.rawCells())
          if(id.equals(new String(kv.getRow)))
          {
            cached +=(id -> new String(kv.getValue))
            new String(kv.getValue)
          }
      else
        println("can not find uid.")
        null
  }
  def getId(name : String) : String = {""}
  def Insert(name : String) : String = {""}
}
