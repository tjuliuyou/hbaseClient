package byone.hbase.core.actor

import akka.actor.Actor
import byone.hbase.core.task.HTaskManager

/**
 * Created by liuyou on 14/11/6.
 */
class TaskActor extends Actor {
  override def receive: Receive = {
    case RunWork(workId) => HTaskManager.get(workId).start

    case WorkDone(workId)  =>
      HTaskManager.remove(workId)
      HTaskManager.checkLeft()

    case HError(workId) =>
      HTaskManager.get(workId).stop
      HTaskManager.checkLeft()
  }
}
case class RunWork(workId: String)
case class WorkDone(workId: String)
case class HError(workId: String)

