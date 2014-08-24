import org.apache.hadoop.hbase.client.Scan
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by dream on 8/22/14.
 */
class BySparkContext(config: SparkConf) extends SparkContext {

  def newAPIHbaseRDD[K,V](scan: Scan)
    :RDD[(K,V)] = {
    new NewHbaseRDD(this,scan)
  }
}
