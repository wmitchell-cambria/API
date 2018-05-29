package gov.ca.cwds.data.ns.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.inject.XaNsSessionFactory;

/**
 * Address DAO takes an XA session factory for distributed transactions.
 * 
 * @author CWDS API Team
 */
public class XAAddressDao extends CrudsDaoImpl<Addresses> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public XAAddressDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
