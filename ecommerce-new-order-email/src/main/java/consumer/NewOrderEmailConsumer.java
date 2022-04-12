package consumer;

import models.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.consumer.DefaultConsumer;
import org.luizcnn.ecommerce.consumer.ServiceRunner;
import org.luizcnn.ecommerce.dispatcher.impl.KafkaDispatcherImpl;
import org.luizcnn.ecommerce.utils.JsonUtils;
import services.EmailService;

import java.util.List;

import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_NEW_ORDER;

public class NewOrderEmailConsumer extends DefaultConsumer {

  private final EmailService emailService;

  public static void main(String[] args) {
    final var emailDispatcher = new KafkaDispatcherImpl<String, byte[]>();
    final var emailService = new EmailService(emailDispatcher);
    new ServiceRunner(
            () -> new NewOrderEmailConsumer(emailService)
    ).start(1);
  }

  public NewOrderEmailConsumer(EmailService emailService) {
    this.emailService = emailService;
  }

  @Override
  public void consume(ConsumerRecord<String, byte[]> record) {
    try {
      final var order = JsonUtils.readValue(record.value(), Order.class);
      emailService.sendEmail(order);
    } catch (Exception e) {
      e.printStackTrace();
      sendDLQ(record);
    }
  }

  @Override
  public List<String> getTopics() {
    return List.of(ECOMMERCE_NEW_ORDER.getTopic());
  }

  @Override
  public List<String> getDLQ() {
    return List.of(ECOMMERCE_NEW_ORDER.getDLQTopic());
  }
}
