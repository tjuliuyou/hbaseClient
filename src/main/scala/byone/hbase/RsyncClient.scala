package byone.hbase

import akka.actor.{Props, ActorSystem}
import byone.hbase.core._
import byone.hbase.core.actor.{Cancel, Write, Read, ClientActor}
import byone.hbase.core.task.{HTaskTracker, HTaskManager, HTask}


/**
 * Created by liuyou on 14/11/3.
 */
object RsyncClient {

  val system = ActorSystem("hbaseClient")
  val router = system.actorOf(Props[ClientActor],name = "router")

  def queryData(args: String): String = {
    val handle = HTaskTracker.create(args)
    router ! Read(handle)
    handle
  }

  def writeDataToHBase(data: String) = router ! Write(data)

  def flushToHBase() = router ! "flush"

  def cancelQueryTask(workId: String) = router ! Cancel(workId)

  def taskQueue =  ???

}
