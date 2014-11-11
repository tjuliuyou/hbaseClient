package byone.hbase

import org.apache.hadoop.fs.Path
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.HTable
import org.apache.hadoop.hbase.client.coprocessor.Batch
import org.apache.hadoop.hbase.coprocessor.example.generated.ExampleProtos
import org.apache.hadoop.hbase.coprocessor.example.generated.ExampleProtos.RowCountService
import org.apache.hadoop.hbase.ipc.{BlockingRpcCallback, ServerRpcController}

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
    val table = new HTable(conf,"log_data")

    val request = ExampleProtos.CountRequest.getDefaultInstance

    val results = table.coprocessorService(classOf[ExampleProtos.RowCountService],
                  null,null,
    new Batch.Call[ExampleProtos.RowCountService,Long](){
      override def call(counter: RowCountService): Long = {
        val controller = new ServerRpcController()
        val rpcCallback = new BlockingRpcCallback[ExampleProtos.CountResponse]()
        counter.getRowCount(controller,request,rpcCallback)
        val response = rpcCallback.get()
        if(response != null && response.hasCount) response.getCount else 0

      }
    })

  }
}
