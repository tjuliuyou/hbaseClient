package byone.hbase.util

import org.apache.log4j.Logger

trait Logging {

  protected val log = Logger.getLogger(getClass.getName)
}