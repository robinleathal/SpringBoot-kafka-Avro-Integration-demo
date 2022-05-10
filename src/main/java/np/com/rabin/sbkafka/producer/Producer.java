package np.com.rabin.sbkafka.producer;

import com.github.javafaker.Faker;
import io.confluent.developer.avro.Hobbit;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class Producer {
    //private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final KafkaTemplate<Integer, Hobbit> kafkaTemplate;

    Faker faker;
    @EventListener(ApplicationStartedEvent.class)
    public void generateFakeData() {
        //faker.hobbit().quote();
        faker = Faker.instance();
        //use reactive using flux that allows to generate message every second
        final Flux<Long> interval = Flux.interval(Duration.ofMillis(1_000));

        /**
         Flux.fromStream(Stream.generate(new Supplier<String>() {
        @Override
        public String get() {
        return faker.hobbit().quote();
        }
        }));
         */
        final Flux<String> qoutes = Flux.fromStream(Stream.generate(() -> faker.hobbit().quote()));
        /**
         *
         Flux.zip(interval, qoutes).map(new Function<Tuple2<Long, String>, Object>() {
        @Override
        public Object apply(Tuple2<Long, String> it) {
        return kafkaTemplate.send("hobbit", faker.random().nextInt(42), it.getT2());
        }
        }).blockLast();
         */

        //Also
        /**
         *
         Flux.zip(interval, qoutes).map((Function<Tuple2<Long, String>, Object>) it -> kafkaTemplate.send("hobbit", faker.random().nextInt(42), it.getT2())).blockLast();
         Flux.zip(interval, qoutes)
         .map(it -> kafkaTemplate.send("hobbit", faker.random().nextInt(42), it.getT2())).blockLast();
         Flux.zip(interval, qoutes)
         .map(it -> kafkaTemplate.send("hobbit-avro", faker.random().nextInt(42), it.getT2())).blockLast();
         */
        Flux.zip(interval, qoutes)
                .map(it -> kafkaTemplate.send("hobbit-avro", faker.random().nextInt(42), new Hobbit(it.getT2()))).blockLast();
    }
}
