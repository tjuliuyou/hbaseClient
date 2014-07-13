package byone.hbase.uid

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.{ResultScanner, Scan, HTable}
import org.apache.hadoop.hbase.Cell

import org.apache.spark._
import scala.collection.JavaConverters._
import SparkContext._
import byone.hbase.core.{Man, RW}
import byone.hbase.utils.Conf

/**
 * Created by dream on 7/7/14.
 */
class UniqueId extends java.io.Serializable {
  private val cached = scala.collection.mutable.Map[String, String]()
  val man = new Man
  def getName(id : String) : String = {
    if(cached.contains(id))
      cached(id)
    else
    {
      val name = man.getV(id,"name","uid")
      cached += (name->id)
      name
    }
  }
  def getId(name : String) : String = {
    if(cached.contains(name))
      cached(name)
    else
    {
      val uid = man.getV(name,"id","uid")
      cached += (uid->name)
      uid
    }

  }
  def readToCache (file : String) {
    val txtFile =Conf.sc.textFile(file)
    val txtFileMap = txtFile.map({lines =>
      val ev = lines.split(",")
      (ev(0),ev(1))
    }
    )
    txtFileMap.collect().foreach{case (a,b) =>cached +=(a->b) }

  }
  def Insert(name : String) =
    cached.foreach{ case(a,b) =>
      man.add(b,"d","name",a,"uid")
      man.add(a,"d","id",b,"uid")
    }
}
