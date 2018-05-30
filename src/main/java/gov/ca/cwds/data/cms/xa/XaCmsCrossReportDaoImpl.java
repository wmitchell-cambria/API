package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsCrossReportDaoImpl extends CrossReportDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsCrossReportDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
