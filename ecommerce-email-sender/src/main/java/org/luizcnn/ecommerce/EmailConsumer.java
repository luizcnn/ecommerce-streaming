package org.luizcnn.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.consumer.DefaultConsumer;
import org.luizcnn.ecommerce.consumer.ServiceRunner;
import org.luizcnn.ecommerce.models.Email;
import org.luizcnn.ecommerce.utils.JsonUtils;

import java.util.List;

import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_SEND_EMAIL;

public class EmailConsumer extends DefaultConsumer {

  public static void main(String[] args) {
    new ServiceRunner(EmailConsumer::new).start(5);
  }

  @Override
  public void consume(ConsumerRecord<String, byte[]> record) {
    try {
      final var email = JsonUtils.readValue(record.value(), Email.class);
      System.out.println("-------------------------------------");
      System.out.println("Processing. Sending email...");
      System.out.println(record.key());
      System.out.println(email);
      System.out.println(record.partition());
      System.out.println(record.offset());

      simulateProcessing();

      System.out.println("Email Sent!");
    } catch (Exception e) {
      e.printStackTrace();
      this.sendDLQ(record);
    }
  }

  @Override
  public List<String> getTopics() {
    return List.of(ECOMMERCE_SEND_EMAIL.getTopic());
  }

  @Override
  public List<String> getDLQ() {
    return List.of(ECOMMERCE_SEND_EMAIL.getDLQTopic());
  }

  private void simulateProcessing() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
