import java.util.{Collections, Properties}
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

object ReadKafka {
  def main(args: Array[String]) {
    val  props = new Properties()
    props.put("bootstrap.servers", "172.31.117.46:9092,172.31.117.47:9092,172.31.117.65:9092")
    props.put("group.id", "group12345")
    //props.put("auto.offset.reset", "earliest")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    val c = new KafkaConsumer[String, String](props)
    c.subscribe(Collections.singletonList("test"))

    while (true) {
      val rec = c.poll(100)
      import scala.collection.JavaConversions._
      for (r <- rec) {
        println(r.value)
      }
    }
    c.close()
  }
}
