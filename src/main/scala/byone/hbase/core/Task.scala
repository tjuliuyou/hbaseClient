package byone.hbase.core

import java.util.Calendar

import byone.hbase.uid.UIDCreater
import byone.hbase.util.Logging
import net.liftweb.json.JsonParser._


/**
 * Created by liuyou on 14/11/3.
 */
class Task(queryArgs: String) extends Logging {

  private var stat = 0
  private var priority = 0
  private val buildtime = Calendar.getInstance.getTime

  log.debug("Now create uuid for this task.")
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

object Task {

  def apply(args: String) = {
    val handle = new Task(args)
    TaskQueue.add(handle)
    handle
  }

}

