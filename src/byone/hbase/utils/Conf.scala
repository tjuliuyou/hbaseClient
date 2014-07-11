package byone.hbase.utils

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.hadoop.hbase.HBaseConfiguration

/**
 * Created by dream on 7/11/14.
 */
object Conf {
  // glable conf
  val tablename = "log_data"
  val sparkConf = new SparkConf().setAppName("HBaseTest").setMaster("local")
  val sc = new SparkContext(sparkConf)
  val conf = HBaseConfiguration.create()
  conf.addResource("/home/dream/workspace/scalahbaseClient/conf/hbase-site.xml")
  conf.addResource("/home/dream/workspace/scalahbaseClient/conf/yarn-site.xml")
  conf.addResource("/home/dream/workspace/scalahbaseClient/conf/mapred-site.xml")
}