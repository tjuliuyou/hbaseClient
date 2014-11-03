package byone.hbase.core

import byone.hbase.uid.UIDCreater
import byone.hbase.util.Logging
import net.liftweb.json.JsonParser._
import org.slf4j.LoggerFactory

/**
 * Created by liuyou on 14/11/3.
 */
class Task(info: String) extends Logging {
  private var stat = 0
  private var priority = 0
  logDebug("Now create uuid for this task.")
  val id = UIDCreater.uuid

  val args = parser(info)

  private def updateStatus(ss: Int) = {stat = ss}

  private def argsChecker(args: QueryArgs) = {

    //    if (args.Range.length != 2) {
    //      logger.error("range list size must be 2!")
    //      stat = -1
    //    }
    //    if (args.Range(0) > args.Range(1)) {
    //      logger.error("start time bigger than stop time.")
    //      stat = -1
    //    }

  }

  private def parser(args: String) = {

    updateStatus(1)
    logInfo("Parser args...")
    implicit val formats = net.liftweb.json.DefaultFormats
    val source = scala.io.Source.fromFile("src/main/resources/test.json").mkString
    val m = parse(source)

    val testlist: Seq[QueryArgs] = m.children.map(_.extract[QueryArgs])
    testlist(0)
  }

  def get = {
    //
    //    val query = Query.create(args)
    //    val rdd = query.get()
  }
  def stop = ???

  def start = {

  }
  def restart = ???
  def cancel = ???

  def status = stat match {
    case -1 => "Error..."
    case 0 => "Init resource..."
    case 1 => "Parser args..."
    case 2 => "Reading data from HBase..."
    case 3 => "Aggregate with args..."
    case 4 => "Done."
    case _ => "Fail with unknown error."
  }


}

object Task {

  def apply(args: String) = {
    val handle = new Task(args)
    TaskManager.add(handle)
    handle
  }

}


object TaskManager extends Logging {

  //private val logger = LoggerFactory.getLogger(objectOf[TaskManager])

  def TaskQueue = ???

  def add(workId: String) = {

  }
  def add(tk: Task) ={
    logInfo("add to taskManager.")

  }
  def remove(workId: String) = ???
  def remove(task: Task) = ???

  private var workThread = 0

}
