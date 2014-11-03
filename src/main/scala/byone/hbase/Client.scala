package byone.hbase

import byone.hbase.core.{TaskManager, Task}

/**
 * Created by liuyou on 14/11/3.
 */
object Client {

  def read(args: String) = Task(args)

  def write(data: String) = {}

  def taskQueue = TaskManager.TaskQueue

  def cancelTask(uid: String) = TaskManager.remove(uid)
  def cancelTask(task: Task) = TaskManager.remove(task)
}
