package vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dataprovider.models.OrderEntity;
import dataprovider.models.UserEntity;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderVO {

  private String orderId;
  private String email;
  private BigDecimal amount;

  public OrderVO(){}

  public OrderVO(String email, String orderId, BigDecimal amount) {
    this.email = email;
    this.orderId = orderId;
    this.amount = amount;
  }

  public String getEmail() {
    return email;
  }

  public String getOrderId() {
    return orderId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public OrderEntity toOrderEntity(UserEntity user, Boolean isFraud) {
    return new OrderEntity(user, this.email, isFraud, this.amount.longValue());
  }

}
