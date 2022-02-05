package exceptions;

public class GerenateReportException extends RuntimeException {

  public GerenateReportException(String msg) {
    super(msg);
  }

  public GerenateReportException(String msg, Throwable t) {
    super(msg, t);
  }

}
