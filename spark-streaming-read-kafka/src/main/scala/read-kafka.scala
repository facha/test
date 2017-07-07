import kafka.serializer.StringDecoder
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf

object ReadKafka {
  def main(args: Array[String]) {
    val brokers = "172.31.117.46:9092,172.31.117.47:9092,172.31.117.65:9092"
    val topic = "test"

    val sparkConf = new SparkConf().setAppName("ReadKafkaTopic")
    val ssc = new StreamingContext(sparkConf, Seconds(10))

    val kafkaParams = Map[String, String]("bootstrap.servers" -> brokers, "auto.offset.reset" -> "smallest")
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, Set(topic))
    messages.foreachRDD(rdd => if (!rdd.isEmpty) {
      // First 10 messages in this batch
      rdd.take(10).map(x => x._2).foreach(println)
    })
    // Total No of messages in this batch
    messages.count().print()

    ssc.start()
    ssc.awaitTermination()
  }
}
