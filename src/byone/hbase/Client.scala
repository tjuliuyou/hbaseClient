package byone.hbase

import byone.hbase.uid.UniqueId
import org.apache.hadoop.hbase.{Cell, HBaseConfiguration}
import org.apache.hadoop.hbase.client.{Result, ResultScanner, Scan, HTable}
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark._
import org.apache.spark.rdd.NewHadoopRDD
import scala.collection.JavaConverters._
import SparkContext._
import byone.hbase.utils.{DatePoint, ScanCovert}
import byone.hbase.core.{RW, Man}
import scala.collection.mutable.Map

/**
 * Created by dream on 7/7/14.
 */
object Client {

  val sparkConf = new SparkConf().setAppName("HBaseTest").setMaster("local")
  val sc = new SparkContext(sparkConf)
  val conf = HBaseConfiguration.create()
  conf.addResource("/home/dream/workspace/scalahbaseClient/conf/hbase-site.xml")
  conf.addResource("/home/dream/workspace/scalahbaseClient/conf/yarn-site.xml")
  conf.addResource("/home/dream/workspace/scalahbaseClient/conf/mapred-site.xml")

  def ScanToString(scan : Scan) : String = new ScanCovert(scan).coverToScan()

  def getRowArea(range: Vector[String], event: Vector[String]): Map[String,String] = {
    val area = Map[String, String]()
    if(event.isEmpty)
      area
    else {
      val uid = new UniqueId(conf,sc)
      uid.readToCache("test/eventuid.txt")
      for(pre <- event)
      {
        val p = uid.getId(pre)
        area += ((p + DatePoint.dateToTs(range(1)) + "0000")->
          ( p + DatePoint.dateToTs(range(0)) + "0064"))
      }
      area
    }
  }

  def getScan(cdn: Map[String,Vector[String]]) : Vector[Scan] = {
    require(cdn.contains("range"))
    //val sl =
    val area = getRowArea(cdn("range"),cdn("event"))
    val sl = area.map{rows =>
      val sn = new Scan(rows._1.getBytes,rows._2.getBytes)
      println(rows)
      val fl = new SingleColumnValueFilter("d".getBytes,"collectorId".getBytes,CompareOp.EQUAL,"10050".getBytes)
      sn.setFilter(fl)
      if(!cdn("back").isEmpty)
        cdn("back").foreach(dis =>sn.addColumn("d".getBytes,dis.getBytes))
      sn
    }
    Vector() ++ sl
  }
  def main(args: Array[String]) {

    //args
    val tablename = "log_data"
    val timerange = Vector("18/06/2014 14:40:11","18/06/2014 14:50:11")
    val display = Vector("phRecvTime", "collectorId", "eventType", "relayDevIpAddr","pollIntv")
    val eventType = Vector("PH_DEV_MON_SYS_PER_CPU_UTIL")
    val filters = Vector("collectorId","<","10050")
    val gpbylist = Vector("relayDevIpAddr")
    val aggrelist = Vector("cpuUtil","collectorId")
    val condtion = Vector("avg")


    //parser args
    val scanCdn = Map("range"  -> timerange,
                      "event"  -> eventType,
                      "back"   -> display,
                      "filter" -> filters)
    val s = getScan(scanCdn)









//    val table = new HTable(conf,tablename)
//    //delete 'uid' table
//    val admin = new Man(conf)
//    admin.delete("uid")
//    val ar = Array("d")
//    admin.create("uid",ar)

//    val rw = new RW("uid",conf,sc)
//    val row =  "0001" + DatePoint.dateToTs("18/06/2014 14:40:11")+ "000"
//    for(i <- 1 to 9)
//      rw.add(row+i.toString,"id","e","event"+i.toString)

//    val uid = new UniqueId(conf,sc)
//    uid.readToCache("test/eventuid.txt")
//   // uid.Insert("uid")
//    //println(uid.getName("0001"))
//    println(uid.getId("PH_DEV_MON_PROC_RESOURCE_UTIL"))


//    val scan = new Scan(startRow.getBytes(),stopRow.getBytes())
//    val fl = new SingleColumnValueFilter("d".getBytes(),"collectorId".getBytes(),CompareOp.EQUAL,"10050".getBytes())
//
//    scan.setFilter(fl)
//    scan.addColumn("d".getBytes(),"collectorId".getBytes())
//    scan.addColumn("d".getBytes(),"relayDevIpAddr".getBytes())
//    scan.addColumn("d".getBytes(),"cpuUtil".getBytes())
//    scan.addColumn("d".getBytes(),"eventType".getBytes())
//    val ss: ResultScanner = table.getScanner(scan)
//    var a = 0
//
//    for ( va <- ss.asScala)
//    {  a=a+1}
//    println(a)
//
//    def preProc(a:ImmutableBytesWritable,b:Result,gb:String) : (String , String) = {
//      var vl = ""
//
//      for(kv:Cell <- b.rawCells())
//      {
//        val key = new String(kv.getQualifier())
//        val value = new String(kv.getValue())
//        vl += key +","+value
//      }
//      (gb,vl)
//    }
//
    val scan = s(0)
    conf.set(TableInputFormat.INPUT_TABLE, tablename)
    conf.set(TableInputFormat.SCAN,ScanToString(scan))
    //conf.set(TableInputFormat.)
    val hBaseRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])

    println("count = "+hBaseRDD.count())

    val gb = "relayDevIpAddr"
    val middata = hBaseRDD.map{case (a,b) =>
      var ky = ""
      var vl = ""
      for(kv:Cell<- b.rawCells())
      {
        val key = new String(kv.getQualifier())
        val value = new String(kv.getValue())
        if(key == gb) {
          ky = value
        }
        else
          vl += key +"="+value+","
      }
      (ky,vl)


    }
    middata.collect().foreach(x =>println(x))

    val avgar = "cpuUtil"
    val last=middata.map{case (a,b)=>
      var u= 0.0
      b.split(",").foreach(kvpairs => {
        if(!kvpairs.isEmpty()){
          val kv=kvpairs.split("=")
          if(kv(0).contains(avgar))
            u = kv(1).toFloat
        }
      }
      )
      (a,(u,1))
    }
    last.collect().foreach(x =>println(x))

    last.reduceByKey((x,y)=> (x._1+y._1,x._2+y._2)).mapValues{ case (sum,count) =>
      1.0*sum/count
    }.collectAsMap().foreach(x=>println(x))




    sc.stop()
  }
}
