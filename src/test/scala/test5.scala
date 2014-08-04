import scala.util.Random

/**
 * Created by liuyou on 2014/8/2.
 */
object test5 {

  def Int2Byte(num: Int,max: Int = 3):Array[Byte] = {
    val ret = for(i <- 0 until max) yield {
      (num >>>(8*(max-i-1)) & 0xff).toByte
    }
    ret.toArray
  }

  def num2Byte(num: Long,max: Int = 8):Array[Byte] = {
    val ret = for(i <- 0 until max) yield {
      (num >>>(8*(max-i-1)) & 0xff).toByte
    }
    ret.toArray
  }

  def alist: Iterable[Array[Byte]] = {
    for(i <- 0 to 20) yield {
      val ts = System.currentTimeMillis()
      val pre = Random.nextInt(256)
      Int2Byte(pre,1) ++ num2Byte(ts/1000,4) ++ Int2Byte(Random.nextInt(5)) ++ num2Byte(ts%1000,3)
    }
  }

  def main(args: Array[String]) {
//    alist.foreach(x =>{ x.foreach(sub => print(sub+","));println})
//    println()
      val a = 0.toByte
      val b = 0.toByte
      val c = 5.toByte
    val x = Array(a,b,c)
    val y =for(sub <- x) yield {
      sub.toChar
    }

  }
}
