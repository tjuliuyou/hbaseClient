import byone.hbase.uid.{EventFactory, RandEvent}
import byone.hbase.utils.{DatePoint, Conf, ScanCovert}
import java.util.concurrent.{Future, Callable, Executors, ExecutorService}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.regionserver.BloomType
import org.apache.hadoop.hbase.{HBaseConfiguration, HRegionInfo, HColumnDescriptor, HTableDescriptor}
import org.apache.spark.rdd.RDD
import scala.collection.JavaConverters._
import scala.collection.mutable.Map
//import scala.Predef.String

/**
 * Created by dream on 8/1/14.
 */
object tests4 {

  def ScanToString = (scan : Scan) => new ScanCovert().coverToScan(scan)
  val tablename ="log_data"
  def createtable = {

    val admin = new HBaseAdmin(Conf.conf)
    if(admin.tableExists(tablename)){
      admin.disableTable(tablename)
      admin.deleteTable(tablename)
      println("drop table: '" +tablename + "' successfully.")
    }

    val desc : HTableDescriptor = new HTableDescriptor(tablename)
    val hdes: HColumnDescriptor = new HColumnDescriptor("d".getBytes)
    hdes.setInMemory(true)
    hdes.setMaxVersions(1)
    hdes.setCompressionType(Algorithm.SNAPPY)
    hdes.setBloomFilterType(BloomType.ROW)
    desc.addFamily(hdes)

    def getSplits(startkey: Int, stopkey: Int, num: Int): Array[Array[Byte]] ={
      val range = stopkey - startkey
      val rangeIncrement = range/(num-1)
      val ret =for(i <- 0 until (num-1)) yield {
        val key = startkey + rangeIncrement*i
        RandEvent.Int2Byte(key,1) //++ RandEvent.Int2Byte(Int.MaxValue, 4)
      }
      ret.toArray
    }

    admin.createTable(desc,getSplits(16,256,16))

    println("create table: '" +tablename + "' successfully.")

  }



  def watchtable = {

    val tb = new HTable(Conf.conf,tablename)
    val keys = tb.getRegionLocations.navigableKeySet()


    val keysseq = for(k: HRegionInfo <- keys.asScala) yield {
      k.getStartKey.foreach(x=>print(x + ","))
      print("    ")
      k.getEndKey.foreach(x=>print(x.toInt + ","))
      println()
      (k.getStartKey,k.getEndKey)
    }
  }

  def putdata = {
    val tb = new HTable(Conf.conf,tablename)
    //val tbutil = new HTableUtil()

    tb.setAutoFlush(false)
    tb.setWriteBufferSize(10*1024*1024)
    var a: Int = 0
    while (a < 1000){
      a += 1
      val plist = EventFactory.rand(1000)
      if(a%10 == 0) println(a*1000)
      //tb.put(plist.asJava)
      HTableUtil.bucketRsPut(tb,plist.asJava)
    }

  }


  def startList: Array[Int] = {  //: List[Scan]

    val tb = new HTable(Conf.conf,tablename)
    val keys = tb.getRegionLocations.navigableKeySet()

    val keysseq = for(k: HRegionInfo <- keys.asScala) yield {
      val starpre = if(k.getStartKey.isEmpty) 0 else {
        val temp = k.getStartKey
        (temp)(0) + 0
      }

//      val stoppre = if(k.getEndKey.isEmpty) -1 else {
//        val temp = k.getEndKey
//        (temp)(0) -1
//      }


      starpre
    }
    keysseq.toArray.sorted
  }


  def scanOne(scan: Scan) = {

  }

  def totolCount = {

    val sn = new Scan()
    sn.setCacheBlocks(false)
    sn.setCaching(10000)
    sn.setReversed(true)
    Conf.conf.set(TableInputFormat.INPUT_TABLE, Conf.tablename)
    Conf.conf.set(TableInputFormat.SCAN,ScanToString(sn))
    val hbaseRDD = Conf.sc.newAPIHadoopRDD(Conf.conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])
    val tmp = hbaseRDD.count()
    println("hbaseRDD count: " + tmp)
  }

  val ghconn = HConnectionManager.createConnection(Conf.conf)


  def danCount = {

    val range: List[String] = List("08/08/2014 14:44:57","08/08/2014 14:46:14")

    val startTs =  DatePoint.toTs(range(0))
    val stopTs = DatePoint.toTs(range(1))
    val list = startList
    var mconts:Long =0
    var counts: Long = 0
    for(startkey <- list) {
      println(" -------------")
      var ret: RDD[(ImmutableBytesWritable,Result)] = Conf.sc.emptyRDD
      for(i <- startkey to (startkey +15)) {
        val pre = DatePoint.Int2Byte(i,1)
        val starrow = pre ++ startTs
        val stoprow = pre ++ stopTs
        val sn = new Scan(starrow,stoprow)
        starrow.foreach(x=>print(x + ","))
        print("     ")
        stoprow.foreach(x=>print(x.toInt + ","))
        println(" ")
        sn.setCacheBlocks(false)
        sn.setCaching(10000)
        sn.setReversed(true)
        Conf.conf.set(TableInputFormat.INPUT_TABLE, tablename)
        Conf.conf.set(TableInputFormat.SCAN,ScanToString(sn))
        val hbaseRDD = Conf.sc.newAPIHadoopRDD(Conf.conf, classOf[TableInputFormat],
          classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
          classOf[org.apache.hadoop.hbase.client.Result])
        val tmp = hbaseRDD.count()
        counts += tmp
        println("hbaseRDD count: " + tmp)
        ret = ret ++ hbaseRDD
      }
      val temp = ret.count()
      mconts += temp
      println("startkey : "  + startkey+"    hbaseRDD count "+ temp)
    }
    println("counts: " + counts)
    println("mcounts: " + counts)
  }



  def main(args: Array[String]) {
//    createtable
    //watchtable
    //putdata



//    danCount
    totolCount
  //val list = startList
    //list.foreach(x=>println( x +","))
////
////
////     //val pool:ExecutorService = Executors.newFixedThreadPool(16)
//
//    val range: List[String] = List("04/08/2014 10:15:17","05/08/2014 19:15:18")
//
//    val startTs =  DatePoint.toTs(range(0))
//    val stopTs = DatePoint.toTs(range(1))
//
//    val threads = new Array[Thread](list.length)
//    var i = 0
//    for(startkey <- list){
//
//      threads(i) = new Thread(new Query(startTs, stopTs, startkey.toByte, 16, ""))
//
//      threads(i).start()
//      i += 1
//    }
//
//    threads.foreach(_.join())
//
    println("thread end")

  }



  class Query(startTime:Array[Byte], endTime:Array[Byte], startKey:Byte, range:Int, Filter:String) extends Runnable
  {
    // parse Filter to ScanFilter
    // ...
    val pool:ExecutorService = Executors.newFixedThreadPool(range)

    def run()
    {
      try{
          val futures = new Array[Future[RDD[(ImmutableBytesWritable,Result)]]](range)
          var ret: RDD[(ImmutableBytesWritable,Result)] = Conf.sc.emptyRDD
          for (i <- 0 until range)
          {
            val pre = DatePoint.Int2Byte(startKey+i,1)
            val startRow = pre ++ startTime
            val stopRow = pre ++ endTime
            futures(i) = pool.submit(new RegionQuery(startRow, stopRow, i))

          }
           var x = 0
          for(future <- futures ){

              val r = future.get()
              println("futures startpre:  "+ x + "   singlerdd: "+ r.count())
              ret = ret ++ r
            x +=1
          }

          println("startkey: "+ startKey.toInt +"  ret count: " + ret.count())
      } finally {
        pool.shutdown()
      }
    }
  }

  class RegionQuery(startRow:Array[Byte], stopRow:Array[Byte], num:Int) extends Callable[RDD[(ImmutableBytesWritable,Result)]]
  {
    def call() : RDD[(ImmutableBytesWritable,Result)] =
    {

      val sn = new Scan(startRow, stopRow)
      //val sn = new Scan()
      sn.setCacheBlocks(true)
      sn.setCaching(10000)
      sn.setReversed(true)

      Conf.conf.set(TableInputFormat.INPUT_TABLE,tablename)
     // val conf = new HBaseConfiguration(Conf.conf)
      val conf = HBaseConfiguration.create(Conf.conf)
      conf.set(TableInputFormat.SCAN,ScanToString(sn))
      val singlerdd = Conf.sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
        classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
        classOf[org.apache.hadoop.hbase.client.Result])
      //print("startpre:  "+(startRow(0)).toInt+"   ")
      //println("startpre:  "+(startRow(0)).toInt+ "   singlerdd: "+singlerdd.count())
      singlerdd

    }
  }
}
