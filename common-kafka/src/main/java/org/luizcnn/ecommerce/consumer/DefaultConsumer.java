package org.luizcnn.ecommerce.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public interface DefaultConsumer {

  void consume(ConsumerRecord<String, byte[]> record);

  List<String> getTopics();

}
