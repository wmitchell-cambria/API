package gov.ca.cwds.rest.validation;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.messages.MessageBuilder;

/**
 * @author CWDS API Team
 */
public class StartDateTimeValidator {
  private static final Logger LOGGER = LoggerFactory.getLogger(StartDateTimeValidator.class);

  /**
   * default constructor
   */
  private StartDateTimeValidator() {

  }

  /**
   * 
   * @param startDateTime - start date/time
   * @param builder - logError messages
   * @return - timeStarted
   */
  public static String extractStartTime(String startDateTime, MessageBuilder builder) {
    String timeStarted = null;
    try {
      timeStarted = LocalDateTime.parse(startDateTime)
          .toLocalTime().withNano(0).format(DateTimeFormatter.ISO_TIME);
    } catch (Exception e) {
      String message = " parsing Start Date/Time ";
      builder.addError(message);
      logError(message, e);
    }
    return timeStarted;
  }

  /**
   * 
   * @param startDateTime - start date/time
   * @param builder - logError messages
   * @return dateStarted
   */
  public static String extractStartDate(String startDateTime, MessageBuilder builder) {
    String dateStarted = null;
    try {
      dateStarted = LocalDateTime.parse(startDateTime).toLocalDate().toString();
    } catch (Exception e) {
      String message = " parsing Start Date/Time ";
      builder.addError(message);
      logError(message, e);
    }
    return dateStarted;
  }

  private static void logError(String message, Exception exception) {
    LOGGER.error(message, exception.getMessage());
  }
}
