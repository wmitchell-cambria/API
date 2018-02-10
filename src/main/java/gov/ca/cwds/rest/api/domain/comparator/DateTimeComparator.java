package gov.ca.cwds.rest.api.domain.comparator;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Compares the last update time stamp of the incoming domain object and the existing object
 * dateTime. Both objects are different representations of the same object at different parts of
 * application flow.
 * 
 * Joda time is used to handle
 * <p>
 * 
 * @author CWDS API Team
 */
public class DateTimeComparator implements DateTimeComparatorInterface {

  private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeComparator.class);

  /**
   *
   * @param incomingDateTime the incoming DateTime object
   * @param savedDateTime the dateTime of the same other object
   * @return returns true if the date is equal to the second resolution
   */
  @Override
  public boolean compare(DateTime incomingDateTime, DateTime savedDateTime) {
    DateTime incomingDate = trimMilliseconds(incomingDateTime);
    DateTime savedDate = trimMilliseconds(savedDateTime);
    boolean isSameDate = incomingDate.getMillis() == savedDate.getMillis();
    if (!isSameDate) {
      logDateNotEqual(incomingDate, savedDate);
    }
    return isSameDate;
  }

  /**
   * @param dt - dateTime
   * @return the DateTime
   */
  public DateTime trimMilliseconds(DateTime dt) {
    return new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), dt.getHourOfDay(),
        dt.getMinuteOfHour(), dt.getSecondOfMinute(), 0, dt.getZone());
  }

  /**
   * @param incomingDate - incomingDate
   * @param savedDate - savedDate
   */
  public void logDateNotEqual(DateTime incomingDate, DateTime savedDate) {
    if(LOGGER.isWarnEnabled()){
      StringBuilder builder = new StringBuilder();
      builder.append("Date comparison failed. incomingDate: ");
      builder.append(incomingDate);
      builder.append(' ');
      builder.append(incomingDate.getZone());
      builder.append(' ');
      builder.append(incomingDate.getMillis());
      builder.append(" savedDate: ");
      builder.append(savedDate);
      builder.append(' ');
      builder.append(savedDate.getZone());
      builder.append(' ');
      builder.append(savedDate.getMillis());
      LOGGER.warn(builder.toString());
    }
  }

}
