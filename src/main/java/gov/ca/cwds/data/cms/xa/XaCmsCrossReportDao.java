package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsCrossReportDao extends CrossReportDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsCrossReportDao(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
