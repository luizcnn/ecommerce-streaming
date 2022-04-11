package org.luizcnn.ecommerce.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.dispatcher.KafkaDispatcher;
import org.luizcnn.ecommerce.dispatcher.impl.KafkaDispatcherImpl;

import java.util.List;

public abstract class DefaultConsumer {

  private final KafkaDispatcher<String, byte[]> dispatcher;

  protected DefaultConsumer() {
    this.dispatcher = new KafkaDispatcherImpl<>();
  }

  public abstract void consume(ConsumerRecord<String, byte[]> record);

  public abstract List<String> getTopics();

  public abstract List<String> getDLQ();

  public void sendDLQ(ConsumerRecord<String, byte[]> record) {
    getDLQ().forEach(dlq -> dispatcher.send(dlq, record.key(), record.value()));
  }

}
