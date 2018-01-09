package gov.ca.cwds.rest.services.investigation;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.dao.investigation.RelationshipsDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.fixture.investigation.RelationshipListEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipList;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipTo;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on Investigation
 * 
 * @author CWDS API Team
 */
public class RelationshipListService
    implements TypedCrudsService<String, RelationshipList, Response> {

  private RelationshipsDao relationshipsDao;
  private ClientDao clientDao;

  private RelationshipList validRelationshipList = new RelationshipListEntityBuilder().build();

  /**
   * Constructor and injecting the beans
   * 
   * @param relationshipsDao - relationshipsDao instance
   * @param clientDao - clientDao instance
   */
  @Inject
  public RelationshipListService(RelationshipsDao relationshipsDao, ClientDao clientDao) {
    super();
    this.relationshipsDao = relationshipsDao;
    this.clientDao = clientDao;
  }

  @Override
  public Response find(String primaryKey) {
    return validRelationshipList;
  }

  @Override
  public RelationshipList delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response create(RelationshipList request) {
    throw new NotImplementedException("create not implemented");
  }

  @Override
  public Response update(String primaryKey, RelationshipList request) {
    throw new NotImplementedException("update not implemented");
  }

  /**
   * service to find list of relationship which are connected to referral.
   * 
   * @param referral - referral object
   * @return list of relationship
   */
  public Set<Relationship> findRelationshipByReferralId(Referral referral) {
    final Set<Relationship> setRels = new HashSet<>();
    Relationship relationship;
    for (ReferralClient refClient : referral.getReferralClients()) {
      ClientRelationship[] persistedClientRelationships =
          this.relationshipsDao.findClientRelationshipByPrimaryClientId(refClient.getClientId());
      if (persistedClientRelationships.length > 0) {
        relationship =
            this.constructRelationshipData(persistedClientRelationships, refClient.getClientId());
        setRels.add(relationship);
      }
    }

    return setRels;
  }

  /**
   * construing Relationship object
   * 
   * @param persistedClientRelationships - list of clientRelationship
   * @param primaryClientId - primary client id
   * @return constructed Relationship object
   */
  private Relationship constructRelationshipData(ClientRelationship[] persistedClientRelationships,
      String primaryClientId) {
    String secondaryClientId;
    Set<RelationshipTo> relationShipToList = new HashSet<>();
    Client secondaryClient;
    Client primaryClient;
    for (ClientRelationship clientRelationship : persistedClientRelationships) {
      secondaryClientId = clientRelationship.getSecondaryClientId();
      secondaryClient = this.clientDao.find(secondaryClientId);
      relationShipToList.add(new RelationshipTo(clientRelationship, secondaryClient));
    }
    primaryClient = this.clientDao.find(primaryClientId);
    return new Relationship(primaryClient, relationShipToList);
  }

}
