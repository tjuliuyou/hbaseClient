package byone.hbase

import byone.hbase.protobuf.PreAnalyseProtos
import byone.hbase.protobuf.PreAnalyseProtos.{MapEntry, PreAnalyseService}
import byone.hbase.util.Converter
import com.google.protobuf.ByteString
import org.apache.hadoop.fs.Path
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.coprocessor.Batch
import org.apache.hadoop.hbase.client.{HTable, Scan}
import org.apache.hadoop.hbase.ipc.{BlockingRpcCallback, ServerRpcController}
import org.apache.hadoop.hbase.protobuf.ProtobufUtil

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

  def main(args: Array[String]) {
   val table = new HTable(conf,"test1")

    val events = ByteString.copyFrom("abc".getBytes())
    //val request: PreAnalyseProtos.AnalyseRequest = null
    val scan = new Scan()

    val ts = System.currentTimeMillis()
    val group = List("a","b")

    val range = List(Converter.Int2Byte(0,4),Converter.num2Byte(ts/1000,4))
    val protorange = range.map(ByteString.copyFrom).asJava
    val request = PreAnalyseProtos.AnalyseRequest.newBuilder()
        .setScan(ProtobufUtil.toScan(scan))
        .addAllRange(protorange)
        .addAllGroups(group.toIterable.asJava)
        .build()


    println(request.getGroupsList)


    val results = table.coprocessorService(classOf[PreAnalyseProtos.PreAnalyseService],
                  null,null,
    new Batch.Call[PreAnalyseProtos.PreAnalyseService,java.util.List[MapEntry]](){
      override def call(counter: PreAnalyseService): java.util.List[MapEntry] = {
        val controller = new ServerRpcController()
        val rpcCallback = new BlockingRpcCallback[PreAnalyseProtos.AnalyseResponse]()
        counter.getPreData(controller, request, rpcCallback)
        val response = rpcCallback.get()
        //if(response != null && response.isInitialized)
        response.getDataList
      }
    })

    val data = results.values().asScala

    data.foreach(x =>{
      x.asScala.foreach(y=> {
        y.getKvList.asScala.foreach(kv => {
          println(kv.getValue)
        })
      })
    })

    println(results.size())


  }
}
