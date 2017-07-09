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
    val brokers = "172.26.18.181:9092,172.26.27.103:9092,172.26.17.9:9092"
    val topic = "test"

    val sparkConf = new SparkConf().setAppName("ReadKafkaTopic")
    val ssc = new StreamingContext(sparkConf, Seconds(10))
    val kafkaParams = Map[String, String]("bootstrap.servers" -> brokers, "auto.offset.reset" -> "smallest")
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, Set(topic))
    messages.foreachRDD(rdd => if (!rdd.isEmpty) {
      rdd.foreach(x => {
        val (kafka_key, kafka_value) = x
        val kafka_value_list = kafka_value.split(",")
        val timestamp = kafka_value_list(0)
        val lat = kafka_value_list(1)
        val lng = kafka_value_list(2)
        val value = kafka_value_list(3)

        val conf  = HBaseConfiguration.create()
        conf.set("hbase.zookeeper.quorum", "172.26.24.152");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        val con = ConnectionFactory.createConnection(conf)
        val table = con.getTable(TableName.valueOf("table1"))
        val put = new Put(Bytes.toBytes(kafka_value.hashCode()))
        val cf = Bytes.toBytes("col_family1")
        put.addColumn(cf, Bytes.toBytes("timestamp"), Bytes.toBytes(timestamp))
        put.addColumn(cf, Bytes.toBytes("lat"), Bytes.toBytes(lat))
        put.addColumn(cf, Bytes.toBytes("lng"), Bytes.toBytes(lng))
        put.addColumn(cf, Bytes.toBytes("value"), Bytes.toBytes(value))
        table.put(put)
        con.close()
      })
    })
    ssc.start()
    ssc.awaitTermination()
  }
}
