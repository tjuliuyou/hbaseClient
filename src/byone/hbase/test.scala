package byone.hbase

import byone.hbase.core.{Man, FilterParser}
import java.lang.String

import byone.hbase.utils.Conf

/**
 * Created by dream on 7/11/14.
 */
object test {
  private val cached = scala.collection.mutable.Map[String, String]()
    def readToCache (file : String) {
      val txtFile =Conf.sc.textFile(file)
      val txtFileMap = txtFile.map({lines =>
        val ev = lines.split(",")
        (ev(0),ev(1))
      }
      )
      txtFileMap.collect().foreach{case (a,b) =>cached +=(a->b) }

    }


  def main(args: Array[String]){
    val man = new Man
    val fc = Array("d")

    def Insert(name : String) =
      cached.foreach{ case(a,b) =>
        man.add(b,"d","name",a,"uid")
        man.add(a,"d","id",b,"uid")
      }

    man.create("uid",fc)
    readToCache("test/eventuid.txt")
    Insert("uid")
  }
}
