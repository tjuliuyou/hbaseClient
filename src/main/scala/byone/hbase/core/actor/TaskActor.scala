package byone.hbase.core.actor

import akka.actor.Actor
import byone.hbase.core.task.HTaskTracker

import scala.collection.script.Update

/**
 * Created by liuyou on 14/11/6.
 */
class TaskActor extends Actor {
  override def receive: Receive = {
    case RunWork(workId) => HTaskTracker.get(workId).start
    case WorkDone(workId)  =>
      if(HTaskTracker.created.nonEmpty) {
        val uid = HTaskTracker.created.head
        HTaskTracker.get(workId).start
      }
      HTaskTracker.remove(workId)
  }
}
case class RunWork(workId: String)
case class WorkDone(workId: String)
