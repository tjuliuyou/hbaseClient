package byone.hbase.core

import scala.collection.mutable.Map
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext._
/**
 * Created by dream on 7/10/14.
 */
class Aggre(rdd : RDD[(String, Map[String,String])]) extends java.io.Serializable {

  private val raw = rdd
  def pre(event: (String, Map[String,String]), args: List[String]): (String,Map[String, (Double, Int)]) ={
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

  def merge= (x:Map[String, (Double,Int)],y:Map[String, (Double,Int)]) => {
    for(subx <- x ) yield {
      val sum = subx._2._1 + y(subx._1)._1
      val count = subx._2._2 + y(subx._1)._2
      (subx._1 ->(sum,count))
    }
  }

  def avg(args: List[String]): RDD[(String,Map[String,(Double)])] = {

    def calc(sum: Map[String,(Double,Int)]):Map[String,(Double)] = {
      for(x <- sum) yield {
        x._1-> {
          if(x._2._2.equals(0))
            0
          else
            x._2._1/x._2._2
        }
      }
    }
    val prerdd = raw.map(x =>pre(x,args))
    prerdd.reduceByKey((x,y) => merge(x,y)).mapValues(calc)
  }

  def max(args: List[String]): RDD[(String,Map[String,(Double)])] = {
    def calc = (max: Map[String,(Double,Int)]) => {
      for(x <- max) yield (x._1 -> x._2._1)
    }
    val prerdd = raw.map(x =>pre(x,args))
    prerdd.reduceByKey((x,y) => {
    val value = for(subx <- x ) yield {
      val sum = Math.max(subx._2._1, y(subx._1)._1)
      subx._1 ->(sum,1)
    }
    value}).mapValues{calc}
  }

  def exec(cond : String)(args: List[String]): RDD[(String,Map[String,(Double)])] = {
    cond match {
      case "avg" => avg(args)
      case "max" => max(args)
      case _     => {println("not ready");avg(args)}
    }
  }

}
