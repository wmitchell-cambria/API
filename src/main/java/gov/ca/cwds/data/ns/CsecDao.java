package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.CsecEntity;
import gov.ca.cwds.inject.NsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * CWDS API Team
 */
public class CsecDao extends BaseDaoImpl<CsecEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public CsecDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
