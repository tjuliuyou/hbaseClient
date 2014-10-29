name := "hbaseClient"

version := "0.2.2"

scalaVersion := "2.10.4"

resolvers += "Apache" at "https://repository.apache.org/content/repositories/releases"

libraryDependencies ++= Seq(
  //"org.apache.spark" % "spark-core_2.10" % "1.0.1",
  //"org.apache.hadoop" % "hadoop-common" % "2.3.0",
  //"org.apache.hadoop" % "hadoop-mapreduce-client" % "2.3.0",
  //"org.apache.hbase" % "hbase-common" % "0.98.2-hadoop2",
  //"org.apache.hbase" % "hbase-client" % "0.98.2-hadoop2",
  //"org.apache.hbase" % "hbase-server" % "0.98.2-hadoop2",
  "net.liftweb" %% "lift-json" % "2.6-M4",
  "com.twitter" %% "util-collection" % "6.12.1",
  "net.debasishg" %% "redisclient" % "2.13"
)
    
