package gov.ca.cwds.data.ns;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * CWDS API Team
 */
public class SafelySurrenderedBabiesDao extends BaseDaoImpl<SafelySurrenderedBabiesEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public SafelySurrenderedBabiesDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
