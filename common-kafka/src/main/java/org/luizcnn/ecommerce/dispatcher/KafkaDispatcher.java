package org.luizcnn.ecommerce.dispatcher;

import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.Closeable;
import java.util.concurrent.Future;

public interface KafkaDispatcher<K, V> extends Closeable {

  void send(String topic, K key, V value);

  Future<RecordMetadata> sendAsync(String topic, K key, V value);

  @Override
  void close();

}
