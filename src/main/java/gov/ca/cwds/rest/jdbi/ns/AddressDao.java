package gov.ca.cwds.rest.jdbi.ns;

import gov.ca.cwds.rest.api.persistence.ns.Address;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

import org.hibernate.SessionFactory;

/**
 * Address DAO
 * 
 * @author CWDS API Team
 */
public class AddressDao extends CrudsDaoImpl<Address> {

  public AddressDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
