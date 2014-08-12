package byone.hbase.core

import java.util.concurrent.{Callable, ExecutorService, Executors}

import byone.hbase.utils.{Constants, DatePoint}
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Result, Scan}
import org.apache.hadoop.hbase.filter.Filter
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark.rdd.RDD

/**
 * Created by liuyou on 2014/8/2.
 *
 * Query all needed data for a region
 * @param startKey :startkey for regions
 * @param filter   :scan filter list
 * @param range    :time range
 * @param items    :get back items to display
 * return a RDD[(ImmutableBytesWritable,Result)]
 */
class Query(startKey: Int,filter: Filter, range: List[Array[Byte]],items: List[String])
  extends Callable[RDD[(ImmutableBytesWritable,Result)]] {

  private val regions = Constants.REGIONNUM
  private val pool: ExecutorService = Executors.newFixedThreadPool(regions)

  def call(): RDD[(ImmutableBytesWritable,Result)] = {

    var ret: RDD[(ImmutableBytesWritable,Result)] = Constants.sc.emptyRDD
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

      pool.submit(new RegionQuery(DatePoint.ScanToString(sn)))

    }
    for(future <- futures ){
      val r = future.get()
      ret = ret ++ r
    }
    pool.shutdown()
    ret

  }


}

/**
 * get a pice of RDD[(ImmutableBytesWritable,Result)]
 * @param scanString
 */
class RegionQuery(scanString: String) extends Callable[RDD[(ImmutableBytesWritable,Result)]]
{
  def call() : RDD[(ImmutableBytesWritable,Result)] =
  {
    val conf = HBaseConfiguration.create(Constants.conf)
    conf.set(TableInputFormat.SCAN,scanString)
    val singlerdd = Constants.sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    singlerdd
  }
}