package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.CollateralIndividual;

/**
 * 
 * @author CWDS API Team
 * 
 */
@SuppressWarnings("javadoc")
public class CollateralIndividualEntityBuilder {

  String id = "AarHGUP0Ki";
  String badgeNumber = "12345670";
  Date birthDate = new Date();
  String cityName = "Sacramento";
  String commentDescription = "commentDescription";
  String emailAddress = "abc@123.com";
  String employerName = "EmployerName";
  String establishedForCode = "564";
  Long faxNumber = 1L;
  String firstName = "firstName";
  String foreignAddressIndicatorVariable = "variableName";
  String genderCode = "M";
  String lastName = "lastName";
  Short maritalStatus = (short) 12;
  String middleInitialName = "middleName";
  String namePrefixDescription = "PrefixDescription";
  Integer primaryExtensionNumber = 123;
  Long primaryPhoneNo = 1L;
  String residedOutOfStateIndicator = "Y";
  Short stateCode = (short) 1828;
  String streetName = "West River";
  String streetNumber = "2751";
  String suffixTitleDescription = "Jr";
  Integer zipNumber = 95833;
  Short zipSuffixNumber = (short) 7812;

  public CollateralIndividual build() {
    return new CollateralIndividual(badgeNumber, birthDate, cityName, commentDescription,
        emailAddress, employerName, establishedForCode, faxNumber, firstName,
        foreignAddressIndicatorVariable, genderCode, id, lastName, maritalStatus, middleInitialName,
        namePrefixDescription, primaryExtensionNumber, primaryPhoneNo, residedOutOfStateIndicator,
        stateCode, streetName, streetNumber, suffixTitleDescription, zipNumber, zipSuffixNumber);
  }

  public CollateralIndividualEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public CollateralIndividualEntityBuilder setBadgeNumber(String badgeNumber) {
    this.badgeNumber = badgeNumber;
    return this;
  }

  public CollateralIndividualEntityBuilder setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  public CollateralIndividualEntityBuilder setCityName(String cityName) {
    this.cityName = cityName;
    return this;
  }

  public CollateralIndividualEntityBuilder setCommentDescription(String commentDescription) {
    this.commentDescription = commentDescription;
    return this;
  }

  public CollateralIndividualEntityBuilder setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  public CollateralIndividualEntityBuilder setEmployerName(String employerName) {
    this.employerName = employerName;
    return this;
  }

  public CollateralIndividualEntityBuilder setEstablishedForCode(String establishedForCode) {
    this.establishedForCode = establishedForCode;
    return this;
  }

  public CollateralIndividualEntityBuilder setFaxNumber(Long faxNumber) {
    this.faxNumber = faxNumber;
    return this;
  }

  public CollateralIndividualEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public CollateralIndividualEntityBuilder setForeignAddressIndicatorVariable(
      String foreignAddressIndicatorVariable) {
    this.foreignAddressIndicatorVariable = foreignAddressIndicatorVariable;
    return this;
  }

  public CollateralIndividualEntityBuilder setGenderCode(String genderCode) {
    this.genderCode = genderCode;
    return this;
  }

  public CollateralIndividualEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public CollateralIndividualEntityBuilder setMaritalStatus(Short maritalStatus) {
    this.maritalStatus = maritalStatus;
    return this;
  }

  public CollateralIndividualEntityBuilder setMiddleInitialName(String middleInitialName) {
    this.middleInitialName = middleInitialName;
    return this;
  }

  public CollateralIndividualEntityBuilder setNamePrefixDescription(String namePrefixDescription) {
    this.namePrefixDescription = namePrefixDescription;
    return this;
  }

  public CollateralIndividualEntityBuilder setPrimaryExtensionNumber(
      Integer primaryExtensionNumber) {
    this.primaryExtensionNumber = primaryExtensionNumber;
    return this;
  }

  public CollateralIndividualEntityBuilder setPrimaryPhoneNo(Long primaryPhoneNo) {
    this.primaryPhoneNo = primaryPhoneNo;
    return this;
  }

  public CollateralIndividualEntityBuilder setResidedOutOfStateIndicator(
      String residedOutOfStateIndicator) {
    this.residedOutOfStateIndicator = residedOutOfStateIndicator;
    return this;
  }

  public CollateralIndividualEntityBuilder setStateCode(Short stateCode) {
    this.stateCode = stateCode;
    return this;
  }

  public CollateralIndividualEntityBuilder setStreetName(String streetName) {
    this.streetName = streetName;
    return this;
  }

  public CollateralIndividualEntityBuilder setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
    return this;
  }

  public CollateralIndividualEntityBuilder setSuffixTitleDescription(
      String suffixTitleDescription) {
    this.suffixTitleDescription = suffixTitleDescription;
    return this;
  }

  public CollateralIndividualEntityBuilder setZipNumber(Integer zipNumber) {
    this.zipNumber = zipNumber;
    return this;
  }

  public CollateralIndividualEntityBuilder setZipSuffixNumber(Short zipSuffixNumber) {
    this.zipSuffixNumber = zipSuffixNumber;
    return this;
  }

}
