package org.luizcnn.ecommerce.service.impl;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.luizcnn.ecommerce.kafkaproperties.ConsumerProperties;
import org.luizcnn.ecommerce.service.ConsumerFunction;
import org.luizcnn.ecommerce.service.KafkaService;

import java.io.Closeable;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class KafkaServiceImpl<K, V> implements KafkaService, Closeable {

  private final KafkaConsumer<K, V> kafkaConsumer;
  private final ConsumerFunction<K, V> consumerFunction;

  public KafkaServiceImpl(List<String> topics, ConsumerFunction<K, V> consumerFunction, Map<String, String> customProperties) {
    this.kafkaConsumer = new KafkaConsumer<>(ConsumerProperties.getProperties(customProperties));
    this.consumerFunction = consumerFunction;
    kafkaConsumer.subscribe(topics);
  }

  public <T> KafkaServiceImpl(List<String> topics, ConsumerFunction<K, V> consumerFunction, Class<T> clazz) {
    this.kafkaConsumer = new KafkaConsumer<>(ConsumerProperties.getProperties(clazz));
    this.consumerFunction = consumerFunction;
    kafkaConsumer.subscribe(topics);
  }

  public <T> KafkaServiceImpl(List<String> topics, ConsumerFunction<K, V> consumerFunction, Class<T> clazz, Map<String, String> customProperties) {
    this.kafkaConsumer = new KafkaConsumer<>(ConsumerProperties.getProperties(clazz, customProperties));
    this.consumerFunction = consumerFunction;
    kafkaConsumer.subscribe(topics);
  }

  @Override
  public void run() {
    while(true) {
      final var records = kafkaConsumer.poll(Duration.ofMillis(100));
      if(!records.isEmpty()) {
        records.forEach(this.consumerFunction::consume);
      }
    }
  }

  @Override
  public void close() {
    kafkaConsumer.close();
  }

}
