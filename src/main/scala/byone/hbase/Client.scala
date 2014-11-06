package byone.hbase

import byone.hbase.core.{HTask, TaskQueue}



/**
 * Created by liuyou on 14/11/3.
 */
object Client {

  def read = (args: String) => HTask(args)

  def write(data: String) = {}

  def taskQueue = TaskQueue.queue

  def cancelTask(uid: String) = TaskQueue.remove(uid)
  def cancelTask(task: HTask) = TaskQueue.remove(task)


}
