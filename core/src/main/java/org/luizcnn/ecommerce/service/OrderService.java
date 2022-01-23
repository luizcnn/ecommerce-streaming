package org.luizcnn.ecommerce.service;

import org.luizcnn.ecommerce.dispatcher.KafkaDispatcher;
import org.luizcnn.ecommerce.models.Order;
import org.luizcnn.ecommerce.utils.JsonUtils;

import java.math.BigDecimal;

import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_ORDER_APPROVED;
import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_ORDER_REJECTED;

public class OrderService {

  private final KafkaDispatcher<String, byte[]> orderDispatcher;

  public OrderService(KafkaDispatcher<String, byte[]> orderDispatcher) {
    this.orderDispatcher = orderDispatcher;
  }

  public void processOrder(Order order) {
    this.simulateProcessing();
    if(isFraud(order.getAmount())) {
      System.out.println("Order is a fraud!!! " + order);
      this.send(ECOMMERCE_ORDER_REJECTED.getTopic(), order);
    } else {
      System.out.println("Order Accepted: " + order);
      this.send(ECOMMERCE_ORDER_APPROVED.getTopic(), order);
    }
    System.out.println("Order Processed!");
  }

  private boolean isFraud(BigDecimal amount) {
    return amount.compareTo(new BigDecimal("3500")) > 0;
  }

  private void simulateProcessing() {
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void send(String topic, Order order) {
    orderDispatcher.send(topic, order.getUserId(), JsonUtils.writeValueAsBytes(order));
  }
}
