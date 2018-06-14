package gov.ca.cwds.fixture;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;
import java.time.LocalDate;
import java.util.Date;
import gov.ca.cwds.data.persistence.cms.SpecialProjectReferral;

public class SpecialProjectReferralEntityBuilder {

  private String countySpecificCode = "24";
  private String referralId = "1234567ABC";
  private String specialProjectId = "2345678ABC";
  private LocalDate participationEndDate = null;
  private LocalDate participationStartDate = LocalDate.now();
  private String safelySurrenderedBabiesIndicator = "N";
  private String id = "3456789ABC";
  
  public SpecialProjectReferral build() {
    return new SpecialProjectReferral(countySpecificCode, referralId, specialProjectId, 
        participationEndDate, participationStartDate, safelySurrenderedBabiesIndicator, id);
  }
  
  public SpecialProjectReferralEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }
  
  public SpecialProjectReferralEntityBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }
  
  public SpecialProjectReferralEntityBuilder setSpecialProjectId(String specialProjectId) {
    this.specialProjectId = specialProjectId;
    return this;
  }
  
  public SpecialProjectReferralEntityBuilder setParticipationEndDate(LocalDate participationEndDate) {
    this.participationEndDate = participationEndDate;
    return this;
  }
  
  public SpecialProjectReferralEntityBuilder setParticipationStartDate(LocalDate participationStartDate) {
    this.participationStartDate = participationStartDate;
    return this;
  }
  
  public SpecialProjectReferralEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }
}
