package byone.hbase.core

/**
 * Created by liuyou on 14/11/3.
 */
class TaskQueue {

  def get(workId: String): Task = {
    new Task("")
  }

}
