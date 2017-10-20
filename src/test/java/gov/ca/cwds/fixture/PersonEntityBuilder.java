package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.ns.Person;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PersonEntityBuilder {
  Long id = 12345L;
  String firstName = "John";
  String middleName = "";
  String lastName = "Smitties";
  String nameSuffix = "";
  String gender = "M";
  Date birthDate = new Date();
  String ssn = "222331111";
  Set personAddress = new HashSet<>();
  Set personPhone = new HashSet<>();
  Set personLanguage = new HashSet<>();
  Set personEthnicity = new HashSet<>();
  Set personRace = new HashSet<>();

  public Person build(){
    return new Person(id, firstName,middleName, lastName, gender, birthDate, ssn,
        personAddress, personPhone, personLanguage, personRace, personEthnicity);
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

  public String getNameSuffix() {
    return nameSuffix;
  }

  public PersonEntityBuilder setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
    return this;
  }

  public String getGender() {
    return gender;
  }

  public PersonEntityBuilder setGender(String gender) {
    this.gender = gender;
    return this;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public PersonEntityBuilder setBirthDate(Date birthDate) {
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

  public Set getPersonAddress() {
    return personAddress;
  }

  public PersonEntityBuilder setPersonAddress(Set personAddress) {
    this.personAddress = personAddress;
    return this;
  }

  public Set getPersonPhone() {
    return personPhone;
  }

  public PersonEntityBuilder setPersonPhone(Set personPhone) {
    this.personPhone = personPhone;
    return this;
  }

  public Set getPersonLanguage() {
    return personLanguage;
  }

  public PersonEntityBuilder setPersonLanguage(Set personLanguage) {
    this.personLanguage = personLanguage;
    return this;
  }

  public Set getPersonEthnicity() {
    return personEthnicity;
  }

  public PersonEntityBuilder setPersonEthnicity(Set personEthnicity) {
    this.personEthnicity = personEthnicity;
    return this;
  }

  public Set getPersonRace() {
    return personRace;
  }

  public PersonEntityBuilder setPersonRace(Set personRace) {
    this.personRace = personRace;
    return this;
  }
}
