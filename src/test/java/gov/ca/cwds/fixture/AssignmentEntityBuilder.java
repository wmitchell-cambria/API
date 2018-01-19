package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.DomainChef;
import java.math.BigDecimal;
import java.util.Date;

import gov.ca.cwds.data.persistence.cms.Assignment;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AssignmentEntityBuilder {

  private String id = "SlCAr46088";
  private String countySpecificCode = "99";
  private Date endDate = new Date();
  private Date endTime = new Date();
  private String establishedForCode = "C";
  private String establishedForId = "0iiVVuE088";
  private String fkCaseLoad = "Aci1N2I00E";
  private String fkOutOfStateContactParty = null;
  private String responsibilityDescription = "some kind of descrption";
  private Short secondaryAssignmentRoleType = (short) 0;
  private Date startDate = DomainChef.uncookDateString("2008-01-15");
  private Date startTime = DomainChef.uncookTimeString("15:11:45");
  private String typeOfAssignmentCode = "P";
  private BigDecimal weightingNumber = new BigDecimal(0.00);

  public Assignment build() {
    return new Assignment(id, countySpecificCode, endDate, endTime, establishedForCode,
        establishedForId, fkCaseLoad, fkOutOfStateContactParty, responsibilityDescription,
        secondaryAssignmentRoleType, startDate, startTime, typeOfAssignmentCode, weightingNumber);
  }

  public String getId() {
    return id;
  }

  public AssignmentEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public AssignmentEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public Date getEndDate() {
    return endDate;
  }

  public AssignmentEntityBuilder setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public AssignmentEntityBuilder setEndDate(String endDate) {
    this.endDate = DomainChef.uncookDateString(endDate);
    return this;
  }

  public Date getEndTime() {
    return endTime;
  }

  public AssignmentEntityBuilder setEndTime(Date endTime) {
    this.endTime = endTime;
    return this;
  }

  public AssignmentEntityBuilder setEndTime(String endTime) {
    this.endTime = DomainChef.uncookTimeString(endTime);
    return this;
  }

  public String getEstablishedForCode() {
    return establishedForCode;
  }

  public AssignmentEntityBuilder setEstablishedForCode(String establishedForCode) {
    this.establishedForCode = establishedForCode;
    return this;
  }

  public String getEstablishedForId() {
    return establishedForId;
  }

  public AssignmentEntityBuilder setEstablishedForId(String establishedForId) {
    this.establishedForId = establishedForId;
    return this;
  }

  public AssignmentEntityBuilder setFkCaseLoad(String fkCaseLoad) {
    this.fkCaseLoad = fkCaseLoad;
    return this;
  }

  public AssignmentEntityBuilder setFkOutOfStateContactParty(String fkOutOfStateContactParty) {
    this.fkOutOfStateContactParty = fkOutOfStateContactParty;
    return this;
  }

  public String getResponsibilityDescription() {
    return responsibilityDescription;
  }

  public AssignmentEntityBuilder setResponsibilityDescription(String responsibilityDescription) {
    this.responsibilityDescription = responsibilityDescription;
    return this;
  }

  public Short getSecondaryAssignmentRoleType() {
    return secondaryAssignmentRoleType;
  }

  public AssignmentEntityBuilder setSecondaryAssignmentRoleType(Short secondaryAssignmentRoleType) {
    this.secondaryAssignmentRoleType = secondaryAssignmentRoleType;
    return this;
  }

  public Date getStartDate() {
    return startDate;
  }

  public AssignmentEntityBuilder setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public AssignmentEntityBuilder setStartDate(String startDate) {
    this.startDate = DomainChef.uncookDateString(startDate);
    return this;
  }

  public Date getStartTime() {
    return startTime;
  }

  public AssignmentEntityBuilder setStartTime(Date startTime) {
    this.startTime = startTime;
    return this;
  }

  public AssignmentEntityBuilder setStartTime(String startTime) {
    this.startTime = DomainChef.uncookTimeString(startTime);
    return this;
  }

  public String getTypeOfAssignmentCode() {
    return typeOfAssignmentCode;
  }

  public AssignmentEntityBuilder setTypeOfAssignmentCode(String typeOfAssignmentCode) {
    this.typeOfAssignmentCode = typeOfAssignmentCode;
    return this;
  }

  public BigDecimal getWeightingNumber() {
    return weightingNumber;
  }

  public AssignmentEntityBuilder setWeightingNumber(BigDecimal weightingNumber) {
    this.weightingNumber = weightingNumber;
    return this;
  }

}
