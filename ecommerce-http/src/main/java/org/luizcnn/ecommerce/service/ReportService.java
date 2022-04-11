package org.luizcnn.ecommerce.service;

import org.luizcnn.ecommerce.dispatcher.KafkaDispatcher;

import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_SEND_MESSAGE_TO_ALL_USERS;
import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_USER_GENERATE_READING_REPORT;

public class ReportService {

  public final KafkaDispatcher<String, byte[]> batchDispatcher;

  public ReportService(KafkaDispatcher<String, byte[]> batchDispatcher) {
    this.batchDispatcher = batchDispatcher;
  }

  public void sendReport() {
    this.batchDispatcher.send(
            ECOMMERCE_SEND_MESSAGE_TO_ALL_USERS.getTopic(),
            ECOMMERCE_USER_GENERATE_READING_REPORT.name(),
            ECOMMERCE_USER_GENERATE_READING_REPORT.name().getBytes()
    );
  }

}
