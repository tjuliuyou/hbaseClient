/**
 * Created by liuyou on 2014/8/2.
 */
object test5 {


  def alist: Iterable[Int] = {
    for(i <- 0 to 20) yield {
      i
    }
  }

  def main(args: Array[String]) {
    alist.foreach(x => print(x+", "))
    println()
    for(x <- alist;i <- 1 to 21){

      print("( "+x+", "+i+")")
    }
    println(alist.size)
  }
}
