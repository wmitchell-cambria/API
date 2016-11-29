package gov.ca.cwds.rest.jdbi.ns;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.ns.Person;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

/**
 * Person DAO
 * 
 * @author CWDS API Team
 */
public class PersonDao extends BaseDaoImpl<Person> {

  public PersonDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
