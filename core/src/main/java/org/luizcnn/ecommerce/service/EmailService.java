package org.luizcnn.ecommerce.service;

import org.luizcnn.ecommerce.dispatcher.KafkaDispatcher;
import org.luizcnn.ecommerce.models.Email;
import org.luizcnn.ecommerce.models.Order;
import org.luizcnn.ecommerce.utils.JsonUtils;

import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_SEND_EMAIL;

public class EmailService {

  public final KafkaDispatcher<String, byte[]> emailDispatcher;


  public EmailService(KafkaDispatcher<String, byte[]> emailDispatcher) {
    this.emailDispatcher = emailDispatcher;
  }

  public void sendEmail(String userEmail, String orderId) {
    final var email = buildEmail(userEmail, orderId);
    this.emailDispatcher.send(ECOMMERCE_SEND_EMAIL.getTopic(), userEmail, JsonUtils.writeValueAsBytes(email));
  }

  private Email buildEmail(String userEmail, String orderId) {
    final var subject = "Processing order with id: " + orderId;
    final var body = String.format( "Thank you for your order %s! We are processing your request.", userEmail);

    return new Email(subject, body);
  }
}
