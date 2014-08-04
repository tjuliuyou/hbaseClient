package byone.hbase.core

import java.util.concurrent.{Future, Executors, ExecutorService, Callable}

import byone.hbase.utils.{ScanCovert, DatePoint, Conf}
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Scan, Result}
import org.apache.hadoop.hbase.filter.Filter
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark.rdd.RDD

/**
 * Created by liuyou on 2014/8/2.
 */
class Query(startKey: Int,filter: Filter, range: List[Array[Byte]],items: List[String])
  extends Callable[RDD[(ImmutableBytesWritable,Result)]] {
  //Conf.conf.set(TableInputFormat.INPUT_TABLE,tablename)
  private val regions = Conf.REGIONRANGE

  private val pool: ExecutorService = Executors.newFixedThreadPool(regions)
  private def ScanToString = (scan : Scan) => new ScanCovert().coverToScan(scan)
  def call(): RDD[(ImmutableBytesWritable,Result)] = {

    var ret: RDD[(ImmutableBytesWritable,Result)] = Conf.sc.emptyRDD
    val futures = for (i <- 0 until regions) yield
    {
      val pre = DatePoint.Int2Byte(startKey+i,1)
      val startRow = pre ++ range(0)
      val stopRow = pre ++ range(1)

      val sn = new Scan(startRow, stopRow)
      sn.setFilter(filter)
      sn.setCacheBlocks(true)
      sn.setCaching(10000)
      sn.setReversed(true)
      if(items.nonEmpty){
        items.foreach(item =>sn.addColumn("d".getBytes,item.getBytes))
      }

      pool.submit(new RegionQuery(ScanToString(sn)))

    }
    for(future <- futures ){
      val r = future.get()
      ret = ret ++ r
    }
    pool.shutdown()
    ret

  }


}

class RegionQuery(scanString: String) extends Callable[RDD[(ImmutableBytesWritable,Result)]]
{
  def call() : RDD[(ImmutableBytesWritable,Result)] =
  {
    val conf = HBaseConfiguration.create(Conf.conf)
    conf.set(TableInputFormat.SCAN,scanString)
    val singlerdd = Conf.sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    singlerdd
  }
}