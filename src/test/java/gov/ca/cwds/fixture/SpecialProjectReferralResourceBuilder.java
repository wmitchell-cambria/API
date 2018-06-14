package gov.ca.cwds.fixture;

public class SpecialProjectReferralResourceBuilder {

  private String countySpecificCode = "27";
  private String referralId = "1234567ABC";
  private String specialProjectId = "2345678ABC";
  private String participationEndDate = null;
  private String participationStartDate = "2018-05-30";
  private Boolean safelySurrenderedBabiesIndicator = Boolean.FALSE;
  private String id = "3456789ABC";

  public gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral build() {
    return new gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral(countySpecificCode, 
        referralId, specialProjectId, participationEndDate, participationStartDate,
        safelySurrenderedBabiesIndicator);
  }
  
  public SpecialProjectReferralResourceBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
    
  }
  
  public SpecialProjectReferralResourceBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }
  
  public SpecialProjectReferralResourceBuilder setSpecialProjectId(String specialProjectId) {
    this.specialProjectId = specialProjectId;
    return this;
  }
  
  public SpecialProjectReferralResourceBuilder setParticipationEndDate(String participationEndDate) {
    this.participationEndDate = participationEndDate;
    return this;
  }
  
  public SpecialProjectReferralResourceBuilder setParticipationStartDate(String participationStartDate) {
    this.participationStartDate = participationStartDate;
    return this;
  }
  
  public SpecialProjectReferralResourceBuilder setSafelySurrenderedBabiesIndicator(Boolean indicator) {
    this.safelySurrenderedBabiesIndicator = indicator;
    return this;
  }
  
  public SpecialProjectReferralResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }
}
