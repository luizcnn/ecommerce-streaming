package org.luizcnn.ecommerce.enums;

public enum TopicEnum {

  ECOMMERCE_NEW_ORDER("ECOMMERCE_NEW_ORDER"),
  ECOMMERCE_ORDER_APPROVED("ECOMMERCE_ORDER_APPROVED"),
  ECOMMERCE_ORDER_REJECTED("ECOMMERCE_ORDER_REJECTED"),
  ECOMMERCE_SEND_EMAIL("ECOMMERCE_SEND_EMAIL");

  private final String name;

  TopicEnum(String name) {
    this.name = name;
  }

  public String getTopic() {
    return this.name;
  }

}
