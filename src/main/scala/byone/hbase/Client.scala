package byone.hbase

import byone.hbase.core.{QueryArgs, Query}
import byone.hbase.util.Constants
import net.liftweb.json.JsonParser._

/**
 * Created by dream on 14-8-13.
 */
object Client {

  def main(args: Array[String]) {

    // read test.json to class testlist
    implicit val formats = net.liftweb.json.DefaultFormats
    val source = scala.io.Source.fromFile("src/main/resources/test.json").mkString
    val m = parse(source)

    val testlist: Seq[QueryArgs] = m.children.map(_.extract[QueryArgs])

    // using one of testlist

    val thistest = testlist(0)
    val query = Query.create(thistest)

    val rdd = query.get()
    //val sortRdd = rdd.collect().sortBy(raw => raw._1)
    rdd.collect().foreach(println)
    println("multi get count: " + rdd.count())

    Query.close()

    Constants.sc.stop()
  }

}
