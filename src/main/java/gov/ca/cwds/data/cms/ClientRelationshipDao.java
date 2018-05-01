package gov.ca.cwds.data.cms;

import static gov.ca.cwds.data.persistence.cms.ClientRelationship.FIND_CLIENT_RELATIONSHIP_BY_PRIMARY_CLIENT_ID;
import static gov.ca.cwds.data.persistence.cms.ClientRelationship.FIND_CLIENT_RELATIONSHIP_BY_SECONDARY_CLIENT_ID;
import static gov.ca.cwds.data.persistence.cms.RelationshipWrapper.FIND_ALL_RELATED_CLIENTS_BY_CLIENT_ID;

import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
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
    final Query<ClientRelationship> query = this.getSessionFactory().getCurrentSession().getNamedQuery(FIND_CLIENT_RELATIONSHIP_BY_PRIMARY_CLIENT_ID);
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
    final Query<ClientRelationship> query = this.getSessionFactory().getCurrentSession().getNamedQuery(FIND_CLIENT_RELATIONSHIP_BY_SECONDARY_CLIENT_ID);
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
        .getNamedNativeQuery(FIND_ALL_RELATED_CLIENTS_BY_CLIENT_ID)
        .addEntity(RelationshipWrapper.class)
        .setParameter("clientId", clientId, StringType.INSTANCE);
    return query.list();
  }
}
