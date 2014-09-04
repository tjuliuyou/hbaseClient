package byone.hbase.util

import byone.hbase.core.Table
import org.apache.hadoop.hbase.client.Put

import scala.collection.mutable.Map
import scala.util.Random

/**
 * Created by dream on 7/22/14.
 */
object RandEvent {

  def data1: Map[String, String] = {
    val cols = Map("eventType" -> "PH_DEV_MON_PROC_RESOURCE_UTIL",
      "eventSeverity" -> "1",
      "phRecvTime" -> "Thu May 22 09:46:01 CST 2014",
      "rawEventMsg" -> """[PH_DEV_MON_PROC_RESOURCE_UTIL]:[eventSeverity]=PHL_INFO,[fileName]=phPerfJob.cpp,[lineNumber]=3847,[swProcName]=mysqld_safe,[hostName]=GZ-Web,[hostIpAddr]=10.133.64.4,[memUtil]=0.019452,[cpuUtil]=0.000000,[appName]=mysqld_safe,[appGroupName]=,[pollIntv]=176,[swParam]= /usr/bin/mysqld_safe --datadir=/var/lib/mysql --socket=/var/lib/mysql/mysql.sock --pid-file=/var/run/mysqld/mysqld.pid --basedir,[phLogDetail]=""",
      "phEventCategory" -> "6",
      "phEventCategory" -> "Performance Event6)",
      "eventName" -> "Process CPU and Memory Utilization stats",
      "eventSeverityCat" -> "LOW",
      "swParam" -> "/usr/bin/mysqld_safe --datadir=/var/lib/mysql --socket=/var/lib/mysql/mysql.sock --pid-file=/var/run/mysqld/mysqld.pid --basedir",
      "reptVendor" -> "byone",
      "reptModel" -> "byone")

    val h = "10.133.64." + Random.nextInt(100).toString
    cols += ("reptDevName" -> h)
    cols += ("hostIpAddr" -> h)
    cols += ("relayDevIpAddr" -> ("10.133.64." + Random.nextInt(180).toString))
    val x = "GZ-Web" + Random.nextInt(251).toString
    cols += ("reptDevName" -> x)
    cols += ("hostName" -> x)

    cols("cpuUtil") = Random.nextFloat.toString
    cols("memUtil") = Random.nextFloat.toString
    cols("collectorId") = Random.nextInt(200).toString

    cols
    //table.adds(row,cols)
  }

  def data2: Map[String, String] = {
    val cols = Map("eventType" -> "PH_DEV_MON_SYS_MEM_FREE",
      "eventSeverity" -> "1",
      "phRecvTime" -> "Thu May 22 09:45:35 CST 2014",
      "rawEventMsg" -> "[PH_DEV_MON_SYS_MEM_FREE]:[eventSeverity]=PHL_INFO,[fileName]=phPerfJob.cpp,[lineNumber]=9548,[memName]=Cisco Network Device Free Contiguous IO Memory KB),[hostName]=CNlink-GZ-PE-7206A,[hostIpAddr]=202.181.254.158,[freeMemKB]=58706,[pollIntv]=176,[phLogDetail]=",
      "phEventCategory" -> "6",
      "phEventCategory" -> "Performance Event6)",
      "memName" -> "Cisco Network Device Free Contiguous IO Memory KB)",
      "eventName" -> "Free system memory stats for a device",
      "eventSeverityCat" -> "LOW",
      "reptVendor" -> "byone",
      "hostGeoCountry" -> "Hong Kong",
      "reptModel" -> "byone",
      "reptGeoCountry" -> "Hong Kong",
      "hostGeoOrg" -> "- Hkcix -",
      "reptGeoOrg" -> "- Hkcix -",
      "hostGeoLongitude" -> "114.167",
      "reptGeoLongitude" -> "114.167",
      "hostGeoLatitude" -> "22.25",
      "reptGeoLatitude" -> "22.25")
    cols += ("collectorId" -> Random.nextInt(200).toString)
    val h = "202.181.254." + Random.nextInt(100).toString
    cols += ("reptDevName" -> h)
    cols += ("hostIpAddr" -> h)
    cols += ("relayDevIpAddr" -> ("202.181.254." + Random.nextInt(180).toString))
    val x = "CNlink-GZ-PE-7206A" + Random.nextInt(251).toString
    cols += ("reptDevName" -> x)
    cols += ("hostName" -> x)
    cols += ("freeMemKB" -> (2000 + Random.nextInt(19202)).toString)
    //table.adds(row,cols)
  }

  def data3: Map[String, String] = {
    val cols = Map("eventType" -> "PH_DEV_MON_HW_TEMP",
      "eventSeverity" -> "1",
      "phRecvTime" -> "Thu May 22 09:46:31 CST 2014",
      "rawEventMsg" -> "[PH_DEV_MON_HW_TEMP]:[eventSeverity]=PHL_INFO,[fileName]=deviceCiscoIOS.cpp,[lineNumber]=2219,[hostName]=A2-BB-BJ-CH,[hostIpAddr]=202.181.254.85,[hwComponentName]=NPE Inlet,[envTempDegC]=38,[envTempHighThreshDegC]=59,[envTempOffHighDegC]=21,[envTempDegF]=94,[envTempHighThreshDegF]=131,[envTempOffHighDegF]=37,[phLogDetail]=",
      "phEventCategory" -> "6",
      "phEventCategory" -> "Performance Event6)",
      "eventName" -> "Temperature measurement",
      "hostName" -> "A2-BB-BJ-CH",
      "eventSeverityCat" -> "LOW",
      "envTempOffHighDegC" -> "21",
      "reptVendor" -> "byone",
      "hostGeoCountry" -> "Hong Kong",
      "reptModel" -> "byone",
      "reptGeoCountry" -> "Hong Kong",
      "envTempOffHighDegF" -> "37",
      "hwComponentName" -> "NPE Inlet",
      "hostGeoOrg" -> "- Hkcix -",
      "envTempHighThreshDegF" -> "131",
      "reptGeoOrg" -> "- Hkcix -",
      "envTempDegF" -> "94",
      "envTempHighThreshDegC" -> "59",
      "envTempDegC" -> "38",
      "hostGeoLongitude" -> "114.167",
      "reptGeoLongitude" -> "114.167",
      "hostGeoLatitude" -> "22.25",
      "reptGeoLatitude" -> "22.25")
    cols += ("collectorId" -> Random.nextInt(200).toString)
    val h = "10.64.252." + Random.nextInt(100).toString
    cols += ("reptDevName" -> h)
    cols += ("hostIpAddr" -> h)
    cols += ("relayDevIpAddr" -> ("202.181.254." + Random.nextInt(180).toString))
    val x = "A2-BB-BJ-CH" + Random.nextInt(251).toString
    cols += ("reptDevName" -> x)
    cols += ("hostName" -> x)
    cols += ("envTempOffHighDegC" -> (15 + Random.nextInt(45)).toString)
    //table.adds(row,cols)
  }

  def data4: Map[String, String] = {
    val cols = Map("eventType" -> "PH_DEV_MON_BGP_NBR_STATUS",
      "eventSeverity" -> "1",
      "phRecvTime" -> "Thu May 22 09:48:49 CST 2014",
      "rawEventMsg" -> "[PH_DEV_MON_BGP_NBR_STATUS]:[eventSeverity]=PHL_INFO,[fileName]=phPerfJob.cpp,[lineNumber]=10385,[srcName]=,[srcIpAddr]=202.181.254.85,[destName]=,[destIpAddr]=202.181.254.30,[srcASNum]=65003,[destASNum]=65001,[bgpState]=Established,[phLogDetail]=",
      "phEventCategory" -> "6",
      "phEventCategory" -> "Performance Event6)",
      "eventName" -> "BGP neighbor status",
      "eventSeverityCat" -> "LOW",
      "bgpState" -> "Established",
      "reptVendor" -> "byone",
      "srcGeoCountry" -> "Hong Kong",
      "reptModel" -> "byone",
      "destGeoCountry" -> "Hong Kong",
      "srcGeoOrg" -> "- Hkcix -",
      "destGeoOrg" -> "- Hkcix -",
      "srcGeoLongitude" -> "114.167",
      "destGeoLongitude" -> "114.167",
      "srcGeoLatitude" -> "22.25",
      "destGeoLatitude" -> "22.25")

    val h = "10.64.252." + Random.nextInt(100).toString
    cols += ("reptDevName" -> h)
    cols += ("hostIpAddr" -> h)
    cols += ("relayDevIpAddr" -> ("202.181.254." + Random.nextInt(180).toString))
    val x = "GZskylcollector" + Random.nextInt(251).toString
    cols += ("reptDevName" -> x)
    cols += ("hostName" -> x)
    cols += ("collectorId" -> Random.nextInt(200).toString)
    cols += ("srcASNum" -> (1500 + Random.nextInt(4500)).toString)
    cols += ("destASNum" -> (1500 + Random.nextInt(4500)).toString)
    //table.adds(row,cols)
  }

  def data5: Map[String, String] = {
    val cols = Map("eventType" -> "PH_DEV_MON_PING_STAT",
      "eventSeverity" -> "1",
      "phRecvTime" -> "Thu May 22 09:46:52 CST 2014",
      "rawEventMsg" -> "[PH_DEV_MON_PING_STAT]:[eventSeverity]=PHL_INFO,[fileName]=phPerfMonitor.cpp,[lineNumber]=2567,[hostName]=KXC_Monitor,[hostIpAddr]=10.128.244.134,[avgDurationMSec]=0,[minDurationMSec]=0,[maxDurationMSec]=1,[pktLossPct]=0.000000,[sysDownTime]=0,[sysDegradedTime]=0,[pollIntv]=120,[phLogDetail]=",
      "sysDownTime" -> "0",
      "phEventCategory" -> "6",
      "phEventCategory" -> "Performance Event6)",
      "sysDegradedTime" -> "0",
      "eventName" -> "Ping Statistics",
      "eventSeverityCat" -> "LOW",
      "maxDurationMSec" -> "1",
      "minDurationMSec" -> "0",
      "avgDurationMSec" -> "0",
      "pktLossPct" -> "0.00",
      "reptVendor" -> "byone",
      "pollIntv" -> "120",
      "reptModel" -> "byone")

    val h = "10.133.64." + Random.nextInt(100).toString
    cols += ("reptDevName" -> h)
    cols += ("hostIpAddr" -> h)
    cols += ("relayDevIpAddr" -> ("10.128.244." + Random.nextInt(180).toString))
    val x = "KXC_Monitor" + Random.nextInt(251).toString
    cols += ("reptDevName" -> x)
    cols += ("hostName" -> x)
    cols += ("collectorId" -> Random.nextInt(200).toString)
    cols += ("minDurationMSec" -> Random.nextInt(45).toString)
    cols += ("maxDurationMSec" -> Random.nextInt(45).toString)
    //table.adds(row,cols)
  }

  def data6: Map[String, String] = {
    val cols = Map("eventType" -> "PH_DEV_MON_OSPF_NBR_STATUS",
      "eventSeverity" -> "1",
      "phRecvTime" -> "Thu May 22 09:46:05 CST 2014",
      "rawEventMsg" -> "[PH_DEV_MON_OSPF_NBR_STATUS]:[eventSeverity]=PHL_INFO,[fileName]=phPerfJob.cpp,[lineNumber]=10358,[srcName]=,[srcIpAddr]=202.181.254.60,[destName]=,[destIpAddr]=202.181.254.58,[ospfAreaId]=0,[ospfState]=Two way,[phLogDetail]=",
      "phEventCategory" -> "6",
      "phEventCategory" -> "Performance Event6)",
      "eventName" -> "OSPF neighbor status",
      "eventSeverityCat" -> "LOW",
      "ospfAreaId" -> "0",
      "ospfState" -> "Two way",
      "reptVendor" -> "byone",
      "srcGeoCountry" -> "Hong Kong",
      "reptModel" -> "byone",
      "destGeoCountry" -> "Hong Kong",
      "srcGeoOrg" -> "- Hkcix -",
      "destGeoOrg" -> "- Hkcix -",
      "srcGeoLongitude" -> "114.167",
      "destGeoLongitude" -> "114.167",
      "srcGeoLatitude" -> "22.25",
      "destGeoLatitude" -> "22.25")

    val h = "10.133.64." + Random.nextInt(100).toString
    cols += ("reptDevName" -> h)
    cols += ("hostIpAddr" -> h)
    cols += ("relayDevIpAddr" -> ("10.133.64." + Random.nextInt(180).toString))
    val x = "GZskylcollector" + Random.nextInt(251).toString
    cols += ("reptDevName" -> x)
    cols += ("hostName" -> x)
    cols += ("collectorId" -> Random.nextInt(200).toString)
    cols += ("ospfAreaId" -> (Random.nextInt(4500)).toString)
    ///table.adds(row,cols)
  }

  def data7: Map[String, String] = {
    val cols = Map("eventType" -> "PH_DEV_MON_SYS_UPTIME",
      "eventSeverity" -> "1",
      "phRecvTime" -> "Thu May 22 09:45:34 CST 2014",
      "rawEventMsg" -> "[PH_DEV_MON_SYS_UPTIME]:[eventSeverity]=PHL_INFO,[fileName]=phPerfJob.cpp,[lineNumber]=915,[hostName]=A1-BB-BJ-CH,[hostIpAddr]=202.181.254.95,[sysUpTime]=29230434,[sysUpTimePct]=100.000000,[sysDownTime]=0,[pollIntv]=176,[phLogDetail]=",
      "sysUpTimePct" -> "100.00",
      "sysDownTime" -> "0",
      "phEventCategory" -> "6",
      "phEventCategory" -> "Performance Event6)",
      "eventName" -> "System uptime for a device",
      "eventSeverityCat" -> "LOW",
      "reptVendor" -> "byone",
      "hostGeoCountry" -> "Hong Kong",
      "sysUpTime" -> "29230434",
      "reptModel" -> "byone",
      "reptGeoCountry" -> "Hong Kong",
      "hostGeoOrg" -> "- Hkcix -",
      "reptGeoOrg" -> "- Hkcix -",
      "hostGeoLongitude" -> "114.167",
      "reptGeoLongitude" -> "114.167",
      "hostGeoLatitude" -> "22.25",
      "reptGeoLatitude" -> "22.25")

    val h = "10.133.64." + Random.nextInt(100).toString
    cols += ("reptDevName" -> h)
    cols += ("hostIpAddr" -> h)
    cols += ("relayDevIpAddr" -> ("10.133.64." + Random.nextInt(180).toString))
    val x = "GZskylcollector" + Random.nextInt(251).toString
    cols += ("reptDevName" -> x)
    cols += ("hostName" -> x)
    cols += ("collectorId" -> Random.nextInt(200).toString)
    cols += ("sysUpTimePct" -> (100 * Random.nextDouble()).toString)
    cols += ("destASNum" -> (1500 + Random.nextInt(4500)).toString)
    //table.adds(row,cols)
  }

  def data8: Map[String, String] = {
    val cols = Map("cpuInterruptPersec" -> "0.00",
      "contextSwitchPersec" -> "1989.00",
      "eventType" -> "PH_DEV_MON_SYS_CPU_UTIL",
      "kernCpuUtil" -> "0.00",
      "eventSeverity" -> "1",
      "waitCpuUtil" -> "0.00",
      "phRecvTime" -> "Thu May 22 09:45:49 CST 2014",
      "rawEventMsg" -> "[PH_DEV_MON_SYS_CPU_UTIL]:[eventSeverity]=PHL_INFO,[fileName]=phPerfJob.cpp,[lineNumber]=1699,[cpuName]=Generic CPU,[hostName]=,[hostIpAddr]=10.133.64.1,[cpuUtil]=2.000000,[sysCpuUtil]=0.465909,[userCpuUtil]=1.000000,[waitCpuUtil]=0.000000,[kernCpuUtil]=0.000000,[contextSwitchPersec]=1989.000000,[cpuInterruptPersec]=0.000000,[pollIntv]=176,[phLogDetail]=",
      "phEventCategory" -> "6",
      "phEventCategory" -> "Performance Event6)",
      "sysCpuUtil" -> "0.47",
      "userCpuUtil" -> "1.00",
      "eventName" -> "System CPU Utilization for a device",
      "hostName" -> "byone-super",
      "eventSeverityCat" -> "LOW",
      "reptVendor" -> "byone",
      "cpuUtil" -> "2.00",
      "reptModel" -> "byone",
      "cpuName" -> "Generic CPU")

    val h = "10.133.64." + Random.nextInt(100).toString
    cols += ("reptDevName" -> h)
    cols += ("hostIpAddr" -> h)
    cols += ("relayDevIpAddr" -> ("10.133.64." + Random.nextInt(180).toString))
    val x = "byone-super" + Random.nextInt(251).toString
    cols += ("reptDevName" -> x)
    cols += ("hostName" -> x)
    cols += ("collectorId" -> Random.nextInt(200).toString)
    cols += ("waitCpuUtil" -> Random.nextFloat().toString)
    //table.adds(row,cols)
  }

  def data9: Map[String, String] = {
    val cols = Map("eventType" -> "PH_DEV_MON_SYS_MEM_UTIL",
      "eventSeverity" -> "1",
      "phRecvTime" -> "Thu May 22 09:45:34 CST 2014",
      "rawEventMsg" -> "[PH_DEV_MON_SYS_MEM_UTIL]:[eventSeverity]=PHL_INFO,[fileName]=phPerfJob.cpp,[lineNumber]=7614,[memName]=Physical Memory,[hostName]=GZskylcollector,[hostIpAddr]=10.133.64.3,[memUtil]=24.993720,[freeMemKB]=2159980,[bufMemKB]=140932,[cacheMemKB]=873856,[swapMemUtil]=0.000000,[freeSwapMemKB]=6289436,[pollIntv]=176,[phLogDetail]=",
      "phEventCategory" -> "6",
      "phEventCategory" -> "Performance Event6)",
      "swapMemUtil" -> "0.00",
      "freeMemKB" -> "2159980",
      "memName" -> "Physical Memory",
      "freeSwapMemKB" -> "6289436",
      "hostIpAddr" -> "10.133.64.3",
      "bufMemKB" -> "140932",
      "eventName" -> "System memory Utilization stats for a device",
      "memUtil" -> "24.99",
      "cacheMemKB" -> "873856",
      "hostName" -> "GZskylcollector",
      "eventSeverityCat" -> "LOW",
      "collectorId" -> "10001",
      "reptVendor" -> "byone",
      "reptModel" -> "byone")

    val h = "10.133.64." + Random.nextInt(100).toString
    cols += ("reptDevName" -> h)
    cols += ("hostIpAddr" -> h)
    cols += ("relayDevIpAddr" -> ("10.133.64." + Random.nextInt(180).toString))
    val x = "GZskylcollector" + Random.nextInt(251).toString
    cols += ("reptDevName" -> x)
    cols += ("hostName" -> x)
    cols += ("collectorId" -> Random.nextInt(200).toString)
    cols += ("freeMemKB" -> (1500 + Random.nextInt(2159980)).toString)
    //table.adds(row,cols)
  }

  def data10: Map[String, String] = {
    val cols = Map("eventType" -> "PH_DEV_MON_SYS_PER_CPU_UTIL",
      "eventSeverity" -> "1",
      "phRecvTime" -> "Thu May 22 09:46:08 CST 2014",
      "rawEventMsg" -> "[PH_DEV_MON_SYS_PER_CPU_UTIL]:[eventSeverity]=PHL_INFO,[fileName]=phPerfJob.cpp,[lineNumber]=3253,[cpuName]=Generic CPU 0,[hostName]=CNlink-GZ-PE-7206A,[hostIpAddr]=202.181.254.158,[cpuUtil]=26.000000,[pollIntv]=176,[phLogDetail]=",
      "phEventCategory" -> "6",
      "phEventCategory" -> "Performance Event6)",
      "eventName" -> "System per CPU Utilization for a device",
      "hostName" -> "CNlink-GZ-PE-7206A",
      "eventSeverityCat" -> "LOW",
      "reptVendor" -> "byone",
      "cpuUtil" -> "26.00",
      "hostGeoCountry" -> "Hong Kong",
      "reptModel" -> "byone",
      "cpuName" -> "Generic CPU 0",
      "reptGeoCountry" -> "Hong Kong",
      "hostGeoOrg" -> "- Hkcix -",
      "reptGeoOrg" -> "- Hkcix -",
      "hostGeoLongitude" -> "114.167",
      "reptGeoLongitude" -> "114.167",
      "hostGeoLatitude" -> "22.25",
      "reptGeoLatitude" -> "22.25")

    val h = "10.133.64." + Random.nextInt(100).toString
    cols += ("reptDevName" -> h)
    cols += ("hostIpAddr" -> h)
    cols += ("relayDevIpAddr" -> ("10.133.64." + Random.nextInt(180).toString))
    val x = "CNlink-GZ-PE-7206A" + Random.nextInt(251).toString
    cols += ("reptDevName" -> x)
    cols += ("hostName" -> x)
    cols += ("collectorId" -> Random.nextInt(200).toString)
    cols += ("cpuUtil" -> Random.nextDouble.toString)
    //table.adds(row,cols)
  }

  def data11: Map[String, String] = {
    val cols = Map("eventType" -> "PH_DEV_MON_SYS_PROC_COUNT",
      "eventSeverity" -> "1",
      "phRecvTime" -> "Thu May 22 09:45:34 CST 2014",
      "reptVendor" -> "byone",
      "rawEventMsg" -> "[PH_DEV_MON_SYS_PROC_COUNT]:[eventSeverity]=PHL_INFO,[fileName]=phPerfJob.cpp,[lineNumber]=9527,[hostName]=GZskylcollector,[hostIpAddr]=10.133.64.3,[procCount]=79,[pollIntv]=176,[phLogDetail]=",
      "reptModel" -> "byone",
      "procCount" -> "79",
      "phEventCategory" -> "6",
      "phEventCategory" -> "Performance Event6)",
      "eventName" -> "System process count for a device",
      "eventSeverityCat" -> "LOW")

    val h = "10.133.64." + Random.nextInt(100).toString
    cols += ("reptDevName" -> h)
    cols += ("hostIpAddr" -> h)
    cols += ("relayDevIpAddr" -> ("10.133.64." + Random.nextInt(180).toString))
    val x = "GZskylcollector" + Random.nextInt(251).toString
    cols += ("reptDevName" -> x)
    cols += ("hostName" -> x)
    cols += ("collectorId" -> Random.nextInt(200).toString)
    cols += ("procCount" -> Random.nextInt(450).toString)
    // table.adds(row,cols)
  }

  def data12: Map[String, String] = {
    val cols = Map("eventType" -> "PH_DEV_MON_HW_STATUS",
      "hwFanStatus" -> "0",
      "hwFanStatus" -> "0",
      "eventSeverity" -> "1",
      "phRecvTime" -> "Thu May 22 09:46:31 CST 2014",
      "rawEventMsg" -> "[PH_DEV_MON_HW_STATUS]:[eventSeverity]=PHL_INFO,[fileName]=deviceCiscoIOS.cpp,[lineNumber]=2235,[hostName]=A2-BB-BJ-CH,[hostIpAddr]=202.181.254.85,[hwStatusCode]=0,[hwPowerSupplyStatus]=0,[hwTempSensorStatus]=0,[hwFanStatus]=0,[phLogDetail]=",
      "phEventCategory" -> "6",
      "phEventCategory" -> "Performance Event6)",
      "eventName" -> "Hardware health status",
      "hwStatusCode" -> "0",
      "eventSeverityCat" -> "LOW",
      "hwPowerSupplyStatus" -> "0",
      "hwPowerSupplyStatus" -> "0",
      "hwTempSensorStatus" -> "0",
      "hwTempSensorStatus" -> "0",
      "reptVendor" -> "byone",
      "hostGeoCountry" -> "Hong Kong",
      "reptModel" -> "byone",
      "reptGeoCountry" -> "Hong Kong",
      "hostGeoOrg" -> "- Hkcix -",
      "reptGeoOrg" -> "- Hkcix -",
      "hostGeoLongitude" -> "114.167",
      "reptGeoLongitude" -> "114.167",
      "hostGeoLatitude" -> "22.25",
      "reptGeoLatitude" -> "22.25")

    val h = "10.64.252." + Random.nextInt(100).toString
    cols += ("reptDevName" -> h)
    cols += ("hostIpAddr" -> h)
    cols += ("relayDevIpAddr" -> ("202.181.254." + Random.nextInt(180).toString))
    val x = "A2-BB-BJ-CH" + Random.nextInt(251).toString
    cols += ("reptDevName" -> x)
    cols += ("hostName" -> x)
    cols += ("collectorId" -> Random.nextInt(200).toString)
    cols += ("hwFanStatus" -> Random.nextInt(1).toString)
    cols += ("hwStatusCode" -> Random.nextInt(1).toString)
    //table.adds(row,cols)
  }

  def data13: Map[String, String] = {
    val cols = Map("eventType" -> "PH_DEV_MON_IPSLA_ICMP_MET",
      "eventSeverity" -> "1",
      "phRecvTime" -> "Thu May 22 09:49:06 CST 2014",
      "reptDevIpAddr" -> "10.128.244.134",
      "reptVendor" -> "byone",
      "reptDevName" -> "KXC_Monitor",
      "rawEventMsg" -> "[PH_DEV_MON_IPSLA_ICMP_MET]:[eventSeverity]=PHL_INFO,[fileName]=phPerfJob.cpp,[lineNumber]=2951,[reptDevIpAddr]=10.128.244.134,[reptDevName]=KXC_Monitor,[ipslaTestName]=,[icmpResponseTimeMs]=1878121527,[phLogDetail]=",
      "reptModel" -> "byone",
      "phEventCategory" -> "6",
      "phEventCategory" -> "Performance Event6)",
      "icmpResponseTimeMs" -> "1878121527",
      "eventName" -> "ICMP performance metrics collected via IP SLA",
      "eventSeverityCat" -> "LOW")

    val h = "10.133.64." + Random.nextInt(100).toString
    cols += ("reptDevName" -> h)
    cols += ("hostIpAddr" -> h)
    cols += ("relayDevIpAddr" -> ("10.128.244." + Random.nextInt(180).toString))
    val x = "KXC_Monitor" + Random.nextInt(251).toString
    cols += ("reptDevName" -> x)
    cols += ("hostName" -> x)
    cols += ("collectorId" -> Random.nextInt(200).toString)
    cols += ("icmpResponseTimeMs" -> (18781215 + Random.nextInt(18781215)).toString)
    //table.adds(row,cols)
  }

  // val dataTable = new Table(Constants.dataTable)

  def randData(u: Int): Put = {
    val event = Random.nextInt(13) + 1
    val data = event match {
      case 1 => data1
      case 2 => data2
      case 3 => data3
      case 4 => data4
      case 5 => data5
      case 6 => data6
      case 7 => data7
      case 8 => data8
      case 9 => data9
      case 10 => data10
      case 11 => data11
      case 12 => data12
      case 13 => data13
    }
    val ts = System.currentTimeMillis()
    val pre = Random.nextInt(Constants.REGIONRANGE)

    val row = DatePoint.Int2Byte(pre, 1) ++ DatePoint.num2Byte(ts / 1000, 4) ++
      DatePoint.Int2Byte(event) ++ DatePoint.num2Byte(ts % 1000, 3) ++
      DatePoint.Int2Byte(u)
    Table.mapToPut(data.toMap, row, Constants.dataFamily(0).getBytes)
  }

  def rand(num: Int): List[Put] = {
    val pl = for (i <- 0 until num) yield {
      randData(i)
    }
    pl.toList
  }

}
