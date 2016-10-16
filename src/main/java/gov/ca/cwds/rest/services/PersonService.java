package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PersonCreated;

/**
 * Business layer object to work on {@link Person}
 * 
 * @author CWDS API Team
 */

public class PersonService implements CrudsService {

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    if (new Long(123).equals(primaryKey)) {
      Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
      Person person = new Person("Bart", "Simpson", "M", "04/01/1990", "1234556789", address);
      return person;
    } else {
      return null;
    }
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
    Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
    return new PersonCreated(11111, "Bart", "Simpson", "M", "04/01/1990", "1234556789", address);
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
