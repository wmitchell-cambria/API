package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.Race;
import gov.ca.cwds.inject.XaNsSessionFactory;

/**
 * PhoneNumber DAO
 * 
 * @author CWDS API Team
 */
public class RaceDao extends CrudsDaoImpl<Race> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public RaceDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
