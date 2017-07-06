## sends "GET /" to google.com:80 and receives a response
```
curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
yum -y install sbt
sbt assembly
java -jar target/scala-2.10/scala-write-socket-assembly-0.0.1.jar
