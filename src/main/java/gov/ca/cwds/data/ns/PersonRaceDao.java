package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.PersonRace;
import gov.ca.cwds.inject.XaNsSessionFactory;

/**
 * PersonRace DAO
 * 
 * @author CWDS API Team
 */
public class PersonRaceDao extends BaseDaoImpl<PersonRace> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public PersonRaceDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

