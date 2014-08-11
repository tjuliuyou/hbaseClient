package byone.hbase

import byone.hbase.core.{RwRDD, Aggre}
import byone.hbase.utils.{Args, Constants}
import net.liftweb.json.JsonParser._
import org.apache.spark.SparkContext

/**
 * Created by dream on 7/7/14.
 */
object Client {

  def main(args: Array[String]) {

    // read test.json to class testlist
    implicit val formats = net.liftweb.json.DefaultFormats
    val source = scala.io.Source.fromFile("src/main/resources/test.json").mkString
    val m = parse(source)
    val testlist: List[Args] = m.children.map(_.extract[Args])

    // using one of testlist

    val thistest = testlist(8)
    val rw = new RwRDD(Constants.tablename)
    val hbaseRDD =rw.get(thistest)
    // if group args is empty print raw rdd using  group 'd'
    if(thistest.Groupby.isEmpty){

     // hbaseRDD.collect().foreach(x =>println(x._2))
      println("hbaseRDD count: " + hbaseRDD.count())
    }
    else
    {
      //if aggregate args is empty print raw rdd with group args
      if(thistest.Aggres.isEmpty){
        hbaseRDD.collect().foreach(println)
        println("hbaseRDD count: " + hbaseRDD.count())
      }
      else
      {  //using aggregate args to aggre RDD then sort it
        val ag = new Aggre(hbaseRDD,thistest.Aggres)
        val tm = ag.doAggre()
//        val cond = thistest.Aggres(0)
//        val ar = cond.drop(1)
//        val tm = ag.exec(cond.head)(ar)
//        //val sortrdd =tm.collect().sortBy(r => r._2.values.map{case x=> -x})
        val sortrdd =tm.collect().sortBy(x=>x._1)
        sortrdd.foreach(println)
        println("sorted count: " + tm.count())

      }
    }

    Constants.sc.stop()
  }
}
