package np.com.rabin.sbkafka.consumer;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//@Component
public class Processor {
    //To create or use topology - need StreamBuilder
    @Autowired
    public void process(StreamsBuilder streamsBuilder) {
        //Add SerDes as well as a KStream to specify the topic to read from
        //Serializers/Deserializers (serde) for String and Long types
        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();
        final Serde<Integer> integerSerde = Serdes.Integer();
        /**Construct a KStream for the input topic "streams-plaintest-input, where message values
         * represent lines of text (for the sake of this example, we ignore whatever may be stored
         * in the message keys.
         */
        KStream<Integer, String> kStreamTextLines = streamsBuilder.stream("hobbit", Consumed.with(integerSerde, stringSerde));
        //add a KTable to process your streamed data, splitting each message into words with flatMapValues, grouping, and then counting:
        /**
         * Split each textline, by with spac,e into words. The text lines are the message values
         * i.e. we can ignore whatever data is in the message keys and thus invoke 'flatMapValues instead of the more generic flatMap.
         * */
        KTable<String, Long> wordCounts = kStreamTextLines
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                //we use groupBy to ensure the words are avilable as message keys
                .groupBy((key, value) -> value, Grouped.with(stringSerde, stringSerde))
                //count the occurances of each words (message keys)
                .count(Materialized.as("counts"));
        //Convert the KTable<String, Long> into a KStream<String, Long> and write to the output  topic
        wordCounts.toStream().to("streams-wordcount-output", Produced.with(stringSerde, longSerde));

    }
}
