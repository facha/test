import scala.io.Source

object ReadFile {
  def main(args: Array[String]) {
    val filename = "README.md"
    for (line <- Source.fromFile(filename).getLines) {
      println(line)
    }
  }
}
