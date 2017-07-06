## Hello World in Scala

- Install SBT
- Build, Assembly (self-sufficient jar)
- Execute (java has to be installed and on your path
```
curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
yum -y install sbt
sbt assembly
java -jar target/scala-2.10/spark-test-assembly-0.0.1.jar
