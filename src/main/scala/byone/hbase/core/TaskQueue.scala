package byone.hbase.core

import org.slf4j.LoggerFactory

import scala.collection.mutable


/**
 * Created by liuyou on 14/11/3.
 */
object TaskQueue {

  private val logger = LoggerFactory.getLogger(getClass)
  def get(workId: String): HTask = {
    new HTask("")
  }

  var queue = mutable.Queue.empty[HTask]


  def add(workId: String) = {

  }

  def add(tk: HTask) = {
    logger.info("add to taskManager.")

  }

  def remove(workId: String) = ???

  def remove(task: HTask) = ???

  def removeAll = ???

  def retain = ???

  def addAll = ???

  private var workThread = 0
}
