package byone.hbase.core.task

import java.util.Calendar

import akka.actor.{ActorSystem, Props}
import byone.hbase.core.actor.{TaskActor, WorkDone}
import byone.hbase.uid.UIDCreater
import org.slf4j.LoggerFactory


/**
 * Created by liuyou on 14/11/3.
 */
class HTask(queryArgs: String) extends java.io.Serializable {

  private var stat = 0
  private var priority = 0
  private val buildtime = Calendar.getInstance.getTime
  private val logger = LoggerFactory.getLogger(classOf[HTask])
  logger.debug("Now create uuid for this task.")
  val id = UIDCreater.uuid

  val info = "Task id: " + id +
    "\r\nQuery Args: " + queryArgs +
    "\r\nCreate at: " + buildtime

  //  private val query = Query.create(queryArgs)

  // def status = query.status

  //def take = query.get()

  def start = {
    println("Task: " + id + "now running.")
    println("1.paser args...\r\n")
    Thread.sleep(3000)
    println("2.get data from hbase...\r\n")
    Thread.sleep(4000)
    println("3.aggregate the data...")
    Thread.sleep(3000)
    println("4.done")
    HTaskTracker.statusUpdate(id,2)
    HTask.sender ! WorkDone(id)
  }

  def restart = {
    println("Task:" + id + "now restart.")
  }

  def cancel = {
    println("Task:" + id + "now cancel.")
  }

  def stop = {
    println("Task:" + id + "now stop.")
  }


}

object HTask {
  val system = ActorSystem("TaskManager")
  val sender = system.actorOf(Props[TaskActor],name = "TaskActor")
}


