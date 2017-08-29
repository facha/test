name := "scala-rw-solr"
version := "0.0.1"
scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  "org.apache.solr" % "solr-solrj" % "4.10.3-cdh5.12.0",
  "org.apache.solr" % "solr-core" % "4.10.3-cdh5.12.0",
"commons-logging" % "commons-logging" % "1.1.1"
)
resolvers += "Restlet Repositories" at "http://maven.restlet.org"
resolvers += "cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos/"
