package byone.hbase.core.task

import byone.hbase.core.actor.RunWork
import org.slf4j.LoggerFactory


/**
 * Created by liuyou on 14/11/3.
 */
object HTaskManager {

  private val logger = LoggerFactory.getLogger(getClass)
  def get(workId: String): HTask = {
    new HTask("")
  }

  //val queue = TrieMap[String,Int]()

  val MaxSize = 3



  def add(workId: String){
    logger.info("add " + workId + "and curr running size: " + HTaskTracker.running.size)
    if(HTaskTracker.running.size < 3){
      HTaskTracker.statusUpdate(workId, 1)
      HTask.sender ! RunWork(workId)
    }

    else{
      logger.info("add " + workId + "with status 0.")
      HTaskTracker.statusUpdate(workId,0)
    }

    //add(HTaskTracker.get(workId))
  }


  def remove(workId: String) = {
     HTaskTracker.remove(workId)
  }


  def removeAll = ???

  def retain = ???

  def addAll(works: Seq[String]) = works.foreach(add)

}
