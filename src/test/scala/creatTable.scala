import byone.hbase.uid.RandEvent
import byone.hbase.utils.{Constants, ScanCovert}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm
import org.apache.hadoop.hbase.regionserver.BloomType
import org.apache.hadoop.hbase.{HColumnDescriptor, HTableDescriptor}
/**
 * Created by dream on 7/11/14.
 */
object creatTable {
  def ScanToString = (scan : Scan) => new ScanCovert().coverToScan(scan)

  def main(args: Array[String]) {


    val tablename ="log_data"
        val admin = new HBaseAdmin(Constants.conf)
        if(admin.tableExists(tablename)){
          admin.disableTable(tablename)
          admin.deleteTable(tablename)
          println("drop table: '" +tablename + "' successfully.")
        }

          val desc : HTableDescriptor = new HTableDescriptor(tablename)
          val hdes: HColumnDescriptor = new HColumnDescriptor("d".getBytes)
          hdes.setInMemory(true)
          hdes.setMaxVersions(1)
          hdes.setCompressionType(Algorithm.SNAPPY)
          hdes.setBloomFilterType(BloomType.ROW)
          desc.addFamily(hdes)

          def getSplits(startkey: Int, stopkey: Int, num: Int): Array[Array[Byte]] ={
            val range = stopkey - startkey
            val rangeIncrement = range/(num-1)
            val ret =for(i <- 0 until (num-1)) yield {
              val key = startkey + rangeIncrement*i
              RandEvent.Int2Byte(key,1) //++ RandEvent.Int2Byte(Int.MaxValue, 4)
            }
            ret.toArray
          }

    admin.createTable(desc,getSplits(16,Constants.REGIONRANGE,Constants.REGIONNUM))

    println("create table: '" +tablename + "' successfully.")





  }
}
