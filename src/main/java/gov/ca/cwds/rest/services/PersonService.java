package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.ns.AddressDao;
import gov.ca.cwds.data.ns.EthnicityDao;
import gov.ca.cwds.data.ns.LanguageDao;
import gov.ca.cwds.data.ns.PersonAddressDao;
import gov.ca.cwds.data.ns.PersonDao;
import gov.ca.cwds.data.ns.PersonEthnicityDao;
import gov.ca.cwds.data.ns.PersonLanguageDao;
import gov.ca.cwds.data.ns.PersonPhoneDao;
import gov.ca.cwds.data.ns.PersonRaceDao;
import gov.ca.cwds.data.ns.PhoneNumberDao;
import gov.ca.cwds.data.ns.RaceDao;
import gov.ca.cwds.data.persistence.ns.PersonAddress;
import gov.ca.cwds.data.persistence.ns.PersonEthnicity;
import gov.ca.cwds.data.persistence.ns.PersonLanguage;
import gov.ca.cwds.data.persistence.ns.PersonPhone;
import gov.ca.cwds.data.persistence.ns.PersonRace;
import gov.ca.cwds.data.persistence.xa.XAUnitOfWork;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Ethnicity;
import gov.ca.cwds.rest.api.domain.Language;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.PostedPerson;
import gov.ca.cwds.rest.api.domain.Race;

/**
 * Business layer object to work on {@link Person} and {@link Person}.
 * 
 * @author CWDS API Team
 */
public class PersonService implements CrudsService {

  private PersonDao personDao;
  private PersonAddressDao personAddressDao;
  private AddressDao addressDao;
  private PhoneNumberDao phoneNumberDao;
  private PersonPhoneDao personPhoneDao;
  private LanguageDao languageDao;
  private PersonLanguageDao personLanguageDao;
  private RaceDao raceDao;
  private PersonRaceDao personRaceDao;
  private EthnicityDao ethnicityDao;
  private PersonEthnicityDao personEthnicityDao;

  /**
   * Constructor.
   * 
   * @param personDao The {@link Dao} handling {@link Person} objects.
   * @param personAddressDao The {@link Dao} handling {@link PersonAddress}
   * @param addressDao The {@link Dao} handling {@link Address}
   * @param personPhoneDao The {@link Dao} handling {@link PersonPhone}
   * @param phoneNumberDao The {@link Dao} handling {@link PhoneNumber}
   * @param personLanguageDao The {@link Dao} handling {@link PersonLanguage}
   * @param languageDao The {@link Dao} handling {@link Language}
   * @param personRaceDao The {@link Dao} handling {@link PersonRace}
   * @param raceDao The {@link Dao} handling {@link Race}
   * @param personEthnicityDao The {@link Dao} handling {@link PersonEthnicity}
   * @param ethnicityDao The {@link Dao} handling {@link Ethnicity}
   */
  @Inject
  public PersonService(PersonDao personDao, PersonAddressDao personAddressDao,
      AddressDao addressDao, PersonPhoneDao personPhoneDao, PhoneNumberDao phoneNumberDao,
      PersonLanguageDao personLanguageDao, LanguageDao languageDao, PersonRaceDao personRaceDao,
      RaceDao raceDao, PersonEthnicityDao personEthnicityDao, EthnicityDao ethnicityDao) {
    this.personDao = personDao;
    this.personAddressDao = personAddressDao;
    this.addressDao = addressDao;
    this.personPhoneDao = personPhoneDao;
    this.phoneNumberDao = phoneNumberDao;
    this.personLanguageDao = personLanguageDao;
    this.languageDao = languageDao;
    this.personRaceDao = personRaceDao;
    this.raceDao = raceDao;
    this.personEthnicityDao = personEthnicityDao;
    this.ethnicityDao = ethnicityDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Person find(Serializable primaryKey) {
    assert primaryKey instanceof Long;
    gov.ca.cwds.data.persistence.ns.Person persistedPerson = personDao.find(primaryKey);
    if (persistedPerson != null) {
      return new Person(persistedPerson);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  @XAUnitOfWork
  public PostedPerson create(Request request) {
    assert request instanceof Person;
    final Person person = (Person) request;

    gov.ca.cwds.data.persistence.ns.Person managedPerson =
        new gov.ca.cwds.data.persistence.ns.Person(person, null, null);
    managedPerson = personDao.create(managedPerson);
    populatePersonDetails(person, managedPerson);
    managedPerson = personDao.find(managedPerson.getId());
    return new PostedPerson(managedPerson);
  }

  // ===================
  // NOT IMPLEMENTED:
  // ===================

  /**
   * <strong>NOT IMPLEMENTED! REQUIRED BY {@link CrudsService}!</strong> {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(final Serializable primaryKey) {
    assert primaryKey instanceof Long;
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(final Serializable primaryKey, Request request) {
    assert primaryKey instanceof Long;
    assert request instanceof Person;
    final Person person = (Person) request;
    gov.ca.cwds.data.persistence.ns.Person managedPerson =
        new gov.ca.cwds.data.persistence.ns.Person(person, null, null);
    managedPerson = personDao.update(managedPerson);
    populatePersonDetails(person, managedPerson);
    managedPerson = personDao.find(managedPerson.getId());
    return new PostedPerson(managedPerson);
  }

  /**
   * 
   * @param person - The person
   * @param managedPerson - The managedPerson
   */
  private void populatePersonDetails(Person person,
      gov.ca.cwds.data.persistence.ns.Person managedPerson) {
    if (person.getAddress() != null) {
      saveAddress(person, managedPerson);
    }
    if (person.getPhoneNumber() != null) {
      savePhoneNumber(person, managedPerson);
    }
    if (person.getLanguage() != null) {
      saveLanguages(person, managedPerson);
    }
    if (person.getRace() != null) {
      saveRaces(person, managedPerson);
    }
    if (person.getEthnicity() != null) {
      saveEthnicity(person, managedPerson);
    }
  }

  private void saveEthnicity(Person person, gov.ca.cwds.data.persistence.ns.Person managedPerson) {
    for (Ethnicity ethnicity : person.getEthnicity()) {
      gov.ca.cwds.data.persistence.ns.Ethnicity managedEthnicity =
          new gov.ca.cwds.data.persistence.ns.Ethnicity(ethnicity, null, null);
      PersonEthnicity personEthnicity = new PersonEthnicity(managedPerson, managedEthnicity);
      managedPerson.addPersonEthnicity(personEthnicity);
      ethnicityDao.create(managedEthnicity);
      personEthnicityDao.create(personEthnicity);
    }
  }

  private void saveRaces(Person person, gov.ca.cwds.data.persistence.ns.Person managedPerson) {
    for (Race race : person.getRace()) {
      gov.ca.cwds.data.persistence.ns.Race managedRace =
          new gov.ca.cwds.data.persistence.ns.Race(race, null, null);
      PersonRace personRace = new PersonRace(managedPerson, managedRace);
      managedPerson.addPersonRace(personRace);
      raceDao.create(managedRace);
      personRaceDao.create(personRace);
    }
  }

  private void saveLanguages(Person person, gov.ca.cwds.data.persistence.ns.Person managedPerson) {
    for (Language language : person.getLanguage()) {
      gov.ca.cwds.data.persistence.ns.Language managedLanguage =
          new gov.ca.cwds.data.persistence.ns.Language(language, null, null);
      PersonLanguage personLanguage = new PersonLanguage(managedPerson, managedLanguage);
      managedPerson.addPersonLanguage(personLanguage);
      languageDao.create(managedLanguage);
      personLanguageDao.create(personLanguage);
    }
  }

  private void savePhoneNumber(Person person,
      gov.ca.cwds.data.persistence.ns.Person managedPerson) {
    for (PhoneNumber phoneNumber : person.getPhoneNumber()) {
      gov.ca.cwds.data.persistence.ns.PhoneNumber managedPhoneNumber =
          new gov.ca.cwds.data.persistence.ns.PhoneNumber(phoneNumber, null, null);
      PersonPhone personPhone = new PersonPhone(managedPerson, managedPhoneNumber);
      managedPerson.addPersonPhone(personPhone);
      phoneNumberDao.create(managedPhoneNumber);
      personPhoneDao.create(personPhone);
    }
  }

  private void saveAddress(Person person, gov.ca.cwds.data.persistence.ns.Person managedPerson) {
    for (Address address : person.getAddress()) {
      gov.ca.cwds.data.persistence.ns.Address managedAddress =
          new gov.ca.cwds.data.persistence.ns.Address(address, null, null);
      PersonAddress personAddress = new PersonAddress(managedPerson, managedAddress);
      managedPerson.addPersonAddress(personAddress);
      addressDao.create(managedAddress);
      personAddressDao.create(personAddress);
    }
  }

}
