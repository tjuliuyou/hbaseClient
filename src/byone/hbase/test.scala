package byone.hbase

import byone.hbase.core.{Man}
import java.lang.String
import net.liftweb.json._
import net.liftweb.json.JsonParser._
/**
 * Created by dream on 7/11/14.
 */
object test {

  def main(args: Array[String]){
//    val pf = new ParseFilter()
//    val x = pf.parseFilterString("PrefixFilter ('P') AND (SingleColumnValueFilter ('d','id',<,'binary:0005'))")
//    val s = new Scan
//    s.setFilter(x)
//    val man = new Man
//    man.scanV(s,"uid").foreach(println)
//    Conf.sc.stop()
//    implicit formats = DefaultFormats
//    case class Args(testitem: String)
    val source = scala.io.Source.fromFile("test/test.json").mkString
    println(source)
   // val m = source.extract[Args]

  }
}
