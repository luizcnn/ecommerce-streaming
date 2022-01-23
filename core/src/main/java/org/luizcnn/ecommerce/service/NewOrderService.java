package org.luizcnn.ecommerce.service;

import org.luizcnn.ecommerce.dispatcher.KafkaDispatcher;
import org.luizcnn.ecommerce.models.Order;
import org.luizcnn.ecommerce.utils.JsonUtils;

import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_NEW_ORDER;

public class NewOrderService {

  private final KafkaDispatcher<String, byte[]> newOrderDispatcher;

  public NewOrderService(KafkaDispatcher<String, byte[]> newOrderDispatcher) {
    this.newOrderDispatcher = newOrderDispatcher;
  }

  public void sendToFraudAnalisys(Order order) {
    this.newOrderDispatcher.send(ECOMMERCE_NEW_ORDER.getTopic(), order.getUserId(), JsonUtils.writeValueAsBytes(order));
  }

}
