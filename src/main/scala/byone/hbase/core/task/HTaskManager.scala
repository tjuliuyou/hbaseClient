package byone.hbase.core.task

import byone.hbase.core.actor.RunWork
import org.slf4j.LoggerFactory

import scala.collection.concurrent.TrieMap

/**
 * Created by liuyou on 14/11/3.
 */
object HTaskManager {

  private val logger = LoggerFactory.getLogger(getClass)

  private val manager = new HTaskManager

  def get(uid: String): ReadTask = {manager.tasks.getOrElseUpdate(uid,new ReadTask(""))}

  val MaxSize = manager.MAXTHREAD

  def create(args: String) = {
    val task = new ReadTask(args)
    val taskId = task.id
    manager.tasks.update(taskId,task)
    taskId
  }

  def add(workId: String){
    logger.info("add " + workId + "and curr running size: " + manager.running.size)
    if(manager.running.size < MaxSize){
      statusUpdate(workId, 1)
      ReadTask.sender ! RunWork(workId)
    }

    else{
      logger.info("add " + workId + "with status 0.")
      statusUpdate(workId,0)
    }
  }


  def checkLeft() = {
    if(manager.created.nonEmpty){
      val uid = manager.created.head
      statusUpdate(uid, 1)
      ReadTask.sender ! RunWork(uid)
    }
  }



  def removeAll = ???

  def retain = ???

  def addAll(works: Seq[String]) = works.foreach(add)

  def remove(workId: String) ={
    println("before remove task:" + workId +" created.size: " + manager.created.size.toString)
    manager.tasks.remove(workId)
    statusUpdate(workId,3)
    logger.info("after remove task created.size: " + manager.created.size.toString)
  }

  def store = ???

  def statusUpdate(uid:String, status: Int) = {
    manager.taskStatuses.update(uid,status)
  }



}

class HTaskManager(workThread: Int = 3) {

  private val logger = LoggerFactory.getLogger(getClass)

  val PREFIX = "task: "

  val MAXTHREAD = workThread

  private[HTaskManager] val tasks = TrieMap[String,ReadTask]()
  private[HTaskManager] val taskStatuses = TrieMap[String,Int]()

  /**
   *  value                 status
   *  0                     created
   *  1                     running
   *  2                     finished
   *  3                     failed
   *  4                     terminated
   */

  def created = {
    taskStatuses.filter(x => x._2 == 0).keys
  }

  def running = {
    taskStatuses.filter(x => x._2 == 1)
  }

  def finished = {
    taskStatuses.filter(x => x._2 == 2)
  }

  def failed = {
    taskStatuses.filter(x => x._2 == 3)
  }

  def terminated = {
    taskStatuses.filter(x => x._2 == 4)
  }






}