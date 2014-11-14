package byone.hbase

import byone.hbase.core.task.ReadTask

/**
 * Created by liuyou on 14/11/14.
 */
object ReadTest {
  def main(args: Array[String]) {

    val queryArgs = scala.io.Source.fromFile("src/main/resources/test.json").mkString

    println(queryArgs)

    val readTask = new ReadTask(queryArgs)

    readTask.start

    val data = readTask.get
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
