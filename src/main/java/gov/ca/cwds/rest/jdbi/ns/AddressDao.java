package gov.ca.cwds.rest.jdbi.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.NsSessionFactory;
import gov.ca.cwds.rest.api.persistence.ns.Address;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

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
  public AddressDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
