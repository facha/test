import org.apache.kudu.client.KuduClient
import scala.collection.JavaConverters._

object RwKudu {
  def main(args: Array[String]) {
    val tableName = "impala::default.table1"
    val client = new KuduClient.KuduClientBuilder("172.26.24.152:7051").build()
    val table = client.openTable(tableName)
    val session = client.newSession()
 
    val insert = table.newInsert()
    val row = insert.getRow()
    row.addInt(0, 1)
    row.addString(1, "1499576474")
    row.addDouble(2, 1.01)
    row.addDouble(3, 5.01)
    row.addInt(4, 100)
    session.apply(insert)
    session.flush()


    val cols = List("id", "tstamp", "lat", "lng", "value").asJava
    val scanner = client.newScannerBuilder(table)
              .setProjectedColumnNames(cols)
              .build()
    while (scanner.hasMoreRows()) {
      val results = scanner.nextRows();
      while (results.hasNext()) {
        val result = results.next();
        println(result.rowToString);
      }
    }

    session.close()
    client.shutdown()
  }
}
