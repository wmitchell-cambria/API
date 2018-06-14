package gov.ca.cwds.data.cms;

import java.util.Collection;

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
   * @param referralId - referral id
   * @return the referralClient,
   */
  public ReferralClient[] findByReferralId(String referralId) {
    @SuppressWarnings("unchecked")
    final Query<ReferralClient> query = grabSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.ReferralClient.findByReferral");
    query.setParameter("referralId", referralId, StringType.INSTANCE);
    return query.list().toArray(new ReferralClient[0]);
  }

  /**
   * Find by client id's
   * 
   * @param clientIds - client id's
   * @return the referalClients
   */
  public ReferralClient[] findByClientIds(Collection<String> clientIds) {
    @SuppressWarnings("unchecked")
    final Query<ReferralClient> query = grabSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.ReferralClient.findByClientIds");
    query.setParameterList("clientIds", clientIds, StringType.INSTANCE);
    return query.list().toArray(new ReferralClient[0]);
  }

}
