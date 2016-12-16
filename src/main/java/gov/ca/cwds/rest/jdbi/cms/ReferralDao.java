package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.persistence.cms.Referral;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

/**
 * 
 * 
 * @author CWDS API Team
 */
public class ReferralDao extends CrudsDaoImpl<Referral> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ReferralDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
