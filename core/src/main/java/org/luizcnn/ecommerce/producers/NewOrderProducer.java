package org.luizcnn.ecommerce.producers;

import org.luizcnn.ecommerce.models.Email;
import org.luizcnn.ecommerce.models.Order;
import org.luizcnn.ecommerce.dispatcher.impl.KafkaDispatcherImpl;
import org.luizcnn.ecommerce.utils.JsonUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.luizcnn.ecommerce.enums.TopicEnum.ECOMMERCE_NEW_ORDER;
import static org.luizcnn.ecommerce.enums.TopicEnum.ECOMMERCE_SEND_EMAIL;

public class NewOrderProducer {

  public static void main(String[] args) {
    try(var orderDispatcher = new KafkaDispatcherImpl<String, byte[]>()) {
      for(int i = 0; i < 5; i++) {
        final var order = getOrder();
        orderDispatcher.send(getTopic(), order.getUserId(), JsonUtils.writeValueAsBytes(order));
        sendEmail(order.getUserId(), order.getOrderId());
      }
    }
  }

  private static Order getOrder() {
    final var userId = UUID.randomUUID().toString();
    final var orderId = UUID.randomUUID().toString();
    final var amount = BigDecimal.valueOf((Math.random()*5000) + 1);

    return new Order(userId, orderId, amount);
  }

  private static void sendEmail(String key, String orderId) {
    try(var emailDispatcher = new KafkaDispatcherImpl<String, byte[]>()) {
      final var email = buildEmail(orderId);

      emailDispatcher.send(getEmailTopic(), key, JsonUtils.writeValueAsBytes(email));
    }
  }

  private static Email buildEmail(String orderId) {
    final var subject = "Processing order with id: " + orderId;
    final var body = "Thank you for your order! We are processing your request.";

    return new Email(subject, body);
  }

  private static String getTopic() {
    return ECOMMERCE_NEW_ORDER.getTopic();
  }

  private static String getEmailTopic() {
    return ECOMMERCE_SEND_EMAIL.getTopic();
  }
}
