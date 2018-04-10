package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.inject.NsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * Address DAO
 * 
 * @author Intake Team 4
 */
public class LegacyDescriptorDao extends CrudsDaoImpl<LegacyDescriptorEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public LegacyDescriptorDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
