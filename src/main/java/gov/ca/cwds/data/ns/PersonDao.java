package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.Person;
import gov.ca.cwds.inject.XaNsSessionFactory;

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
  public PersonDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
