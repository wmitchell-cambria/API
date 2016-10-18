package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PersonCreated;
import gov.ca.cwds.rest.jdbi.ns.AddressDao;
import gov.ca.cwds.rest.jdbi.ns.PersonDao;

/**
 * Business layer object to work on {@link Person}
 * 
 * @author CWDS API Team
 */

public class PersonService implements CrudsService {
  private PersonDao personDao;
  private AddressDao addressDao;

  /**
   * Constructor
   * 
   * @param crudsDao
   * @param addressDao
   */
  public PersonService(PersonDao crudsDao, AddressDao addressDao) {
    this.personDao = crudsDao;
    this.addressDao = addressDao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    gov.ca.cwds.rest.api.persistence.ns.Person object = personDao.find(primaryKey);
    if (object != null) {
      Long addressPrimaryKey = object.getPersonAddressId();
      gov.ca.cwds.rest.api.persistence.ns.Address address = null;
      if (addressPrimaryKey != null) {
        address = addressDao.find(addressPrimaryKey);
      }
      if (address != null) {
        return new Person(object, address);
      }
      return new Person(object, null);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    throw new NotImplementedException("Delete is not implemented");
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response create(Request request) {
    assert (request instanceof Person);

    Person person = ((Person) request);
    Address address = person.getAddress();

    gov.ca.cwds.rest.api.persistence.ns.Address persistedAddress =
        new gov.ca.cwds.rest.api.persistence.ns.Address(address, null);
    persistedAddress = addressDao.create(persistedAddress);


    gov.ca.cwds.rest.api.persistence.ns.Person persistedPerson =
        new gov.ca.cwds.rest.api.persistence.ns.Person(person, null);
    Long id = persistedAddress.getId();
    persistedPerson.setPersonAddressId(id);
    persistedPerson = personDao.create(persistedPerson);
    return new PersonCreated(persistedPerson, persistedAddress);
  }


  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   * gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(Serializable primaryKey, Request request) {
    throw new NotImplementedException("Update is not implemented");
  }

}
