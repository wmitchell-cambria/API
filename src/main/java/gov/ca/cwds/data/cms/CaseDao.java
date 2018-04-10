package gov.ca.cwds.data.cms;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link CmsCase}.
 * 
 * @author CWDS API Team
 */
public class CaseDao extends CrudsDaoImpl<CmsCase> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public CaseDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find by Victim Client Ids
   * 
   * @param clientIds - the victim client Ids
   * @return all the cases for all the clients
   */
  @SuppressWarnings("unchecked")
  public CmsCase[] findByVictimClientIds(Collection<String> clientIds) {
    final Query<CmsCase> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.CmsCase.findByVictimClientIds");
    query.setParameterList("clientIds", clientIds, StringType.INSTANCE);
    return query.list().toArray(new CmsCase[0]);
  }

  /**
   * Find all related cases by Victim Client Id
   * 
   * @param clientId - the victim client Id
   * @return all cases for all related client including given client id
   */
  @SuppressWarnings("unchecked")
  public CmsCase[] findAllRelatedByVictimClientId(String clientId) {
    final NativeQuery<CmsCase> query =
        this.getSessionFactory().getCurrentSession().getNamedNativeQuery(
            "gov.ca.cwds.data.persistence.cms.CmsCase.findAllRelatedByVictimClientId");
    query.setParameter("clientId", clientId, StringType.INSTANCE);
    return query.list().toArray(new CmsCase[0]);
  }

}
