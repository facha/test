import kafka.serializer.StringDecoder
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.util.Bytes

object Kafka2Hbase {
  def main(args: Array[String]) {
    val brokers = "172.31.117.46:9092,172.31.117.47:9092,172.31.117.65:9092"
    val topic = "test"

    val sparkConf = new SparkConf().setAppName("ReadKafkaTopic")
    val ssc = new StreamingContext(sparkConf, Seconds(10))
    val kafkaParams = Map[String, String]("bootstrap.servers" -> brokers, "auto.offset.reset" -> "smallest")
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, Set(topic))
    messages.foreachRDD(rdd => if (!rdd.isEmpty) {
      rdd.foreach(x => {
        val conf  = HBaseConfiguration.create()
        conf.set("hbase.zookeeper.quorum", "172.31.117.197");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        val con = ConnectionFactory.createConnection(conf)
        val table = con.getTable(TableName.valueOf("table1"))
        val put = new Put(Bytes.toBytes(x._2))
        val cf = Bytes.toBytes("col_family1")
        put.addColumn(cf, Bytes.toBytes("column1"), Bytes.toBytes(x._2))
        table.put(put)
        con.close()
      })
    })
    ssc.start()
    ssc.awaitTermination()
  }
}
