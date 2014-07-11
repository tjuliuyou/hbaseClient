package byone.hbase.core

import org.apache.hadoop.hbase.filter.{FilterList, SingleColumnValueFilter}
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp

/**
 * Created by dream on 7/11/14.
 */
object FilterParser {
  private val f = "d".getBytes
  //private val list = new FilterList(FilterList.Operator.MUST_PASS_ALL)
  private def opType(op: String) = op match {
    case "<"  => { println("<: less ") ; CompareOp.LESS }
    case "<=" => CompareOp.LESS_OR_EQUAL
    case "="  => CompareOp.EQUAL
    case ">=" => CompareOp.GREATER_OR_EQUAL
    case ">"  => CompareOp.GREATER
    case "!=" => CompareOp.NOT_EQUAL
    case _    => CompareOp.NO_OP
  }
  def singleParser(in: String): SingleColumnValueFilter = {
    val agrs =in.split(",")
    //agrs.foreach(println)
    new SingleColumnValueFilter(f,agrs(0).getBytes,opType(agrs(1)),agrs(2).getBytes)
  }
  //def apply() = new SingleColumnValueFilter(f,k,opType(operator),v)
}
