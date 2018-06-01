package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsAddressUcDaoImpl extends AddressUcDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public XaCmsAddressUcDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
