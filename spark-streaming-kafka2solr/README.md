## reads from kafka to solr
```
curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
yum -y install sbt
sbt assembly

spark-submit --class Kafka2Solr target/scala-2.10/spark-streaming-kafka2solr-assembly-0.0.1.jar
