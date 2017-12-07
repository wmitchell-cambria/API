package gov.ca.cwds.rest.api.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * @author CWDS API Team
 *
 */
public enum LimitedAccessType {
  NONE("N", "None"), SEALED("R", "Sealed"), SENSITIVE("S", "Sensitive");

  private final String value;
  private final String description;

  private LimitedAccessType(String value, String description) {
    this.value = value;
    this.description = description;
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

}
