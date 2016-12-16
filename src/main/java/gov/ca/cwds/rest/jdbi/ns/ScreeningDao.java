package gov.ca.cwds.rest.jdbi.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.NsSessionFactory;
import gov.ca.cwds.rest.api.persistence.ns.Screening;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

/**
 * Screening DAO
 * 
 * @author CWDS API Team
 */
public class ScreeningDao extends CrudsDaoImpl<Screening> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ScreeningDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
