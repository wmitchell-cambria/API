package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ReferralAssignmentDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsReferralAssignmentDaoImpl extends ReferralAssignmentDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsReferralAssignmentDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
