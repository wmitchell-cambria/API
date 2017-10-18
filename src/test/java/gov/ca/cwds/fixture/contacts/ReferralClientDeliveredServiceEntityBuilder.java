package gov.ca.cwds.fixture.contacts;

import java.util.Date;

import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity;

/**
 * The Builder class for {@link IndividualDeliveredServiceEntity}
 * 
 * @author CWDS API Team
 *
 */
public class ReferralClientDeliveredServiceEntityBuilder {


  String countySpecificCode = "99";
  private String deliveredServiceId = "ABC1234567";
  private String referralId = "ABC1234567";
  private String clientId = "APc109852u";
  private String staffId = "0X5";
  private Date currentTime = new Date();

  public ReferralClientDeliveredServiceEntity build() {
    return new ReferralClientDeliveredServiceEntity(deliveredServiceId, referralId, clientId,
        countySpecificCode, staffId, currentTime);
  }

  /**
   * @param deliveredServiceId the deliveredServiceId to set
   * @return the builder
   */
  public ReferralClientDeliveredServiceEntityBuilder setdeliveredServiceId(
      String deliveredServiceId) {
    this.deliveredServiceId = deliveredServiceId;
    return this;
  }

  /**
   * @param referralId the referralId to set
   * @return the builder
   */
  public ReferralClientDeliveredServiceEntityBuilder setreferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }

  /**
   * @param countySpecificCode the countySpecificCode to set
   * @return the builder
   */
  public ReferralClientDeliveredServiceEntityBuilder setCountySpecificCode(
      String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  /**
   * @param currentTime the currentTime to set
   * @return the builder
   */
  public ReferralClientDeliveredServiceEntityBuilder setcurrentTime(Date currentTime) {
    this.currentTime = currentTime;
    return this;
  }

  /**
   * @param staffId the staffId to set
   * @return the builder
   */
  public ReferralClientDeliveredServiceEntityBuilder setstaffId(String staffId) {
    this.staffId = staffId;
    return this;
  }

}
