package producers;

import dataprovider.models.UserEntity;
import org.luizcnn.ecommerce.dispatcher.KafkaDispatcher;
import org.luizcnn.ecommerce.dispatcher.impl.KafkaDispatcherImpl;
import org.luizcnn.ecommerce.utils.JsonUtils;

import java.util.List;

public class UsersToReportProducer {

  private final KafkaDispatcher<String, byte[]> usersDispatcher = new KafkaDispatcherImpl<>();

  public void process(List<UserEntity> userEntities, String topic) {
    userEntities.forEach(user -> usersDispatcher.sendAsync(topic, user.getEmail(), JsonUtils.writeValueAsBytes(user)));
  }

}
