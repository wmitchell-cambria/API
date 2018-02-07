package gov.ca.cwds.rest.api.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * @author CWDS API Team
 *
 */
public enum LimitedAccessType {

  NONE("N", "None", 0),

  SENSITIVE("S", "Sensitive", 1),

  SEALED("R", "Sealed", 2);

  /**
   * Highest level limited access code in order of priority
   */
  public static final LimitedAccessType HIGHEST_PRIORITY = LimitedAccessType.SEALED;

  private final String value;
  private final String description;
  private final int priority;

  private LimitedAccessType(String value, String description, int priority) {
    this.value = value;
    this.description = description;
    this.priority = priority;
  }

  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return Limited access code level in order of priority
   */
  public int getPriority() {
    return priority;
  }

  /**
   * @param value - value
   * @return the limitedAccessType
   */
  public static LimitedAccessType getByValue(String value) {
    if (StringUtils.isBlank(value)) {
      return null;
    }

    LimitedAccessType limitedAccessType = null;
    for (LimitedAccessType accessType : LimitedAccessType.values()) {
      if (accessType.getValue().equals(value.trim())) {
        limitedAccessType = accessType;
        break;
      }
    }
    return limitedAccessType;
  }

  /**
   * Determine if given limited access code is the highest priority one.
   * 
   * @param limitedAccessType Limited access type
   * @return true if given limited access code is the highest priority one.
   */
  public static boolean isHighestPriority(LimitedAccessType limitedAccessType) {
    return LimitedAccessType.HIGHEST_PRIORITY == limitedAccessType;
  }
}
