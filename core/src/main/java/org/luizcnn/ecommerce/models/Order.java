package org.luizcnn.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.String.format;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

  private String email;
  private String orderId;
  private BigDecimal amount;

  public Order() {}

  public Order(String orderId, BigDecimal amount, String email) {
    this.orderId = orderId;
    this.amount = amount;
    this.email = email;
  }

  public String getOrderId() {
    return orderId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    final var formattedAmount = this.amount.setScale(2, RoundingMode.FLOOR);
    return format("Order: id(%s) | email(%s) | amount(R$ %s)", this.orderId, this.email, formattedAmount);
  }
}
