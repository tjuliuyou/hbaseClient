package byone.hbase

import byone.hbase.core.{Task, TaskQueue}
import byone.hbase.util.Logging


/**
 * Created by liuyou on 14/11/3.
 */
object Client extends Logging {

  def read = (args: String) => Task(args)

  def write(data: String) = {}

  def taskQueue = TaskQueue.queue

  def cancelTask(uid: String) = TaskQueue.remove(uid)
  def cancelTask(task: Task) = TaskQueue.remove(task)


}
