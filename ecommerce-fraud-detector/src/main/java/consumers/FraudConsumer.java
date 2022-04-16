package consumers;

import models.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.consumer.DefaultConsumer;
import org.luizcnn.ecommerce.consumer.ServiceRunner;
import org.luizcnn.ecommerce.dispatcher.impl.KafkaDispatcherImpl;
import org.luizcnn.ecommerce.utils.JsonUtils;
import service.FraudDetectorService;

import java.util.List;

import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_NEW_ORDER;

public class FraudConsumer extends DefaultConsumer {

  private final FraudDetectorService fraudDetectorService;

  public FraudConsumer(FraudDetectorService fraudDetectorService) {
    this.fraudDetectorService = fraudDetectorService;
  }

  public static void main(String[] args) {
    final var orderDispatcher = new KafkaDispatcherImpl<String, byte[]>();
    final var fraudDetectorService = new FraudDetectorService(orderDispatcher);
    new ServiceRunner(
            () -> new FraudConsumer(fraudDetectorService)
    ).start(1);
  }

  @Override
  public void consume(ConsumerRecord<String, byte[]> record) {
    try {
      final var order = JsonUtils.readValue(record.value(), Order.class);
      System.out.println("-------------------------------------");
      System.out.println("Processing new order. Checking for fraud");
      System.out.println(record.key());
      System.out.println(order);
      System.out.println(record.partition());
      System.out.println(record.offset());

      fraudDetectorService.process(order);
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
