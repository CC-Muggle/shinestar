package com.github.shinestar.factory;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Objects;
import java.util.Properties;

public class Connection<K, V> {

    private KafkaProducer<K, V> producer;

    private static volatile Connection<K, V> connector;

    private Connection(){
        Properties props = new Properties();
        producer = new KafkaProducer<K, V>(props);
    }

    public static <K, V> Connection<K, V> getConnection(){
        //双重锁校验
        if(Objects.isNull(connector)){

        }
        return connector;
    }
}
