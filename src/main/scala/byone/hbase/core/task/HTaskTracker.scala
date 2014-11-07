package byone.hbase.core.task

import scala.collection.concurrent.TrieMap

/**
 * Created by liuyou on 14/11/4.
 */
object HTaskTracker {



  val PREFIX = "task: "

  private val tasks = TrieMap[String,HTask]()
  private val taskStatuses = TrieMap[String,Int]()

  def get(uid: String): HTask = {tasks.getOrElseUpdate(uid,new HTask(""))}

  def create(args: String) = {
    val task = new HTask(args)
    tasks.update(task.id,task)
    task.id
  }

  def list = ???

  def remove(workId: String) ={
    tasks.remove(workId)
    statusUpdate(workId,3)
  }

  def created = {
    taskStatuses.filter(x => x._2 == 0).keys
  }

  def running = {
    taskStatuses.filter(x => x._2 == 1)
  }

  def terminated = {
    taskStatuses.filter(x => x._2 == 4)
  }

  def finished = {
    taskStatuses.filter(x => x._2 == 2)
  }

  def failed = {
    taskStatuses.filter(x => x._2 == 3)
  }

  def statusUpdate(uid:String, status: Int) = {
    taskStatuses.update(uid,status)
  }

  def store = ???



}
