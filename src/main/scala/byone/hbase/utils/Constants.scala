package byone.hbase.utils

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.hadoop.hbase.HBaseConfiguration
import scala.Predef.String
import org.apache.hadoop.fs.Path
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

/**
 * Created by dream on 7/11/14.
 */
object Constants {
  // glable conf
  val tablename = "log_data"

  private val HBASE_CONF_PATH = "src/conf/hbase-site.xml"
  private val YARN_CONF_PATH = "src/conf/yarn-site.xml"
  private val MAPR_CONF_PATH = "src/conf/mapred-site.xml"
  val conf = HBaseConfiguration.create
  conf.addResource(new Path(HBASE_CONF_PATH))
  conf.addResource(new Path(YARN_CONF_PATH))
  conf.addResource(new Path(MAPR_CONF_PATH))

  val sparkConf = new SparkConf()
    .setAppName("HBase Client")
    //.setMaster("local")
    .setMaster("spark://master3.dream:7077")
    .setJars(Seq("out/artifacts/byonehbasecore/byonehbasecore.jar"))
  val sc = new SparkContext(sparkConf)

  val UIDLENGTH     = 4
  val PRELENGTH     = 1
  val TSLENGTH      = 4
  val REGIONRANGE   = 256
  val REGIONNUM     = 16

}

case class Args(Range: List[String]
            ,Items: List[String]
            ,Events: List[String]
            ,Filter:  String
            ,Groupby: List[String]
            ,Aggres: List[List[String]])