package gov.ca.cwds.data.cms;

import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.data.persistence.cms.RelationshipWrapper;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ClientRelationship}.
 * 
 * @author CWDS API Team
 */
public class ClientRelationshipDao extends BaseDaoImpl<ClientRelationship> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ClientRelationshipDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find by primary client id
   * 
   * @param primaryClientId - clientId
   * @return the client relationship
   */
  @SuppressWarnings("unchecked")
  public ClientRelationship[] findByPrimaryClientId(String primaryClientId) {
    final Query<CmsCase> query = this.getSessionFactory().getCurrentSession().getNamedQuery(
        "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipByPrimaryClientId");
    query.setParameter("primaryClientId", primaryClientId, StringType.INSTANCE);
    return query.list().toArray(new ClientRelationship[0]);
  }

  /**
   * Find by secondary client id
   * 
   * @param secondaryClientId - clientId
   * @return the client relationship
   */
  @SuppressWarnings("unchecked")
  public ClientRelationship[] findBySecondaryClientId(String secondaryClientId) {
    final Query<CmsCase> query = this.getSessionFactory().getCurrentSession().getNamedQuery(
        "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipBySecondaryClientId");
    query.setParameter("secondaryClientId", secondaryClientId, StringType.INSTANCE);
    return query.list().toArray(new ClientRelationship[0]);
  }

  /**
   * Find related clients by client Id
   * 
   * @param clientId - the client Id
   * @return - the client relationships
   */
  @SuppressWarnings("unchecked")
  public List<RelationshipWrapper> findRelationshipsByClientId(String clientId) {
    final Query<RelationshipWrapper> query = this.getSessionFactory().getCurrentSession()
        .getNamedNativeQuery(
            "gov.ca.cwds.data.persistence.cms.RelationshipWrapper.findAllRelatedClientsByClientId")
        .addEntity(RelationshipWrapper.class)
        .setParameter("clientId", clientId, StringType.INSTANCE);
    return query.list();
  }
}
