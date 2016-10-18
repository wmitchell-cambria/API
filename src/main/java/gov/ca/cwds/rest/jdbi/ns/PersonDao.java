package gov.ca.cwds.rest.jdbi.ns;

import gov.ca.cwds.rest.api.persistence.ns.Person;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

import org.hibernate.SessionFactory;

/**
 * Person DAO
 * 
 * @author CWDS API Team
 */
public class PersonDao extends CrudsDaoImpl<Person> {

  public PersonDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
