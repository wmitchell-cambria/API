package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ReferralClient}.
 * 
 * @author CWDS API Team
 */
public class ReferralClientDao extends CrudsDaoImpl<ReferralClient> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ReferralClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find by referral id
   * 
   * @param referralId - referralId
   * @return the referralClient,
   */
  @SuppressWarnings("unchecked")
  public ReferralClient[] findByReferralId(String referralId) {
    final Query<ReferralClient> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.ReferralClient.findByReferral");
    query.setParameter("referralId", referralId, StringType.INSTANCE);
    return query.list().toArray(new ReferralClient[0]);
  }

  /**
   * Find by client id
   * 
   * @param clientId - referralId
   * @return the referalClient
   */
  @SuppressWarnings("unchecked")
  public ReferralClient[] findByClientId(String clientId) {
    final Query<ReferralClient> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.ReferralClient.findByClient");
    query.setParameter("clientId", clientId, StringType.INSTANCE);
    return query.list().toArray(new ReferralClient[0]);
  }

}
