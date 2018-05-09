package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.CrossReport;

/**
 * @author CWDS API Team
 *
 */
public class CrossReportEntityBuilder {

  String referralId = "MFzt4NA0Hj";
  String thirdId = "AbiQCgu0Hj";
  Short crossReportMethodType = 2096;
  String filedOutOfStateIndicator = "N";
  String governmentOrgCrossRptIndicatorVar = "N";
  Date informTime = null;
  String recipientBadgeNumber = null;
  Integer recipientPhoneExtensionNumber = (int) 0;
  Long recipientPhoneNumber = 6199226996L;
  Date informDate = new Date();
  String recipientPositionTitleDesc = null;
  String referenceNumber = null;
  String lawEnforcementId = "SriA2Qz02e";
  String staffPersonId = "aaw";
  String description = null;
  String recipientName = "";
  String outStateLawEnforcementAddr = "";
  String countySpecificCode = "17";
  String lawEnforcementIndicator = "Y";
  String outStateLawEnforcementIndicator = "N";
  String satisfyCrossReportIndicator = "N";

  public CrossReport build() {
    return new CrossReport(referralId, thirdId, crossReportMethodType, filedOutOfStateIndicator,
        governmentOrgCrossRptIndicatorVar, informTime, recipientBadgeNumber,
        recipientPhoneExtensionNumber, recipientPhoneNumber, informDate, recipientPositionTitleDesc,
        referenceNumber, lawEnforcementId, staffPersonId, description, recipientName,
        outStateLawEnforcementAddr, countySpecificCode, lawEnforcementIndicator,
        outStateLawEnforcementIndicator, satisfyCrossReportIndicator);
  }

  public CrossReportEntityBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }

  public CrossReportEntityBuilder setThirdId(String thirdId) {
    this.thirdId = thirdId;
    return this;
  }

  public CrossReportEntityBuilder setCrossReportMethodType(Short crossReportMethodType) {
    this.crossReportMethodType = crossReportMethodType;
    return this;
  }

  public CrossReportEntityBuilder setFiledOutOfStateIndicator(String filedOutOfStateIndicator) {
    this.filedOutOfStateIndicator = filedOutOfStateIndicator;
    return this;
  }

  public CrossReportEntityBuilder setGovernmentOrgCrossRptIndicatorVar(
      String governmentOrgCrossRptIndicatorVar) {
    this.governmentOrgCrossRptIndicatorVar = governmentOrgCrossRptIndicatorVar;
    return this;
  }

  public CrossReportEntityBuilder setInformTime(Date informTime) {
    this.informTime = informTime;
    return this;
  }

  public CrossReportEntityBuilder setRecipientBadgeNumber(String recipientBadgeNumber) {
    this.recipientBadgeNumber = recipientBadgeNumber;
    return this;
  }

  public CrossReportEntityBuilder setRecipientPhoneExtensionNumber(
      Integer recipientPhoneExtensionNumber) {
    this.recipientPhoneExtensionNumber = recipientPhoneExtensionNumber;
    return this;
  }

  public CrossReportEntityBuilder setRecipientPhoneNumber(Long recipientPhoneNumber) {
    this.recipientPhoneNumber = recipientPhoneNumber;
    return this;
  }

  public CrossReportEntityBuilder setInformDate(Date informDate) {
    this.informDate = informDate;
    return this;
  }

  public CrossReportEntityBuilder setRecipientPositionTitleDesc(String recipientPositionTitleDesc) {
    this.recipientPositionTitleDesc = recipientPositionTitleDesc;
    return this;
  }

  public CrossReportEntityBuilder setReferenceNumber(String referenceNumber) {
    this.referenceNumber = referenceNumber;
    return this;
  }

  public CrossReportEntityBuilder setLawEnforcementId(String lawEnforcementId) {
    this.lawEnforcementId = lawEnforcementId;
    return this;
  }

  public CrossReportEntityBuilder setStaffPersonId(String staffPersonId) {
    this.staffPersonId = staffPersonId;
    return this;
  }

  public CrossReportEntityBuilder setDescription(String description) {
    this.description = description;
    return this;
  }

  public CrossReportEntityBuilder setRecipientName(String recipientName) {
    this.recipientName = recipientName;
    return this;
  }

  public CrossReportEntityBuilder setOutStateLawEnforcementAddr(String outStateLawEnforcementAddr) {
    this.outStateLawEnforcementAddr = outStateLawEnforcementAddr;
    return this;
  }

  public CrossReportEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public CrossReportEntityBuilder setLawEnforcementIndicator(String lawEnforcementIndicator) {
    this.lawEnforcementIndicator = lawEnforcementIndicator;
    return this;
  }

  public CrossReportEntityBuilder setOutStateLawEnforcementIndicator(
      String outStateLawEnforcementIndicator) {
    this.outStateLawEnforcementIndicator = outStateLawEnforcementIndicator;
    return this;
  }

  public CrossReportEntityBuilder setSatisfyCrossReportIndicator(
      String satisfyCrossReportIndicator) {
    this.satisfyCrossReportIndicator = satisfyCrossReportIndicator;
    return this;
  }

}
