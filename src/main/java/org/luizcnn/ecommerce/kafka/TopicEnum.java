package org.luizcnn.ecommerce.kafka;

public enum TopicEnum {

  ECOMMERCE_NEW_ORDER("ECOMMERCE_NEW_ORDER", "ECOMMERCE_NEW_ORDER_dlq"),
  ECOMMERCE_ORDER_APPROVED("ECOMMERCE_ORDER_APPROVED", "ECOMMERCE_ORDER_APPROVED_dlq"),
  ECOMMERCE_ORDER_REJECTED("ECOMMERCE_ORDER_REJECTED", "ECOMMERCE_ORDER_REJECTED_dlq"),
  ECOMMERCE_SEND_EMAIL("ECOMMERCE_SEND_EMAIL", "ECOMMERCE_SEND_EMAIL_dlq"),
  ECOMMERCE_USER_GENERATE_READING_REPORT("ECOMMERCE_USER_GENERATE_READING_REPORT", "ECOMMERCE_USER_GENERATE_READING_REPORT_dlq"),
  ECOMMERCE_SEND_MESSAGE_TO_ALL_USERS("ECOMMERCE_SEND_MESSAGE_TO_ALL_USERS", "ECOMMERCE_SEND_MESSAGE_TO_ALL_USERS_dlq");

  private final String name;
  private final String dlqTopic;

  TopicEnum(String name, String dlqTopic) {
    this.name = name;
    this.dlqTopic = dlqTopic;
  }

  public String getTopic() {
    return this.name;
  }

  public String getDLQTopic() {
    return this.dlqTopic;
  }

}
