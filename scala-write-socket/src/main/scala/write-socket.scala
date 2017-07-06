import java.net._
import java.io._
import scala.io._


object WriteSocket {
  def main(args: Array[String]) {
    val s = new Socket(InetAddress.getByName("google.com"), 80)
    lazy val in = new BufferedSource(s.getInputStream()).getLines()
    val out = new PrintStream(s.getOutputStream())

    out.println("GET /")
    out.flush()
    println("Received: " + in.next())

    s.close()
  }
}
