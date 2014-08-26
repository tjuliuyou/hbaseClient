import java.text.SimpleDateFormat
import java.util.Date

import byone.hbase.util.Constants
import org.apache.hadoop.conf.Configurable
import org.apache.hadoop.hbase.client.Scan
import org.apache.hadoop.hbase.mapreduce.MultiTableInputFormat
import org.apache.hadoop.io.Writable
import org.apache.hadoop.mapreduce.{InputSplit, JobID}
import org.apache.spark._
import org.apache.spark.annotation.DeveloperApi
import org.apache.spark.rdd.RDD

/**
 * Created by dream on 8/22/14.
 */


private class NewHbasePartition(
                                 rddId: Int,
                                 val index: Int,
                                 @transient rawSplit: InputSplit with Writable)
  extends Partition {

  val serializableHadoopSplit = new SerializableWritable(rawSplit)

  override def hashCode(): Int = 41 * (41 + rddId) + index
}

class NewHbaseRDD[K,V](sc : SparkContext,scans: Seq[Scan])
  extends RDD[(K, V)](sc, Nil)
  with SparkHbaseMapReduceUtil
  with Logging {

  val hbaseConf = Constants.conf

  // A Hadoop Configuration can be about 10 KB, which is pretty big, so broadcast it
  private val confBroadcast = sc.broadcast(new SerializableWritable(hbaseConf))
  // private val serializableConf = new SerializableWritable(conf)

  private val jobTrackerId: String = {
    val formatter = new SimpleDateFormat("yyyyMMddHHmm")
    formatter.format(new Date())
  }

  @transient protected val jobId = new JobID(jobTrackerId, id)

  @DeveloperApi
  override def compute(split: Partition, context: TaskContext): Iterator[(K, V)] = ???

  override protected def getPartitions: Array[Partition] = {
    val multiInputFormat = new MultiTableInputFormat()
    multiInputFormat match {
      case configurable: Configurable =>
        configurable.setConf(hbaseConf)
      case _ =>
    }
    val jobContext = newJobContext(hbaseConf, jobId)
    val rawSplits = multiInputFormat.getSplits(jobContext).toArray
    val result = new Array[Partition](rawSplits.size)
    for (i <- 0 until rawSplits.size) {
      result(i) = new NewHbasePartition(id, i, rawSplits(i).asInstanceOf[InputSplit with Writable])
    }
    result
  }
}
