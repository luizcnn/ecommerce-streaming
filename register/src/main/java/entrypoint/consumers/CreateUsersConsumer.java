package entrypoint.consumers;

import core.service.UserService;
import dataprovider.dao.impl.UserDaoPostgres;
import dataprovider.models.Order;
import dataprovider.models.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.consumer.DefaultConsumer;
import org.luizcnn.ecommerce.service.impl.KafkaServiceImpl;
import org.luizcnn.ecommerce.utils.JsonUtils;

import java.util.List;

import static java.lang.String.format;
import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_NEW_ORDER;

public class CreateUsersConsumer implements DefaultConsumer {

  private final UserService userService;

  public CreateUsersConsumer(UserService userService) {
    this.userService = userService;
  }

  public static void main(String[] args) {
    final var userDao = new UserDaoPostgres();
    final var userService = new UserService(userDao);
    final var createUsersConsumer = new CreateUsersConsumer(userService);

    final var createUsersService = new KafkaServiceImpl<>(
            createUsersConsumer.getTopics(), createUsersConsumer::consume, CreateUsersConsumer.class
    );

    try(createUsersService) {
      createUsersService.run();
    }
  }

  @Override
  public void consume(ConsumerRecord<String, byte[]> record) {
    final var order = JsonUtils.readValue(record.value(), Order.class);
    System.out.println("-------------------------------------");
    System.out.println("Processing. Checking for new user");
    final var email = order.getEmail();

    if(isNewUser(email)) {
      userService.save(new User(email));
      System.out.println(format("User %s successfully added", email));
    } else {
      System.out.println(format("User %s already exists", email));
    }
  }

  @Override
  public List<String> getTopics() {
    return List.of(ECOMMERCE_NEW_ORDER.getTopic());
  }

  private boolean isNewUser(String email) {
    return !userService.existsUser(email);
  }
}
