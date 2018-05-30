package gov.ca.cwds.rest.services.submit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CWDS API Team
 *
 */
public enum IntakeCodeConveter {

  ROMANIAN("Romanian", "White - Romanian*"),

  EUROPEAN("European", "White - European*"),

  ARMENIAN("Armenian", "White - Armenian*"),

  LAOTIAN("Laotian", "Laotian*"),

  MIDDLE_EASTERN("Middle Eastern", "White - Middle Eastern*"),

  FILIPINO("Filipino", "Filipino*"),

  ETHIOPIAN("Ethiopian", "Ethiopian*"),

  WHITE("White", "White*"),

  GUAMANIAN("Guamanian", "Guamanian*"),

  JAPANESE("Japanese", "Japanese*"),

  ALASKA_NATIVE("Alaska Native", "Alaskan Native*"),

  KOREAN("Korean", "Korean*"),

  HMONG("Hmong", "Hmong*"),

  ASIAN_INDIAN("Asian Indian", "Asian Indian*"),

  BLACK_OR_AFRICAN_AMERICAN("Black or African American", "Black*"),

  HAWAIIAN("Hawaiian", "Hawaiian*"),

  NATIVE_HAWAIIAN("Native Hawaiian or Other Pacific Islander", "Other Pacific Islander*"),

  CHINESE("Chinese", "Chinese*"),

  VIETNAMESE("Vietnamese", "Vietnamese*"),

  CAMBODIAN("Cambodian", "Cambodian*"),

  SAMOAN("Samoan", "Samoan*"),

  CARIBBEAN("Caribbean", "Caribbean"),

  CENTRAL_AMERICAN("Central American", "White - Central American*"),

  ASIAN("Asian", "Other Asian*"),

  OTHER_ASIAN("Other Asian", "Other Asian*"),

  AMERICAN_INDIAN("American Indian", "American Indian*"),

  AMERICAN_INDIAN_OR_ALASKANATIVE("American Indian or Alaska Native", "American Indian*"),

  POLYNESIAN("Polynesian", "Polynesian*"),

  OTHER_PACIFIC_ISLANDER("Other Pacific Islander", "Other Pacific Islander*"),

  OTHER_ASIAN_PACIFIC_ISLANDER("Other Asian/Pacific Islander", "Other Pacific Islander*"),

  UNKNOWN("Unknown", "Other Race Unknown*"),

  DECLINED_TO_ANSWER("Declined to answer", "Declines to State*"),

  ABANDONED("Abandoned", "Unable to Determine*");

  private static final Map<String, IntakeCodeConveter> mapper = new HashMap<>();

  private final String intakeValue;

  private final String legacyValue;

  private IntakeCodeConveter(String intakeValue, String legacyValue) {
    this.intakeValue = intakeValue;
    this.legacyValue = legacyValue;
  }

  /**
   * @return the intakeValue
   */
  public String getIntakeValue() {
    return intakeValue;
  }

  /**
   * @return the legacyValue
   */
  public String getLegacyValue() {
    return legacyValue;
  }

  /**
   * @param intakeValue - intakeValue
   * @return the legacy value
   */
  public static IntakeCodeConveter findLegacyDescpretion(String intakeValue) {
    return mapper.containsKey(intakeValue) ? mapper.get(intakeValue) : null;
  }

  static {
    for (IntakeCodeConveter intakeCodeConveter : IntakeCodeConveter.values()) {
      mapper.put(intakeCodeConveter.intakeValue, intakeCodeConveter);
    }
  }

}
