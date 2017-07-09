## Writes a row to Kudu and reads it back
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

java -jar target/scala-2.10/scala-rw-kudu-assembly-0.0.1.jar
