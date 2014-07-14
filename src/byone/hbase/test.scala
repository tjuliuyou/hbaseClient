package byone.hbase

import byone.hbase.core.{Man}
import java.lang.String
import net.liftweb.json.Formats
import net.liftweb.json.JsonParser.parse
/**
 * Created by dream on 7/11/14.
 */
object test {
  //implicit formats = DefaultFormats
  def main(args: Array[String]) {
//    val pf = new ParseFilter()
//    val x = pf.parseFilterString("PrefixFilter ('P') AND (SingleColumnValueFilter ('d','id',<,'binary:0005'))")
//    val s = new Scan
//    s.setFilter(x)
//    val man = new Man
//    man.scanV(s,"uid").foreach(println)
//    Conf.sc.stop()
    implicit val formats = net.liftweb.json.DefaultFormats
    //case class Range(rg: List[String])
    //case class Items(it: List[String])
    case class Agg(op: String, ar: List[String])
    //case class Events(et: List[String])
    //case class Groupby(gp:List[String])
    case class Args(Range: List[String]
                    ,Items: List[String]
                    ,Events: Option [List[String]]
                    ,Filter:  Option [String]
                    ,Groupby: Option [List[String]]
                    ,Aggres: Option[(String,List[String])])

    val source = scala.io.Source.fromFile("test/test.json").mkString
    println(source)
    val m = parse(source)
    val testlist: List[Args] = m.children.map(_.extract[Args])
    testlist.foreach(x => println(x))
  }
}
