package org.luizcnn.ecommerce.dispatcher;

public interface KafkaDispatcher<K, V> {
  void send(String topic, K key, V value);
}
