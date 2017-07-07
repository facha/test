import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.util.Bytes

object RwHbase {
  def main(args: Array[String]) {
    val con = ConnectionFactory.createConnection()
    val table = con.getTable(TableName.valueOf("table1"))
    val put = new Put(Bytes.toBytes("row_key1"))
    val cf = Bytes.toBytes("col_family1")
    put.addColumn(cf, Bytes.toBytes("column1"), Bytes.toBytes("value1"))
    put.addColumn(cf, Bytes.toBytes("column2"), Bytes.toBytes("value2"))
    table.put(put)

    val get = new Get(Bytes.toBytes("row_key1"))
    val result = table.get(get)
    val value1 = result.getValue(Bytes.toBytes("col_family1"), Bytes.toBytes("column1"))
    val value2 = result.getValue(Bytes.toBytes("col_family1"), Bytes.toBytes("column2"))
    println(Bytes.toString(value1) + " " + Bytes.toString(value2))

    con.close()
  }
}
