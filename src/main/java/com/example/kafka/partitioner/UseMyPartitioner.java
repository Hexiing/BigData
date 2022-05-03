package com.example.kafka.partitioner;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;

public class UseMyPartitioner {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092,hadoop103:9092,hadoop104:9092");
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        prop.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.example.kafka.partitioner.MyPartitioner");

        /*
        异步发送
         */
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(prop);

        kafkaProducer.send(new ProducerRecord<>("first","hello"));

        kafkaProducer.close();
    }
}

/*
自定义好的分区器复制全类名加到kafka配置 就可以用了
 */
class MyPartitioner implements Partitioner{

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

        String msgValue = value.toString();

        if (msgValue.contains("hex")) return 0;
        else return 1;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}