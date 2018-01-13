package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.cms.CwsOffice;

/**
 * CWDS API Team
 */
public class CwsOfficeEntityBuilder {
  private String id = "1234567ABC";
  private long faxNumber = 1234567;
  private String geographicRegionCode = "111";
  private short governmentEntityType = 1034;
  private String headquarterIndicator = "Y";
  private String inactiveIndicator = "Y";
  private String mailStopDescription = "office mail stop";
  private long messagePhoneNumber = 2345678;
  private int messagePhoneExtensionNumber = 123;
  private String cwsOfficeNumber = "office number";
  private long primaryPhoneNumber = 3456789;
  private int primaryPhoneExtensionNumber = 234;
  private String fkStaffPerson = "0X5";
  private String commentDescription = "description of comment";
  private String agencyName = "county child welfare";
  private String departmentDivisionName = "county department";
  private String cwsOfficeName = "county office";
  private String countySpecificCode = "34";
  private short agencyCodeNumber = 1122;
  private short locationCountyType = 2233;
  private String directorsNameTitle = "director title";

  public CwsOffice build() {
    return new CwsOffice(id, faxNumber, geographicRegionCode, governmentEntityType, headquarterIndicator,
        inactiveIndicator, mailStopDescription, messagePhoneNumber, messagePhoneExtensionNumber,
        cwsOfficeNumber, primaryPhoneNumber, primaryPhoneExtensionNumber, fkStaffPerson, commentDescription,
        agencyName, departmentDivisionName, cwsOfficeName, countySpecificCode, agencyCodeNumber,
        locationCountyType, directorsNameTitle);
  }

  public String getId() {
    return id;
  }

  public CwsOfficeEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public long getFaxNumber() {
    return faxNumber;
  }

  public CwsOfficeEntityBuilder setFaxNumber(long faxNumber) {
    this.faxNumber = faxNumber;
    return this;
  }

  public String getGeographicRegionCode() {
    return geographicRegionCode;
  }

  public CwsOfficeEntityBuilder setGeographicRegionCode(String geographicRegionCode) {
    this.geographicRegionCode = geographicRegionCode;
    return this;
  }

  public short getGovernmentEntityType() {
    return governmentEntityType;
  }

  public CwsOfficeEntityBuilder setGovernmentEntityType(short governmentEntityType) {
    this.governmentEntityType = governmentEntityType;
    return this;
  }

  public String getHeadquarterIndicator() {
    return headquarterIndicator;
  }

  public CwsOfficeEntityBuilder setHeadquarterIndicator(String headquarterIndicator) {
    this.headquarterIndicator = headquarterIndicator;
    return this;
  }

  public String getInactiveIndicator() {
    return inactiveIndicator;
  }

  public CwsOfficeEntityBuilder setInactiveIndicator(String inactiveIndicator) {
    this.inactiveIndicator = inactiveIndicator;
    return this;
  }

  public String getMailStopDescription() {
    return mailStopDescription;
  }

  public CwsOfficeEntityBuilder setMailStopDescription(String mailStopDescription) {
    this.mailStopDescription = mailStopDescription;
    return this;
  }

  public long getMessagePhoneNumber() {
    return messagePhoneNumber;
  }

  public CwsOfficeEntityBuilder setMessagePhoneNumber(long messagePhoneNumber) {
    this.messagePhoneNumber = messagePhoneNumber;
    return this;
  }

  public int getMessagePhoneExtensionNumber() {
    return messagePhoneExtensionNumber;
  }

  public CwsOfficeEntityBuilder setMessagePhoneExtensionNumber(int messagePhoneExtensionNumber) {
    this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
    return this;
  }

  public String getCwsOfficeNumber() {
    return cwsOfficeNumber;
  }

  public CwsOfficeEntityBuilder setCwsOfficeNumber(String cwsOfficeNumber) {
    this.cwsOfficeNumber = cwsOfficeNumber;
    return this;
  }

  public long getPrimaryPhoneNumber() {
    return primaryPhoneNumber;
  }

  public CwsOfficeEntityBuilder setPrimaryPhoneNumber(long primaryPhoneNumber) {
    this.primaryPhoneNumber = primaryPhoneNumber;
    return this;
  }

  public int getPrimaryPhoneExtensionNumber() {
    return primaryPhoneExtensionNumber;
  }

  public CwsOfficeEntityBuilder setPrimaryPhoneExtensionNumber(int primaryPhoneExtensionNumber) {
    this.primaryPhoneExtensionNumber = primaryPhoneExtensionNumber;
    return this;
  }

  public String getFkStaffPerson() {
    return fkStaffPerson;
  }

  public CwsOfficeEntityBuilder setFkStaffPerson(String fkStaffPerson) {
    this.fkStaffPerson = fkStaffPerson;
    return this;
  }

  public String getCommentDescription() {
    return commentDescription;
  }

  public CwsOfficeEntityBuilder setCommentDescription(String commentDescription) {
    this.commentDescription = commentDescription;
    return this;
  }

  public String getAgencyName() {
    return agencyName;
  }

  public CwsOfficeEntityBuilder setAgencyName(String agencyName) {
    this.agencyName = agencyName;
    return this;
  }

  public String getDepartmentDivisionName() {
    return departmentDivisionName;
  }

  public CwsOfficeEntityBuilder setDepartmentDivisionName(String departmentDivisionName) {
    this.departmentDivisionName = departmentDivisionName;
    return this;
  }

  public String getCwsOfficeName() {
    return cwsOfficeName;
  }

  public CwsOfficeEntityBuilder setCwsOfficeName(String cwsOfficeName) {
    this.cwsOfficeName = cwsOfficeName;
    return this;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public CwsOfficeEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public short getAgencyCodeNumber() {
    return agencyCodeNumber;
  }

  public CwsOfficeEntityBuilder setAgencyCodeNumber(short agencyCodeNumber) {
    this.agencyCodeNumber = agencyCodeNumber;
    return this;
  }

  public short getLocationCountyType() {
    return locationCountyType;
  }

  public CwsOfficeEntityBuilder setLocationCountyType(short locationCountyType) {
    this.locationCountyType = locationCountyType;
    return this;
  }

  public String getDirectorsNameTitle() {
    return directorsNameTitle;
  }

  public CwsOfficeEntityBuilder setDirectorsNameTitle(String directorsNameTitle) {
    this.directorsNameTitle = directorsNameTitle;
    return this;
  }
}

