package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOIRelatedPerson;

/**
 * <p>
 * This service handles a request to get all the parents for the given clientId.
 * <p>
 * 
 * @author CWDS API Team
 *
 */
public class HOIParentService {

  private ClientDao clientDao;
  private ClientRelationshipDao clientRelationshipDao;

  /**
   * @param clientDao {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Client} objects
   * @param clientRelationshipDao {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ClientRelationship} objects
   */
  @Inject
  public HOIParentService(ClientDao clientDao, ClientRelationshipDao clientRelationshipDao) {
    this.clientDao = clientDao;
    this.clientRelationshipDao = clientRelationshipDao;
  }

  protected List<HOIRelatedPerson> getParents(String victimClientId) {
    List<HOIRelatedPerson> parents = new ArrayList<>();
    ClientRelationship[] clientRelationshipByPrimaryClient =
        clientRelationshipDao.findByPrimaryClientId(victimClientId);
    parents.addAll(findParentsByPrimaryRelationship(clientRelationshipByPrimaryClient));
    ClientRelationship[] clientRelationshipBySecondaryClient =
        clientRelationshipDao.findBySecondaryClientId(victimClientId);
    parents.addAll(findParentsBySecondaryRelationship(clientRelationshipBySecondaryClient));
    return parents;
  }

  private List<HOIRelatedPerson> findParentsByPrimaryRelationship(
      ClientRelationship[] clientRelationship) {
    List<HOIRelatedPerson> parents = new ArrayList<>();
    for (ClientRelationship relation : clientRelationship) {
      Short type = relation.getClientRelationshipType();
      if (isRelationTypeChild(type)) {
        String clientId = relation.getSecondaryClientId();
        parents.add(findPersonByClientId(clientId, type));
      }
    }
    return parents;
  }

  private List<HOIRelatedPerson> findParentsBySecondaryRelationship(
      ClientRelationship[] clientRelationship) {
    List<HOIRelatedPerson> parents = new ArrayList<>();
    for (ClientRelationship relation : clientRelationship) {
      Short type = relation.getClientRelationshipType();
      if (isRelationTypeParent(type)) {
        String clientId = relation.getPrimaryClientId();
        parents.add(findPersonByClientId(clientId, type));
      }
    }
    return parents;
  }

  private HOIRelatedPerson findPersonByClientId(String clientId, Short type) {
    Client client = clientDao.find(clientId);
    SystemCodeDescriptor relationship = new SystemCodeDescriptor(type,
        SystemCodeCache.global().getSystemCodeShortDescription(type));
    return createHOIRelatedPerson(client, relationship);
  }

  private HOIRelatedPerson createHOIRelatedPerson(Client client,
      SystemCodeDescriptor relationship) {
    String clientId = client.getId();
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor(clientId, CmsKeyIdGenerator.getUIIdentifierFromKey(clientId),
            new DateTime(client.getLastUpdatedTime()), LegacyTable.CLIENT.getName(),
            LegacyTable.CLIENT.getDescription());
    HOIRelatedPerson person = new HOIRelatedPerson();
    person.setId(clientId);
    person.setFirstName(client.getFirstName());
    person.setLastName(client.getLastName());
    person.setRelationship(relationship);
    person.setLimitedAccessType(LimitedAccessType.getByValue(client.getSensitivityIndicator()));
    person.setLegacyDescriptor(legacyDescriptor);
    return person;
  }

  private boolean isRelationTypeParent(Short type) {
    boolean fatherToChildRelationship = type <= 214 && type >= 201;
    boolean motherToChildRelationship = type <= 254 && type >= 245;
    boolean parentToChildRelationship = type == 272 || type == 273 || type == 5620 || type == 6361;
    return fatherToChildRelationship || motherToChildRelationship || parentToChildRelationship;
  }

  private boolean isRelationTypeChild(Short type) {
    boolean daughterToParentRelationship = type <= 200 && type >= 187;
    boolean sonToParentRelationship = type <= 294 && type >= 282;
    boolean sonToMotherPresumedRelationship = type == 6360;
    return daughterToParentRelationship || sonToParentRelationship
        || sonToMotherPresumedRelationship;
  }

}
