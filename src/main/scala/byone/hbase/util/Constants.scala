package byone.hbase.util

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

  // Default global Hbase Configurations
  private val HBASE_CONF_PATH = "src/conf/hbase-site.xml"
  private val YARN_CONF_PATH = "src/conf/yarn-site.xml"
  private val MAPR_CONF_PATH = "src/conf/mapred-site.xml"
  val conf = HBaseConfiguration.create
  conf.addResource(new Path(HBASE_CONF_PATH))
  conf.addResource(new Path(YARN_CONF_PATH))
  conf.addResource(new Path(MAPR_CONF_PATH))

  //Default spark Configurations
  val sparkConf = new SparkConf()
    .setAppName("hbase test")
    //.setMaster("local")
    .setMaster("spark://master3.dream:7077")
    .setJars(Seq("out/artifacts/ByoneCore/ByoneCore.jar"
            ,"/home/dream/.ivy2/cache/com.twitter/util-core_2.10/jars/util-core_2.10-6.12.1.jar"))
  val sc = new SparkContext(sparkConf)

  /**
   * Default table name
   */
  val tablename = "log_data"
  /**
   * Default family name
   */
  val FAMILY = "d"
  /**
   * Default event uid length store in rowkey (Bytes)
   */
  val UIDLENGTH     =  4
  /**
   * Default random prefix length store in rowkey (Bytes)
   */
  val PRELENGTH     =  1
  /**
   * Default timestamps length store in rowkey (Bytes)
   */
  val TSLENGTH      =  4
  /**
   * Possible pre values for all regions
   */
  val REGIONRANGE   =  256
  /**
   * Default pre-spilt regions numbers
   */
  val REGIONNUM     =  16

}

/**
 * Args holds a bunch of args parsed from test file (json)
 * @param Range   Time range list should be start time and stop time, to see the format {@see DatePoint#toTs}
 * @param Items   Items list that needed to take back for display
 * @param Events  Event type list explicit should be take back
 * @param Filter  Filter String will be used for Scans
 * @param Groups Group by lists
 * @param Aggres  Aggreagte args
 */
case class Args(Range: List[String], Items: List[String]
     ,Events: List[String], Filter: String
     ,Groups: List[String], Aggres: List[List[String]])