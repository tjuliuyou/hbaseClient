package byone.hbase.core

import java.io.IOException
import byone.hbase.filter.{ByParseFilter, EventComparator, CompareFilter, RowFilter}
import byone.hbase.uid.UniqueId
import byone.hbase.util.{QueryArgs, Constants, DatePoint, Args}
import com.twitter.util.Future
import org.apache.hadoop.hbase.{HBaseConfiguration, Cell}
import org.apache.hadoop.hbase.client.{Result, Scan}
import org.apache.hadoop.hbase.filter.{Filter, FilterList}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark.rdd.RDD
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._

/**
 * Created by dream on 14-8-13.
 */

class Query(args: QueryArgs) extends java.io.Serializable {


  private val logger = LoggerFactory.getLogger(classOf[Query])
  private val family = Constants.FAMILY
  private var range = args.Range
  private var items = args.Items
  private var events = args.Events
  private var filters = args.Filter
  private var groups = if(args.Groups.isEmpty) List("d") else args.Groups
  private var aggres = args.Aggres

//  private val aggitems = {
//    for(ar <- aggres) yield ar.drop(1)
//    .flatten
//  }
  println(filters)
  println(groups)

  private val uid = new UniqueId
  uid.readToCache("hdfs://master1.dream:9000/spark/eventuid.txt")

  def setRange(rg: List[String]) {
    if(rg.size != 2)
      logger.error("range list size must be 2!")
    if(rg(0)>rg(1)) {
      logger.error("start time bigger than stop time")
    }
    range = rg
  }

  def setItems(it: List[String]) {
    items = it
  }

  def setEvents(et: List[String]) {
    events = et
  }
  def setFilter(fr: String) {
    filters = fr
  }

  def setGroups(gp: List[String]) {
    groups = gp
  }

  def setAggres(ag: Seq[Seq[String]]){
    aggres = ag
  }



  def get(): Future[RDD[(String,Map[String,String])]] = {

    val rdd = rawRdd().flatMap(raw => Future(raw.map(grouBy)))
    if(aggres.nonEmpty) {
         val aggargs = for(ar <- aggres) yield {
        val cond = ar.head
        val item = ar.drop(1)
        (cond,item)
        }
      val prerdd  = rdd.flatMap(raw =>{
        //val ag = new Aggre(raw,aggres)
        Future(Aggre.doAggre(raw,aggargs)) })
    prerdd
    } else
      rdd

  }


  def grouBy(raw: (Array[Byte],Map[String,String]))
  : (String,Map[String,String]) = {
    val keys = for(g <- groups) yield {
      raw._2.getOrElse(g,"")
    }
    val ky = keys.foldLeft("")((x,y)=>x+y)
    val key = if(ky.isEmpty) family else ky
    val filterdmap = items.map(x=>{
      x -> raw._2.getOrElse(x,"")
    })


    (key,filterdmap.toMap)



  }

  /**
   * raw Future rdd
   * @return Future[RDD[(String,Map[String,String])]
   */
  def rawRdd(): Future[RDD[(Array[Byte],Map[String,String])]] = {
    logger.info("get future rdds")

    val timeRange = range.map(DatePoint.toTs)
    val scanFilter = {
      if(filters.equals("null") && events.isEmpty)
        null
      else
        hbaseFilter(filters,events)
    }
    val scans = scanList(scanFilter,timeRange,items)

    val futureList = for(scan <- scans) yield Future(hbaseRDD(scan))

    Future.collect(futureList)
          .flatMap(accRDD)
          .flatMap(raw =>Future(raw.map(normalize)))

  }

  def normalize(raw: (ImmutableBytesWritable, Result))
  : (Array[Byte],Map[String,String]) = {
    val navkey = raw._2.getNoVersionMap.firstEntry().getValue
    val retmap = navkey.asScala.map{case (x,y) => {
        new String(x) -> new String(y)
    }}
    raw._1.get -> retmap.toMap
  }



  /**
   * parser filter args and events to filter
   * @param args   : filter args
   * @param events : list of events
   * @return Parsered filter list
   */
  private def hbaseFilter(args:String,events: Seq[String]): FilterList = {
    val flist =new FilterList(FilterList.Operator.MUST_PASS_ALL)
    if(events.nonEmpty){
      val ents = for(event <- events) yield {
        val rowfilter: Filter = new RowFilter(
          CompareFilter.CompareOp.EQUAL,new EventComparator(uid.id(event)))
        rowfilter
      }
      val rowlist: Filter = new FilterList(FilterList.Operator.MUST_PASS_ONE,ents.asJava)
      flist.addFilter(rowlist)
    }

    if(!args.equals("null")){
      flist.addFilter(new ByParseFilter().parseFilterString(args))

    }
    flist
  }

  /**
   * get Scan list for scan
   * @return
   */
  private def scanList = (scanfilter: Filter,timerange: Seq[Array[Byte]],items:Seq[String]) => {
    val area = rowArea(timerange)
    area.map{rows =>
      val scan = new Scan(rows._1,rows._2)
      scan.setCacheBlocks(false)
      scan.setCaching(10000)
      scan.setReversed(true)
      scan.setFilter(scanfilter)
      scan
    }
  }

  /**
   * get all row area ( every region area)
   * @return : map(startkey and stopkey)
   */
  private def rowArea = (range: Seq[Array[Byte]]) => {
    val length = Constants.PRELENGTH
    val regionRange = Constants.REGIONRANGE
    for(num <- 0 until regionRange) yield {
      val pre = DatePoint.Int2Byte(num,length)
      ( pre ++ range(0)) -> (pre ++ range(1))
    }
  }

  /**
   *  get base hbase RDD with one Scan
   */
  def hbaseRDD(scan: Scan) = {
    val tablename = Constants.tablename
    Constants.conf.set(TableInputFormat.INPUT_TABLE, tablename)
    val conf = HBaseConfiguration.create(Constants.conf)
    conf.set(TableInputFormat.SCAN,DatePoint.ScanToString(scan))
    val hBaseRDD = Constants.sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    hBaseRDD
  }

  def accRDD(rawrdd: Seq[RDD[(ImmutableBytesWritable,Result)]]) ={
    val ret: RDD[(ImmutableBytesWritable,Result)] = Constants.sc.emptyRDD
    val rdd = rawrdd.foldLeft(ret)((rhs,left) => rhs ++ left)
    Future.value(rdd)
  }
}
