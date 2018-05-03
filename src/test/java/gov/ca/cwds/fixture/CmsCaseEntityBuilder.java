package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.ChildClient;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.StaffPerson;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class CmsCaseEntityBuilder {
  private String id;
  private String alertText;
  private String approvalNumber;
  private Short approvalStatusType;
  private Short caseClosureReasonType;
  private String caseplanChildrenDetailIndVar;
  private String closureStatementText;
  private Short countryCodeType;
  private String countySpecificCode;
  private String drmsNotesDoc;
  private String emancipationDate;
  private Date endDate;
  private String fkchldClt;
  private String fkreferlt;
  private String fkstfperst = "q1p";
  private Short governmentEntityType;
  private String icpcOutgngPlcmtStatusIndVar;
  private String icpcOutgoingRequestIndVar;
  private String limitedAccessCode;
  private Date limitedAccessDate;
  private String limitedAccessDesc;
  private Short limitedAccessGovernmentEntityType;
  private String caseName;
  private Date nextTilpDueDate;
  private Date projectedEndDate;
  private String responsibleAgencyCode;
  private String specialProjectCaseIndVar;
  private Date startDate;
  private Short stateCodeType;
  private Short activeServiceComponentType;
  private Date activeSvcComponentStartDate;
  private String tickleIndVar;
  private ChildClient childClient;
  private StaffPerson staffPerson;
  private Referral riReferral;

  public CmsCase build() {
    return new CmsCase(id, alertText, approvalNumber, approvalStatusType, caseClosureReasonType,
        caseplanChildrenDetailIndVar, closureStatementText, countryCodeType, countySpecificCode,
        drmsNotesDoc, emancipationDate, endDate, fkchldClt, fkreferlt, fkstfperst,
        governmentEntityType, icpcOutgngPlcmtStatusIndVar, icpcOutgoingRequestIndVar,
        limitedAccessCode, limitedAccessDate, limitedAccessDesc, limitedAccessGovernmentEntityType,
        caseName, nextTilpDueDate, projectedEndDate, responsibleAgencyCode,
        specialProjectCaseIndVar, startDate, stateCodeType, activeServiceComponentType,
        activeSvcComponentStartDate, tickleIndVar, childClient, staffPerson, riReferral);
  }

  public CmsCaseEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public CmsCaseEntityBuilder setAlertText(String alertText) {
    this.alertText = alertText;
    return this;
  }


  public CmsCaseEntityBuilder setApprovalNumber(String approvalNumber) {
    this.approvalNumber = approvalNumber;
    return this;
  }


  public CmsCaseEntityBuilder setApprovalStatusType(Short approvalStatusType) {
    this.approvalStatusType = approvalStatusType;
    return this;
  }


  public CmsCaseEntityBuilder setCaseClosureReasonType(Short caseClosureReasonType) {
    this.caseClosureReasonType = caseClosureReasonType;
    return this;
  }


  public CmsCaseEntityBuilder setCaseplanChildrenDetailIndVar(String caseplanChildrenDetailIndVar) {
    this.caseplanChildrenDetailIndVar = caseplanChildrenDetailIndVar;
    return this;
  }


  public CmsCaseEntityBuilder setClosureStatementText(String closureStatementText) {
    this.closureStatementText = closureStatementText;
    return this;
  }


  public CmsCaseEntityBuilder setCountryCodeType(Short countryCodeType) {
    this.countryCodeType = countryCodeType;
    return this;
  }


  public CmsCaseEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }


  public CmsCaseEntityBuilder setDrmsNotesDoc(String drmsNotesDoc) {
    this.drmsNotesDoc = drmsNotesDoc;
    return this;
  }


  public CmsCaseEntityBuilder setEmancipationDate(String emancipationDate) {
    this.emancipationDate = emancipationDate;
    return this;
  }


  public CmsCaseEntityBuilder setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }


  public CmsCaseEntityBuilder setFkchldClt(String fkchldClt) {
    this.fkchldClt = fkchldClt;
    return this;
  }


  public CmsCaseEntityBuilder setFkreferlt(String fkreferlt) {
    this.fkreferlt = fkreferlt;
    return this;
  }


  public CmsCaseEntityBuilder setFkstfperst(String fkstfperst) {
    this.fkstfperst = fkstfperst;
    return this;
  }


  public CmsCaseEntityBuilder setGovernmentEntityType(Short governmentEntityType) {
    this.governmentEntityType = governmentEntityType;
    return this;
  }


  public CmsCaseEntityBuilder setIcpcOutgngPlcmtStatusIndVar(String icpcOutgngPlcmtStatusIndVar) {
    this.icpcOutgngPlcmtStatusIndVar = icpcOutgngPlcmtStatusIndVar;
    return this;
  }


  public CmsCaseEntityBuilder setIcpcOutgoingRequestIndVar(String icpcOutgoingRequestIndVar) {
    this.icpcOutgoingRequestIndVar = icpcOutgoingRequestIndVar;
    return this;
  }


  public CmsCaseEntityBuilder setLimitedAccessCode(String limitedAccessCode) {
    this.limitedAccessCode = limitedAccessCode;
    return this;
  }


  public CmsCaseEntityBuilder setLimitedAccessDate(Date limitedAccessDate) {
    this.limitedAccessDate = limitedAccessDate;
    return this;
  }


  public CmsCaseEntityBuilder setLimitedAccessDesc(String limitedAccessDesc) {
    this.limitedAccessDesc = limitedAccessDesc;
    return this;
  }


  public CmsCaseEntityBuilder setLimitedAccessGovernmentEntityType(
      Short limitedAccessGovernmentEntityType) {
    this.limitedAccessGovernmentEntityType = limitedAccessGovernmentEntityType;
    return this;
  }


  public CmsCaseEntityBuilder setCaseName(String caseName) {
    this.caseName = caseName;
    return this;
  }


  public CmsCaseEntityBuilder setNextTilpDueDate(Date nextTilpDueDate) {
    this.nextTilpDueDate = nextTilpDueDate;
    return this;
  }


  public CmsCaseEntityBuilder setProjectedEndDate(Date projectedEndDate) {
    this.projectedEndDate = projectedEndDate;
    return this;
  }


  public CmsCaseEntityBuilder setResponsibleAgencyCode(String responsibleAgencyCode) {
    this.responsibleAgencyCode = responsibleAgencyCode;
    return this;
  }


  public CmsCaseEntityBuilder setSpecialProjectCaseIndVar(String specialProjectCaseIndVar) {
    this.specialProjectCaseIndVar = specialProjectCaseIndVar;
    return this;
  }


  public CmsCaseEntityBuilder setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }


  public CmsCaseEntityBuilder setStateCodeType(Short stateCodeType) {
    this.stateCodeType = stateCodeType;
    return this;
  }


  public CmsCaseEntityBuilder setActiveServiceComponentType(Short activeServiceComponentType) {
    this.activeServiceComponentType = activeServiceComponentType;
    return this;
  }


  public CmsCaseEntityBuilder setActiveSvcComponentStartDate(Date activeSvcComponentStartDate) {
    this.activeSvcComponentStartDate = activeSvcComponentStartDate;
    return this;
  }


  public CmsCaseEntityBuilder setTickleIndVar(String tickleIndVar) {
    this.tickleIndVar = tickleIndVar;
    return this;
  }


  public CmsCaseEntityBuilder setChildClient(ChildClient childClient) {
    this.childClient = childClient;
    return this;
  }


  public CmsCaseEntityBuilder setStaffPerson(StaffPerson staffPerson) {
    this.staffPerson = staffPerson;
    return this;
  }


  public CmsCaseEntityBuilder setRiReferral(Referral riReferral) {
    this.riReferral = riReferral;
    return this;
  }

}
