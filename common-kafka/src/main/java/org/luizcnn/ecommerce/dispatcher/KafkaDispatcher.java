package org.luizcnn.ecommerce.dispatcher;

import java.io.Closeable;

public interface KafkaDispatcher<K, V> extends Closeable {

  void send(String topic, K key, V value);

  @Override
  void close();

}
