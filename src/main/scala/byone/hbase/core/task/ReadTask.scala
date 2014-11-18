package byone.hbase.core.task

import java.util.Calendar

import akka.actor.{ActorSystem, Props}
import byone.hbase.core.{Query, QueryArgs}
import byone.hbase.core.actor.{HError, TaskActor, WorkDone}
import byone.hbase.uid.UIDCreater
import byone.hbase.util.{Converter, Constants}
import net.liftweb.json.JsonParser._
import org.apache.hadoop.hbase.client.HTable
import org.slf4j.LoggerFactory
import scala.collection.JavaConverters._

/**
 * Created by liuyou on 14/11/3.
 */
class ReadTask(queryArgs: String) extends java.io.Serializable {

  private[ReadTask] val buildtime = Calendar.getInstance.getTime
  private[ReadTask] val logger = LoggerFactory.getLogger(getClass)



  private[ReadTask] var stat = 0
  private[ReadTask] var errors = 0
  private[ReadTask] var priority = 0

  logger.debug("Now create uuid for this task.")
  val id = UIDCreater.uuid

  private val family = Constants.dataFamily(0)
  private val tablename = Constants.dataTable
 // private val constConnect = new HTable(Constants.conf, tablename)
  //val readArgs:
  updateStatus(1)
  private lazy val args = parseArgs(queryArgs)

  private lazy val range: Seq[String] = args.Range.getOrElse({
    updateStatus(-1,"did not set time range or with error format.")
    Seq.empty
  })
  if (range.length != 2 || range(0) > range(1)) {
    updateStatus(-1,"time range size must be 2 or start time big than stop time.")

  }
  private lazy val events = args.Events.getOrElse(Seq.empty)
  private lazy val filters = args.Filter.getOrElse("")


  private lazy val aggres = {
    val in = args.Aggres.getOrElse(Seq.empty)
    if (in.nonEmpty) {
      for (ar <- in) yield {
        val cond = ar.head
        val item = ar.last
        (cond, item)
      }
    } else Map.empty[String,String]
  }.toMap

  private lazy val groups = args.Groups.getOrElse(Seq.empty)
  private lazy val aggitems = aggres.values


  private lazy val items = (args.Items.getOrElse(Seq.empty) ++ groups ++ aggitems).toSet



  def info = "Task id: " + id +
    "\r\nQuery Args: " + queryArgs +
    "\r\nCreate ReadTask at: " + buildtime

 //private val query = Query.create(queryArgs)

  // def status = query.status

  //def take = query.get()



  def start = {
    println("Task: " + id + "now running.")
    println("1.paser args...\r\n")
    updateStatus(2)
    val query = Query.create(tablename,family)
    if(groups.isEmpty) {
      query.rawRdd(filters, events, range, items)
    } else {
      query.resRdd(filters, events, range, items,groups,aggres)
    }
//    println("2.get data from hbase...\r\n")
//    Thread.sleep(4000)
//    println("3.aggregate the data...")
//    Thread.sleep(3000)
//    println("4.done")
//    HTaskManager.statusUpdate(id, 2)
    //ReadTask.sender ! WorkDone(id)
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

  def get = {}

  def status = stat match {
    case -1 => "Error..."
    case 0 => "Init resource..."
    case 1 => "Parser args..."
    case 2 => "Reading data from HBase..."
    case 3 => "Aggregate with args..."
    case 4 => "Done."
    case _ => "Fail with unknown error."
  }

  private def updateStatus(currStatus: Int,info: String = "") {
    if (currStatus == -1){
      ReadTask.sender ! HError(id)
      logger.error(info)
    }
    stat = currStatus
  }

  private[ReadTask] def parseArgs(queryAr: String): QueryArgs = {

    logger.info("Parser args...")
    updateStatus(1)
    try {
      implicit val formats = net.liftweb.json.DefaultFormats
      parse(queryAr).extract[QueryArgs]
    } catch {
      case e: Exception => {
        updateStatus(-1,"Parser QueryArgs with liftweb json error: " + e.getMessage)
        QueryArgs(None,None,None,None,None,None,None)
      }
    }
  }
 // def close() = constConnect.close()
}

object ReadTask {

  val system = ActorSystem("TaskManager")
  val sender = system.actorOf(Props[TaskActor], name = "TaskActor")

  private val logger = LoggerFactory.getLogger(getClass)

}


