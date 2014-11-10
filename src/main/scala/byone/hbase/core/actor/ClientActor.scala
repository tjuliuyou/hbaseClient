package byone.hbase.core.actor

import akka.actor.{Actor, ActorLogging}
import byone.hbase.core.WriteCache
import byone.hbase.core.task.HTaskManager

/**
 * Created by liuyou on 14/11/6.
 */

class ClientActor extends Actor with ActorLogging{

  override def receive: Receive = {
    case Read(taskId) =>
      log.info("read command received. create new task." )
      HTaskManager.add(taskId)

    case Write(data) =>
      log.info("write command received.")
      WriteCache.add(data)

    case Cancel(taskId) =>
      log.info("Cancel command received.")
      HTaskManager.remove(taskId)

    case "flush" => WriteCache.flush
  }
}

case class Read(taskId: String)
case class Write(data: String)
case class Cancel(taskId: String)