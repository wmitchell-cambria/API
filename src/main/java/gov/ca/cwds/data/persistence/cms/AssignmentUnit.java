package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;
/**
 * CWDS API Team
 */
@Entity
@Table(name = "ASG_UNIT")
public class AssignmentUnit extends CmsPersistentObject {
  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String id;

  @Basic
  @Column(name = "ASGDSK_IND", nullable = false, length = 1)
  private String assignmentDeskUnitIndicator;

  @Basic
  @Column(name = "END_DT", nullable = true)
  private Date endDate;

  @Basic
  @Column(name = "PHONE_NO", nullable = false, precision = 0)
  private Long phoneNumber;

  @Basic
  @Column(name = "TEL_EXT_NO", nullable = false)
  private int phoneExtensionNumber;

  @Basic
  @Column(name = "START_DT", nullable = false)
  private Date startDate;

  @Basic
  @Column(name = "FKCWS_OFFT", nullable = false, length = 10)
  private String fkCwsOffice;

  @Basic
  @Column(name = "ASGMUNT_NM", nullable = false, length = 45)
  private String assignmentUnitName;

  @Basic
  @Column(name = "CNTY_SPFCD", nullable = false, length = 2)
  private String countySpecificCode;

  public AssignmentUnit() {
  }

  public AssignmentUnit(String id, String assignmentDeskUnitIndicator, Date endDate, Long phoneNumber,
                        int phoneExtensionNumber, Date startDate, String fkCwsOffice,
                        String assignmentUnitName, String countySpecificCode) {
    this.id = id;
    this.assignmentDeskUnitIndicator = assignmentDeskUnitIndicator;
    this.endDate = freshDate(endDate);
    this.phoneNumber = phoneNumber;
    this.phoneExtensionNumber = phoneExtensionNumber;
    this.startDate = freshDate(startDate);
    this.fkCwsOffice = fkCwsOffice;
    this.assignmentUnitName = assignmentUnitName;
    this.countySpecificCode = countySpecificCode;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAssignmentDeskUnitIndicator() {
    return assignmentDeskUnitIndicator;
  }

  public void setAssignmentDeskUnitIndicator(String assignmentDeskUnitIndicator) {
    this.assignmentDeskUnitIndicator = assignmentDeskUnitIndicator;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Long getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(Long phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public int getPhoneExtensionNumber() {
    return phoneExtensionNumber;
  }

  public void setPhoneExtensionNumber(int phoneExtensionNumber) {
    this.phoneExtensionNumber = phoneExtensionNumber;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public String getFkCwsOffice() {
    return fkCwsOffice;
  }

  public void setFkCwsOffice(String fkCwsOffice) {
    this.fkCwsOffice = fkCwsOffice;
  }

  public String getAssignmentUnitName() {
    return assignmentUnitName;
  }

  public void setAssignmentUnitName(String assignmentUnitName) {
    this.assignmentUnitName = assignmentUnitName;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public void setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
  }

  @Override
  public String getPrimaryKey() {
    return getId();
  }
}
