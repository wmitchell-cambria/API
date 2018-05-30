package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * CWDS API Team
 */
@Entity
@Table(name = "CWS_OFFT")
public class CwsOffice extends CmsPersistentObject {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String id;

  @Basic
  @Column(name = "FAX_NO", nullable = false, precision = 0)
  private Long faxNumber;

  @Basic
  @Column(name = "GEO_RGNTCD", nullable = false, length = 2)
  private String geographicRegionCode;

  @Basic
  @Column(name = "GVR_ENTC", nullable = false)
  private short governmentEntityType;

  @Basic
  @Column(name = "HDQRTR_IND", nullable = false, length = 1)
  private String headquarterIndicator;

  @Basic
  @Column(name = "INACTV_IND", nullable = false, length = 1)
  private String inactiveIndicator;

  @Basic
  @Column(name = "MAILST_DSC", nullable = false, length = 7)
  private String mailStopDescription;

  @Basic
  @Column(name = "MSG_TEL_NO", nullable = false, precision = 0)
  private Long messagePhoneNumber;

  @Basic
  @Column(name = "MSG_EXT_NO", nullable = false)
  private int messagePhoneExtensionNumber;

  @Basic
  @Column(name = "CWS_OFF_NO", nullable = false, length = 5)
  private String cwsOfficeNumber;

  @Basic
  @Column(name = "PRM_TEL_NO", nullable = false, precision = 0)
  private Long primaryPhoneNumber;

  @Basic
  @Column(name = "PRM_EXT_NO", nullable = false)
  private int primaryPhoneExtensionNumber;

  @Basic
  @Column(name = "FKSTFPERST", nullable = true, length = 3)
  private String fkStaffPerson;

  @Basic
  @Column(name = "COMNT_DSC", nullable = false, length = 120)
  private String commentDescription;

  @Basic
  @Column(name = "AGENCY_NM", nullable = false, length = 45)
  private String agencyName;

  @Basic
  @Column(name = "DPT_DIV_NM", nullable = false, length = 45)
  private String departmentDivisionName;

  @Basic
  @Column(name = "CWS_OFF_NM", nullable = false, length = 45)
  private String cwsOfficeName;

  @Basic
  @Column(name = "CNTY_SPFCD", nullable = false, length = 2)
  private String countySpecificCode;

  @Basic
  @Column(name = "AGCY_CD_NO", nullable = false)
  private short agencyCodeNumber;

  @Basic
  @Column(name = "LOC_CNTY", nullable = false)
  private short locationCountyType;

  @Basic
  @Column(name = "DIR_NM_TL", nullable = false, length = 45)
  private String directorsNameTitle;

  public CwsOffice() {}

  public CwsOffice(String id, Long faxNumber, String geographicRegionCode,
      short governmentEntityType, String headquarterIndicator, String inactiveIndicator,
      String mailStopDescription, Long messagePhoneNumber, int messagePhoneExtensionNumber,
      String cwsOfficeNumber, Long primaryPhoneNumber, int primaryPhoneExtensionNumber,
      String fkStaffPerson, String commentDescription, String agencyName,
      String departmentDivisionName, String cwsOfficeName, String countySpecificCode,
      short agencyCodeNumber, short locationCountyType, String directorsNameTitle) {
    this.id = id;
    this.faxNumber = faxNumber;
    this.geographicRegionCode = geographicRegionCode;
    this.governmentEntityType = governmentEntityType;
    this.headquarterIndicator = headquarterIndicator;
    this.inactiveIndicator = inactiveIndicator;
    this.mailStopDescription = mailStopDescription;
    this.messagePhoneNumber = messagePhoneNumber;
    this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
    this.cwsOfficeNumber = cwsOfficeNumber;
    this.primaryPhoneNumber = primaryPhoneNumber;
    this.primaryPhoneExtensionNumber = primaryPhoneExtensionNumber;
    this.fkStaffPerson = fkStaffPerson;
    this.commentDescription = commentDescription;
    this.agencyName = agencyName;
    this.departmentDivisionName = departmentDivisionName;
    this.cwsOfficeName = cwsOfficeName;
    this.countySpecificCode = countySpecificCode;
    this.agencyCodeNumber = agencyCodeNumber;
    this.locationCountyType = locationCountyType;
    this.directorsNameTitle = directorsNameTitle;
  }

  public String getId() {
    return id;
  }

  public void setId(String identifier) {
    this.id = identifier;
  }

  public Long getFaxNumber() {
    return faxNumber;
  }

  public void setFaxNumber(Long faxNo) {
    this.faxNumber = faxNo;
  }

  public String getGeographicRegionCode() {
    return geographicRegionCode;
  }

  public void setGeographicRegionCode(String geoRgntcd) {
    this.geographicRegionCode = geoRgntcd;
  }

  public short getGovernmentEntityType() {
    return governmentEntityType;
  }

  public void setGovernmentEntityType(short gvrEntc) {
    this.governmentEntityType = gvrEntc;
  }

  public String getHeadquarterIndicator() {
    return headquarterIndicator;
  }

  public void setHeadquarterIndicator(String hdqrtrInd) {
    this.headquarterIndicator = hdqrtrInd;
  }

  public String getInactiveIndicator() {
    return inactiveIndicator;
  }

  public void setInactiveIndicator(String inactvInd) {
    this.inactiveIndicator = inactvInd;
  }

  public String getMailStopDescription() {
    return mailStopDescription;
  }

  public void setMailStopDescription(String mailstDsc) {
    this.mailStopDescription = mailstDsc;
  }

  public Long getMessagePhoneNumber() {
    return messagePhoneNumber;
  }

  public void setMessagePhoneNumber(Long msgTelNo) {
    this.messagePhoneNumber = msgTelNo;
  }

  public int getMessagePhoneExtensionNumber() {
    return messagePhoneExtensionNumber;
  }

  public void setMessagePhoneExtensionNumber(int msgExtNo) {
    this.messagePhoneExtensionNumber = msgExtNo;
  }

  public String getCwsOfficeNumber() {
    return cwsOfficeNumber;
  }

  public void setCwsOfficeNumber(String cwsOffNo) {
    this.cwsOfficeNumber = cwsOffNo;
  }

  public Long getPrimaryPhoneNumber() {
    return primaryPhoneNumber;
  }

  public void setPrimaryPhoneNumber(Long prmTelNo) {
    this.primaryPhoneNumber = prmTelNo;
  }

  public int getPrimaryPhoneExtensionNumber() {
    return primaryPhoneExtensionNumber;
  }

  public void setPrimaryPhoneExtensionNumber(int prmExtNo) {
    this.primaryPhoneExtensionNumber = prmExtNo;
  }

  public String getFkStaffPerson() {
    return fkStaffPerson;
  }

  public void setFkStaffPerson(String fkstfperst) {
    this.fkStaffPerson = fkstfperst;
  }

  public String getCommentDescription() {
    return commentDescription;
  }

  public void setCommentDescription(String comntDsc) {
    this.commentDescription = comntDsc;
  }

  public String getAgencyName() {
    return agencyName;
  }

  public void setAgencyName(String agencyNm) {
    this.agencyName = agencyNm;
  }

  public String getDepartmentDivisionName() {
    return departmentDivisionName;
  }

  public void setDepartmentDivisionName(String dptDivNm) {
    this.departmentDivisionName = dptDivNm;
  }

  public String getCwsOfficeName() {
    return cwsOfficeName;
  }

  public void setCwsOfficeName(String cwsOffNm) {
    this.cwsOfficeName = cwsOffNm;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public void setCountySpecificCode(String cntySpfcd) {
    this.countySpecificCode = cntySpfcd;
  }

  public short getAgencyCodeNumber() {
    return agencyCodeNumber;
  }

  public void setAgencyCodeNumber(short agcyCdNo) {
    this.agencyCodeNumber = agcyCdNo;
  }

  public short getLocationCountyType() {
    return locationCountyType;
  }

  public void setLocationCountyType(short locCnty) {
    this.locationCountyType = locCnty;
  }

  public String getDirectorsNameTitle() {
    return directorsNameTitle;
  }

  public void setDirectorsNameTitle(String dirNmTl) {
    this.directorsNameTitle = dirNmTl;
  }

  @Override
  public String getPrimaryKey() {
    return getId();
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

}
