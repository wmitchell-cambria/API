package gov.ca.cwds.data.persistence.ns;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.PersonResourceBuilder;

@SuppressWarnings("javadoc")
public class PersonTest {
  private gov.ca.cwds.rest.api.domain.Address address;
  private gov.ca.cwds.rest.api.domain.Person domainPerson;
  private String lastUpdatedId = "0X5";
  private String userId = "0X5";
  private Long id = 12345L;
  private String firstName = "John";
  private String middleName = "";
  private String lastName = "Smitties";
  private String gender = "M";
  private Date birthDate = new Date();
  private String ssn = "222331111";
  private Set<PersonAddress> personAddress = new HashSet<>();
  private Set<PersonPhone> personPhone = new HashSet<>();
  private Set<PersonLanguage> personLanguage = new HashSet<>();
  private Set<PersonEthnicity> personEthnicity = new HashSet<>();
  private Set<PersonRace> personRace = new HashSet<>();
  private gov.ca.cwds.rest.api.domain.Language language =
      new gov.ca.cwds.rest.api.domain.Language("English");
  private gov.ca.cwds.rest.api.domain.PhoneNumber phoneNumber =
      new gov.ca.cwds.rest.api.domain.PhoneNumber("4084445555", "Home");
  private gov.ca.cwds.rest.api.domain.Ethnicity ethnicity =
      new gov.ca.cwds.rest.api.domain.Ethnicity("Unknown", "South American");
  private gov.ca.cwds.rest.api.domain.Race race =
      new gov.ca.cwds.rest.api.domain.Race("White", "European");

  @Before
  public void setup() {
    address = new AddressResourceBuilder().createAddress();
    Set<gov.ca.cwds.rest.api.domain.Address> addresses = new HashSet<>();
    addresses.add(address);
    Set<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumbers = new HashSet<>();
    phoneNumbers.add(phoneNumber);
    Set<gov.ca.cwds.rest.api.domain.Language> languages = new HashSet<>();
    languages.add(language);
    Set<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicities = new HashSet<>();
    ethnicities.add(ethnicity);
    Set<gov.ca.cwds.rest.api.domain.Race> races = new HashSet<>();
    races.add(race);

    domainPerson = new PersonResourceBuilder().setAddress(addresses).setPhoneNumber(phoneNumbers)
        .setLanguage(languages).setRace(races).setEthnicity(ethnicities).build();
  }

  @Test
  public void shouldCreatePerson() {
    Person person = new Person(id, firstName, middleName, lastName, gender, birthDate, ssn,
        personAddress, personPhone, personLanguage, personRace, personEthnicity);
    assertEquals(person.getId(), id);
    assertEquals(person.getPrimaryKey(), id);
    assertEquals(person.getFirstName(), firstName);
    assertEquals(person.getLastName(), lastName);
    assertEquals(person.getGender(), gender);
    assertEquals(person.getDateOfBirth(), birthDate);
    assertEquals(person.getSsn(), ssn);
    assertEquals(person.getPersonAddress(), personAddress);
    assertEquals(person.getPersonPhone(), personPhone);
    assertEquals(person.getPersonLanguage(), personLanguage);
    assertEquals(person.getPersonEthnicity(), personEthnicity);
    assertEquals(person.getPersonRace(), personRace);
  }

  @Test
  public void shouldCreatePersonFromDomainPerson() {
    Person person = new Person(domainPerson, lastUpdatedId, userId);
    assertEquals(1, person.getPersonAddress().size());
    for (PersonAddress personAddress : person.getPersonAddress()) {
      assertEquals(personAddress.getAddress().getCity(), address.getCity());
      assertEquals(personAddress.getAddress().getState(), address.getState().toString());
      assertEquals(personAddress.getAddress().getStreetAddress(), address.getStreetAddress());
      assertEquals(personAddress.getAddress().getZip(), address.getZip());
    }
    assertEquals(1, person.getPersonLanguage().size());
    for (PersonLanguage personLanguage : person.getPersonLanguage()) {
      assertEquals(personLanguage.getLanguage().getLanguageCodeId(), language.getTheLanguage());
    }

    assertEquals(1, person.getPersonPhone().size());
    for (PersonPhone personPhone : person.getPersonPhone()) {
      assertEquals(personPhone.getPhoneNumber().getNumber(), phoneNumber.getNumber());
      assertEquals(personPhone.getPhoneNumber().getType(), phoneNumber.getType());
    }

    assertEquals(1, person.getPersonEthnicity().size());
    for (PersonEthnicity personEthnicity : person.getPersonEthnicity()) {
      assertEquals(personEthnicity.getEthnicity().getEthnicityType(), ethnicity.getEthnicityType());
      assertEquals(personEthnicity.getEthnicity().getSubEthnicity(), ethnicity.getSubEthnicity());
    }

    assertEquals(1, person.getPersonRace().size());
    for (PersonRace personRace : person.getPersonRace()) {
      assertEquals(personRace.getRace().getRaceType(), race.getRaceType());
      assertEquals(personRace.getRace().getSubRaceType(), race.getSubRaceType());
    }
  }

  @Test
  public void shouldCreatePersonFromDomainPersonAndCollectionsAreEmpty() {
    domainPerson = new PersonResourceBuilder().setAddress(new HashSet<>())
        .setPhoneNumber(new HashSet<>()).setLanguage(new HashSet<>()).setEthnicity(new HashSet<>())
        .setRace(new HashSet<>()).build();
    Person person = new Person(domainPerson, lastUpdatedId, userId);
    assertTrue(person.getPersonAddress().isEmpty());
    assertTrue(person.getPersonPhone().isEmpty());
    assertTrue(person.getPersonLanguage().isEmpty());
    assertTrue(person.getPersonEthnicity().isEmpty());
    assertTrue(person.getPersonRace().isEmpty());
  }

  @Test
  public void shouldCreatePersonFromDomainPersonAndCollectionsAreNull() {
    domainPerson = new PersonResourceBuilder().setAddress(null).setPhoneNumber(null)
        .setLanguage(null).setEthnicity(null).setRace(null).build();
    Person person = new Person(domainPerson, lastUpdatedId, userId);
    assertTrue(person.getPersonAddress().isEmpty());
    assertTrue(person.getPersonPhone().isEmpty());
    assertTrue(person.getPersonLanguage().isEmpty());
    assertTrue(person.getPersonEthnicity().isEmpty());
    assertTrue(person.getPersonRace().isEmpty());
  }

  @Test
  public void shouldSetPersonAddress() {

    String streetAddress = "123 First St";
    String city = "Merced";
    String state = "CA";
    String zip = "99999";
    String type = "home";

    Person person = new Person(domainPerson, lastUpdatedId, userId);

    gov.ca.cwds.data.persistence.ns.Address persistentAddress =
        new gov.ca.cwds.data.persistence.ns.Address(id, streetAddress, city, state, zip, type);

    PersonAddress personAddress = new PersonAddress(person, persistentAddress);
    Set<PersonAddress> personAddresses = new HashSet<>();
    personAddresses.add(personAddress);

    person.setPersonAddress(personAddresses);
    gov.ca.cwds.rest.api.domain.Address newAddress = new AddressResourceBuilder().createAddress();
    Set<gov.ca.cwds.rest.api.domain.Address> newAddresses = new HashSet<>();
    newAddresses.add(newAddress);

    assertEquals(1, person.getPersonAddress().size());
    for (PersonAddress newPersonAddress : person.getPersonAddress()) {
      assertEquals(newPersonAddress.getAddress().getCity(), persistentAddress.getCity());
      assertEquals(newPersonAddress.getAddress().getState(),
          persistentAddress.getState().toString());
      assertEquals(newPersonAddress.getAddress().getStreetAddress(),
          persistentAddress.getStreetAddress());
      assertEquals(newPersonAddress.getAddress().getZip(), persistentAddress.getZip());
    }
  }

  @Test
  public void shouldSetPersonPhone() {

    String phoneNumber = "4084449999";
    String phoneType = "home";

    Person person = new Person(domainPerson, lastUpdatedId, userId);

    gov.ca.cwds.data.persistence.ns.PhoneNumber phone =
        new gov.ca.cwds.data.persistence.ns.PhoneNumber(id, phoneNumber, phoneType);
    PersonPhone personPhone = new PersonPhone(person, phone);
    Set<PersonPhone> personPhones = new HashSet<>();
    personPhones.add(personPhone);

    person.setPersonPhone(personPhones);

    assertEquals(1, person.getPersonPhone().size());
    for (PersonPhone newPersonPhone : person.getPersonPhone()) {
      assertEquals(newPersonPhone.getPhoneNumber().getNumber(), phone.getNumber());
      assertEquals(newPersonPhone.getPhoneNumber().getType(), phone.getType());
    }
  }
  
  @Test
  public void shouldSetPersonLanguage() {
	Long languageId = 1111L;	
	String languageCodeId = "5555";
	Person person = new Person(domainPerson, lastUpdatedId, userId);
	gov.ca.cwds.data.persistence.ns.Language language = new gov.ca.cwds.data.persistence.ns.Language(languageId,
		languageCodeId);
	PersonLanguage personLanguage = new PersonLanguage(person, language);
	Set<PersonLanguage> personLanguages = new HashSet<>();
	personLanguages.add(personLanguage);
	
	person.setPersonLanguage(personLanguages);
	
	assertEquals(1, person.getPersonLanguage().size());
	for (PersonLanguage newPersonLanguage : person.getPersonLanguage()) {
	  assertEquals(newPersonLanguage.getLanguage().getLanguageCodeId(), language.getLanguageCodeId());
	  assertEquals(newPersonLanguage.getLanguage().getLanguageId(), language.getLanguageId());
	}
  }

  @Test
  public void shouldSetPersonEthnicity() {

    String ethnicityType = "asian";
    String subEthnicity = "chile";

    Person person = new Person(domainPerson, lastUpdatedId, userId);

    gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity =
        new gov.ca.cwds.data.persistence.ns.Ethnicity(id, ethnicityType, subEthnicity);
    PersonEthnicity personEthnicity = new PersonEthnicity(person, ethnicity);

    Set<PersonEthnicity> personEthnicities = new HashSet<>();

    personEthnicities.add(personEthnicity);

    person.setPersonEthnicity(personEthnicities);
    assertEquals(1, person.getPersonEthnicity().size());
    for (PersonEthnicity newPersonEthnicity : person.getPersonEthnicity()) {
      assertEquals(newPersonEthnicity.getEthnicity().getEthnicityType(),
          ethnicity.getEthnicityType());
      assertEquals(newPersonEthnicity.getEthnicity().getSubEthnicity(),
          ethnicity.getSubEthnicity());
    }
  }

  @Test
  public void shouldSetPersonRace() {
    String primaryRace = "asia";
    String subRace = "samoan";

    Person person = new Person(domainPerson, lastUpdatedId, userId);

    gov.ca.cwds.data.persistence.ns.Race race =
        new gov.ca.cwds.data.persistence.ns.Race(id, primaryRace, subRace);
    PersonRace personRace = new PersonRace(person, race);
    Set<PersonRace> personRaces = new HashSet<>();
    personRaces.add(personRace);

    person.setPersonRace(personRaces);
    assertEquals(1, person.getPersonRace().size());
    for (PersonRace newPersonRace : person.getPersonRace()) {
      assertEquals(newPersonRace.getRace().getRaceType(), race.getRaceType());
      assertEquals(newPersonRace.getRace().getSubRaceType(), race.getSubRaceType());
    }
  }
}
