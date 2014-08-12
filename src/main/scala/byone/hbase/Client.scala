package byone.hbase

import byone.hbase.core.{RwRDD, Aggre}
import byone.hbase.utils.{Args, Constants}
import net.liftweb.json.JsonParser._

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

    val thistest = testlist(9)
    val rw = new RwRDD(Constants.tablename)
    val hbaseRDD =rw.futureGet(thistest)
    // if group args is empty print raw rdd using  group 'd'
    if(thistest.Groupby.isEmpty){

     // hbaseRDD.collect().foreach(x =>println(x._2))
      println("hbaseRDD count: " + hbaseRDD.count())
    }
    else
    {
      //if aggregate args is empty print raw rdd with group args
      if(thistest.Aggres.isEmpty){
        //hbaseRDD.collect().foreach(println)
        println("hbaseRDD count: " + hbaseRDD.count())
      }
      else
      {  //using aggregate args to aggre RDD then sort it
        val ag = new Aggre(hbaseRDD,thistest.Aggres)
        val com = ag.doAggre()
//        com.collect().sortBy(x=>x._1)
//        .foreach(println)
        println("e count: " + com.count())
      }
    }
    Constants.sc.stop()
  }
}
