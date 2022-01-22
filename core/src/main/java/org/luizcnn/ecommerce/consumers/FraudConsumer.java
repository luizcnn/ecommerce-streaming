package org.luizcnn.ecommerce.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.models.Order;
import org.luizcnn.ecommerce.service.impl.KafkaServiceImpl;
import org.luizcnn.ecommerce.utils.JsonUtils;

import java.util.List;

import static org.luizcnn.ecommerce.enums.TopicEnum.ECOMMERCE_NEW_ORDER;

public class FraudConsumer {

  public static void main(String[] args) {
    final var fraudConsumer = new FraudConsumer();
    try(var kafkaService = new KafkaServiceImpl<>(fraudConsumer.getTopics(), fraudConsumer::consume, FraudConsumer.class)) {
      kafkaService.run();
    }
  }

  private void consume(ConsumerRecord<String, byte[]> record) {
    final var order = JsonUtils.readValue(record.value(), Order.class);
    System.out.println("-------------------------------------");
    System.out.println("Processing new order. Checking for fraud");
    System.out.println(record.key());
    System.out.println(order);
    System.out.println(record.partition());
    System.out.println(record.offset());

    simulateProcessing();

    System.out.println("Order Processed!");
  }

  private List<String> getTopics() {
    return List.of(ECOMMERCE_NEW_ORDER.getTopic());
  }

  private void simulateProcessing() {
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
