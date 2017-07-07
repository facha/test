import AssemblyKeys._
assemblySettings
name := "scala-rw-hbase"
version := "0.0.1"
scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-common" % "2.6.0",
  "org.apache.hbase" % "hbase-client" % "1.2.0",
  "org.apache.hbase" % "hbase-common" % "1.2.0"
)
mergeStrategy in assembly := {
  case x if x.startsWith("META-INF") => MergeStrategy.discard // Bumf
  case x if x.endsWith(".html") => MergeStrategy.discard // More bumf
  case x if x.contains("slf4j-api") => MergeStrategy.last
  case x if x.contains("org") => MergeStrategy.first
  case x =>
     val oldStrategy = (mergeStrategy in assembly).value
     oldStrategy(x)
}
