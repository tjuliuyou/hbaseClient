package byone.hbase.core

import byone.hbase.utils.Args
import byone.hbase.uid.UniqueId
import byone.hbase.filter._
import byone.hbase.utils.{Constants, DatePoint}

import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.filter.{Filter, FilterList}
import org.apache.hadoop.hbase.{HRegionInfo, Cell}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat

import scala.collection.JavaConverters._
import org.apache.spark.rdd.RDD
import java.util.concurrent.{Executors, ExecutorService}
/**
 * Created by dream on 7/7/14.
 *
 * Core RDD class read hbase to local RDD
 * @param table : table name
 */
class RwRDD(table : String) extends java.io.Serializable {
  private val serialVersionUID = 6529685098267757690L
  private val tablename = Constants.tablename
  private val regionRange = Constants.REGIONRANGE

  private val uid = new UniqueId
  uid.readToCache("hdfs://master1.dream:9000/spark/eventuid.txt")

  /**
   * Get merged rdd using multi threads
   * @param args args to get rdd {@see byone.hbase.utils.Args}
   * @return a raw rdd
   */
  def get(args:Args): RDD[(String,Map[String,String])] = {
    get(args, true)
  }

  /**
   * Get merged rdd using multi threads or single thread
   * @param args args to get rdd {@see byone.hbase.utils.Args}
   * @param rsyc true: multi thread false: single thread
   * @return a raw rdd
   */
  def get(args: Args, rsyc: Boolean): RDD[(String,Map[String,String])] = {

    require(args.Range.nonEmpty)
    val range = List(DatePoint.toTs(args.Range(0)),DatePoint.toTs(args.Range(1)))
    val gp = if(args.Groupby.isEmpty) List("d") else args.Groupby
    val filter = {
      if(args.Filter.equals("null") && args.Events.isEmpty)
        null
      else
        hbaseFilter(args.Filter,args.Events)
    }
    val retRdd = if(rsyc){
      Constants.conf.set(TableInputFormat.INPUT_TABLE,tablename)
      multiGet(filter,range,args.Items)

    } else {
      val sl = scanList(filter,range,args.Items)
      var ret: RDD[(ImmutableBytesWritable,Result)] = Constants.sc.emptyRDD
      for (scan <- sl) yield {
        val rdd =gethbaseRDD(scan)
        rdd.count()
        ret = ret ++ rdd
      }
      ret
    }
    retRdd.map(x => gpBy(x, gp))
  }

  /**
   * parser filter args and events to filter
   * @param args   : filter args
   * @param events : list of events
   * @return Parsered filter list
   */
  private def hbaseFilter(args:String,events: List[String]): FilterList = {
    val flist =new FilterList(FilterList.Operator.MUST_PASS_ALL)
    if(events.nonEmpty){
      val ents = for(event <- events) yield {
        val rowfilter: Filter = new RowFilter(
         CompareFilter.CompareOp.EQUAL,new EventComparator(uid.id(event)))
        rowfilter
      }
      println("row filter")
      val rowlist: Filter = new FilterList(FilterList.Operator.MUST_PASS_ONE,ents.asJava)
      flist.addFilter(rowlist)
    }

    if(!args.equals("null")){
      flist.addFilter(new ByParseFilter().parseFilterString(args))

    }
    flist
  }

  /**
   * get all row area ( every region area)
   * @return : map(startkey and stopkey)
   */
  private def rowArea = (range: List[Array[Byte]]) => {
    for(num <- 0 until regionRange) yield {
       val pre = DatePoint.Int2Byte(num,Constants.PRELENGTH)
       ( pre ++ range(0)) -> (pre ++ range(1))
    }
  }

  /**
   * get Scan list for scan
   * @return
   */
  def scanList = (filter: Filter,range: List[Array[Byte]],items:List[String]) => {
    val area = rowArea(range)
    area.map{rows =>
      val sn = new Scan(rows._1,rows._2)
      sn.setCacheBlocks(false)
      sn.setCaching(10000)
      sn.setReversed(true)
      if(items.nonEmpty)
        items.foreach(item =>sn.addColumn("d".getBytes,item.getBytes))
        sn
    }
  }

  /**
   *  map raw hbase data(ImmutableBytesWritable, Result) to (key,value) by group list
   *
   *
   */
  def gpBy = (raw: (ImmutableBytesWritable, Result), gp: List[String]) => {
    val retmap = scala.collection.mutable.Map[String, String]()
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
    (ky,retmap.toMap)
  }

  /**
   *  get base hbase RDD with one Scan
   */
  def gethbaseRDD = (scan: Scan) =>  {
    Constants.conf.set(TableInputFormat.INPUT_TABLE, tablename)
    Constants.conf.set(TableInputFormat.SCAN,DatePoint.ScanToString(scan))
    val hBaseRDD = Constants.sc.newAPIHadoopRDD(Constants.conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    hBaseRDD
  }

  /**
   * get each region start keys
   * @return Iterable startkey (int)
   */
  def startList: Iterable[Int] = {
    val tb = new HTable(Constants.conf,tablename)
    val keys = tb.getRegionLocations.navigableKeySet()
    for(k: HRegionInfo <- keys.asScala) yield {
      val starpre = if(k.getStartKey.isEmpty) 0 else {
        val temp = k.getStartKey
        temp(0) + 0
      }
      starpre
    }
  }

  /**
   * get rdds from hbase using multi thread
   * @param filter   :scan filter list
   * @param range    :time range
   * @param items    :get back items to display
   * @return RDD[(ImmutableBytesWritable,Result)]
   */
  def multiGet(filter: Filter,range: List[Array[Byte]],items:List[String])
    : RDD[(ImmutableBytesWritable,Result)] ={

    var ret: RDD[(ImmutableBytesWritable,Result)] = Constants.sc.emptyRDD
    val keyRange = startList.size
    val pool: ExecutorService = Executors.newFixedThreadPool(keyRange)
    val futures = for(key <- startList) yield {
        pool.submit(new Query(key,filter,range,items))
    }
    for(future <- futures ){
      val r = future.get()
      ret = ret ++ r
    }
    pool.shutdown()
    ret
  }
}

