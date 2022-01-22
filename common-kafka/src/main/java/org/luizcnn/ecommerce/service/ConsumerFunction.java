package org.luizcnn.ecommerce.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunction<K, V> {
  void consume(ConsumerRecord<K, V> record);
}
