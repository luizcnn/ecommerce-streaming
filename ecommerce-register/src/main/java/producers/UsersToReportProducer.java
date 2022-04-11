package producers;

import dataprovider.models.User;
import org.luizcnn.ecommerce.dispatcher.KafkaDispatcher;
import org.luizcnn.ecommerce.dispatcher.impl.KafkaDispatcherImpl;
import org.luizcnn.ecommerce.utils.JsonUtils;

import java.util.List;

public class UsersToReportProducer {

  private final KafkaDispatcher<String, byte[]> usersDispatcher = new KafkaDispatcherImpl<>();

  public void process(List<User> users, String topic) {
    users.forEach(user -> usersDispatcher.sendAsync(topic, user.getEmail(), JsonUtils.writeValueAsBytes(user)));
  }

}
