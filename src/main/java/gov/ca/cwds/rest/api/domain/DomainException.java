package gov.ca.cwds.rest.api.domain;

@SuppressWarnings("serial")
public class DomainException extends RuntimeException {

  public DomainException() {
    super();
  }

  public DomainException(String message) {
    super(message);
  }

  public DomainException(Throwable cause) {
    super(cause);
  }

  public DomainException(String message, Throwable cause) {
    super(message, cause);
  }

  public DomainException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
