package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CmsDocReferralClientDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsCmsDocReferralClientDaoImpl extends CmsDocReferralClientDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsCmsDocReferralClientDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
