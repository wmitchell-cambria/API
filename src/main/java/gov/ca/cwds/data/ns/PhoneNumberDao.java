package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.PhoneNumber;
import gov.ca.cwds.inject.XaNsSessionFactory;

/**
 * PhoneNumber DAO
 * 
 * @author CWDS API Team
 */
public class PhoneNumberDao extends CrudsDaoImpl<PhoneNumber> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public PhoneNumberDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
