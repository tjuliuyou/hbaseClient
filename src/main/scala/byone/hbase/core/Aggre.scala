package byone.hbase.core

import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext._
import scala.collection.immutable.Map
/**
 * Created by dream on 7/10/14.
 */
class Aggre(rdd : RDD[(String, Map[String,String])], agargs: List[List[String]])
  extends java.io.Serializable {

  //paser input args to aggregate args
  private val aggargs = for(ar <- agargs) yield {
    val cond = ar.head
    val item = ar.drop(1)
    (cond,item)
  }
  private val raw = rdd

  private val aggitems = {
    val ilist = for(ar <- agargs) yield ar.drop(1)
    ilist.flatten
  }

  /**
   * pre covert each event: (String, Map[String,String]) to (String,Map[String, (Double, Int)]) by args
   * @param event raw event data
   * @param args
   * @return
   */
  def preMap(event: (String, Map[String,String]), args: List[String])
      : (String,Map[String, (Double, Int)]) ={
    val retmap = scala.collection.mutable.Map[String, (Double,Int)]()
    args.foreach(ar => {
        retmap += (ar->{
          if(event._2.contains(ar))
            (event._2(ar).toDouble,1)
          else
            (0.0,0)
        })
    })
    val line = (event._1,retmap.toMap)
    line
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


  @deprecated
  def Avg(args: List[String]): RDD[(String,Map[String,Double])] = {
    def SumCount(x:Map[String, (Double,Int)],y:Map[String, (Double,Int)]) = {
      for(subx <- x ) yield {
        val sum = subx._2._1 + y(subx._1)._1
        val count = subx._2._2 + y(subx._1)._2
        subx._1 ->(sum,count)
      }
    }
    val prerdd = raw.map(x =>preMap(x,args))
    prerdd.reduceByKey(SumCount).mapValues(doAvg)
  }

  @deprecated
  def MaxOrMin=(args: List[String],flag: Boolean)=> {
    val prerdd = raw.map(x =>preMap(x,args))

    def MaxVal(x:Map[String, (Double,Int)],y:Map[String, (Double,Int)]) = {
      for(subx <- x ) yield {
        val sum = Math.max(subx._2._1, y(subx._1)._1)
        subx._1 ->(sum,1)
      }
    }

    def MinVal(x:Map[String, (Double,Int)],y:Map[String, (Double,Int)]) = {
      for(subx <- x ) yield {
        val sum = Math.min(subx._2._1, y(subx._1)._1)
        subx._1 ->(sum,1)
      }
    }
    def doValue(value: Map[String,(Double,Int)]) = {
      for(x <- value) yield x._1 -> x._2._1
    }
    val ret = if(flag)
      prerdd.reduceByKey(MaxVal)
    else
      prerdd.reduceByKey(MinVal)
    ret.mapValues{doValue}
  }



  @deprecated
  def exec(cond : String)(args: List[String]): RDD[(String,Map[String,Double])] = {
    cond match {
      case "avg" => Avg(args)
      case "max" => MaxOrMin(args,true)
      case "min" => MaxOrMin(args,false)
      case _     => {println("not ready");Avg(args)}
    }
  }

  /**
   * Aggregate rdd using defult args by defult constuctions
   * @param ar aggregate args
   * @return
   */
  def doAggre(ar: List[(String,List[String])] = aggargs):RDD[(String,Map[String,Double])] = {
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
    }
  }

  /**
   * Merge two events value(Map) to one using aggregate args
   * @param ar aggregate args
   * @param lhs left hand event to be merge
   * @param rhs right hand event to be merge
   * @return merged event
   */
  def Merge(ar: List[(String,List[String])])(lhs: Map[String,(Double,Int)],rhs: Map[String,(Double,Int)])
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
  def CalcValue(ar: List[(String,List[String])])(orig: Map[String,(Double,Int)])
      : Map[String,Double] ={

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
  private def doCalc(method: String)(tuple: (Double,Int)):Double ={
    method match {
      case "avg" => if(tuple._2 == 0) 0.0 else tuple._1/tuple._2
      case _ => tuple._1
    }
  }
}
