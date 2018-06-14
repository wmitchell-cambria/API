package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CwsOfficeDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsCwsOfficeDaoImpl extends CwsOfficeDao {

  /**
   * Constructor
   *
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsCwsOfficeDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
