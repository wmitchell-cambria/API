package gov.ca.cwds.rest.jdbi.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.NsSessionFactory;
import gov.ca.cwds.rest.api.persistence.ns.Person;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

/**
 * Person DAO
 * 
 * @author CWDS API Team
 */
public class PersonDao extends BaseDaoImpl<Person> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public PersonDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
