package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Person;

public class PersonService implements CrudsService<Person> {

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Person find(Serializable primaryKey) {
    if ("found".equals(primaryKey)) {
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
  public Person delete(Serializable id) {
    throw new NotImplementedException("Delete is not implemented");
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.domain.DomainObject)
   */
  @Override
  public Serializable create(Person object) {
    return "someid";
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.domain.DomainObject)
   */
  @Override
  public String update(Person object) {
    throw new NotImplementedException("Delete is not implemented");
  }

}
