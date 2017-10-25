package gov.ca.cwds.data.dao.investigation;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.domain.investigation.SafetyAlerts;

/**
 * DAO for {@link SafetyAlerts}.
 * 
 * @author CWDS API Team
 */
public class SafetyAlertsDao extends CrudsDaoImpl<Allegation> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public SafetyAlertsDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
