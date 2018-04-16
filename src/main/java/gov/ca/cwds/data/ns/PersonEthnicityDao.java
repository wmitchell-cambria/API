package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.PersonEthnicity;
import gov.ca.cwds.inject.XaNsSessionFactory;

/**
 * PersonEthnicity Dao.
 * 
 * @author CWDS API Team
 */
public class PersonEthnicityDao extends CrudsDaoImpl<PersonEthnicity> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public PersonEthnicityDao(@XaNsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
