package com.github.shinestar.factory;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KafkaReceiver<K, V> {

    private KafkaConsumer<K, V> consumer;


    public KafkaReceiver(Properties properties){
        this.consumer = new KafkaConsumer<K, V>(properties);
        consumer.subscribe(Arrays.asList("test"));
    }

    public void receiveMessage(String... topics){

        while(true){
            ConsumerRecords<K, V> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<K, V> record : records) {
                System.out.println(record.offset() + "" + record.partition() + "" + record.key() + "" + record.value());
            }
        }
    }
}
