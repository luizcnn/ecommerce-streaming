package org.luizcnn.ecommerce.service;

import org.luizcnn.ecommerce.dispatcher.KafkaDispatcher;
import org.luizcnn.ecommerce.models.Email;
import org.luizcnn.ecommerce.utils.JsonUtils;

import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_SEND_EMAIL;

public class EmailService {

  public final KafkaDispatcher<String, byte[]> emailDispatcher;


  public EmailService(KafkaDispatcher<String, byte[]> emailDispatcher) {
    this.emailDispatcher = emailDispatcher;
  }

  public void sendEmail(String userId, String orderId) {
    final var email = buildEmail(orderId);
    this.emailDispatcher.send(ECOMMERCE_SEND_EMAIL.getTopic(), userId, JsonUtils.writeValueAsBytes(email));
  }

  private Email buildEmail(String orderId) {
    final var subject = "Processing order with id: " + orderId;
    final var body = "Thank you for your order! We are processing your request.";

    return new Email(subject, body);
  }
}
