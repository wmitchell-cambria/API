package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaAddressDao extends AddressDao {

  public XaAddressDao(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
