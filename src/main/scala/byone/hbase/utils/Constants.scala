package byone.hbase.utils

import org.apache.hadoop.fs.Path
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by dream on 7/11/14.
 *
 * Constants holds a bunch of constants related to global variables
 *
 */
object Constants {
  /**
   * Default table name
   */
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

  /**
   * Default event uid length store in rowkey (Bytes)
   */
  val UIDLENGTH     = 4
  /**
   * Default random prefix length store in rowkey (Bytes)
   */
  val PRELENGTH     = 1
  /**
   * Default timestamps length store in rowkey (Bytes)
   */
  val TSLENGTH      = 4
  /**
   * Possible pre values for all regions
   */
  val REGIONRANGE   = 256
  /**
   * Default pre-spilt regions numbers
   */
  val REGIONNUM     = 16

}

/**
 * Args holds a bunch of args parsed from test file (json)
 * @param Range time range list should be start time and stop time, to see the format {@see DatePoint#toTs}
 * @param Items items list that needed to display
 * @param Events event type list should be use
 * @param Filter filter String will be set for scan
 * @param Groupby group by lists
 * @param Aggres aggreagte args
 */
case class Args(Range: List[String], Items: List[String]
     ,Events: List[String], Filter: String
     ,Groupby: List[String], Aggres: List[List[String]])