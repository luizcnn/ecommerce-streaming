package entrypoint.consumers;

import core.service.UserService;
import dataprovider.dao.impl.UserDaoPostgres;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.consumer.DefaultConsumer;
import org.luizcnn.ecommerce.service.impl.KafkaServiceImpl;
import producers.UsersToReportProducer;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.luizcnn.ecommerce.kafka.TopicEnum.SEND_MESSAGE_TO_ALL_USERS;

public class BatchSendReportConsumer implements DefaultConsumer {

  private final UserService userService;
  private final UsersToReportProducer usersToReportProducer;

  public BatchSendReportConsumer(UserService userService, UsersToReportProducer usersToReportProducer) {
    this.userService = userService;
    this.usersToReportProducer = usersToReportProducer;
  }

  public static void main(String[] args) {
    final var userDao = new UserDaoPostgres();
    final var userService = new UserService(userDao);
    final var usersToReportProducer = new UsersToReportProducer();
    final var batchSendReportConsumer = new BatchSendReportConsumer(userService, usersToReportProducer);

    final var batchSendReportService = new KafkaServiceImpl<>(
            batchSendReportConsumer.getTopics(), batchSendReportConsumer::consume, BatchSendReportConsumer.class
    );

    try(batchSendReportService) {
      batchSendReportService.run();
    }
  }

  @Override
  public void consume(ConsumerRecord<String, byte[]> record) {
    final var topic = new String(record.value(), StandardCharsets.UTF_8);
    final var users = userService.findAll();
    System.out.println("Sending users list to topic. Total of " + users.size() + " users.");
    this.usersToReportProducer.process(users, topic);
  }

  @Override
  public List<String> getTopics() {
    return List.of(SEND_MESSAGE_TO_ALL_USERS.getTopic());
  }
}
