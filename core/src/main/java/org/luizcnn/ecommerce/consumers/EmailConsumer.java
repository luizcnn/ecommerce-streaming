package org.luizcnn.ecommerce.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.models.Email;
import org.luizcnn.ecommerce.service.impl.KafkaServiceImpl;
import org.luizcnn.ecommerce.utils.JsonUtils;

import java.util.List;

import static org.luizcnn.ecommerce.enums.TopicEnum.ECOMMERCE_SEND_EMAIL;

public class EmailConsumer {

  public static void main(String[] args) {
    final var emailConsumer = new EmailConsumer();
    try(var kafkaService = new KafkaServiceImpl<>(emailConsumer.getTopics(), emailConsumer::consume, EmailConsumer.class)) {
      kafkaService.run();
    }
  }

  private void consume(ConsumerRecord<String, byte[]> record) {
    final var email = JsonUtils.readValue(record.value(), Email.class);
    System.out.println("-------------------------------------");
    System.out.println("Processing. Sending email...");
    System.out.println(record.key());
    System.out.println(email);
    System.out.println(record.partition());
    System.out.println(record.offset());

    simulateProcessing();

    System.out.println("Email Sent!");
  }

  private List<String> getTopics() {
    return List.of(ECOMMERCE_SEND_EMAIL.getTopic());
  }

  private void simulateProcessing() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}