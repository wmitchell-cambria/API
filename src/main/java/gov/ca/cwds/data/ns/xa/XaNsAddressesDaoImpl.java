package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.AddressesDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

public class XaNsAddressesDaoImpl extends AddressesDao {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public XaNsAddressesDaoImpl(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
