package byone.hbase

/**
 * Created by liuyou on 14/11/3.
 */
object taskTest {
  def main(args: Array[String]) {
    val handle = Client.read("dd")
    println(handle.id)
  }
}
