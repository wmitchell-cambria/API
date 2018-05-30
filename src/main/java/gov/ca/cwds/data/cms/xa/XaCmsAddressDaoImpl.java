package gov.ca.cwds.data.cms.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.inject.XaCmsSessionFactory;

public class XaCmsAddressDaoImpl extends AddressDao {

  @Inject
  public XaCmsAddressDaoImpl(@XaCmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
