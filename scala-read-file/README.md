## reads a README.md line by line
```
curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
yum -y install sbt
sbt assembly
java -jar target/scala-2.10/scala-read-file-assembly-0.0.1.jar
