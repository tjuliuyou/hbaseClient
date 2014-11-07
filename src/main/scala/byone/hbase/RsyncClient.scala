package byone.hbase

import akka.actor.{Props, ActorSystem}
import byone.hbase.core._
import byone.hbase.core.actor.{Cancel, Write, Read, ClientActor}
import byone.hbase.core.task.{HTaskManager, HTask}


/**
 * Created by liuyou on 14/11/3.
 */
object RsyncClient {

  val system = ActorSystem("hbaseClient")
  val router = system.actorOf(Props[ClientActor],name = "router")

  /**
   * queryData from HBase or cached data. This will return Query task handle id immediately.
   * then we can get the data or task status with the id @See {byone.hbase.core.task.Htask}.
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
   * @return task id to access the task.
   */
  def queryData(args: String): String = {
    val taskId = HTaskManager.create(args)
    router ! Read(taskId)
    taskId
  }

  /**
   * put data to HBase
   * @param data
   */
  def writeDataToHBase(data: String) = router ! Write(data)

  def flushToHBase() = router ! "flush"

  def cancelQueryTask(workId: String) = router ! Cancel(workId)

  def taskQueue =  ???

}
