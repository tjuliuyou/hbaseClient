import byone.hbase.core.Table
import byone.hbase.util.{Constants, ScanCovert}
import org.apache.hadoop.hbase.client._

/**
 * Created by dream on 7/11/14.
 */
object creatTable {
  def ScanToString = (scan: Scan) => new ScanCovert().coverToScan(scan)

  def main(args: Array[String]) {

    val tablename = "log_data1"
    val dataTable = new Table(tablename)
    dataTable.delete
    dataTable.create(Constants.dataFamily, Constants.STARTKEY, Constants.REGIONRANGE, Constants.REGIONNUM)

    dataTable.close
    println("create table: '" + tablename + "' successfully.")


  }
}
