import byone.hbase.core.Table
import byone.hbase.util.{Constants, ScanCovert}
import org.apache.hadoop.hbase.client._
/**
 * Created by dream on 7/11/14.
 */
object creatTable {
  def ScanToString = (scan : Scan) => new ScanCovert().coverToScan(scan)

  def main(args: Array[String]) {

    val tablename ="log_data1"
    val dataTable = new Table(tablename)
    if(dataTable.delete)
      dataTable.create(Constants.dataFamily,16,Constants.REGIONRANGE,Constants.REGIONNUM)

    println("create table: '" +tablename + "' successfully.")


  }
}
