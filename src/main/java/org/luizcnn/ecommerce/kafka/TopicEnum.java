package org.luizcnn.ecommerce.kafka;

public enum TopicEnum {

  ECOMMERCE_NEW_ORDER("ECOMMERCE_NEW_ORDER"),
  ECOMMERCE_ORDER_APPROVED("ECOMMERCE_ORDER_APPROVED"),
  ECOMMERCE_ORDER_REJECTED("ECOMMERCE_ORDER_REJECTED"),
  ECOMMERCE_SEND_EMAIL("ECOMMERCE_SEND_EMAIL"),
  USER_GENERATE_READING_REPORT("USER_GENERATE_READING_REPORT"),
  SEND_MESSAGE_TO_ALL_USERS("SEND_MESSAGE_TO_ALL_USERS");

  private final String name;

  TopicEnum(String name) {
    this.name = name;
  }

  public String getTopic() {
    return this.name;
  }

}
