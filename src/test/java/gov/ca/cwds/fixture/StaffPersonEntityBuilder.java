package gov.ca.cwds.fixture;

import java.math.BigDecimal;
import java.util.Date;

import gov.ca.cwds.data.persistence.cms.StaffPerson;

/**
 * @author CWDS API Team
 */
public class StaffPersonEntityBuilder {

  String id = "q1p";
  Date endDate = null;
  String firstName = "Jak";
  String jobTitle = "Developer";
  String lastName = "Simmon";
  String middleInitial = "K";
  String namePrefix = "P";
  BigDecimal phoneNumber = new BigDecimal(9165672100L);
  Integer phoneExt = 0;
  Date startDate = new Date();
  String nameSuffix = "L";
  String telecommuterIndicator = "N";
  String cwsOffice = "Cws Office";
  String availabilityAndLocationDescription = "availability And Location Description";
  String ssrsLicensingWorkerId = "ABc12345567";
  String countyCode = "99";
  String dutyWorkerIndicator = "false";
  String cwsOfficeAddress = "2870 Gatewayd Oaks";
  String emailAddress = "abc123";

  public StaffPerson build() {
    return new StaffPerson(id, endDate, firstName, jobTitle, lastName, middleInitial, namePrefix,
        phoneNumber, phoneExt, startDate, nameSuffix, telecommuterIndicator, cwsOffice,
        availabilityAndLocationDescription, ssrsLicensingWorkerId, countyCode, dutyWorkerIndicator,
        cwsOfficeAddress, emailAddress);
  }

  public StaffPersonEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public StaffPersonEntityBuilder setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public StaffPersonEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public StaffPersonEntityBuilder setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
    return this;
  }

  public StaffPersonEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public StaffPersonEntityBuilder setMiddleInitial(String middleInitial) {
    this.middleInitial = middleInitial;
    return this;
  }

  public StaffPersonEntityBuilder setNamePrefix(String namePrefix) {
    this.namePrefix = namePrefix;
    return this;
  }

  public StaffPersonEntityBuilder setPhoneNumber(BigDecimal phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public StaffPersonEntityBuilder setPhoneExt(Integer phoneExt) {
    this.phoneExt = phoneExt;
    return this;
  }

  public StaffPersonEntityBuilder setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public StaffPersonEntityBuilder setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
    return this;
  }

  public StaffPersonEntityBuilder setTelecommuterIndicator(String telecommuterIndicator) {
    this.telecommuterIndicator = telecommuterIndicator;
    return this;
  }

  public StaffPersonEntityBuilder setCwsOffice(String cwsOffice) {
    this.cwsOffice = cwsOffice;
    return this;
  }

  public StaffPersonEntityBuilder setAvailabilityAndLocationDescription(
      String availabilityAndLocationDescription) {
    this.availabilityAndLocationDescription = availabilityAndLocationDescription;
    return this;
  }

  public StaffPersonEntityBuilder setSsrsLicensingWorkerId(String ssrsLicensingWorkerId) {
    this.ssrsLicensingWorkerId = ssrsLicensingWorkerId;
    return this;
  }

  public StaffPersonEntityBuilder setCountyCode(String countyCode) {
    this.countyCode = countyCode;
    return this;
  }

  public StaffPersonEntityBuilder setDutyWorkerIndicator(String dutyWorkerIndicator) {
    this.dutyWorkerIndicator = dutyWorkerIndicator;
    return this;
  }

  public StaffPersonEntityBuilder setCwsOfficeAddress(String cwsOfficeAddress) {
    this.cwsOfficeAddress = cwsOfficeAddress;
    return this;
  }

  public StaffPersonEntityBuilder setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

}
