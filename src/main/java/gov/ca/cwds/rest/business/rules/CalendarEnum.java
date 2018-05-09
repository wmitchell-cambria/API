package gov.ca.cwds.rest.business.rules;

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
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

}
