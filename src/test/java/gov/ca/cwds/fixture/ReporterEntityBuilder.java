package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.Reporter;

/**
 * @author CWDS API Team
 *
 */
public class ReporterEntityBuilder {

  String referralId = "Abc0987654";
  String badgeNumber = null;
  String cityName = "Sacramento";
  Short colltrClientRptrReltnshpType = 591;
  Short communicationMethodType = 410;
  String confidentialWaiverIndicator = "N";
  String drmsMandatedRprtrFeedback = null;
  String employerName = "Butterfield Elementary";
  Date feedbackDate = null;
  String feedbackRequiredIndicator = "N";
  String firstName = "Fred";
  String lastName = "Reporter";
  String mandatedReporterIndicator = "N";
  Integer messagePhoneExtensionNumber = 0;
  Long messagePhoneNumber = 0L;
  String middleInitialName = "W";
  String namePrefixDescription = null;
  Long primaryPhoneNumber = 6199221167L;
  Integer primaryPhoneExtensionNumber = (int) 0;
  Short stateCodeType = 1828;
  String streetName = "First Street";
  String streetNumber = "2751";
  String suffixTitleDescription = null;
  Integer zipNumber = 95833;
  String lawEnforcementId = null;
  Short zipSuffixNumber = 0;
  String countySpecificCode = "51";

  /**
   * @return the build
   */
  public Reporter build() {
    return new Reporter(referralId, badgeNumber, cityName, colltrClientRptrReltnshpType,
        communicationMethodType, confidentialWaiverIndicator, drmsMandatedRprtrFeedback,
        employerName, feedbackDate, feedbackRequiredIndicator, firstName, lastName,
        mandatedReporterIndicator, messagePhoneExtensionNumber, messagePhoneNumber,
        middleInitialName, namePrefixDescription, primaryPhoneNumber, primaryPhoneExtensionNumber,
        stateCodeType, streetName, streetNumber, suffixTitleDescription, zipNumber,
        lawEnforcementId, zipSuffixNumber, countySpecificCode);
  }

  /**
   * @param referralId - referralId
   * @return the referralId
   */
  public ReporterEntityBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }

  /**
   * @param badgeNumber - badgeNumber
   * @return the badgeNumber
   */
  public ReporterEntityBuilder setBadgeNumber(String badgeNumber) {
    this.badgeNumber = badgeNumber;
    return this;
  }

  /**
   * @param cityName - cityName
   * @return the cityName
   */
  public ReporterEntityBuilder setCityName(String cityName) {
    this.cityName = cityName;
    return this;
  }

  /**
   * @param colltrClientRptrReltnshpType - colltrClientRptrReltnshpType
   * @return the colltrClientRptrReltnshpType
   */
  public ReporterEntityBuilder setColltrClientRptrReltnshpType(Short colltrClientRptrReltnshpType) {
    this.colltrClientRptrReltnshpType = colltrClientRptrReltnshpType;
    return this;
  }

  /**
   * @param communicationMethodType - communicationMethodType
   * @return the communicationMethodType
   */
  public ReporterEntityBuilder setCommunicationMethodType(Short communicationMethodType) {
    this.communicationMethodType = communicationMethodType;
    return this;
  }

  /**
   * @param confidentialWaiverIndicator - confidentialWaiverIndicator
   * @return the confidentialWaiverIndicator
   */
  public ReporterEntityBuilder setConfidentialWaiverIndicator(String confidentialWaiverIndicator) {
    this.confidentialWaiverIndicator = confidentialWaiverIndicator;
    return this;
  }

  /**
   * @param drmsMandatedRprtrFeedback - drmsMandatedRprtrFeedback
   * @return the drmsMandatedRprtrFeedback
   */
  public ReporterEntityBuilder setDrmsMandatedRprtrFeedback(String drmsMandatedRprtrFeedback) {
    this.drmsMandatedRprtrFeedback = drmsMandatedRprtrFeedback;
    return this;
  }

  /**
   * @param employerName - employerName
   * @return the employerName
   */
  public ReporterEntityBuilder setEmployerName(String employerName) {
    this.employerName = employerName;
    return this;
  }

  /**
   * @param feedbackDate - feedbackDate
   * @return the feedbackDate
   */
  public ReporterEntityBuilder setFeedbackDate(Date feedbackDate) {
    this.feedbackDate = feedbackDate;
    return this;
  }

  /**
   * @param feedbackRequiredIndicator - feedbackRequiredIndicator
   * @return the feedbackRequiredIndicator
   */
  public ReporterEntityBuilder setFeedbackRequiredIndicator(String feedbackRequiredIndicator) {
    this.feedbackRequiredIndicator = feedbackRequiredIndicator;
    return this;
  }

  /**
   * @param firstName - firstName
   * @return the firstName
   */
  public ReporterEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * @param lastName - lastName
   * @return the lastName
   */
  public ReporterEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * @param mandatedReporterIndicator - mandatedReporterIndicator
   * @return the mandatedReporterIndicator
   */
  public ReporterEntityBuilder setMandatedReporterIndicator(String mandatedReporterIndicator) {
    this.mandatedReporterIndicator = mandatedReporterIndicator;
    return this;
  }

  /**
   * @param messagePhoneExtensionNumber - messagePhoneExtensionNumber
   * @return the messagePhoneExtensionNumber
   */
  public ReporterEntityBuilder setMessagePhoneExtensionNumber(Integer messagePhoneExtensionNumber) {
    this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
    return this;
  }

  /**
   * @param messagePhoneNumber - messagePhoneNumber
   * @return the messagePhoneNumber
   */
  public ReporterEntityBuilder setMessagePhoneNumber(Long messagePhoneNumber) {
    this.messagePhoneNumber = messagePhoneNumber;
    return this;
  }

  /**
   * @param middleInitialName - middleInitialName
   * @return the middleInitialName
   */
  public ReporterEntityBuilder setMiddleInitialName(String middleInitialName) {
    this.middleInitialName = middleInitialName;
    return this;
  }

  /**
   * @param namePrefixDescription - namePrefixDescription
   * @return the namePrefixDescription
   */
  public ReporterEntityBuilder setNamePrefixDescription(String namePrefixDescription) {
    this.namePrefixDescription = namePrefixDescription;
    return this;
  }

  /**
   * @param primaryPhoneNumber - primaryPhoneNumber
   * @return the primaryPhoneNumber
   */
  public ReporterEntityBuilder setPrimaryPhoneNumber(Long primaryPhoneNumber) {
    this.primaryPhoneNumber = primaryPhoneNumber;
    return this;
  }

  /**
   * @param primaryPhoneExtensionNumber - primaryPhoneExtensionNumber
   * @return the primaryPhoneExtensionNumber
   */
  public ReporterEntityBuilder setPrimaryPhoneExtensionNumber(Integer primaryPhoneExtensionNumber) {
    this.primaryPhoneExtensionNumber = primaryPhoneExtensionNumber;
    return this;
  }

  /**
   * @param stateCodeType - stateCodeType
   * @return the stateCodeType
   */
  public ReporterEntityBuilder setStateCodeType(Short stateCodeType) {
    this.stateCodeType = stateCodeType;
    return this;
  }

  /**
   * @param streetName - streetName
   * @return the streetName
   */
  public ReporterEntityBuilder setStreetName(String streetName) {
    this.streetName = streetName;
    return this;
  }

  /**
   * @param streetNumber - streetNumber
   * @return the streetNumber
   */
  public ReporterEntityBuilder setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
    return this;
  }

  /**
   * @param suffixTitleDescription - suffixTitleDescription
   * @return the suffixTitleDescription
   */
  public ReporterEntityBuilder setSuffixTitleDescription(String suffixTitleDescription) {
    this.suffixTitleDescription = suffixTitleDescription;
    return this;
  }

  /**
   * @param zipNumber - zipNumber
   * @return the zipNumber
   */
  public ReporterEntityBuilder setZipNumber(Integer zipNumber) {
    this.zipNumber = zipNumber;
    return this;
  }

  /**
   * @param lawEnforcementId - lawEnforcementId
   * @return the lawEnforcementId
   */
  public ReporterEntityBuilder setLawEnforcementId(String lawEnforcementId) {
    this.lawEnforcementId = lawEnforcementId;
    return this;
  }

  /**
   * @param zipSuffixNumber - zipSuffixNumber
   * @return the zipSuffixNumber
   */
  public ReporterEntityBuilder setZipSuffixNumber(Short zipSuffixNumber) {
    this.zipSuffixNumber = zipSuffixNumber;
    return this;
  }

  /**
   * @param countySpecificCode - countySpecificCode
   * @return the countySpecificCode
   */
  public ReporterEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

}
