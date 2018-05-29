package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsAddressDao extends BaseDaoImpl<Address> {

  @Inject
  public XaCmsAddressDao(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
