package org.luizcnn.ecommerce.models;

import java.util.UUID;

public class User {

  private final UUID id;

  public User(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return this.id;
  }

  public String getReportPath() {
    return String.format("target/%s-report.txt", this.id);
  }
}
