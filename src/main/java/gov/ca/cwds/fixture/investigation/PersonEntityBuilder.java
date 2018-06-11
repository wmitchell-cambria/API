package gov.ca.cwds.fixture.investigation;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.InvestigationAddress;
import gov.ca.cwds.rest.api.domain.investigation.Person;
import gov.ca.cwds.rest.api.domain.investigation.PhoneNumber;

public class PersonEntityBuilder {

  private CmsRecordDescriptor cmsRecordDescriptor;
  private String lastUpdatedBy = "0X5";
  private Date lastUpdatedAt =
      DomainChef.uncookStrictTimestampString("2016-04-27T23:30:14.000-0000");
  private String firstName = "Art";
  private String middleName = "Mike";
  private String lastName = "Griswald";
  private String suffixTitle = "bs";
  private String gender = "M";
  private String birthDate = "1998-10-30";
  private String ssn = "999667777";
  private Set<String> languages = new LinkedHashSet<>();

  protected String primaryLanguage = "1253";
  protected String secondaryLanguage = "1255";
  private RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicityEntityBuilder().build();
  protected Boolean sensitive = Boolean.FALSE;
  protected Boolean sealed = Boolean.FALSE;
  protected Short phoneType = 1111;

  private Long phoneNumber = 1233219876L;
  private CmsRecordDescriptor phoneCmsRecordDescriptor =
      new CmsRecordDescriptor("1234567ABC", "001-2000-3399-415790", "CLIENT_T", "Client");

  private PhoneNumber phone =
      new PhoneNumber(phoneNumber, 3322, phoneType, phoneCmsRecordDescriptor);
  private Set<PhoneNumber> phoneNumbers = new HashSet<>();
  private Set<String> roles = new HashSet<>();

  private InvestigationAddress address = new InvestigationAddressEntityBuilder().build();
  private Set<InvestigationAddress> addresses = new HashSet<>();

  public Person build() {
    roles.add("MND_RPTR");
    addresses.add(address);
    languages.add(primaryLanguage);
    languages.add(secondaryLanguage);
    cmsRecordDescriptor =
        new CmsRecordDescriptor("1234567ABC", "111-222-333-4444", "CLIENT_T", "Client");
    phoneNumbers.add(phone);

    return new Person(cmsRecordDescriptor, lastUpdatedBy, lastUpdatedAt, firstName, middleName,
        lastName, suffixTitle, gender, birthDate, ssn, languages, raceAndEthnicity, sensitive,
        sealed, phoneNumbers, roles, addresses);
  }

  public CmsRecordDescriptor getCmsRecordDescriptor() {
    return cmsRecordDescriptor;
  }

  public PersonEntityBuilder setCmsRecordDescriptor(CmsRecordDescriptor cmsRecordDescriptor) {
    this.cmsRecordDescriptor = cmsRecordDescriptor;
    return this;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public PersonEntityBuilder setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
    return this;
  }

  public Date getLastUpdatedAt() {
    return freshDate(lastUpdatedAt);
  }

  public PersonEntityBuilder setLastUpdatedAt(Date lastUpdatedAt) {
    this.lastUpdatedAt = freshDate(lastUpdatedAt);
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public PersonEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getMiddleName() {
    return middleName;
  }

  public PersonEntityBuilder setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public PersonEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getSuffixTitle() {
    return suffixTitle;
  }

  public PersonEntityBuilder setSuffixTitle(String suffixTitle) {
    this.suffixTitle = suffixTitle;
    return this;
  }

  public String getGender() {
    return gender;
  }

  public PersonEntityBuilder setGender(String gender) {
    this.gender = gender;
    return this;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public PersonEntityBuilder setBirthDate(String birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  public String getSsn() {
    return ssn;
  }

  public PersonEntityBuilder setSsn(String ssn) {
    this.ssn = ssn;
    return this;
  }

  public Set<String> getLanguages() {
    return languages;
  }

  public PersonEntityBuilder setLanguages(Set<String> languages) {
    this.languages = languages;
    return this;
  }

  public RaceAndEthnicity getRaceAndEthnicity() {
    return raceAndEthnicity;
  }

  public PersonEntityBuilder setRaceAndEthnicity(RaceAndEthnicity raceAndEthnicity) {
    this.raceAndEthnicity = raceAndEthnicity;
    return this;
  }

  public Boolean getSensitive() {
    return sensitive;
  }

  public PersonEntityBuilder setSensitive(Boolean sensitive) {
    this.sensitive = sensitive;
    return this;
  }

  public Boolean getSealed() {
    return sealed;
  }

  public PersonEntityBuilder setSealed(Boolean sealed) {
    this.sealed = sealed;
    return this;
  }

  public Long getPhoneNumber() {
    return phoneNumber;
  }

  public PersonEntityBuilder setPhoneNumber(Long phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public CmsRecordDescriptor getPhoneCmsRecordDescriptor() {
    return phoneCmsRecordDescriptor;
  }

  public PersonEntityBuilder setPhoneCmsRecordDescriptor(
      CmsRecordDescriptor phoneCmsRecordDescriptor) {
    this.phoneCmsRecordDescriptor = phoneCmsRecordDescriptor;
    return this;
  }

  public PhoneNumber getPhone() {
    return phone;
  }

  public PersonEntityBuilder setPhone(PhoneNumber phone) {
    this.phone = phone;
    return this;
  }

  public Set<PhoneNumber> getPhoneNumbers() {
    return phoneNumbers;
  }

  public PersonEntityBuilder setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
    return this;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public PersonEntityBuilder setRoles(Set<String> roles) {
    this.roles = roles;
    return this;
  }

  public InvestigationAddress getAddress() {
    return address;
  }

  public PersonEntityBuilder setAddress(InvestigationAddress address) {
    this.address = address;
    return this;
  }

  public Set<InvestigationAddress> getAddresses() {
    return addresses;
  }

  public PersonEntityBuilder setAddresses(Set<InvestigationAddress> addresses) {
    this.addresses = addresses;
    return this;
  }

}
