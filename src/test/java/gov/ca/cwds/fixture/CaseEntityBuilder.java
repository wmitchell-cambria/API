package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.CmsCase;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class CaseEntityBuilder {

  String id = "AadfKnG07n";
  String alertText = "Alerts Text";
  String approvalNumber = "R8TQDh807n";
  Short approvalStatusType = 122;
  Short caseClosureReasonType = 310;
  String caseplanChildrenDetailIndVar = "N";
  String closureStatementText = "Some closure text";
  Short countryCodeType = 563;
  String countySpecificCode = "57";
  String drmsNotesDoc = "null";
  Date emancipationDate = null;
  Date endDate = null;
  String fkchldClt = "8m7hS7i07n";
  String fkreferlt = "8m7hS7i07k";
  String fkstfperst = "0Al";
  Short governmentEntityType = 1124;
  String icpcOutgngPlcmtStatusIndVar = "N";
  String icpcOutgoingRequestIndVar = "N";
  String limitedAccessCode = "N";
  Date limitedAccessDate = new Date();
  String limitedAccessDesc = null;
  Short limitedAccessGovernmentEntityType = 1084;
  String caseName = "pts 20414";
  Date nextTilpDueDate = null;
  Date projectedEndDate = null;
  String responsibleAgencyCode = "C";
  String specialProjectCaseIndVar = "N";
  Date startDate = new Date();
  Short stateCodeType = 1828;
  Short activeServiceComponentType = 1692;
  Date activeSvcComponentStartDate = new Date();
  String tickleIndVar = "N";

  public CmsCase build() {
    return new CmsCase(id, alertText, approvalNumber, approvalStatusType, caseClosureReasonType,
        caseplanChildrenDetailIndVar, closureStatementText, countryCodeType, countySpecificCode,
        drmsNotesDoc, emancipationDate, endDate, fkchldClt, fkreferlt, fkstfperst,
        governmentEntityType, icpcOutgngPlcmtStatusIndVar, icpcOutgoingRequestIndVar,
        limitedAccessCode, limitedAccessDate, limitedAccessDesc, limitedAccessGovernmentEntityType,
        caseName, nextTilpDueDate, projectedEndDate, responsibleAgencyCode,
        specialProjectCaseIndVar, startDate, stateCodeType, activeServiceComponentType,
        activeSvcComponentStartDate, tickleIndVar, null, null, null);
  }

  public String getId() {
    return id;
  }

  public CaseEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getAlertText() {
    return alertText;
  }

  public CaseEntityBuilder setAlertText(String alertText) {
    this.alertText = alertText;
    return this;
  }

  public String getApprovalNumber() {
    return approvalNumber;
  }

  public CaseEntityBuilder setApprovalNumber(String approvalNumber) {
    this.approvalNumber = approvalNumber;
    return this;
  }

  public Short getApprovalStatusType() {
    return approvalStatusType;
  }

  public CaseEntityBuilder setApprovalStatusType(Short approvalStatusType) {
    this.approvalStatusType = approvalStatusType;
    return this;
  }

  public Short getCaseClosureReasonType() {
    return caseClosureReasonType;
  }

  public CaseEntityBuilder setCaseClosureReasonType(Short caseClosureReasonType) {
    this.caseClosureReasonType = caseClosureReasonType;
    return this;
  }

  public String getCaseplanChildrenDetailIndVar() {
    return caseplanChildrenDetailIndVar;
  }

  public CaseEntityBuilder setCaseplanChildrenDetailIndVar(String caseplanChildrenDetailIndVar) {
    this.caseplanChildrenDetailIndVar = caseplanChildrenDetailIndVar;
    return this;
  }

  public String getClosureStatementText() {
    return closureStatementText;
  }

  public CaseEntityBuilder setClosureStatementText(String closureStatementText) {
    this.closureStatementText = closureStatementText;
    return this;
  }

  public Short getCountryCodeType() {
    return countryCodeType;
  }

  public CaseEntityBuilder setCountryCodeType(Short countryCodeType) {
    this.countryCodeType = countryCodeType;
    return this;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public CaseEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public String getDrmsNotesDoc() {
    return drmsNotesDoc;
  }

  public CaseEntityBuilder setDrmsNotesDoc(String drmsNotesDoc) {
    this.drmsNotesDoc = drmsNotesDoc;
    return this;
  }

  public Date getEmancipationDate() {
    return emancipationDate;
  }

  public CaseEntityBuilder setEmancipationDate(Date emancipationDate) {
    this.emancipationDate = emancipationDate;
    return this;
  }

  public Date getEndDate() {
    return endDate;
  }

  public CaseEntityBuilder setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public String getFkchldClt() {
    return fkchldClt;
  }

  public CaseEntityBuilder setFkchldClt(String fkchldClt) {
    this.fkchldClt = fkchldClt;
    return this;
  }

  public String getFkreferlt() {
    return fkreferlt;
  }

  public CaseEntityBuilder setFkreferlt(String fkreferlt) {
    this.fkreferlt = fkreferlt;
    return this;
  }

  public String getFkstfperst() {
    return fkstfperst;
  }

  public CaseEntityBuilder setFkstfperst(String fkstfperst) {
    this.fkstfperst = fkstfperst;
    return this;
  }

  public Short getGovernmentEntityType() {
    return governmentEntityType;
  }

  public CaseEntityBuilder setGovernmentEntityType(Short governmentEntityType) {
    this.governmentEntityType = governmentEntityType;
    return this;
  }

  public String getIcpcOutgngPlcmtStatusIndVar() {
    return icpcOutgngPlcmtStatusIndVar;
  }

  public CaseEntityBuilder setIcpcOutgngPlcmtStatusIndVar(String icpcOutgngPlcmtStatusIndVar) {
    this.icpcOutgngPlcmtStatusIndVar = icpcOutgngPlcmtStatusIndVar;
    return this;
  }

  public String getIcpcOutgoingRequestIndVar() {
    return icpcOutgoingRequestIndVar;
  }

  public CaseEntityBuilder setIcpcOutgoingRequestIndVar(String icpcOutgoingRequestIndVar) {
    this.icpcOutgoingRequestIndVar = icpcOutgoingRequestIndVar;
    return this;
  }

  public String getLimitedAccessCode() {
    return limitedAccessCode;
  }

  public CaseEntityBuilder setLimitedAccessCode(String limitedAccessCode) {
    this.limitedAccessCode = limitedAccessCode;
    return this;
  }

  public Date getLimitedAccessDate() {
    return limitedAccessDate;
  }

  public CaseEntityBuilder setLimitedAccessDate(Date limitedAccessDate) {
    this.limitedAccessDate = limitedAccessDate;
    return this;
  }

  public String getLimitedAccessDesc() {
    return limitedAccessDesc;
  }

  public CaseEntityBuilder setLimitedAccessDesc(String limitedAccessDesc) {
    this.limitedAccessDesc = limitedAccessDesc;
    return this;
  }

  public Short getLimitedAccessGovernmentEntityType() {
    return limitedAccessGovernmentEntityType;
  }

  public CaseEntityBuilder setLimitedAccessGovernmentEntityType(
      Short limitedAccessGovernmentEntityType) {
    this.limitedAccessGovernmentEntityType = limitedAccessGovernmentEntityType;
    return this;
  }

  public String getCaseName() {
    return caseName;
  }

  public CaseEntityBuilder setCaseName(String caseName) {
    this.caseName = caseName;
    return this;
  }

  public Date getNextTilpDueDate() {
    return nextTilpDueDate;
  }

  public CaseEntityBuilder setNextTilpDueDate(Date nextTilpDueDate) {
    this.nextTilpDueDate = nextTilpDueDate;
    return this;
  }

  public Date getProjectedEndDate() {
    return projectedEndDate;
  }

  public CaseEntityBuilder setProjectedEndDate(Date projectedEndDate) {
    this.projectedEndDate = projectedEndDate;
    return this;
  }

  public String getResponsibleAgencyCode() {
    return responsibleAgencyCode;
  }

  public CaseEntityBuilder setResponsibleAgencyCode(String responsibleAgencyCode) {
    this.responsibleAgencyCode = responsibleAgencyCode;
    return this;
  }

  public String getSpecialProjectCaseIndVar() {
    return specialProjectCaseIndVar;
  }

  public CaseEntityBuilder setSpecialProjectCaseIndVar(String specialProjectCaseIndVar) {
    this.specialProjectCaseIndVar = specialProjectCaseIndVar;
    return this;
  }

  public Date getStartDate() {
    return startDate;
  }

  public CaseEntityBuilder setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public Short getStateCodeType() {
    return stateCodeType;
  }

  public CaseEntityBuilder setStateCodeType(Short stateCodeType) {
    this.stateCodeType = stateCodeType;
    return this;
  }

  public Short getActiveServiceComponentType() {
    return activeServiceComponentType;
  }

  public CaseEntityBuilder setActiveServiceComponentType(Short activeServiceComponentType) {
    this.activeServiceComponentType = activeServiceComponentType;
    return this;
  }

  public Date getActiveSvcComponentStartDate() {
    return activeSvcComponentStartDate;
  }

  public CaseEntityBuilder setActiveSvcComponentStartDate(Date activeSvcComponentStartDate) {
    this.activeSvcComponentStartDate = activeSvcComponentStartDate;
    return this;
  }

  public String getTickleIndVar() {
    return tickleIndVar;
  }

  public CaseEntityBuilder setTickleIndVar(String tickleIndVar) {
    this.tickleIndVar = tickleIndVar;
    return this;
  }

}
