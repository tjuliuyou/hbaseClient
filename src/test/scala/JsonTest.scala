import byone.hbase.util.Converter

import org.apache.hadoop.fs.Path
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Result, ResultScanner, HTable, Scan}
import scala.collection.JavaConverters._
/**
 * Created by liuyou on 14/11/18.
 */
object JsonTest extends Application {


  private val HBASE_CONF_PATH = "src/main/resources/conf/hbase-site.xml"
  private val YARN_CONF_PATH = "src/main/resources/conf/yarn-site.xml"
  private val MAPR_CONF_PATH = "src/main/resources/conf/mapred-site.xml"
  val conf = HBaseConfiguration.create
  conf.addResource(new Path(HBASE_CONF_PATH))
  conf.addResource(new Path(YARN_CONF_PATH))
  conf.addResource(new Path(MAPR_CONF_PATH))

  val table = new HTable(conf, "log_data")
  val items = Seq("collectorId", "eventType", "relayDevIpAddr","eventSeverity")
  //val request: PreAggProtos.AnalyseRequest = null
  val scan = new Scan()


  items.foreach(x => scan.addColumn("d".getBytes,x.getBytes))


  val ts = System.currentTimeMillis()
  val groups = List("hostName")
  val arrges = Map("cpuUtil" -> "avg", "memUtil" -> "avg")

  val range = List(Converter.num2Byte(0, 4), Converter.num2Byte(ts / 1000, 4))

  //scan.setStartRow(range(0))
  //scan.setStopRow(range(1))

  val rs  = table.getScanner(scan).asScala
  var y = 0
  for ( x: Result <- rs){
     y += 1
  }
  println(y)

}
