## reads from kafka to kudu
```
curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
yum -y install sbt
sbt assembly

impala-shell <<EOF
CREATE TABLE table1
(
id int,
tstamp string,
lat double,
lng double,
value int,
primary key(id)
) partition by hash(id) partitions 2 STORED AS KUDU;
EOF

spark-submit --class ReadKafka target/scala-2.10/spark-streaming-kafka2kudu-assembly-0.0.1.jar
