package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link Reporter}.
 * 
 * @author CWDS API Team
 */
public class ReporterDao extends BaseDaoImpl<Reporter> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ReporterDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * finding reporters based on referral id
   * 
   * @param referralId - referral id
   * @return - list of Reporters
   */
  public Reporter[] findInvestigationReportersByReferralId(String referralId) {
    @SuppressWarnings("unchecked")
    Query<Reporter> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(
            "gov.ca.cwds.data.persistence.cms.Reporter.findInvestigationReportersByReferralId")
        .setParameter("referralId", referralId);
    return query.list().toArray(new Reporter[0]);
  }
}
