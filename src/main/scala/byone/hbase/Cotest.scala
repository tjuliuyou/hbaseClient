package byone.hbase

import byone.hbase.protobuf.PreAnalyseProtos
import byone.hbase.protobuf.PreAnalyseProtos.{MapEntry, PreAnalyseService}
import com.google.protobuf.ByteString
import org.apache.hadoop.fs.Path
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.HTable
import org.apache.hadoop.hbase.client.coprocessor.Batch
import org.apache.hadoop.hbase.ipc.{BlockingRpcCallback, ServerRpcController}
import scala.collection.JavaConverters._
import scala.collection.mutable

/**
 * Created by liuyou on 14/11/11.
 */
object Cotest {

  // Default global Hbase Configurations
  private val HBASE_CONF_PATH = "src/main/resources/conf/hbase-site.xml"
  private val YARN_CONF_PATH = "src/main/resources/conf/yarn-site.xml"
  private val MAPR_CONF_PATH = "src/main/resources/conf/mapred-site.xml"
  val conf = HBaseConfiguration.create
  conf.addResource(new Path(HBASE_CONF_PATH))
  conf.addResource(new Path(YARN_CONF_PATH))
  conf.addResource(new Path(MAPR_CONF_PATH))

  def main(args: Array[String]) {
   val table = new HTable(conf,"test1")

    val events = ByteString.copyFrom("abc".getBytes())
    //val request: PreAnalyseProtos.AnalyseRequest = null


    val group = List("a","b")
    val request = PreAnalyseProtos.AnalyseRequest.newBuilder()
        .setFilterString("SingleColumnValueFilter ('d','hostIpAddr',=,'binary:10.133.64.2')")
        .addAllGroups(group.toIterable.asJava)
        .build()


    println(request.getGroupsList)


    val results = table.coprocessorService(classOf[PreAnalyseProtos.PreAnalyseService],
                  null,null,
    new Batch.Call[PreAnalyseProtos.PreAnalyseService,mutable.Buffer[MapEntry]](){
      override def call(counter: PreAnalyseService): mutable.Buffer[MapEntry] = {
        val controller = new ServerRpcController()
        val rpcCallback = new BlockingRpcCallback[PreAnalyseProtos.AnalyseResponse]()
        counter.getPreData(controller,request,rpcCallback)
        val response = rpcCallback.get()
        if(response != null && response.isInitialized)
          response.getItemsList.asScala
        else {
          val mp =PreAnalyseProtos.MapEntry.newBuilder.setKey("a").setValue("b").build
          mutable.Buffer(mp)
        }
      }
    })

    println(results.size())


  }
}
