import org.apache.hadoop.hbase.client.Scan
import org.apache.spark.annotation.DeveloperApi
import org.apache.spark.{TaskContext, Partition, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Created by dream on 8/22/14.
 */
class NewHbaseRDD[K,V](sc : SparkContext,scan: Scan)
  extends RDD[(K, V)](sc, Nil){
  @DeveloperApi
  override def compute(split: Partition, context: TaskContext): Iterator[(K, V)] = ???

  override protected def getPartitions: Array[Partition] = ???
}
