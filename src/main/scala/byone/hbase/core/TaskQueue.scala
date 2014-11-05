package byone.hbase.core

import byone.hbase.util.Logging

import scala.collection.mutable


/**
 * Created by liuyou on 14/11/3.
 */
object TaskQueue extends Logging  {

  def get(workId: String): Task = {
    new Task("")
  }

  var queue = mutable.Queue.empty[Task]


  def add(workId: String) = {

  }

  def add(tk: Task) = {
    log.info("add to taskManager.")

  }

  def remove(workId: String) = ???

  def remove(task: Task) = ???

  def removeAll = ???

  def retain = ???

  def addAll = ???

  private var workThread = 0
}
