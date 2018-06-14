package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsGovernmentOrganizationDaoImpl extends GovernmentOrganizationDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsGovernmentOrganizationDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
