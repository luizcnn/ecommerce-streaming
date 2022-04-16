package dataprovider.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "order_id")
  private UUID orderId;

  @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserEntity.class)
  private UserEntity user;

  @Column(name = "email")
  private String email;

  @Column(name = "fraud")
  private Boolean isFraud;

  @Column(name = "amount")
  private Long amount;

  public OrderEntity() {

  }

  public OrderEntity(UserEntity user, String email, Boolean isFraud, Long amount) {
    this.user = user;
    this.email = email;
    this.isFraud = isFraud;
    this.amount = amount;
  }

  public UUID getOrderId() {
    return orderId;
  }

  public UserEntity getUser() {
    return user;
  }

  public String getEmail() {
    return email;
  }

  public Boolean getFraud() {
    return isFraud;
  }

  public Long getAmount() {
    return amount;
  }
}
