package byone.hbase.core.actor

import akka.actor.Actor
import byone.hbase.core.WriteCache

/**
 * Created by liuyou on 14/11/6.
 */
class CacheActor extends Actor {

  override def receive: Receive = {
    case _:String => { println("flush data ing");WriteCache.flush}
    case "timeup"   => WriteCache.flush
  }
}
