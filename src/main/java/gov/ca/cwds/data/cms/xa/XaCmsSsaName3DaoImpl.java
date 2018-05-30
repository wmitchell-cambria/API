package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.SsaName3Dao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsSsaName3DaoImpl extends SsaName3Dao {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsSsaName3DaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
