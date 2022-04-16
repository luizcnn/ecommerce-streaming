package entrypoint.consumers;

import core.service.UserService;
import dataprovider.dao.impl.UserDaoPostgres;
import dataprovider.models.UserEntity;
import vo.OrderVO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.consumer.DefaultConsumer;
import org.luizcnn.ecommerce.consumer.ServiceRunner;
import org.luizcnn.ecommerce.utils.JsonUtils;

import java.util.List;

import static java.lang.String.format;
import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_NEW_ORDER;

public class CreateUsersConsumer extends DefaultConsumer {

  private final UserService userService;

  public CreateUsersConsumer(UserService userService) {
    this.userService = userService;
  }

  public static void main(String[] args) {
    final var userDao = new UserDaoPostgres();
    final var userService = new UserService(userDao);
    new ServiceRunner(
            () -> new CreateUsersConsumer(userService)
    ).start(1);
  }

  @Override
  public void consume(ConsumerRecord<String, byte[]> record) {
    try {
      final var order = JsonUtils.readValue(record.value(), OrderVO.class);
      System.out.println("-------------------------------------");
      System.out.println("Processing. Checking for new user");
      final var email = order.getEmail();

      if(isNewUser(email)) {
        userService.save(new UserEntity(email));
        System.out.println(format("User %s successfully added", email));
      } else {
        System.out.println(format("User %s already exists", email));
      }
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

  private boolean isNewUser(String email) {
    return !userService.existsUser(email);
  }
}
