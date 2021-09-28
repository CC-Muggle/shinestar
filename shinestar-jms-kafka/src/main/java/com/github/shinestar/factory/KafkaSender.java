package com.github.shinestar.factory;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * kafka消息生产者
 *
 * producer要求能够单利存在，发送消息需要多线程，单线程直接不能进行通信
 *
 * @author yangcj
 */
public class KafkaSender<K, V> {

    private static volatile Producer<Object, Object> producer;

    /**
     * 创建单例
     *
     * @param properties
     */
    public KafkaSender(Properties properties){
        if(Objects.isNull(producer)){
            synchronized (this){
                if(Objects.isNull(producer)){
                    producer = new KafkaProducer<>(properties);
                }
            }
        }
    }

    /**
     * 发送消息至某个topic
     *
     * @param topic
     * @param key
     * @param value
     */
    public void sendMessage(String topic, Object key, Object value){
        ProducerRecord<Object, Object> record = new ProducerRecord(topic, key, value);
        producer.send(record);
        producer.flush();
    }
}
