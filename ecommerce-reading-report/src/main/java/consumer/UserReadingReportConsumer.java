package consumer;

import models.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.consumer.DefaultConsumer;
import org.luizcnn.ecommerce.service.impl.KafkaServiceImpl;
import org.luizcnn.ecommerce.utils.JsonUtils;
import service.UserReportService;

import java.util.List;

import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_USER_GENERATE_READING_REPORT;

public class UserReadingReportConsumer extends DefaultConsumer {

  private final UserReportService userReportService;

  public UserReadingReportConsumer(UserReportService userReportService) {
    this.userReportService = userReportService;
  }

  public static void main(String[] args) {
    final var reportService = new UserReportService();
    final var readingReportConsumer = new UserReadingReportConsumer(reportService);
    final var kafkaService = new KafkaServiceImpl<>(
            readingReportConsumer.getTopics(), readingReportConsumer::consume, UserReadingReportConsumer.class
    );
    try(kafkaService) {
      kafkaService.run();
    }
  }

  @Override
  public void consume(ConsumerRecord<String, byte[]> record) {
    try {
      final var user = JsonUtils.readValue(record.value(), User.class);
      System.out.println("-------------------------------------");
      System.out.println("Processing report for " + user.getEmail());

      userReportService.process(user);
    } catch (Exception e) {
      e.printStackTrace();
      sendDLQ(record);
    }

  }

  @Override
  public List<String> getTopics() {
    return List.of(ECOMMERCE_USER_GENERATE_READING_REPORT.getTopic());
  }

  @Override
  public List<String> getDLQ() {
    return List.of(ECOMMERCE_USER_GENERATE_READING_REPORT.getDLQTopic());
  }

}
