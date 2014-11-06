package byone.hbase.core

import java.util.Calendar

import byone.hbase.uid.UIDCreater
import net.liftweb.json.JsonParser._
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

  private val query = Query.create(queryArgs)

  def status = query.status

  def take = query.get()

  def start = {

  }

  def restart = ???

  def cancel = ???

  def stop = ???



}

object HTask {

  def apply(args: String) = {
    val handle = new HTask(args)
    TaskQueue.add(handle)
    handle
  }

}

