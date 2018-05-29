package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsReferralDao extends ReferralDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public XaCmsReferralDao(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
