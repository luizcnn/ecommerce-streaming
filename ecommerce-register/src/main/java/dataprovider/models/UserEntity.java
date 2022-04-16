package dataprovider.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;

  private String email;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private final List<OrderEntity> orders = new ArrayList<>();

  public UserEntity() {}

  public UserEntity(String email) {
    this.email = email;
  }

  public UUID getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }
}
