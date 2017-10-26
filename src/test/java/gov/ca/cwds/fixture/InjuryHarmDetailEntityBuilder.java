package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.cms.InjuryHarmDetail;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class InjuryHarmDetailEntityBuilder {

  String thirdId = "CRq1Z8H0La";
  Short injuryHarmType = 5627;
  String injuryToBodyDetailIndicator = "N";
  String allegationId = "AbqeqZx0La";
  String countySpecificCode = "99";

  public InjuryHarmDetail build() {
    return new InjuryHarmDetail(thirdId, injuryHarmType, injuryToBodyDetailIndicator, allegationId,
        countySpecificCode);
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  public Short getInjuryHarmType() {
    return injuryHarmType;
  }

  public void setInjuryHarmType(Short injuryHarmType) {
    this.injuryHarmType = injuryHarmType;
  }

  public String getInjuryToBodyDetailIndicator() {
    return injuryToBodyDetailIndicator;
  }

  public void setInjuryToBodyDetailIndicator(String injuryToBodyDetailIndicator) {
    this.injuryToBodyDetailIndicator = injuryToBodyDetailIndicator;
  }

  public String getAllegationId() {
    return allegationId;
  }

  public void setAllegationId(String allegationId) {
    this.allegationId = allegationId;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public void setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
  }

}
