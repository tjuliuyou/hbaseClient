package byone.hbase.core

import java.util.concurrent.{Executors, ExecutorService, Future}

import byone.hbase.utils.{Args, Conf, DatePoint,ScanCovert}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.{HRegionInfo, Cell}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import scala.collection.mutable.Map
import byone.hbase.uid.UniqueId
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.{MultiTableInputFormat, TableInputFormat}
import org.apache.hadoop.hbase.filter.ParseFilter
import scala.collection.JavaConverters._

/**
 * Created by dream on 7/7/14.
 */
class RwRDD(table : String) extends java.io.Serializable {
  private val serialVersionUID = 6529685098267757690L
  private val tablename = Conf.tablename

  private val uid = new UniqueId
  uid.readToCache("hdfs://master1.dream:9000/spark/eventuid.txt")


  private def ScanToString = (scan : Scan) => new ScanCovert().coverToScan(scan)
  private def ScansToString = (sl : List[Scan]) => new ScanCovert().coverToScan(sl.asJava)
  private def hbaseFilter(in:String) = new ParseFilter().parseFilterString(in)

  private def NumRegion(): Int = 16
  /**
   *  get (startrow,stoprow) pairs
   */
  private def rowAreaOrg = (range: List[String], event: List[String]) => {
    val startTs =  DatePoint.toTs(range(0)) ++ DatePoint.Int2Byte(0)
    val stopTs = DatePoint.toTs(range(1)) ++ DatePoint.Int2Byte(500)
    if(event.isEmpty){
      for(p <- uid.getCached) yield {
        (p ++ startTs)->( p ++ stopTs)
      }
    }
    else {
      for(pre <- event) yield
      {
        val p = uid.id(pre)
        (p ++ startTs)->( p ++ stopTs)
      }
    }
  }

  private def rowArea = (range: List[String], event: List[String]) => {

    val startTs =  DatePoint.toTs(range(0),-1) //add -1 mill seconds
    val stopTs = DatePoint.toTs(range(1),1)    //add 1 mill seconds
    val ret = for(num <- 0 to NumRegion) yield {
       val pre1 = DatePoint.Int2Byte(num*256/NumRegion,Conf.PRELENGTH)
        val pre2 = DatePoint.Int2Byte((num+1)*256/NumRegion,Conf.PRELENGTH)
       ( pre1 ++ startTs) -> (pre2 ++ stopTs)
    }
    if(event.isEmpty){
      ret
    }
    else {
      ret.flatMap{case (x,y) => {
        for(pre <- event) yield {
          val p = uid.id(pre)
          (x ++ p)->( y ++ p)
        }
      }}
    }
  }

  /**
   *  get Scan list for scan
   */
  def scanList = (args: Args) => {
    require(!args.Range.isEmpty)
    val area = rowArea(args.Range,args.Events)
    val sl = if(args.Filter.equals("null")){
      area.map{rows =>
      val sn = new Scan(rows._1,rows._2)
      sn.setCacheBlocks(false)
      sn.setCaching(10000)
      sn.setReversed(true)
     // sn.setAttribute(Scan.SCAN_ATTRIBUTES_TABLE_NAME, tablename.getBytes);
      if(!args.Items.isEmpty)
        args.Items.foreach(item =>sn.addColumn("d".getBytes,item.getBytes))
        sn
      }
    }else
       {
         val fl = hbaseFilter(args.Filter)
         area.map{rows =>
           val sn = new Scan(rows._1,rows._2)
           sn.setFilter(fl)
           if(!args.Items.isEmpty)
             args.Items.foreach(item =>sn.addColumn("d".getBytes,item.getBytes))
           sn
         }
       }
    Vector() ++ sl //thanslate iterater to vector
  }

  /**
   *  map raw hbase date to (string,string) by grouplist
   */
  def gpBy = (raw: (ImmutableBytesWritable, Result), gp: List[String]) => {
    val retmap = Map[String, String]()
    var ky = ""
    for(kv:Cell<- raw._2.rawCells())
    {
      val key = new String(kv.getQualifier)
      val value = new String(kv.getValue)
      if(gp.contains(key)) {
        ky = value
      }
      else
        retmap += (key->value)
    }
    (ky,retmap)
  }

  /**
   *  get base hbase RDD with one Scan
   */
  def gethbaseRDD = (scan: Scan) =>  {
    Conf.conf.set(TableInputFormat.INPUT_TABLE, tablename)
    Conf.conf.set(TableInputFormat.SCAN,ScanToString(scan))
    val hBaseRDD = Conf.sc.newAPIHadoopRDD(Conf.conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    hBaseRDD
  }

  /**
   *  get base hbase RDD with Scan list
   */
//  def gethbaseRDDs = (scans: List[Scan]) =>  {
//    //Conf.conf.set(TableInputFormat.INPUT_TABLE, "log_data")
//    Conf.conf.setStrings(MultiTableInputFormat.SCANS,ScanToString(scans))
//    val hBaseRDD = Conf.sc.newAPIHadoopRDD(Conf.conf, classOf[TableInputFormat],
//      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
//      classOf[org.apache.hadoop.hbase.client.Result])
//    hBaseRDD
//  }

  def startList: Iterable[Int] = {
    val tb = new HTable(Conf.conf,tablename)
    val keys = tb.getRegionLocations.navigableKeySet()
    for(k: HRegionInfo <- keys.asScala) yield {
      val starpre = if(k.getStartKey.isEmpty) 0 else {
        val temp = k.getStartKey
        (temp)(0) + 0
      }
      starpre
    }
  }




  /**
   *  get and merge hbase RDD
   */
  def get = (args: Args)=>{
    val sl = scanList(args)
    val gp = if(args.Groupby.isEmpty) List("d") else args.Groupby
    var ret: RDD[(String, Map[String,String])] = Conf.sc.emptyRDD
    for (scan <- sl)  {
      val rdd =gethbaseRDD(scan).map(x =>gpBy(x,gp))
      //rdd.collect()
      ret = ret ++ rdd
    }
    ret
  }

  def singleGet()={}

  def multiGet(args: Args): RDD[(String, Map[String,String])] ={
    require(!args.Range.isEmpty)
    val range = List(DatePoint.toTs(args.Range(0)),DatePoint.toTs(args.Range(1)))
    Conf.conf.set(TableInputFormat.INPUT_TABLE,tablename)
    val ret: RDD[(String, Map[String,String])] = Conf.sc.emptyRDD

    val keyRange = startList.size
    val pool: ExecutorService = Executors.newFixedThreadPool(keyRange)
    //val futures = new Array[Future[RDD[(ImmutableBytesWritable,Result)]]](keyRange)

    val futures = for(key <- startList) yield {
        pool.submit(new Query(key,range,hbaseFilter(args.Filter)))
    }
    var x = 0

    for(future <- futures ){

      val r = future.get()
      println("futures startpre:  "+ x + "   singlerdd: "+ r.count())
      //ret = ret ++ r
      x +=1
    }

    val gp = if(args.Groupby.isEmpty) List("d") else args.Groupby
    ret
  }

}

