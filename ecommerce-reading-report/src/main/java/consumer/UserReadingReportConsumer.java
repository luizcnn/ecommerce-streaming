package consumer;

import models.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.consumer.DefaultConsumer;
import org.luizcnn.ecommerce.service.impl.KafkaServiceImpl;
import org.luizcnn.ecommerce.utils.JsonUtils;
import service.UserReportService;

import java.util.List;

import static org.luizcnn.ecommerce.kafka.TopicEnum.USER_GENERATE_READING_REPORT;

public class UserReadingReportConsumer implements DefaultConsumer {

  private final UserReportService userReportService;

  public UserReadingReportConsumer(UserReportService userReportService) {
    this.userReportService = userReportService;
  }

  public static void main(String[] args) {
    final var reportService = new UserReportService();
    final var readingReportConsumer = new UserReadingReportConsumer(reportService);

    try(var kafkaService = new KafkaServiceImpl<>(readingReportConsumer.getTopics(), readingReportConsumer::consume, UserReadingReportConsumer.class)) {
      kafkaService.run();
    }
  }

  @Override
  public void consume(ConsumerRecord<String, byte[]> record) {
    final var user = JsonUtils.readValue(record.value(), User.class);
    System.out.println("-------------------------------------");
    System.out.println("Processing report for " + user.getId());

    userReportService.process(user);

  }

  @Override
  public List<String> getTopics() {
    return List.of(USER_GENERATE_READING_REPORT.getTopic());
  }

}
