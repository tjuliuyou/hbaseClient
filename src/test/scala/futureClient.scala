import byone.hbase.core.{Query, Aggre, RwRDD}
import byone.hbase.util.{Constants, Args}
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
    val testlist: List[Args] = m.children.map(_.extract[Args])

    // using one of testlist

    val thistest = testlist(1)
    val rw = new Query(thistest)
    val futureRDD =rw.get()


    // hbaseRDD.collect().foreach(x =>println(x._2))
    futureRDD onSuccess (hbaseRDD => {
        println("hbaseRDD count: " + hbaseRDD.count())
    })

    Constants.sc.stop()
    }

}
