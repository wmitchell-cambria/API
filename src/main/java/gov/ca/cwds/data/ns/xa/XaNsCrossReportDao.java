package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.CrossReportDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

/**
 * Postgres Cross Report DAO takes an XA session factory for distributed transactions.
 * 
 * @author CWDS API Team
 */
public class XaNsCrossReportDao extends CrossReportDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XaNsCrossReportDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
