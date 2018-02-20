package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;

/**
 * CWDS API Team
 */
public class ReferralClientEntityBuilder {
  private String referralId = "-1";
  private String clientId = "-2";
  private String approvalNumber;
  private Short approvalStatusType;
  private Short dispositionClosureReasonType;
  private String dispositionCode;
  private Date dispositionDate;
  private String selfReportedIndicator;
  private String staffPersonAddedIndicator;
  private String dispositionClosureDescription;
  private Short ageNumber = (short) 3;
  private String agePeriodCode = "D";
  private String countySpecificCode;
  private String mentalHealthIssuesIndicator;
  private String alcoholIndicator;
  private String drugIndicator;
  private Referral referral = new ReferralEntityBuilder().build();

  public ReferralClient build() {
    ReferralClient referralClient =
        new ReferralClient(referralId, clientId, approvalNumber, approvalStatusType,
            dispositionClosureReasonType, dispositionCode, dispositionDate, selfReportedIndicator,
            staffPersonAddedIndicator, dispositionClosureDescription, ageNumber, agePeriodCode,
            countySpecificCode, mentalHealthIssuesIndicator, alcoholIndicator, drugIndicator);
    referralClient.setReferral(referral);
    return referralClient;
  }

  public String getApprovalNumber() {
    return approvalNumber;
  }

  public ReferralClientEntityBuilder setApprovalNumber(String approvalNumber) {
    this.approvalNumber = approvalNumber;
    return this;
  }

  public Short getApprovalStatusType() {
    return approvalStatusType;
  }

  public ReferralClientEntityBuilder setApprovalStatusType(Short approvalStatusType) {
    this.approvalStatusType = approvalStatusType;
    return this;
  }

  public Short getDispositionClosureReasonType() {
    return dispositionClosureReasonType;
  }

  public ReferralClientEntityBuilder setDispositionClosureReasonType(
      Short dispositionClosureReasonType) {
    this.dispositionClosureReasonType = dispositionClosureReasonType;
    return this;
  }

  public String getDispositionCode() {
    return dispositionCode;
  }

  public ReferralClientEntityBuilder setDispositionCode(String dispositionCode) {
    this.dispositionCode = dispositionCode;
    return this;
  }

  public Date getDispositionDate() {
    return dispositionDate;
  }

  public ReferralClientEntityBuilder setDispositionDate(Date dispositionDate) {
    this.dispositionDate = dispositionDate;
    return this;
  }

  public String getSelfReportedIndicator() {
    return selfReportedIndicator;
  }

  public ReferralClientEntityBuilder setSelfReportedIndicator(String selfReportedIndicator) {
    this.selfReportedIndicator = selfReportedIndicator;
    return this;
  }

  public String getStaffPersonAddedIndicator() {
    return staffPersonAddedIndicator;
  }

  public ReferralClientEntityBuilder setStaffPersonAddedIndicator(
      String staffPersonAddedIndicator) {
    this.staffPersonAddedIndicator = staffPersonAddedIndicator;
    return this;
  }

  public String getReferralId() {
    return referralId;
  }

  public ReferralClientEntityBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }

  public ReferralClientEntityBuilder setReferral(Referral referral) {
    this.referral = referral;
    return this;
  }

  public String getClientId() {
    return clientId;
  }

  public ReferralClientEntityBuilder setClientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  public String getDispositionClosureDescription() {
    return dispositionClosureDescription;
  }

  public ReferralClientEntityBuilder setDispositionClosureDescription(
      String dispositionClosureDescription) {
    this.dispositionClosureDescription = dispositionClosureDescription;
    return this;
  }

  public Short getAgeNumber() {
    return ageNumber;
  }

  public ReferralClientEntityBuilder setAgeNumber(Short ageNumber) {
    this.ageNumber = ageNumber;
    return this;
  }

  public String getAgePeriodCode() {
    return agePeriodCode;
  }

  public ReferralClientEntityBuilder setAgePeriodCode(String agePeriodCode) {
    this.agePeriodCode = agePeriodCode;
    return this;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public ReferralClientEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public String getMentalHealthIssuesIndicator() {
    return mentalHealthIssuesIndicator;
  }

  public ReferralClientEntityBuilder setMentalHealthIssuesIndicator(
      String mentalHealthIssuesIndicator) {
    this.mentalHealthIssuesIndicator = mentalHealthIssuesIndicator;
    return this;
  }

  public String getAlcoholIndicator() {
    return alcoholIndicator;
  }

  public ReferralClientEntityBuilder setAlcoholIndicator(String alcoholIndicator) {
    this.alcoholIndicator = alcoholIndicator;
    return this;
  }

  public String getDrugIndicator() {
    return drugIndicator;
  }

  public ReferralClientEntityBuilder setDrugIndicator(String drugIndicator) {
    this.drugIndicator = drugIndicator;
    return this;
  }
}
