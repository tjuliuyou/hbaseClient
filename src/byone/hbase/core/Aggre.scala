package byone.hbase.core

import scala.collection.mutable.Map
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext._
/**
 * Created by dream on 7/10/14.
 */
class Aggre(rdd : RDD[(String, Map[String,String])]) extends java.io.Serializable {

  private val raw = rdd
  def PreAggre(event: (String, Map[String,String]), args: List[String]): (String,Map[String, (Double, Int)]) ={
    val retmap = Map[String, (Double,Int)]()
    args.foreach(ar => {
        retmap += (ar->{
          if(event._2.contains(ar))
            (event._2(ar).toDouble,1)
          else
            (0.0,0)
        })
    })
    val line = (event._1,retmap)
    line
  }

  def AggreRDD(x:Map[String, (Double,Int)],y:Map[String, (Double,Int)]): Map[String, (Double,Int)] = {
    val ret = Map[String, (Double,Int)]()
    for(subx <- x ) {
      val sum = subx._2._1 + y(subx._1)._1
      val count = subx._2._2 + y(subx._1)._2
      ret += (subx._1 ->(sum,count))
    }
//  x.foreach(subx =>{
//    if(y.contains(subx._1)) {
//      val sum = subx._2._1 + y(subx._1)._1
//      val count = subx._2._2 + y(subx._1)._2
//      ret += (subx._1->(sum,count))
//    }
//    else
//      ret += (subx._1->subx._2)
//  })
//  y.foreach(suby => {
//    if (!x.contains(suby._1)) {
//      ret += (suby._1->suby._2)
//    }
//  })
    ret
  }

  def avg(args: List[String]): RDD[(String,Map[String,(Double)])] = {

    def calc(sum: Map[String,(Double,Int)]):Map[String,(Double)] = {
      val ret = Map[String,(Double)]()
      sum.foreach(x => {
        ret += (x._1-> {
          if(x._2._2.equals(0))
            0
          else
            x._2._1/x._2._2
        })
      })
      ret
    }
    val pre = raw.map(x =>PreAggre(x,args))
    //pre.collect().foreach(println)
    //pre.reduceByKey((x,y) => AggreRDD(x,y)).collect().foreach(println)
    val ret = pre.reduceByKey((x,y) => AggreRDD(x,y)).mapValues(calc)
    ret
  }

  def exec(cond : String)(args: List[String]): RDD[(String,Map[String,(Double)])] = {
    cond match {
      case "avg" => avg(args)
      case _     => {println("not ready");avg(args)}
    }
  }

}
