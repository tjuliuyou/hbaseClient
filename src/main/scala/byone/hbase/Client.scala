package byone.hbase

import byone.hbase.core.{Table, QueryArgs, Query}
import byone.hbase.util.{RandEvent, Constants}
import net.liftweb.json.JsonParser._
import org.apache.hadoop.hbase.client.{HTableUtil, HTable}
import scala.collection.JavaConverters._

/**
 * Created by dream on 14-8-13.
 */
object Client {


  def read(user: String) {
    // read test.json to class testlist
    implicit val formats = net.liftweb.json.DefaultFormats
    val source = scala.io.Source.fromFile("/home/dream/json.txt").mkString
    val m = parse(source)

    val testlist: Seq[QueryArgs] = m.children.map(_.extract[QueryArgs])

    // using one of testlist

    val currtest = testlist(0)
    val query = Query.create(currtest)

    val rdd = query.get()
    //val sortRdd = rdd.collect().sortBy(raw => raw._1)
   // rdd.collect().foreach(println)
    println("multi get count: " + rdd.count())

    //val raw = query.rawRdd()

    Query.close()

    Constants.sc.stop()
  }

  def create(user: String) {
    val tablename = Constants.dataTable
    val dataTable = new Table(tablename)
    dataTable.delete
    dataTable.create(Constants.dataFamily, Constants.STARTKEY, Constants.REGIONRANGE, Constants.REGIONNUM)

    dataTable.close
    println("create table: '" + tablename + "' successfully.")
  }

  def putData(user: String) {
    val tablename = Constants.dataTable

    val tb = new HTable(Constants.conf, tablename)
    //val tbutil = new HTableUtil()

    tb.setAutoFlush(false, false)
    tb.setWriteBufferSize(10 * 1024 * 1024)
    var a: Int = 0
    while (a < 100) {
      a += 1
      val plist = RandEvent.rand(1000)
      if (a % 10 == 0) println(a * 1000)
      //tb.put(plist.asJava)
      HTableUtil.bucketRsPut(tb, plist.asJava)
    }
    Constants.sc.stop()

  }

  def main(args: Array[String]) {
    val usage = "\r\nUsage: run <op> <usr>\r\n" +
      "Options:\r\n" +
      "\tcreate - Create table.\r\n" +
      "\tread - Read data for hbase.\r\n" +
      "\tinsert - Put random data to hbase.\r\n" +
      "\r\nUsers: cpp , java or scala\r\n"

    if (args.length != 2)
      println(usage)
    else
      args(0) match {
        case "read" => read(args(1))
        case "create" => read(args(1))
        case "putdata" => read(args(1))
        case _ => println(usage)
      }
  }

}
