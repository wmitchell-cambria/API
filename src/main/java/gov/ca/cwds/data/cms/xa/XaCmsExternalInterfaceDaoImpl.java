package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ExternalInterfaceDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsExternalInterfaceDaoImpl extends ExternalInterfaceDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsExternalInterfaceDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
