package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CWDS API Team
 *
 */
public class LegacyToIntakeCodeConveter {

  /**
   * Enum to get the intake codes using the legacy description.
   *
   */
  public enum IntakeRaceCode {

    ALASKA_NATIVE("Alaskan Native*", "American Indian or Alaska Native", "Alaska Native"),

    AMERICAN_INDIA("American Indian*", "American Indian or Alaska Native", "American Indian"),

    ASIAN_INDIAN("Asian Indian*", "Asian", "Asian Indian"),

    BLACK("Black*", "Black or African American", ""),

    COMBODIAN("Cambodian*", "Asian", "Cambodian"),

    CARIBBEAN("Caribbean", "Black or African American", "Caribbean"),

    CHINESE("Chinese*", "Asian", "Chinese"),

    DECLINED_TO_STATE("Declines to State*", "Declined to answer", ""),

    ETHIOPIAN("Ethiopian*", "Black or African American", "Ethiopian"),

    FILIPINO("Filipino*", "Asian", "Filipino"),

    GUAMANIAN("Guamanian*", "Native Hawaiian or Other Pacific Islander", "Guamanian"),

    HAWAIIAN("Hawaiian*", "Native Hawaiian or Other Pacific Islander", "Hawaiian"),

    HMONG("Hmong*", Constants.ASIAN, "Hmong"),

    JAPANESE("Japanese*", Constants.ASIAN, "Japanese"),

    KOREAN("Korean*", "Asian", "Korean"),

    LAOTIAN("Laotian*", "Asian", "Laotian"),

    OTHER_ASIAN("Other Asian*", "Asian", "Other Asian"),

    OTHER_PACIFIC_ISLANDER("Other Pacific Islander*", "Native Hawaiian or Other Pacific Islander",
        "Other Pacific Islander"),

    OTHER_RACE_UNKNOWN("Other Race Unknown*", "Unknown", ""),

    OTHER_ASIAN_ISLANDER("Other Asian/Pacific Islander*",
        "Native Hawaiian or Other Pacific Islander", "Other Asian/Pacific Islander"),

    POLYNESIAN("Polynesian*", "Native Hawaiian or Other Pacific Islander", "Polynesian"),

    SAMOAN("Samoan*", "Native Hawaiian or Other Pacific Islander", "Samoan"),

    UNABLE_TO_DETERMINE("Unable to Determine*", "Abandoned", ""),

    VIETNAMESE("Vietnamese*", "Asian", "Vietnamese"),

    WHITE_ARMENIAN("White - Armenian*", "White", "Armenian"),

    WHITE_CENTRAL_AMERICAN("White - Central American*", "White", "Central American"),

    WHITE_EUROPEAN("White - European*", "White", "European"),

    WHITE_MIDDLE_EASTERN("White - Middle Eastern*", "White", "Middle Eastern"),

    WHITE_ROMANIAN("White - Romanian*", "White", "Romanian"),

    WHITE("White*", "", "White");

    private static final Map<String, IntakeRaceCode> mapper = new HashMap<>();

    private final String legacyValue;
    private final String race;
    private final String raceDetail;

    private IntakeRaceCode(String legacyValue, String race, String raceDetail) {
      this.legacyValue = legacyValue;
      this.race = race;
      this.raceDetail = raceDetail;
    }

    public String getLegacyValue() {
      return legacyValue;
    }

    public String getRace() {
      return race;
    }

    public String getRaceDetail() {
      return raceDetail;
    }

    /**
     * @param legacyValue - legacyValue
     * @return the intake code values
     */
    public static IntakeRaceCode findByLegacyValue(String legacyValue) {
      return mapper.containsKey(legacyValue) ? mapper.get(legacyValue) : null;
    }

    static {
      for (IntakeRaceCode intakeRaceCode : IntakeRaceCode.values()) {
        mapper.put(intakeRaceCode.legacyValue, intakeRaceCode);
      }
    }
  }

  private static class Constants {
    public static final String ASIAN = "Asian";
  }

}
