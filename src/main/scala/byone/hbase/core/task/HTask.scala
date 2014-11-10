package byone.hbase.core.task

import java.util.Calendar

import akka.actor.{ActorSystem, Props}
import byone.hbase.core.QueryArgs
import byone.hbase.core.actor.{HError, TaskActor, WorkDone}
import byone.hbase.uid.UIDCreater
import net.liftweb.json.JsonParser._
import org.slf4j.LoggerFactory


/**
 * Created by liuyou on 14/11/3.
 */
class HTask(queryArgs: String) extends java.io.Serializable {

  private[byone] var stat = 0
  private[byone] var errors = 0
  private[byone] var priority = 0
  private[byone] val buildtime = Calendar.getInstance.getTime
  private[byone] val logger = LoggerFactory.getLogger(classOf[HTask])
  logger.debug("Now create uuid for this task.")
  val id = UIDCreater.uuid

  val info = "Task id: " + id +
    "\r\nQuery Args: " + queryArgs +
    "\r\nCreate at: " + buildtime

  private def parser = {

    logger.info("Parser args...")
    updateStatus(1)
    try {
      implicit val formats = net.liftweb.json.DefaultFormats
      val args = parse(queryArgs).extract[QueryArgs]
      if (args.Range.get.length != 2) {
        logger.error("range list size must be 2!")
        errors += 1
      }
      if (args.Range.get(0) > args.Range.get(1)) {
        logger.error("start time bigger than stop time.")
        errors += 1
      }
      args
    } catch {
      case e: Exception => {
        logger.error("Parser QueryArgs with liftweb json error: " + e.getMessage)
        errors += 1
      }
    }
    finally {
      if(errors > 0)
        updateStatus(-1)
    }


  }

  //  private val query = Query.create(queryArgs)

  // def status = query.status

  //def take = query.get()

  def start = {
    println("Task: " + id + "now running.")
    println("1.paser args...\r\n")
    parser
    println("2.get data from hbase...\r\n")
    Thread.sleep(4000)
    println("3.aggregate the data...")
    Thread.sleep(3000)
    println("4.done")
    HTaskManager.statusUpdate(id,2)
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

  def status = stat match {
    case -1 => "Error..."
    case 0 => "Init resource..."
    case 1 => "Parser args..."
    case 2 => "Reading data from HBase..."
    case 3 => "Aggregate with args..."
    case 4 => "Done."
    case _ => "Fail with unknown error."
  }

  private def updateStatus(currStatus: Int){
   if(currStatus == -1)
     HTask.sender ! HError(id)
    stat = currStatus
  }

}

object HTask {


  val system = ActorSystem("TaskManager")
  val sender = system.actorOf(Props[TaskActor],name = "TaskActor")

  private val logger = LoggerFactory.getLogger(getClass)


}


