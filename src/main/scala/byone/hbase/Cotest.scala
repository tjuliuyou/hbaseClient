package byone.hbase

import byone.hbase.protobuf.PreAnalyseProtos
import com.google.protobuf.ByteString
import scala.collection.JavaConverters._
/**
 * Created by liuyou on 14/11/11.
 */
object Cotest {

//  // Default global Hbase Configurations
//  private val HBASE_CONF_PATH = "src/main/resources/conf/hbase-site.xml"
//  private val YARN_CONF_PATH = "src/main/resources/conf/yarn-site.xml"
//  private val MAPR_CONF_PATH = "src/main/resources/conf/mapred-site.xml"
//  val conf = HBaseConfiguration.create
//  conf.addResource(new Path(HBASE_CONF_PATH))
//  conf.addResource(new Path(YARN_CONF_PATH))
//  conf.addResource(new Path(MAPR_CONF_PATH))

  def main(args: Array[String]) {
   // val table = new HTable(conf,"log_data")

    val events = ByteString.copyFrom("abc".getBytes())
    //val request: PreAnalyseProtos.AnalyseRequest = null


    val group = List("a","b")
    val request = PreAnalyseProtos.AnalyseRequest.newBuilder()
        .setFilterString("null")
        .addAllGroups(group.toIterable.asJava)
        .build()
        //.setEvents(2,events).build()


    println(request.getGroupsList)


//    val results = table.coprocessorService(classOf[PreAnalyseProtos.PreAnalyseService],
//                  null,null,
//    new Batch.Call[PreAnalyseProtos.PreAnalyseService,Long](){
//      override def call(counter: PreAnalyseService): Long = {
//        val controller = new ServerRpcController()
//        val rpcCallback = new BlockingRpcCallback[PreAnalyseProtos.AnalyseResponse]()
//        counter.getPreData(controller,request,rpcCallback)
//        val response = rpcCallback.get()
//        if(response != null && response.hasCount) response.getCount else 0
//
//      }
//    })

  }
}
