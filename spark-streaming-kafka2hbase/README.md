## reads from kafka to hbase
```
curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
yum -y install sbt
sbt assembly

echo "create 'table1', 'col_family1'" | hbase shell

spark-submit --class ReadKafka target/scala-2.10/spark-streaming-kafka2hbase-assembly-0.0.1.jar
