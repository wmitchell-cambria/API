package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

/**
 * Screening DAO takes an XA session factory for distributed transactions.
 * 
 * @author CWDS API Team
 */
public class XAScreeningDao extends ScreeningDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XAScreeningDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
