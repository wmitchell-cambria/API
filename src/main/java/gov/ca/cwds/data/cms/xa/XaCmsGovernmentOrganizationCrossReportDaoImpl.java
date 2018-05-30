package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.GovernmentOrganizationCrossReportDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsGovernmentOrganizationCrossReportDaoImpl
    extends GovernmentOrganizationCrossReportDao {

  /**
   * Constructor
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public XaCmsGovernmentOrganizationCrossReportDaoImpl(
      @XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
