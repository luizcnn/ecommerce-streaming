package org.luizcnn.ecommerce.service;

import org.luizcnn.ecommerce.dispatcher.KafkaDispatcher;
import org.luizcnn.ecommerce.models.Email;
import org.luizcnn.ecommerce.models.Order;
import org.luizcnn.ecommerce.models.User;
import org.luizcnn.ecommerce.utils.JsonUtils;

import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_SEND_EMAIL;
import static org.luizcnn.ecommerce.kafka.TopicEnum.USER_GENERATE_READING_REPORT;

public class UserReportService {

  public final KafkaDispatcher<String, byte[]> emailDispatcher;

  public UserReportService(KafkaDispatcher<String, byte[]> emailDispatcher) {
    this.emailDispatcher = emailDispatcher;
  }

  public void sendReport(User user) {
    this.emailDispatcher.send(
            USER_GENERATE_READING_REPORT.getTopic(),
            user.getId().toString(),
            JsonUtils.writeValueAsBytes(user)
    );
  }
}
