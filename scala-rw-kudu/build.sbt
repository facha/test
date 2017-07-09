import AssemblyKeys._
assemblySettings
name := "scala-rw-kudu"
version := "0.0.1"
scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  "org.apache.kudu" % "kudu-client" % "1.3.0" 
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
