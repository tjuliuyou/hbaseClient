package byone.hbase

/**
 * Created by liuyou on 14/11/7.
 */
object WriteTest {

  def main(args: Array[String]) {

    for(i <- 1000 to 1100) {
//      if(i % 15 == 0)
//        Thread.sleep(2000)
      RsyncClient.writeDataToHBase("data"+i.toString)
      //    rdd onSuccess(x => println("rdd already!"))
      //    while(true){
      //      println(handle.status)
      //      Thread.sleep(5000)
      //    }
    }

  }


}
