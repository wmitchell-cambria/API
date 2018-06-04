package gov.ca.cwds.rest.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.messages.MessageBuilder;

/**
 * @author CWDS API Team
 */
public class StartDateTimeValidator {

  public static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
  public static final String TIME_FORMAT_PATTERN = "HH:mm:ss";

  public static final Logger LOGGER = LoggerFactory.getLogger(StartDateTimeValidator.class);

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
    DateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN, Locale.US);
    DateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT_PATTERN);
    try {
      Date dateTime = dateTimeFormat.parse(startDateTime);
      timeStarted = timeFormat.format(dateTime);
    } catch (ParseException | NullPointerException e) {
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
    DateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN, Locale.US);
    DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
    String dateStarted = null;
    try {
      Date dateTime = dateTimeFormat.parse(startDateTime);
      dateStarted = dateFormat.format(dateTime);
    } catch (ParseException | NullPointerException e) {
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
