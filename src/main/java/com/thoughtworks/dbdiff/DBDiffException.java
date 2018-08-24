package com.thoughtworks.dbdiff;

public class DBDiffException extends RuntimeException {

  public DBDiffException(String message) {
    super(message);
  }

  public DBDiffException(Throwable cause) {
    super(cause);
  }

  public DBDiffException() {
  }

  public DBDiffException(String message, Throwable cause) {
    super(message, cause);
  }
}
