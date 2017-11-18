package gov.ca.cwds.fixture.investigation;

import java.util.ArrayList;
import java.util.List;

import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;

@SuppressWarnings("javadoc")
public class RaceAndEthnicityEntityBuilder {

  private List<Short> raceCode = new ArrayList<>();
  private String unableToDetermineCode = "A";
  private List<Short> hispanicCode = new ArrayList<>();
  private String hispanicOriginCode = "N";
  private String hispanicUnableToDetermineCode = "";

  public RaceAndEthnicity build() {
    return new RaceAndEthnicity(raceCode, unableToDetermineCode, hispanicCode, hispanicOriginCode,
        hispanicUnableToDetermineCode);
  }

  public List<Short> getRaceCode() {
    return raceCode;
  }

  public RaceAndEthnicityEntityBuilder setRaceCode(List<Short> raceCode) {
    this.raceCode = raceCode;
    return this;
  }

  public String getUnableToDetermineCode() {
    return unableToDetermineCode;
  }

  public RaceAndEthnicityEntityBuilder setUnableToDetermineCode(String unableToDetermineCode) {
    this.unableToDetermineCode = unableToDetermineCode;
    return this;
  }

  public List<Short> getHispanicCode() {
    return hispanicCode;
  }

  public RaceAndEthnicityEntityBuilder setHispanicCode(List<Short> hispanicCode) {
    this.hispanicCode = hispanicCode;
    return this;
  }

  public String getHispanicOriginCode() {
    return hispanicOriginCode;
  }

  public RaceAndEthnicityEntityBuilder setHispanicOriginCode(String hispanicOriginCode) {
    this.hispanicOriginCode = hispanicOriginCode;
    return this;
  }

  public String getHispanicUnableToDetermineCode() {
    return hispanicUnableToDetermineCode;
  }

  public RaceAndEthnicityEntityBuilder setHispanicUnableToDetermineCode(
      String hispanicUnableToDetermineCode) {
    this.hispanicUnableToDetermineCode = hispanicUnableToDetermineCode;
    return this;
  }

}
