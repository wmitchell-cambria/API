package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.AddressDao;
import gov.ca.cwds.inject.XaNsSessionFactory;

/**
 * Address DAO takes an XA session factory for distributed transactions.
 * 
 * @author CWDS API Team
 */
public class XaNsAddressDao extends AddressDao {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public XaNsAddressDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
