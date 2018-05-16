package gov.ca.cwds.fixture;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * @author CWDS API Team
 *
 */
public class ParticipantIntakeApiResourceBuilder {

  String id = "12";
  String firstName = "Johnny";
  String middleName = "g";
  String lastName = "Max";
  String nameSuffix = "jr";
  String gender = "male";
  String ssn = "123456789";
  Date dateOfBirth = new Date();
  String approximateAge = "17";
  String approximateAgeUnits = "Y";
  List<String> languages = new LinkedList<>(Arrays.asList("English"));
  String clientId = "ABc1u7hgdg";
  String legacySourceTable = LegacyTable.CLIENT.getName();
  String races = "[\n" + "{\"race\":\"White\",\"race_detail\":\"Armenian\"}]";
  String ethnicity = "{\n" + "  \"hispanic_latino_origin\": \"Yes\",\n"
      + "  \"ethnicity_detail\": [\n" + "    \"South American\"\n" + "  ]\n" + "}";
  String screeningId = "123";
  Set<String> roles = new HashSet<>(Arrays.asList("Victim"));
  Set<AddressIntakeApi> addresses = new HashSet<>();
  Set<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumbers = new HashSet<>();
  Boolean sealed = false;
  Boolean sensitive = false;
  LegacyDescriptor legacyDescriptor = new LegacyDescriptor();;

  /**
   * @return the ParticipantIntakeApi
   */
  public ParticipantIntakeApi build() {
    return new ParticipantIntakeApi(id, legacySourceTable, clientId, legacyDescriptor, firstName,
        middleName, lastName, nameSuffix, gender, approximateAge, approximateAgeUnits, ssn,
        dateOfBirth, languages, screeningId, roles, addresses, phoneNumbers, sealed, sensitive);
  }

  public ParticipantIntakeApiResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setGender(String gender) {
    this.gender = gender;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setSsn(String ssn) {
    this.ssn = ssn;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setApproximateAge(String approximateAge) {
    this.approximateAge = approximateAge;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setApproximateAgeUnits(String approximateAgeUnits) {
    this.approximateAgeUnits = approximateAgeUnits;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setLanguages(List<String> languages) {
    this.languages = languages;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setClientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setRaces(String races) {
    this.races = races;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setEthnicity(String ethnicity) {
    this.ethnicity = ethnicity;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setScreeningId(String screeningId) {
    this.screeningId = screeningId;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setRoles(Set<String> roles) {
    this.roles = roles;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setAddresses(Set<AddressIntakeApi> addresses) {
    this.addresses = addresses;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setPhoneNumbers(
      Set<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setSealed(Boolean sealed) {
    this.sealed = sealed;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setSensitive(Boolean sensitive) {
    this.sensitive = sensitive;
    return this;
  }

  public ParticipantIntakeApiResourceBuilder setLegacyDescriptor(
      LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

}
