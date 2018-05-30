package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.AgencyDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XaNsAgencyDaoImpl extends AgencyDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XaNsAgencyDaoImpl(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
