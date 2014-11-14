package byone.hbase

import byone.hbase.core.Table
import byone.hbase.util.Constants

/**
 * Created by liuyou on 14/11/5.
 */
object TableTest {

  def main(args: Array[String]) {
    val tablename = Constants.dataTable
    val dataTable = new Table(tablename)
    dataTable.delete
    dataTable.create(Constants.dataFamily, Constants.STARTKEY, Constants.REGIONRANGE, Constants.REGIONNUM)

    dataTable.close
    println("create table: '" + tablename + "' successfully.")

  }
}
