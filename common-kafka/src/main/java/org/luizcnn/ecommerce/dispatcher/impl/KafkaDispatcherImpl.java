package org.luizcnn.ecommerce.dispatcher.impl;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.luizcnn.ecommerce.kafkaproperties.ProducerProperties;
import org.luizcnn.ecommerce.dispatcher.KafkaDispatcher;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public class KafkaDispatcherImpl<K, V> implements KafkaDispatcher<K, V> {

  private final KafkaProducer<K, V> producer;

  public KafkaDispatcherImpl() {
    this.producer = new KafkaProducer<>(ProducerProperties.getDefaultProperties());
  }

  public KafkaDispatcherImpl(Map<String, String> customProperties) {
    this.producer = new KafkaProducer<>(ProducerProperties.getProperties(customProperties));
  }

  @Override
  public void send(String topic, K key, V value) {
    try {
      producer.send(buildRecord(topic, key, value), getCallback()).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new KafkaException(format("Fail to send message: Topic: %s | Message: %s", topic, value));
    }
  }

  @Override
  public void close() {
    producer.close();
  }

  private ProducerRecord<K,V> buildRecord(String topic, K key, V value) {
    return new ProducerRecord<>(topic, key, value);
  }

  private Callback getCallback() {
    return (data, error) -> {
      if (isNull(error)) {
        final var successMsg = "Topic: %s | Partition: %s | Offset: %s | datSent: %s";
        System.out.println(format(successMsg, data.topic(), data.partition(), data.offset(), data.timestamp()));
      } else {
        error.printStackTrace();
      }
    };
  }
}
