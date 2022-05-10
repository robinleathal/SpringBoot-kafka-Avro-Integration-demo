package np.com.rabin.sbkafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
//@EnableKafkaStreams
public class SbKafkaIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbKafkaIntegrationApplication.class, args);
	}
	/*
	@Bean
	NewTopic hobbitTopic1() {
		final NewTopic topic = TopicBuilder.name("hobbit2")
				.partitions(13)
				.replicas(3)
				.build();
		return topic;
	}

	@Bean
	NewTopic counts() {
		return TopicBuilder.name("streams-wordcount-output").partitions(6).replicas(3).build();
	}

	*/
	@Bean
	NewTopic hobbitAvro() {

		return TopicBuilder.name("hobbit-avro").partitions(3).replicas(3).build();
	}

}
