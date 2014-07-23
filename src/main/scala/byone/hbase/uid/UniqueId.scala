package byone.hbase.uid

import scala.collection.JavaConverters._
import byone.hbase.core.Table
import byone.hbase.utils.Conf
import java.lang.String
import org.apache.hadoop.hbase.client.{Result, Scan, HTable}
import org.apache.hadoop.hbase.filter.KeyOnlyFilter
import org.apache.hadoop.hbase.Cell

/**
 * Created by dream on 7/7/14.
 */
class UniqueId extends java.io.Serializable {
  private val cached = scala.collection.mutable.Map[String, String]()
  val man = new Table
  def name(uid : String) : String = {
    if(cached.contains(uid))
      cached(uid)
    else
    {
      val name = man.getV(uid,"name","uid")
      cached += (name->uid)
      name
    }
  }
  def id(uname : String) : String = {
    if(cached.contains(uname))
      cached(uname)
    else
    {
      val uid = man.getV(uname,"id","uid")
      cached += (uid->uname)
      uid
    }
  }

  def ids : List[String] = {
    var ret: List[String] = List.empty
    val tb = new HTable(Conf.conf,"uid")
    val scan = new Scan()
    val fl = new KeyOnlyFilter ()
    scan.setFilter(fl)
    val ss = tb.getScanner(scan)
    for(res:Result <- ss.asScala)
      for(kv:Cell <- res.rawCells()) {
        val id = new String(kv.getRow)
        if(id.length.equals(Conf.UIDLENGTH))
        ret = ret :+ id
      }
    ss.close()
    ret.sorted
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
