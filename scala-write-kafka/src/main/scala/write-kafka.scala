import java.util.Properties
import org.apache.kafka.clients.producer._

object WriteKafka {
  def main(args: Array[String]) {
    val  props = new Properties()
    props.put("bootstrap.servers", "172.31.117.46:9092,172.31.117.47:9092,172.31.117.65:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val topic="test"
    for(i<- 1 to 50){
      val record = new ProducerRecord(topic, "key", s"hello $i")
      producer.send(record)
    }
    producer.close()
  }
}
