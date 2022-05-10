package np.com.rabin.sbkafka.consumer;

import io.confluent.developer.avro.Hobbit;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    /**
     *
     @KafkaListener(topics = {"hobbit"}, groupId = "sb-kafka")
     public void consume(String qoute) {
     System.out.println("received qoute = "+qoute);
     }
     */
    /**
     @KafkaListener(topics = {"hobbit"}, groupId = "sb-kafka")
     public void consume(ConsumerRecord<Integer, String> consumerRecord) {
     System.out.println("received qoute = "+consumerRecord.value()+ "with key "+consumerRecord.key());
     }

     @KafkaListener(topics = {"streams-wordcount-output"}, groupId = "sb-kafka")
     public void consume(ConsumerRecord<String, Long> consumerRecord) {
     System.out.println("received qoute = "+consumerRecord.value()+ " with key "+consumerRecord.key());
     }
     */
    @KafkaListener(topics = {"hobbit-avro"}, groupId = "sb-kafka")
    public void consume(ConsumerRecord<Integer, Hobbit> consumerRecord) {
        System.out.println("received qoute = "+consumerRecord.value()+ " with key "+consumerRecord.key());
    }
}
