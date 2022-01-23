package dataprovider.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

  private String userId;

  private String email;

  public Order(){}

  public Order(String userId, String email) {
    this.userId = userId;
    this.email = email;
  }

  public String getUserId() {
    return userId;
  }

  public String getEmail() {
    return email;
  }
}
