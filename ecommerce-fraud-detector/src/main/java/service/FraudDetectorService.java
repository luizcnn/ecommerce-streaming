package service;

import models.Order;
import org.luizcnn.ecommerce.dispatcher.KafkaDispatcher;
import org.luizcnn.ecommerce.utils.JsonUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static java.lang.String.format;
import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_ORDER_APPROVED;
import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_ORDER_REJECTED;

public class FraudDetectorService {

  private final KafkaDispatcher<String, byte[]> orderDispatcher;
  private final OrderService orderService;

  public FraudDetectorService(KafkaDispatcher<String, byte[]> orderDispatcher, OrderService orderService) {
    this.orderDispatcher = orderDispatcher;
    this.orderService = orderService;
  }

  public void process(Order order) {
    if(wasProcessed(order.getOrderId())){
      System.out.println(format("The order with id %s was already processed", order.getOrderId()));
      return;
    }
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

  private boolean wasProcessed(String orderId) {
    return orderService.existsById(UUID.fromString(orderId));
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
    orderDispatcher.send(topic, order.getEmail(), JsonUtils.writeValueAsBytes(order));
  }
}
