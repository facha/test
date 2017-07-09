import kafka.serializer.StringDecoder
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf
import org.apache.kudu.client.KuduClient

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
        val id: Int = kafka_value.hashCode()
        val timestamp: String = kafka_value_list(0)
        val lat: Double = kafka_value_list(1).toDouble
        val lng: Double = kafka_value_list(2).toDouble
        val value: Int = kafka_value_list(3).toInt

        val tableName = "impala::default.table1"
        val client = new KuduClient.KuduClientBuilder("172.26.24.152:7051").build()
        val table = client.openTable(tableName)
        val session = client.newSession()
        val insert = table.newInsert()
        val row = insert.getRow()
        row.addInt(0, id)
        row.addString(1, timestamp)
        row.addDouble(2, lat)
        row.addDouble(3, lng)
        row.addInt(4, value)
        session.apply(insert)
        session.flush()
        session.close()
        client.shutdown()
      })
    })
    ssc.start()
    ssc.awaitTermination()
  }
}
