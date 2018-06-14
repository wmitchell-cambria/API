package gov.ca.cwds.rest.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.RelationshipWrapper;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipTo;

/**
 * Genealogist. Noun. A researcher who discovers a person's hereditary relationships in order to
 * answer the question, "Who's your daddy?" Or your daddy's daddy's daddy's daddy, etc.
 * 
 * <p>
 * Any clients that the current user is not authorized to view are omitted from results.
 * </p>
 * 
 * @author CWDS API Team
 */
public class Genealogist {

  ClientDao clientDao;

  /**
   * Constructor
   *
   * @param clientDao The Client Dao object
   */
  @Inject
  public Genealogist(ClientDao clientDao) {
    this.clientDao = clientDao;
  }

  public Relationship buildRelationships(List<RelationshipWrapper> relationships, String clientId) {
    if (relationships == null || clientId == null) {
      return new Relationship();
    }

    final Set<RelationshipTo> relations = new HashSet<>(relationships.size());
    for (RelationshipWrapper relationship : relationships) {
      boolean clientIsPrimary = clientId.equals(relationship.getPrimaryLegacyId());
      relations.add(createBar(relationship, clientIsPrimary));
    }

    final Client primaryClient = findClient(clientId);
    Relationship relationship;
    if (primaryClient != null) {
      relationship = new Relationship(primaryClient, relations);
    } else {
      relationship = new Relationship();
    }
    return relationship;
  }

  private RelationshipTo createBar(RelationshipWrapper relationship, boolean clientIsPrimary) {
    RelationshipTo relationshipTo;
    if (clientIsPrimary) {
      relationshipTo = createRelationShipTo(relationship.getSecondaryLegacyId(),
          relationship.getPrimaryRelationshipCode(), relationship.getSecondaryRelationshipCode(),
          relationship.getSecondaryFirstName(), relationship.getSecondaryLastName(),
          relationship.getSecondaryNameSuffix(), "");
    } else {
      relationshipTo = createRelationShipTo(relationship.getPrimaryLegacyId(),
          relationship.getSecondaryRelationshipCode(), relationship.getPrimaryRelationshipCode(),
          relationship.getPrimaryFirstName(), relationship.getPrimaryLastName(),
          relationship.getPrimaryNameSuffix(), "");
    }
    return relationshipTo;
  }

  private RelationshipTo createRelationShipTo(String relationId, String primaryRelationCode,
      String secondaryRelation, String secondaryFirstname, String secodnaryLastName,
      String nameSuffix, String relationContext) {
    return new RelationshipTo(secondaryFirstname, secodnaryLastName, nameSuffix, secondaryRelation,
        relationContext, primaryRelationCode, relationId);
  }

  private Client findClient(String id) {
    return this.clientDao.find(id);
  }

}
