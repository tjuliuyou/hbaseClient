import byone.hbase.core.Table
import byone.hbase.uid.UniqueId
import byone.hbase.util.{DatePoint, Constants}

/**
 * Created by dream on 8/1/14.
 */
object test4 {

  def test{
    val event = Map("aaa"->"111","bbb"->"222","ccc"->"333","ddd"->"444","fff"->"666")

    val groups = Seq("fff","eee")

    val items = Seq("aaa","ccc","fff","ggg")

    val aggres = Seq("ccc")

    val keys = for(g <- groups) yield {
      event.getOrElse(g,"")
    }

    val key = keys.foldLeft("")((x,y)=>x+y)
    println(key)

    val display = items.map(x=>{
      val y = event.getOrElse(x,"null")
      println(x +" : is empty " + y.isEmpty)
      x-> y.toDouble
    })

    val ret = (key,display)
    println("ret: " +ret)
  }


  def main(args: Array[String]) {

//    val uid = new UniqueId
//
//    for(x <- 1 to 13) {
//      val temp = uid.toName(x)
//      println(new String(temp))
//    }
//    println("--------------------------")
//    uid.getCached.foreach(x=>println(new String(x)))
//
//    uid.ids.foreach(y => {
//      //y.foreach(x=>print(x + ","))
//      println(new String(y) + "   " + y.length)
//    })





  }

}
