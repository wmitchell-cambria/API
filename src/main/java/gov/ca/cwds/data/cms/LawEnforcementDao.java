package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.LawEnforcementEntity;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link LawEnforcementEntity}.
 * 
 * @author CWDS API Team
 */
public class LawEnforcementDao extends BaseDaoImpl<LawEnforcementEntity> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public LawEnforcementDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

