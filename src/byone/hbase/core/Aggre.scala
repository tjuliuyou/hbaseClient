package byone.hbase.core

import scala.collection.mutable.Map
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext._
/**
 * Created by dream on 7/10/14.
 */
object Aggre {

  def avg(rdd : RDD[(String, Map[String,String])],agrs: Vector[String]): RDD[(String,Map[String,(Double)])] = {
    def PreAggre(event: (String, Map[String,String]), args: Vector[String]): (String,Map[String, (Double, Int)]) ={
      val retmap = Map[String, (Double,Int)]()
      args.foreach(ar => {
        if(event._2.contains(ar))
          retmap += (ar->(event._2(ar).toDouble,1))
      })
      val line = (event._1,retmap)
      line
    }

    def AggreRDD(x:Map[String, (Double,Int)],y:Map[String, (Double,Int)]): Map[String, (Double,Int)] = {
      val ret = Map[String, (Double,Int)]()
      x.foreach(subx =>{
        if(y.contains(subx._1)) {
          val sum = subx._2._1 + y(subx._1)._1
          val count = subx._2._2 + y(subx._1)._2
          ret += (subx._1->(sum,count))
        }
        else
          ret += (subx._1->subx._2)
      })
      y.foreach(suby => {
        if (!x.contains(suby._1)) {
          ret += (suby._1->suby._2)
        }
      })
      ret
    }

    def avg1(sum: Map[String,(Double,Int)]):Map[String,(Double)] = {
      val ret = Map[String,(Double)]()
      sum.foreach(x => ret += (x._1->(x._2._1/x._2._2)))
      ret
    }
    val pre = rdd.map(x =>PreAggre(x,agrs))
    val ret = pre.reduceByKey((x,y) => AggreRDD(x,y)).mapValues(avg1)
    ret
  }

}
