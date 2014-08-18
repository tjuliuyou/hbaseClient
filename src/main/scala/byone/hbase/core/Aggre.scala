package byone.hbase.core

import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext._
import scala.collection.immutable.Map
/**
 * Created by dream on 7/10/14.
 */

object Aggre {
  /**
   * pre covert each event: (String, Map[String,String]) to (String,Map[String, (Double, Int)]) by args
   * @param event raw event data
   * @param args
   * @return
   */
  def preMap(event: (String, Map[String,String]), args: Seq[String])
  : (String,Map[String, (Double, Int)]) ={
    //val retmap = scala.collection.mutable.Map[String, (Double,Int)]()
    val ret = args.map(ar => {
        if(event._2(ar).equals("null"))
          ar -> (0.0,0)
        else
          ar -> (event._2(ar).toDouble,1)
      })

    (event._1,ret.toMap)
  }

  /**
   * calc avg for a map
   * @param sum Map
   * @return avg Map
   */
  def doAvg(sum: Map[String,(Double,Int)]):Map[String,Double] = {
    for(x <- sum) yield {
      x._1-> {
        if(x._2._2.equals(0))
          0
        else
          x._2._1/x._2._2
      }
    }
  }


  /**
   * Aggregate rdd using default args by default constructions
   * @param ar aggregate args
   * @return
   */
  def doAggre(raw:RDD[(String,Map[String,String])],ar: Seq[(String,Seq[String])])
    :RDD[(String,Map[String,String])] = {
    val aggitems = {
      val ilist = for(agg <- ar) yield agg._2
      ilist.flatten
    }
    val prerdd = raw.map(x =>preMap(x,aggitems))
    prerdd.reduceByKey(Merge(ar))
      .mapValues(CalcValue(ar))
  }

  /**
   * Merge two keyValue to one
   * @param method What method will be used (avg, min, max ...)
   * @param lt left hand value to be merge
   * @param rt right hand value to be merge
   * @return merged value
   */
  private def doMerge(method: String)(lt: (Double,Int),rt: (Double,Int))
  : (Double,Int) = {
    method match {
      case "min" => (Math.min(rt._1, lt._1), 1)
      case "max" => (Math.max(rt._1, lt._1), 1)
      case "avg" => (rt._1+lt._1, rt._2+lt._2)
      case "count" => (0,rt._2+lt._2)
      case _ => (0,rt._2+lt._2)
    }
  }

  /**
   * Merge two events value(Map) to one using aggregate args
   * @param ar aggregate args
   * @param lhs left hand event to be merge
   * @param rhs right hand event to be merge
   * @return merged event
   */
  def Merge(ar: Seq[(String,Seq[String])])(lhs: Map[String,(Double,Int)],rhs: Map[String,(Double,Int)])
  :Map[String,(Double,Int)] = {
    val ret = for (args <- ar) yield {
      for(item <- args._2) yield {
        item->doMerge(args._1)(rhs(item),lhs(item))
      }
    }
    ret.flatten.toMap
  }

  /**
   * Calc the final value by aggregate args
   * @param ar aggregate args
   * @param orig orig value
   * @return
   */
  def CalcValue(ar: Seq[(String,Seq[String])])(orig: Map[String,(Double,Int)])
  : Map[String,String] ={

    val ret = for (args <- ar) yield {
      for(item <- args._2) yield {
        item->doCalc(args._1)(orig(item))
      }
    }
    ret.flatten.toMap
  }

  /**
   * Calc one record value and it count
   * @param method What method will be used (avg, min, max ...)
   * @param tuple input Tuple(value, count)
   * @return value
   */
  private def doCalc(method: String)(tuple: (Double,Int)):String ={
    method match {
      case "avg" => if(tuple._2 == 0) "0.0" else (tuple._1/tuple._2).toString
      case "count" => tuple._2.toString
      case _ => tuple._1.toString
    }
  }
}
