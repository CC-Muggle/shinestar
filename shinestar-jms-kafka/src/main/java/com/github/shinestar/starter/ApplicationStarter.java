package com.github.shinestar.starter;


import com.github.shinestar.factory.KafkaReceiver;
import com.github.shinestar.factory.KafkaSender;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ApplicationStarter {

    public static void main(String[] args) {
        Properties propSender = new Properties();

        propSender.put("bootstrap.servers", "127.0.0.1:9092");
        propSender.put("acks", "all");
        propSender.put("retries", 0);
        propSender.put("batch.size", 16384);
        propSender.put("linger.ms", 1);
        propSender.put("buffer.memory", 33554432);
        propSender.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        propSender.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        threadPoolExecutor.execute(()-> {
            KafkaSender<String, String> producer = new KafkaSender<>(propSender);
            producer.sendMessage("test", "key", "value");
        });

        Properties propReceiver = new Properties();
        propReceiver.setProperty("bootstrap.servers", "127.0.0.1:9092");
        propReceiver.setProperty("group.id", "test");
        propReceiver.setProperty("enable.auto.commit", "true");
        propReceiver.setProperty("auto.commit.interval.ms", "1000");
        propReceiver.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        propReceiver.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaReceiver<String, String> receiver = new KafkaReceiver(propReceiver);
        receiver.receiveMessage("test");

    }
}
