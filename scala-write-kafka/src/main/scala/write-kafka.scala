import java.util.Properties
import org.apache.kafka.clients.producer._

object WriteKafka {
  def main(args: Array[String]) {
    val props = new Properties()
    props.put("bootstrap.servers", "172.31.113.188:9092,172.31.113.210:9092,172.31.113.224:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val topic = "test"
    for(i<- 1 to 1000){
      val date = util.Random.nextInt(1500000000).toString()
      val lat = "%2.2f".format(-90+180*util.Random.nextFloat)
      val lng = "%2.2f".format(-180+360*util.Random.nextFloat)
      val value = util.Random.nextInt(101).toString()
      val record = new ProducerRecord[String, String](topic, List(date,lat,lng,value).mkString(","))
      producer.send(record)
    }
    producer.close()
  }
}
