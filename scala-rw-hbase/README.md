## Writes a row to HBase and reads it back
```
curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
yum -y install sbt
sbt assembly
java -jar target/scala-2.10/scala-rw-hbase-assembly-0.0.1.jar
