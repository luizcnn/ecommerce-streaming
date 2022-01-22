package org.luizcnn.ecommerce.utils.exceptions;

public class BytesToObjectException extends RuntimeException {

  public BytesToObjectException(String msg) {
    super(msg);
  }

  public BytesToObjectException(String msg, Throwable throwable) {
    super(msg, throwable);
  }

}
