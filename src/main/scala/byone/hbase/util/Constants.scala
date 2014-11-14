package byone.hbase.util

import byone.hbase.uid.UniqueId
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
   * Default data table name
   */
  val dataTable   = "log_data"
  /**
   * Default data family name
   */
  val dataFamily  = Seq("d")
  /**
   * Default uid table name
   */
  val uidTable    = "uid"
  /**
   * Default uid family names
   */
  val uidfamily   = Seq("id","name")
  /**
   * Default event uid length store in rowkey (Bytes)
   */
  val UIDLENGTH     =  3
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
  val REGIONRANGE   =  24
  /**
   * Default pre-spilt regions numbers
   */
  val REGIONNUM     =  24
  /**
   * Default startkey
   */
  val STARTKEY     =  1

  val uid = new UniqueId

  // Default global Hbase Configurations
  private val HBASE_CONF_PATH = "src/main/resources/conf/hbase-site.xml"
  private val YARN_CONF_PATH = "src/main/resources/conf/yarn-site.xml"
  private val MAPR_CONF_PATH = "src/main/resources/conf/mapred-site.xml"
  val conf = HBaseConfiguration.create
  conf.addResource(new Path(HBASE_CONF_PATH))
  conf.addResource(new Path(YARN_CONF_PATH))
  conf.addResource(new Path(MAPR_CONF_PATH))

  //Default spark Configurations
  val sparkConf = new SparkConf()
    .setAppName("hbase test")
    //.setMaster("local")
    //.setMaster("yarn-client")
    .setMaster("spark://client.dream:7077")
    .setJars(Seq("target/scala-2.10/hbaseclient_2.10-0.2.2.jar"))

  val sc = new SparkContext(sparkConf)

}


