package org.luizcnn.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.math.RoundingMode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

  private String userId;
  private String orderId;
  private BigDecimal amount;

  public Order() {}

  public Order(String userId, String orderId, BigDecimal amount) {
    this.userId = userId;
    this.orderId = orderId;
    this.amount = amount;
  }

  public String getUserId() {
    return userId;
  }

  public String getOrderId() {
    return orderId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    final var formattedAmount = this.amount.setScale(2, RoundingMode.FLOOR);
    return String.format("Order: id(%s) | userId(%s) | amount(R$ %s)", this.orderId, this.userId, formattedAmount);
  }
}
