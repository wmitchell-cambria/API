package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.PhoneNumbersDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XaNsPhoneNumbersDaoImpl extends PhoneNumbersDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XaNsPhoneNumbersDaoImpl(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
