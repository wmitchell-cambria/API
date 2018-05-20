package gov.ca.cwds.rest.services.submit;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumerated types for Gender
 *
 * @author CWDS API Team
 */
public enum Gender {

  /**
   * Unknown
   */
  UNKNOWN("U", "unknown"),

  /**
   * Female
   */
  FEMALE("F", "female"),

  /**
   * Male
   */
  MALE("M", "male"),

  /**
   * Intersex
   */
  INTERSEX("I", "intersex");

  private final String nsDescription;
  private final String cmsDescription;

  private Gender(String cmsDescription, String nsDescription) {
    ;
    this.nsDescription = nsDescription;
    this.cmsDescription = cmsDescription;
  }

  public String getCmsDescription() {
    return cmsDescription;
  }

  public static Gender findByNsDescription(String nsDescription) {
    return mapByNsDescription.containsKey(nsDescription) ? mapByNsDescription.get(nsDescription)
        : Gender.UNKNOWN;
  }

  private static final Map<String, Gender> mapByNsDescription = new HashMap<>();

  static {
    for (Gender e : Gender.values()) {
      mapByNsDescription.put(e.nsDescription, e);
    }
  }

}

