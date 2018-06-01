package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsClientUcDaoImpl extends ClientUcDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsClientUcDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
