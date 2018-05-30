package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsReferralClientDaoImpl extends ReferralClientDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public XaCmsReferralClientDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
