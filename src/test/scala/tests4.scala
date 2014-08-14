

/**
 * Created by dream on 8/1/14.
 */
object test4 {

  def main(args: Array[String]) {

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

}
