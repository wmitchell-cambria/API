package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StringType;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.ChildClient;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ChildClient}.
 * 
 * @author CWDS API Team
 */
public class ChildClientDao extends CrudsDaoImpl<ChildClient> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ChildClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find the victim Child Clients associated with a referral.
   * 
   * @param referralId the referral identifier
   * @return the Child Clients
   */
  @SuppressWarnings("unchecked")
  public ChildClient[] findVictimClients(String referralId) {
    final NativeQuery<ChildClient> query = grabSession()
        .getNamedNativeQuery("gov.ca.cwds.data.persistence.cms.ChildClient.findVictimClients");
    query.setParameter("referralId", referralId, StringType.INSTANCE);
    return query.list().toArray(new ChildClient[0]);
  }

}
