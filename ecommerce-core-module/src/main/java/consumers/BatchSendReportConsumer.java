package consumers;

import dataprovider.dao.impl.UserDaoPostgres;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.consumer.DefaultConsumer;
import org.luizcnn.ecommerce.consumer.ServiceRunner;
import org.luizcnn.ecommerce.kafka.TopicEnum;
import producers.UsersToReportProducer;
import service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class BatchSendReportConsumer extends DefaultConsumer {

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
    new ServiceRunner(
            () -> new BatchSendReportConsumer(userService, usersToReportProducer)
    ).start(1);
  }

  @Override
  public void consume(ConsumerRecord<String, byte[]> record) {
    try {
      final var topic = new String(record.value(), StandardCharsets.UTF_8);
      final var users = userService.findAll();
      System.out.println("Sending users list to topic. Total of " + users.size() + " users.");
      this.usersToReportProducer.process(users, topic);
    } catch (Exception e) {
      e.printStackTrace();
      sendDLQ(record);
    }
  }

  @Override
  public List<String> getTopics() {
    return List.of(TopicEnum.ECOMMERCE_SEND_MESSAGE_TO_ALL_USERS.getTopic());
  }

  @Override
  public List<String> getDLQ() {
    return List.of(TopicEnum.ECOMMERCE_SEND_MESSAGE_TO_ALL_USERS.getDLQTopic());
  }
}
