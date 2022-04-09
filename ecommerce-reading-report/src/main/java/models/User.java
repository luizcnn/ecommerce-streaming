package models;

import java.util.UUID;

public class User {

  private UUID id;
  private String email;

  public User() {}

  public User(UUID id, String email) {
    this.id = id;
    this.email = email;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public UUID getId() {
    return this.id;
  }

  public String getReportPath() {
    return String.format("target/%s-report.txt", this.id);
  }
}
