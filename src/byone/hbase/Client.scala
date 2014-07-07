package byone.hbase

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

/**
 * Created by dream on 7/7/14.
 */
object Client {

  def ScanToString(scan : Scan) : String = new ScanCovert(scan).coverToScan()

  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setAppName("HBaseTest").setMaster("local")
    val sc = new SparkContext(sparkConf)
    val conf = HBaseConfiguration.create()
    conf.addResource("/home/dream/workspace/scalahbaseClient/conf/hbase-site.xml")
    conf.addResource("/home/dream/workspace/scalahbaseClient/conf/yarn-site.xml")
    conf.addResource("/home/dream/workspace/scalahbaseClient/conf/mapred-site.xml")

    val tablename = "log_data"
    val table = new HTable(conf,tablename)
    val startRow = "000a71cce12ee3e0000"
    val stopRow = "000a71cce1c15fe0064"

    //delete 'uid' table
    val admin = new Man(conf)
    admin.delete("uid")
    val ar = Array("id","name")
    admin.create("uid",ar)

    val rw = new RW(conf,"uid")
    val row =  "0001" + DatePoint.dateToTs("2014/6/4 14:32:21")+ "000"
    for(i <- 1 to 9)
      rw.add(row+i.toString,"id","e","event")

    println(rw.get("000140a5026725ee0007"))
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
//    conf.set(TableInputFormat.INPUT_TABLE, tablename)
//    conf.set(TableInputFormat.SCAN,ScanToString(scan))
//    //conf.set(TableInputFormat.)
//    val hBaseRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
//      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
//      classOf[org.apache.hadoop.hbase.client.Result])
//
//    println("count = "+hBaseRDD.count())
//
//    val gb = "relayDevIpAddr"
//    val middata = hBaseRDD.map{case (a,b) =>
//      var ky = ""
//      var vl = ""
//      for(kv:Cell<- b.rawCells())
//      {
//        val key = new String(kv.getQualifier())
//        val value = new String(kv.getValue())
//        if(key == gb) {
//          ky = value
//        }
//        else
//          vl += key +"="+value+","
//      }
//      (ky,vl)
//
//
//    }
//    middata.collect().foreach(x =>println(x))
//
//    val avgar = "cpuUtil"
//    val last=middata.map{case (a,b)=>
//      var u= 0.0
//      b.split(",").foreach(kvpairs => {
//        if(!kvpairs.isEmpty()){
//          val kv=kvpairs.split("=")
//          if(kv(0).contains(avgar))
//            u = kv(1).toFloat
//        }
//      }
//      )
//      (a,(u,1))
//    }
//    last.collect().foreach(x =>println(x))
//
//    last.reduceByKey((x,y)=> (x._1+y._1,x._2+y._2)).mapValues{ case (sum,count) =>
//      1.0*sum/count
//    }.collectAsMap().foreach(x=>println(x))




    sc.stop()
  }
}
