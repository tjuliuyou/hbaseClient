package byone.hbase.core.actor

import akka.actor.{Actor, ActorLogging}
import byone.hbase.core.WriteCache
import byone.hbase.core.task.HTaskManager

/**
 * Created by liuyou on 14/11/6.
 */

class ClientActor extends Actor with ActorLogging{

  override def receive: Receive = {
    case Read(workId) =>
      log.info("read command received. workId :" + workId )
      HTaskManager.add(workId)

    case Write(data) =>
      log.info("write command received.")
      WriteCache.add(data)

    case Cancel(task) =>
      log.info("Cancel command received.")
      HTaskManager.remove(task)

    case "flush" => WriteCache.flush
  }
}

case class Read(workId: String)
case class Write(data: String)
case class Cancel(workId: String)