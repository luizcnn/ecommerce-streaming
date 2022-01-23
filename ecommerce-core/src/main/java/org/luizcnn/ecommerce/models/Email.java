package org.luizcnn.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Email {

  private String subject;

  private String body;

  public Email() {}

  public Email(String subject, String body) {
    this.subject = subject;
    this.body = body;
  }

  public String getSubject() {
    return subject;
  }

  public String getBody() {
    return body;
  }

  @Override
  public String toString() {
    return String.format("[Email] Subject: %s\nMessage: \n%s", this.subject, this.body);
  }
}
