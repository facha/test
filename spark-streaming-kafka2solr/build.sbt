name := "spark-streaming-kafka2solr"
version := "0.0.1"
scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  "org.apache.solr" % "solr-solrj" % "4.10.3-cdh5.12.0",
  "org.apache.solr" % "solr-core" % "4.10.3-cdh5.12.0",
  "org.apache.spark" % "spark-core_2.10" % "1.6.0-cdh5.9.3" % "provided",
  "org.apache.spark" % "spark-streaming_2.10" % "1.6.0-cdh5.9.3" % "provided",
  "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.0-cdh5.9.3"
)

//resolvers += "Restlet Repositories" at "http://maven.restlet.org"
resolvers += "cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos/"

mergeStrategy in assembly := {
  case x if x.startsWith("META-INF") => MergeStrategy.discard // Bumf
  case x if x.endsWith(".html") => MergeStrategy.discard // More bumf
  case x if x.contains("slf4j-api") => MergeStrategy.last
  case x if x.contains("org") => MergeStrategy.first
  case x =>
     val oldStrategy = (mergeStrategy in assembly).value
     oldStrategy(x)
}
