package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

import static java.lang.String.format;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

  private String email;
  private String orderId;
  private BigDecimal amount;

  public Order() {}

  public Order(String email, String orderId, BigDecimal amount) {
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
    return format("Order: id(%s) | email(%s) | amount(R$ %s)", this.orderId, this.email, this.amount);
  }
}
