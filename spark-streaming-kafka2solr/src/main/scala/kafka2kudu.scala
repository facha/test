import kafka.serializer.StringDecoder
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf
import org.apache.solr.client.solrj.impl.CloudSolrServer
import org.apache.solr.common.SolrInputDocument

object Kafka2Solr {
  def main(args: Array[String]) {
    val brokers = "172.31.113.188:9092,172.31.113.210:9092,172.31.113.224:9092"
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
        val id: String = kafka_value.hashCode().toString()
        val date: String = kafka_value_list(0)
        val lat: String = kafka_value_list(1).toString()
        val lng: String = kafka_value_list(2).toString()
        val value: Int = kafka_value_list(3).toInt

        val zk_conn = "172.31.113.184:2181/solr"
        val server = new CloudSolrServer(zk_conn)
        server.setDefaultCollection("test");
        val doc = new SolrInputDocument()
        doc.addField( "id", id)
        doc.addField( "date", date)
        doc.addField( "lat", lat)
        doc.addField( "lng", lng)
        doc.addField( "value", value)
        server.add(doc)
        server.commit()
        server.shutdown()
      })
    })
    ssc.start()
    ssc.awaitTermination()
  }
}
