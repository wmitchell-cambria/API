package gov.ca.cwds.rest.business.reminders;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CWS-NS2
 *
 */
public class ReminderHelper {

  private static final short REFERRAL_RESPONSE_TYPE_10_DAYS = (short) 1518;
  private static final short REFERRAL_RESPONSE_TYPE_3_DAYS = (short) 1516;
  private static final short REFERRAL_RESPONSE_TYPE_5_DAYS = (short) 1517;
  private static final short REFERRAL_RESPONSE_TYPE_IMMEDIATE = (short) 1520;

  /**
   * 
   */
  private ReminderHelper() {
    // no-opt
  }

  /**
   * @param dateOfBirth - dateOfBirth
   * @return the ageDifference
   */
  public static int checkForAgeDifference(String dateOfBirth) {
    LocalDate dob = LocalDate.parse(dateOfBirth);
    return (int) dob.until(LocalDate.now(), ChronoUnit.YEARS);
  }

  /**
   * Mapped all the Referral Response Type Code
   * 
   * @return the number of days the dueDate to set
   */
  public static Map<Short, Integer> getMapTheDueDate() {
    Map<Short, Integer> valueMap = new HashMap<>();
    valueMap.put(REFERRAL_RESPONSE_TYPE_10_DAYS, 10);
    valueMap.put(REFERRAL_RESPONSE_TYPE_3_DAYS, 3);
    valueMap.put(REFERRAL_RESPONSE_TYPE_5_DAYS, 5);
    valueMap.put(REFERRAL_RESPONSE_TYPE_IMMEDIATE, 1);
    return valueMap;
  }

}
