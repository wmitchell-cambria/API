package gov.ca.cwds.rest.api.domain.enums;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Intake Team 4
 */
public enum RoleType {

  /**
   * Participant role type
   */
  ANONIMOUS_REPORTER("Anonymous Reporter"),
  MANDATED_REPORTER("Mandated Reporter"),
  NON_MANDATED_REPORTER("Non-mandated Reporter"),
  PERPETRATOR("Perpetrator"),
  VICTIM("Victim");

  private static final String[] allTheTypes = Arrays.stream(values()).map(RoleType::getTheType)
      .toArray(String[]::new);
  private final String theType;

  RoleType(String theType) {
    this.theType = theType;
  }

  /**
   * @param value - roe type value
   * @return true or false
   */
  public static boolean hasType(String value) {
    if (value == null) {
      return false;
    }
    for (RoleType language : values()) {
      if (language.theType.equals(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param values - role types values array
   * @return true or false
   */
  public static boolean hasTypes(String[] values) {
    if (values == null) {
      return false;
    }
    return hasTypes(Arrays.asList(values));
  }

  /**
   * @param values - role types value - comma separated
   * @return true or false
   */
  public static boolean hasTypes(Collection<String> values) {
    if (values == null) {
      return false;
    }
    for (String type : values) {
      if (!hasType(type)) {
        return false;
      }
    }
    return true;
  }

  /**
   * @return string array of all the role types
   */
  public static String[] getAllTheTypes() {
    return Arrays.copyOf(allTheTypes, allTheTypes.length);
  }

  /**
   * @return the role type
   */
  public String getTheType() {
    return theType;
  }
}
