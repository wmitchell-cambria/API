package gov.ca.cwds.rest.business.rules;

import org.apache.commons.lang3.StringUtils;

/**
 * @author CWDS API team
 *
 */
public enum CalendarEnum {

  //
  // CHECKSTYLE:OFF
  //
  YEARS("Y", "years"),

  MONTHS("M", "months"),

  WEEKS("W", "weeks"),

  DAYS("D", "days");

  private final String name;

  private final String description;

  private CalendarEnum(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * getName.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * getDescription.
   * 
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param calendar - calendar
   * @return the name
   */
  public static CalendarEnum lookUpByDescription(String calendar) {
    if (StringUtils.isBlank(calendar)) {
      return null;
    }

    CalendarEnum calendarEnum = null;
    for (CalendarEnum ce : CalendarEnum.values()) {
      if (ce.getName().equals(calendar.trim())) {
        calendarEnum = ce;
        break;
      }
    }
    return calendarEnum;

  }

}
