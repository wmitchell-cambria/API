package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CWDS API Team
 *
 */
public class IntakeRaceAndEthnicity {

  private IntakeRaceAndEthnicity() {
    // no-opt
  }

  /**
   * IntakeRace Class
   *
   */
  public static class IntakeRace {
    @JsonProperty("race")
    private String race;

    @JsonProperty("race_detail")
    private String raceDetail;

    public IntakeRace(String race, String raceDetail) {
      super();
      this.race = race;
      this.raceDetail = raceDetail;
    }

    public String getRace() {
      return race;
    }

    public String getRaceDetail() {
      return raceDetail;
    }
  }

  public static class IntakeEthnicity {
    @JsonProperty("hispanic_latino_origin")
    private String hispanicLatinoOrigin;

    @JsonProperty("ethnicity_detail")
    private List<String> ethnicityDetail;

    public IntakeEthnicity(String hispanicLatinoOrigin, List<String> ethnicityDetail) {
      super();
      this.hispanicLatinoOrigin = hispanicLatinoOrigin;
      this.ethnicityDetail = ethnicityDetail;
    }

    public String getHispanicLatinoOrigin() {
      return hispanicLatinoOrigin;
    }

    public List<String> getEthnicityDetail() {
      return ethnicityDetail;
    }
  }

}
