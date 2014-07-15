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


    case class Args(Range: List[String]
                    ,Items: List[String]
                    ,Events: List[String]
                    ,Filter:  String
                    ,Groupby: List[String]
                    ,Aggres: List[List[String]])

    implicit val formats = net.liftweb.json.DefaultFormats
    val source = scala.io.Source.fromFile("test/test.json").mkString
    println(source)
    val m = parse(source)
    val testlist: List[Args] = m.children.map(_.extract[Args])
    testlist.foreach(x => println(x))
  }
}
