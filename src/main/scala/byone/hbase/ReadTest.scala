package byone.hbase

import byone.hbase.core.QueryArgs
import byone.hbase.core.task.ReadTask
import byone.hbase.util.Constants
import net.liftweb.json.JsonParser._
import org.apache.hadoop.hbase.client.HTable

/**
 * Created by liuyou on 14/11/14.
 */
object ReadTest {
  def main(args: Array[String]) {
    val constConnect = new HTable(Constants.conf, Constants.dataTable)
    val queryArgs = scala.io.Source.fromFile("src/main/resources/test.json").mkString

    val read = new ReadTask(queryArgs)

    val data = read.start
    println(data.count())

    constConnect.close()
//    val readTask = new ReadTask(queryArgs)
//
//    readTask.start
//
//    val data = readTask.get
    // using one of testlist

    //val range = List(Converter.Int2Byte(0,4),Converter.num2Byte(ts/1000,4))
    //val protorange = range.map(ByteString.copyFrom).asJava
    //val query = Query.create(currtest)

    // val rdd = query.get()
    //val sortRdd = rdd.collect().sortBy(raw => raw._1)
    // rdd.collect().foreach(println)
    //println("multi get count: " + rdd.count())

    //val raw = query.rawRdd()

    //Query.close()

    //Constants.sc.stop()
  }

}
