## reads from kafka
```
curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
yum -y install sbt
sbt assembly
park-submit --class ReadKafka target/scala-2.10/spark-streaming-read-kafka-assembly-0.0.1.jar
