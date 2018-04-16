package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.Address;
import gov.ca.cwds.inject.XaNsSessionFactory;

/**
 * Address DAO
 * 
 * @author CWDS API Team
 */
public class AddressDao extends CrudsDaoImpl<Address> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public AddressDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
