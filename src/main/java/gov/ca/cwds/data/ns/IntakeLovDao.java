package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.inject.XaNsSessionFactory;

/**
 * DAO for Intake LOV codes.
 * 
 * @author CWDS API Team
 */
public class IntakeLovDao extends BaseDaoImpl<IntakeLov> {

  /**
   * Constructor.
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public IntakeLovDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
