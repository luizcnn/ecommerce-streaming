package consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.consumer.DefaultConsumer;

import java.util.List;

public class ReportsConsumer implements DefaultConsumer {
  @Override
  public void consume(ConsumerRecord<String, byte[]> record) {

  }

  @Override
  public List<String> getTopics() {
    return null;
  }
}
