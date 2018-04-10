package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.PhoneNumbers;
import gov.ca.cwds.inject.NsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * PhoneNumber DAO
 * 
 * @author Intake Team 4
 */
public class PhoneNumbersDao extends CrudsDaoImpl<PhoneNumbers> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public PhoneNumbersDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
