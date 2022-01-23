package org.luizcnn.ecommerce.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.consumer.DefaultConsumer;
import org.luizcnn.ecommerce.dispatcher.impl.KafkaDispatcherImpl;
import org.luizcnn.ecommerce.models.Order;
import org.luizcnn.ecommerce.service.OrderService;
import org.luizcnn.ecommerce.service.impl.KafkaServiceImpl;
import org.luizcnn.ecommerce.utils.JsonUtils;

import java.util.List;

import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_NEW_ORDER;

public class FraudConsumer implements DefaultConsumer {

  private final OrderService orderService;

  public FraudConsumer(OrderService orderService) {
    this.orderService = orderService;
  }

  public static void main(String[] args) {
    final var orderDispatcher = new KafkaDispatcherImpl<String, byte[]>();
    final var orderService = new OrderService(orderDispatcher);
    final var fraudConsumer = new FraudConsumer(orderService);

    try(var kafkaService = new KafkaServiceImpl<>(fraudConsumer.getTopics(), fraudConsumer::consume, FraudConsumer.class)) {
      kafkaService.run();
    }
  }

  @Override
  public void consume(ConsumerRecord<String, byte[]> record) {
    final var order = JsonUtils.readValue(record.value(), Order.class);
    System.out.println("-------------------------------------");
    System.out.println("Processing new order. Checking for fraud");
    System.out.println(record.key());
    System.out.println(order);
    System.out.println(record.partition());
    System.out.println(record.offset());

    orderService.processOrder(order);
  }

  @Override
  public List<String> getTopics() {
    return List.of(ECOMMERCE_NEW_ORDER.getTopic());
  }

}
