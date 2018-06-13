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

  private static final Map<String, Gender> genderMapper = new HashMap<>();

  private final String nsDescription;
  private final String cmsDescription;

  private Gender(String cmsDescription, String nsDescription) {
    this.nsDescription = nsDescription;
    this.cmsDescription = cmsDescription;
  }

  public String getCmsDescription() {
    return cmsDescription;
  }

  public String getNsDescription() {
    return nsDescription;
  }

  public static Gender findByNsDescription(String nsDescription) {
    return genderMapper.containsKey(nsDescription) ? genderMapper.get(nsDescription)
        : Gender.UNKNOWN;
  }

  static {
    for (Gender e : Gender.values()) {
      genderMapper.put(e.nsDescription, e);
    }
  }

  public static Gender findByCmsDescription(String cmsDescription) {
    return genderMapper.containsKey(cmsDescription) ? genderMapper.get(cmsDescription)
        : Gender.UNKNOWN;
  }

  static {
    for (Gender e : Gender.values()) {
      genderMapper.put(e.cmsDescription, e);
    }
  }

}

