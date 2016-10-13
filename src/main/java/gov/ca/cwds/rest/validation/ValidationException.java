package gov.ca.cwds.rest.validation;

/**
 * Runtime exception indicating a problem when performing a validation
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
public class ValidationException extends RuntimeException {

  public ValidationException() {
    super();
  }

  public ValidationException(String message) {
    super(message);
  }

  public ValidationException(Throwable cause) {
    super(cause);
  }

  public ValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  public ValidationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
