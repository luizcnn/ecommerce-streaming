package org.luizcnn.ecommerce.utils.exceptions;

public class WriteValueToBytesException extends RuntimeException {

  public WriteValueToBytesException(String msg) {
    super(msg);
  }

  public WriteValueToBytesException(String msg, Throwable throwable) {
    super(msg, throwable);
  }

}
