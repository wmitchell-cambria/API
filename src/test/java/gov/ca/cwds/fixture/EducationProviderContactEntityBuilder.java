package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.cms.EducationProvider;
import gov.ca.cwds.data.persistence.cms.EducationProviderContact;

public class EducationProviderContactEntityBuilder {

  private String departmentOfEducationIndicator;
  private String emailAddress;
  private Long faxNumber;
  private String firstName = "Firstname";
  private String fKeyEducationProvider = "fk12345678";
  private String id = "1234567ABC";
  private String lastName = "Lastname";
  private String middleName = "Middle";
  private String namePrefixDescription;
  private Integer phoneExtension;
  private Long phoneNumber = Long.valueOf(0);
  private String primaryContactIndicator;
  private String suffixTitleDescription = "Esq";
  private String titleDescription;
  private EducationProvider educationProvider;

  public EducationProviderContact build() {
    EducationProviderContact educationProviderContact = new EducationProviderContact(
        departmentOfEducationIndicator, emailAddress, faxNumber, firstName, fKeyEducationProvider,
        id, lastName, middleName, namePrefixDescription, phoneExtension, phoneNumber,
        primaryContactIndicator, suffixTitleDescription, titleDescription);
    educationProvider = new EducationProviderEntityBuilder().build();
    educationProviderContact.setEducationProvider(educationProvider);
    return educationProviderContact;
  }

  public EducationProviderContactEntityBuilder setDepartmentOfEducationIndicator(
      String departmentOfEducationIndicator) {
    this.departmentOfEducationIndicator = departmentOfEducationIndicator;
    return this;
  }

  public EducationProviderContactEntityBuilder setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  public EducationProviderContactEntityBuilder setFaxNumber(Long faxNumber) {
    this.faxNumber = faxNumber;
    return this;
  }

  public EducationProviderContactEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public EducationProviderContactEntityBuilder setfKeyEducationProvider(
      String fKeyEducationProvider) {
    this.fKeyEducationProvider = fKeyEducationProvider;
    return this;
  }

  public EducationProviderContactEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public EducationProviderContactEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public EducationProviderContactEntityBuilder setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  public EducationProviderContactEntityBuilder setNamePrefixDescription(
      String namePrefixDescription) {
    this.namePrefixDescription = namePrefixDescription;
    return this;
  }

  public EducationProviderContactEntityBuilder setPhoneExtension(Integer phoneExtension) {
    this.phoneExtension = phoneExtension;
    return this;
  }

  public EducationProviderContactEntityBuilder setPhoneNumber(Long phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public EducationProviderContactEntityBuilder setPrimaryContactIndicator(
      String primaryContactIndicator) {
    this.primaryContactIndicator = primaryContactIndicator;
    return this;
  }

  public EducationProviderContactEntityBuilder setSuffixTitleDescription(
      String suffixTitleDescription) {
    this.suffixTitleDescription = suffixTitleDescription;
    return this;
  }

  public EducationProviderContactEntityBuilder setTitleDescription(String titleDescription) {
    this.titleDescription = titleDescription;
    return this;
  }

  public void setEducationProvider(EducationProvider educationProvider) {
    this.educationProvider = educationProvider;
  }

}
