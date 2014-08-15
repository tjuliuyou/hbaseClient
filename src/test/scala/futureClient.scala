import byone.hbase.core.{Query, Aggre, RwRDD}
import byone.hbase.util.{QueryArgs, Constants, Args}
import net.liftweb.json.JsonParser._

/**
 * Created by dream on 14-8-13.
 */
object futureClient {

  def main(args: Array[String]) {

    // read test.json to class testlist
    implicit val formats = net.liftweb.json.DefaultFormats
    val source = scala.io.Source.fromFile("src/main/resources/test.json").mkString
    val m = parse(source)

    val testlist: Seq[QueryArgs] = m.children.map(_.extract[QueryArgs])

    // using one of testlist

    val thistest = testlist(5)
    val rw = new Query(thistest)
    val futureRDD =rw.get()

//    futureRDD onSuccess (hbaseRDD => {
//        hbaseRDD.collect().foreach(println)
//        println("hbaseRDD count: " + hbaseRDD.count())
//    })

    futureRDD.collect().foreach(println)
            println("hbaseRDD count: " + futureRDD.count())


    val futureRDD2 =rw.get()
    futureRDD2.collect().foreach(println)
    println("hbaseRDD count: " + futureRDD2.count())
    Constants.sc.stop()
    }

}
