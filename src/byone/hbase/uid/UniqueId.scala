package byone.hbase.uid

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.{ResultScanner, Scan, HTable}
import org.apache.hadoop.hbase.Cell

import org.apache.spark._
import scala.collection.JavaConverters._
import SparkContext._
import byone.hbase.core.RW

/**
 * Created by dream on 7/7/14.
 */
class UniqueId(conf : Configuration,sc : SparkContext) {
  private val cached = scala.collection.mutable.Map[String, String]()
  private val tb = new HTable(conf,"uid")
  val scan = new Scan()
  val ss : ResultScanner = tb.getScanner(scan)
  val rw = new RW("uid",conf,sc)
  def getName(id : String) : String = {
    if(cached.contains(id))
      cached(id)
    else
    {
      val name = rw.get(id,"name")
      cached += (name->id)
      name
    }
  }
  def getId(name : String) : String = {
    if(cached.contains(name))
      cached(name)
    else
    {
      val uid = rw.get(name,"id")
      cached += (uid->name)
      uid
    }

  }
  def readToCache (file : String) {
    val txtFile =sc.textFile(file)
    val txtFileMap = txtFile.map({lines =>
      val ev = lines.split(",")
      (ev(0),ev(1))
    }
    )
    txtFileMap.collect().foreach{case (a,b) =>cached +=(a->b) }

  }
  def Insert(name : String) =
    cached.foreach{case(a,b) =>
      rw.add(b,"d","name",a)
      rw.add(a,"d","id",b)}
}

object UniqueId {

  def getId(name : String) : String = {
  ""
  }

}