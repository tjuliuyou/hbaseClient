package byone.hbase

import byone.hbase.core.{RwRDD, Aggre}
import byone.hbase.utils.{Args, Conf}
import scala.collection.mutable.Map
import net.liftweb.json.JsonParser._

/**
 * Created by dream on 7/7/14.
 */
object Client {

  def main(args: Array[String]) {

    implicit val formats = net.liftweb.json.DefaultFormats
    val source = scala.io.Source.fromFile("test/test.json").mkString
    //println(source)
    val m = parse(source)
    val testlist: List[Args] = m.children.map(_.extract[Args])
    testlist.foreach(x => println(x))

    // this test
    val thistest = testlist(5)

    val rw = new RwRDD(Conf.tablename)
    val hbaseRDD =rw.get(thistest)

    if(thistest.Groupby.isEmpty){

      hbaseRDD.collect().foreach(x =>println(x._2))
      println("hbaseRDD count: " + hbaseRDD.count())
    }
    else
    {
      if(thistest.Aggres.isEmpty){
        hbaseRDD.collect().foreach(println)
        println("hbaseRDD count: " + hbaseRDD.count())
      }
      else
      {
        val ag = new Aggre(hbaseRDD)
        val cond = thistest.Aggres(0)
        val ar = cond.drop(1)
        val tm = ag.exec(cond.head)(ar)
        println("aggre: " + tm.count)
        tm.collect().foreach(println)
        val sort =tm.collect().sortBy(r => r._2.values.map{case x=> -x})
        sort.foreach(println)
      }
    }

  Conf.sc.stop()
  }
}
