package byone.hbase

import akka.actor.{ActorSystem, Props}
import byone.hbase.core.actor.{Cancel, ClientActor, Read, Write}
import byone.hbase.core.task.HTaskManager


/**
 * Created by liuyou on 14/11/3.
 */
object RsyncClient {

  private val system = ActorSystem("hbaseClient")
  private val dispatcher = system.actorOf(Props[ClientActor],name = "ClientActor")

  /**
   * queryData from HBase or cached data. This will return Query task handle id immediately.
   * then we can get the data or task status with the id @See {byone.hbase.core.task.HTask}.
   * @param args read args using json
   *             i.e.
   *             """{
   *             "Range": ["12/10/2014 11:08:12","12/10/2014 19:08:15"],
   *             "Items": ["collectorId", "eventType", "relayDevIpAddr", "cpuUtil","hostIpAddr","eventSeverity"],
   *             "Events": ["PH_DEV_MON_SYS_MEM_UTIL","PH_DEV_MON_SYS_PER_CPU_UTIL"],
   *             "Filter": "SingleColumnValueFilter ('d','hostIpAddr',=,'binary:10.133.64.2')",
   *             "Groups": ["hostName"],
   *             "Aggres": null,
   *             "Order": "dec"
   *             }"""
   *
   * @return task id to access the task: HtaskManager.get(id) @see {byone.hbase.core.task.HtaskManager}
   */
  def queryData(args: String): String = {
    val taskId = HTaskManager.create(args)
    dispatcher ! Read(taskId)
    taskId
  }

  /**
   * put data to HBase
   * @param data
   */
  def writeDataToHBase(data: String) = dispatcher ! Write(data)

  /**
   * flush cache data to HBase
   */
  def flushToHBase() = dispatcher ! "flush"

  /**
   * cancel query task using work string
   * @param workId
   */
  def cancelQueryTask(workId: String) = dispatcher ! Cancel(workId)

  def taskQueue =  ???

  /**
   * stop listening actor
   */
  def stop = system.awaitTermination()

}
