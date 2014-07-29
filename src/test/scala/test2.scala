import byone.hbase.uid.{RandEvent, EventFactory, UniqueId}
import byone.hbase.utils.{ScanCovert, Conf}
import java.lang.String
import net.liftweb.json.Formats
import net.liftweb.json.JsonParser.parse
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.{Cell, HColumnDescriptor, HTableDescriptor}
import scala.collection.JavaConverters._
/**
 * Created by dream on 7/11/14.
 */
object test2 {
  def ScanToString = (scan : Scan) => new ScanCovert(scan).coverToScan()

  def main(args: Array[String]) {


    val sn = new Scan()
    sn.setCacheBlocks(false)
    sn.setCaching(10000)
    sn.setReversed(true)

    Conf.conf.set(TableInputFormat.INPUT_TABLE, "log_data")
    Conf.conf.set(TableInputFormat.SCAN,ScanToString(sn))
    val hBaseRDD = Conf.sc.newAPIHadoopRDD(Conf.conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    hBaseRDD.collect()


    Conf.sc.stop()

  }

}
