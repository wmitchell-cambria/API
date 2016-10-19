package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PersonCreated;
import gov.ca.cwds.rest.jdbi.Dao;
import gov.ca.cwds.rest.jdbi.ns.PersonDao;

/**
 * Business layer object to work on {@link Person} and
 * {@link gov.ca.cwds.rest.api.persistence.ns.Person}
 * 
 * @author CWDS API Team
 */

public class PersonService implements CrudsService {
  private PersonDao personDao;

  /**
   * Constructor
   * 
   * @param personDao The {@link Dao} handling {@link gov.ca.cwds.rest.api.persistence.ns.Person}
   *        objects.
   */
  public PersonService(PersonDao personDao) {
    this.personDao = personDao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Person find(Serializable primaryKey) {
    assert (primaryKey instanceof Long);

    gov.ca.cwds.rest.api.persistence.ns.Person persistedPerson = personDao.find(primaryKey);
    if (persistedPerson != null) {
      return new Person(persistedPerson);
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
    assert (primaryKey instanceof Long);

    throw new NotImplementedException("Delete is not implemented");
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PersonCreated create(Request request) {
    assert (request instanceof Person);

    Person person = ((Person) request);
    gov.ca.cwds.rest.api.persistence.ns.Person managed =
        new gov.ca.cwds.rest.api.persistence.ns.Person(person, null);

    managed = personDao.create(managed);
    return new PersonCreated(managed);
  }


  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   * gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(Serializable primaryKey, Request request) {
    assert (primaryKey instanceof Long);
    throw new NotImplementedException("Update is not implemented");
  }

}
