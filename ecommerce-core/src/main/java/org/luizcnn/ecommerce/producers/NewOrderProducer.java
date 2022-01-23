package org.luizcnn.ecommerce.producers;

import org.luizcnn.ecommerce.dispatcher.impl.KafkaDispatcherImpl;
import org.luizcnn.ecommerce.models.Order;
import org.luizcnn.ecommerce.service.EmailService;
import org.luizcnn.ecommerce.service.NewOrderService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class NewOrderProducer {

  public static void main(String[] args) {
    final var dispatcher = new KafkaDispatcherImpl<String, byte[]>();
    final var newOrderService = new NewOrderService(dispatcher);
    final var emailService = new EmailService(dispatcher);

    try(dispatcher) {
      generateOrders(newOrderService, emailService);
    }
  }

  private static void generateOrders(NewOrderService newOrderService, EmailService emailService) {
    for(int i = 0; i < 10; i++) {
      final var order = getOrder();
      newOrderService.sendToFraudAnalisys(order);
      emailService.sendEmail(order.getEmail(), order.getOrderId());
    }
  }

  private static Order getOrder() {

    final var orderId = UUID.randomUUID().toString();
    final var email = UUID.randomUUID() + "@email.com";
    final var amount = BigDecimal.valueOf((Math.random()*5000) + 1).setScale(2, RoundingMode.FLOOR);

    return new Order(email, orderId, amount);
  }
}