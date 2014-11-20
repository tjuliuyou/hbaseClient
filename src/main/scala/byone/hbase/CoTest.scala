package byone.hbase

import byone.hbase.core.Query
import byone.hbase.protobuf.PreAggProtos
import byone.hbase.protobuf.PreAggProtos.MapEntry
import byone.hbase.util.{Constants, Converter}
import com.google.protobuf.ByteString
import org.apache.hadoop.fs.Path
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.coprocessor.Batch
import org.apache.hadoop.hbase.client.{HTable, Scan}
import org.apache.hadoop.hbase.ipc.{BlockingRpcCallback, ServerRpcController}
import org.apache.hadoop.hbase.protobuf.ProtobufUtil
import org.apache.spark.SparkContext._

import scala.collection.JavaConverters._

/**
 * Created by liuyou on 14/11/11.
 */
object CoTest {

  // Default global Hbase Configurations
  private val HBASE_CONF_PATH = "src/main/resources/conf/hbase-site.xml"
  private val YARN_CONF_PATH = "src/main/resources/conf/yarn-site.xml"
  private val MAPR_CONF_PATH = "src/main/resources/conf/mapred-site.xml"
  val conf = HBaseConfiguration.create
  conf.addResource(new Path(HBASE_CONF_PATH))
  conf.addResource(new Path(YARN_CONF_PATH))
  conf.addResource(new Path(MAPR_CONF_PATH))

  def aggre(lh:(String, String),rh: (String, String)): (String, String) = {
    if(lh._2.isEmpty ||rh._2.isEmpty)
      (lh._1,"")
    else {
      val lv = lh._2.toDouble
      val rv = rh._2.toDouble
      val ret = lh._1 match {
        case "avg" => (lv + rv)/2
        case "min" => Math.min(lv,rv)
        case "max" => Math.max(lv,rv)
        case "cou" => lv + rv
      }

      (lh._1,ret.toString)
    }
  }

  def main(args: Array[String]) {
    val table = new HTable(conf, "log_data")
    val items = Seq("eventType","cpuUtil","hostName","memUtil")
    //val request: PreAggProtos.AnalyseRequest = null
    val scan = new Scan()


    val filterString = ""
    val event: Seq[String] = Seq("PH_DEV_MON_SYS_MEM_UTIL","PH_DEV_MON_SYS_PER_CPU_UTIL")

    val filter = Query.hbaseFilter(filterString,event)
    scan.setFilter(filter)
    scan.setCacheBlocks(false)
    scan.setCaching(2000)
    //scan.setReversed(true)
    items.foreach(x => scan.addColumn("d".getBytes,x.getBytes))


    val ts = System.currentTimeMillis()
    val groups = List("hostName")
    val arrges = Map("cpuUtil" -> "avg", "memUtil" -> "count")

    val range = List(Converter.num2Byte(0, 4), Converter.num2Byte(ts / 1000, 4))
    val protorange = range.map(ByteString.copyFrom).asJava
    val agg = for (ar <- arrges) yield {
      PreAggProtos.TupleEntry.newBuilder().setKey(ar._1).setValue(ar._2).build()
    }
    val request = PreAggProtos.Request.newBuilder()
      .setScan(ProtobufUtil.toScan(scan))
      .addAllRange(protorange)
      .addAllGroups(groups.asJava)
      .setAggre(PreAggProtos.MapEntry.newBuilder().addAllKv(agg.asJava).build())
      .build()


    println(request.getGroupsList)

//
//    val results = table.coprocessorService(classOf[PreAggProtos.PreAggService],
//      null, null,
//      new Batch.Call[PreAggProtos.PreAggService, mutable.Buffer[MapEntry]]() {
//        override def call(counter: PreAggProtos.PreAggService): mutable.Buffer[MapEntry] = {
//          val controller = new ServerRpcController()
//          val rpcCallback = new BlockingRpcCallback[PreAggProtos.rawResponse]()
//          counter.getRawData(controller, request, rpcCallback)
//          val response = rpcCallback.get()
//          //if(response != null && response.isInitialized)
//          response.getRawList.asScala
//        }
//      })

//    val data = results.values().asScala.toList.flatten
//
//    println(data.size)
//
//    val temp = data.map(sub =>{
//      val kvList = sub.getKvList.asScala
//      kvList.map(kv => {
//        kv.getKey -> kv.getValue
//      }).toMap
//
//    })
//    temp.foreach(println)

//    results.asScala.foreach(regiondata => {
//      val region = regiondata._1.foreach(sub => print("," + sub))
//
//
//      val kvmap = regiondata._2.map(sub => {
//        val kvList = sub.getKvList.asScala
//              kvList.map(kv => {
//                kv.getKey -> kv.getValue
//              }).toMap
//
//      })
//
//      kvmap.foreach(println)
//
//      println("------------------------------------------------------------------------")
//
//    })



    val predata = table.coprocessorService(classOf[PreAggProtos.PreAggService],
      null, null,
      new Batch.Call[PreAggProtos.PreAggService, MapEntry]() {
        override def call(counter: PreAggProtos.PreAggService): MapEntry = {
          val controller = new ServerRpcController()
          val rpcCallback = new BlockingRpcCallback[PreAggProtos.Response]()
          counter.getPreData(controller, request, rpcCallback)
          val response = rpcCallback.get()
          //if(response != null && response.isInitialized)
          response.getData
        }
      })

//    predata.asScala.map(x => {
//      val region = x._1.foreach(sub => print("," + sub))
//
//      x._2.getKvList.asScala.foreach(kv => {
//        println(kv.getKey +":   " + kv.getValue)
//      })
//      println("------------------------------------------------------------------------")
//    })


    val data = predata.values().asScala

    val temp = data.map(sub => {
      val kvList = sub.getKvList.asScala
      kvList.map(kv => {
        kv.getKey -> kv.getValue
      })
    }).flatten
    //val rdd = Constants.sc.emptyRDD[Map[String,String]]()
    val rdd = Constants.sc.makeRDD(temp.toSeq)
    //rdd.collect().foreach(println)

    val red = rdd.map(x => {
      (x._1,(x._1.substring(0,3),x._2))
    }).reduceByKey(aggre).map(x =>{
      (x._1,x._2._2)
    })


    red.collect().foreach(println)
    Constants.sc.stop()

  }
}
