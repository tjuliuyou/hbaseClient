package byone.hbase.core

import akka.actor.{ActorSystem, Props}
import byone.hbase.core.actor.CacheActor
import org.slf4j.LoggerFactory

import scala.collection.concurrent.TrieMap


/**
 * Created by liuyou on 14/11/6.
 */
object WriteCache {
  private val logger  = LoggerFactory.getLogger(getClass)
  val cached = TrieMap[String,String]()
  val MaxSize = 100

  val system = ActorSystem("writeCache")
  val sender = system.actorOf(Props[CacheActor],name = "cacheActor")
  def add(data: String) = {
    if(cached.size > MaxSize)
      flush
      //sender ! "fullsize"
    logger.info("current cached size: " + cached.size.toString)
    cached += (data -> data)
  }
  def flush= {
    logger.info("fush data: current data: " + cached.size )
    cached.foreach(println)
    cached.clear()
  }
  def clear = cached.clear()
}
