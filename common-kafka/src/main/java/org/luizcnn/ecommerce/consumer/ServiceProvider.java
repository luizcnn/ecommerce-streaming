package org.luizcnn.ecommerce.consumer;

import org.luizcnn.ecommerce.service.impl.KafkaServiceImpl;

import java.util.concurrent.Callable;

public class ServiceProvider implements Callable<Void> {

  private final DefaultConsumer consumer;

  public ServiceProvider(ConsumerFactory factory) {
    this.consumer = factory.create();
  }

  @Override
  public Void call() {
    try(var kafkaService = new KafkaServiceImpl<>(consumer.getTopics(), consumer::consume, consumer.getClass())) {
      kafkaService.run();
    }
    return null;
  }

}
