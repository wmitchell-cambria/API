package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.cms.AssignmentUnit;

import java.util.Date;

/**
 * CWDS API Team
 */
public class AssignmentUnitEntityBuilder {
  private String id = "assignmentUnitId";
  private String assignmentDeskUnitIndicator = "assignmentDeskUnitIndicator";
  private Date endDate = new Date();
  private Long phoneNumber = 1234567890L;
  private int phoneExtensionNumber = 1234;
  private Date startDate = new Date();
  private String fkCwsOffice = "fkCwsOffice";
  private String assignmentUnitName = "assignmentUnitName";
  private String countySpecificCode = "countySpecificCode";

  public AssignmentUnit build() {
    return new AssignmentUnit(id, assignmentDeskUnitIndicator, endDate, phoneNumber, phoneExtensionNumber,
        startDate, fkCwsOffice, assignmentUnitName, countySpecificCode);
  }

  public String getId() {
    return id;
  }

  public AssignmentUnitEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getAssignmentDeskUnitIndicator() {
    return assignmentDeskUnitIndicator;
  }

  public AssignmentUnitEntityBuilder setAssignmentDeskUnitIndicator(String assignmentDeskUnitIndicator) {
    this.assignmentDeskUnitIndicator = assignmentDeskUnitIndicator;
    return this;
  }

  public Date getEndDate() {
    return endDate;
  }

  public AssignmentUnitEntityBuilder setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public Long getPhoneNumber() {
    return phoneNumber;
  }

  public AssignmentUnitEntityBuilder setPhoneNumber(Long phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public int getPhoneExtensionNumber() {
    return phoneExtensionNumber;
  }

  public AssignmentUnitEntityBuilder setPhoneExtensionNumber(int phoneExtensionNumber) {
    this.phoneExtensionNumber = phoneExtensionNumber;
    return this;
  }

  public Date getStartDate() {
    return startDate;
  }

  public AssignmentUnitEntityBuilder setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public String getFkCwsOffice() {
    return fkCwsOffice;
  }

  public AssignmentUnitEntityBuilder setFkCwsOffice(String fkCwsOffice) {
    this.fkCwsOffice = fkCwsOffice;
    return this;
  }

  public String getAssignmentUnitName() {
    return assignmentUnitName;
  }

  public AssignmentUnitEntityBuilder setAssignmentUnitName(String assignmentUnitName) {
    this.assignmentUnitName = assignmentUnitName;
    return this;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public AssignmentUnitEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }
}
