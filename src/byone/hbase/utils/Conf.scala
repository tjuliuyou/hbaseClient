package byone.hbase.utils

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.hadoop.hbase.HBaseConfiguration
import java.lang.String
import scala.Predef.String

/**
 * Created by dream on 7/11/14.
 */
object Conf {
  // glable conf
  val tablename = "log_data"
  val sparkConf = new SparkConf()
                      .setAppName("HBaseTest")
                      .setMaster("local")
                      .setSparkHome("/opt/spark")
                      //.setJars(SparkContext.jarOfClass(this.getObject))
  val sc = new SparkContext(sparkConf)
  val conf = HBaseConfiguration.create()
  conf.addResource("/home/dream/workspace/scalahbaseClient/conf/hbase-site.xml")
  conf.addResource("/home/dream/workspace/scalahbaseClient/conf/yarn-site.xml")
  conf.addResource("/home/dream/workspace/scalahbaseClient/conf/mapred-site.xml")

  val UIDLENGTH = 4

}

case class Args(Range: List[String]
                ,Items: List[String]
                ,Events: List[String]
                ,Filter:  String
                ,Groupby: List[String]
                ,Aggres: List[List[String]])