package gov.ca.cwds.rest.services.hoi;

import gov.ca.cwds.rest.services.ServiceException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.NotImplementedException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.CaseDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOICaseResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIRelatedPerson;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOISocialWorker;
import gov.ca.cwds.rest.api.domain.hoi.HOIVictim;
import gov.ca.cwds.rest.resources.SimpleResourceService;
import gov.ca.cwds.rest.services.auth.AuthorizationService;

/**
 * <p>
 * This service handle request from the user to get all the cases involved for the given clientId.
 * <p>
 * 
 * @author CWDS API Team
 *
 */
public class HOICaseService extends SimpleResourceService<HOIRequest, HOICase, HOICaseResponse> {

  private static final Logger LOGGER = LoggerFactory.getLogger(HOICaseService.class);

  private CaseDao caseDao;
  private ClientDao clientDao;
  private ClientRelationshipDao clientRelationshipDao;
  private AuthorizationService authorizationService;
  private HOIParentService hoiParentService;

  /**
   * @param caseDao {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.CmsCase} objects
   * @param clientDao {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Client} objects
   * @param clientRelationshipDao {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ClientRelationship} objects
   * @param authorizationService - authorizationService
   */
  @Inject
  public HOICaseService(CaseDao caseDao, ClientDao clientDao,
      ClientRelationshipDao clientRelationshipDao, AuthorizationService authorizationService) {
    super();
    this.caseDao = caseDao;
    this.clientDao = clientDao;
    this.clientRelationshipDao = clientRelationshipDao;
    this.authorizationService = authorizationService;
    this.hoiParentService = new HOIParentService(clientDao, clientRelationshipDao);
  }

  @Override
  protected HOICaseResponse handleFind(HOIRequest hoiRequest) {
    if (!hoiRequest.getClientIds().isEmpty()) {
      List<HOICase> cases = findByClientIds(hoiRequest);
      Collections.sort(cases);
      return new HOICaseResponse(cases);
    }
    return emptyHoiCaseResponse();
  }

  /**
   * @param clientIds - clientIds
   * @return the list of cases using clientIds
   */
  public Response findHoiCasesbyClientIds(List<String> clientIds) {
    HOIRequest hoiRequest = new HOIRequest();
    hoiRequest.setClientIds(new HashSet<>(clientIds));
    return handleFind(hoiRequest);
  }

  /**
   * @param hoiRequest - hoiCaseRequest
   * @return the cases linked to multiple client
   */
  private List<HOICase> findByClientIds(HOIRequest hoiRequest) {
    Set<String> clientIds = hoiRequest.getClientIds();
    Set<String> allClientIds = new HashSet<>(clientIds);
    for (String clientId : clientIds) {
      clientId = authorizeClient(clientId);
      allClientIds.addAll(findAllRelatedClientIds(clientId));
    }
    return findCasesForClients(allClientIds);
  }

  private HOICaseResponse emptyHoiCaseResponse() {
    HOICaseResponse hoiCaseResponse = new HOICaseResponse();
    hoiCaseResponse.setHoiCases(new ArrayList<>());
    return hoiCaseResponse;
  }

  private List<HOICase> findCasesForClients(Set<String> clientIds) {
    CmsCase[] cmscases = caseDao.findByVictimClientIds(clientIds);
    Map<String, Client> clientMap = clientDao.findClientsByIds(clientIds);
    List<HOICase> hoicases = new ArrayList<>(cmscases.length);
    for (CmsCase cmscase : cmscases) {
      Client client = clientMap.get(cmscase.getFkchldClt());
      if (client == null) {
        throw new ServiceException("Inconsistent CWS/CMS data: there is a case for child id "
            + cmscase.getFkchldClt() + " but the client entity is absent.");
      }
      HOICase hoicase = constructHOICase(cmscase, client);
      hoicases.add(hoicase);
    }
    return hoicases;
  }

  private HOICase constructHOICase(CmsCase cmscase, Client client) {
    HOIVictim focusChild = constructFocusChild(client);
    SystemCodeDescriptor county = constructCounty(cmscase);
    SystemCodeDescriptor serviceComponent = constructServiceComponent(cmscase);
    HOISocialWorker assignedSocialWorker = constructAssignedSocialWorker(cmscase);
    List<HOIRelatedPerson> parents = hoiParentService.getParents(cmscase.getFkchldClt());
    return new HOICaseFactory().createHOICase(cmscase, county, serviceComponent, focusChild,
        assignedSocialWorker, parents);
  }

  private List<String> findAllRelatedClientIds(String clientId) {
    return Stream
        .concat(getClientRelationshipsByPrimaryClient(clientId).stream(),
            getClientRelationshipsBySecondaryClient(clientId).stream())
        .collect(Collectors.toList());
  }

  private List<String> getClientRelationshipsBySecondaryClient(String clientId) {
    ClientRelationship[] clientRelationshipBySecondaryClient =
        clientRelationshipDao.findBySecondaryClientId(clientId);
    List<String> clientIds = new ArrayList<>(clientRelationshipBySecondaryClient.length);
    for (ClientRelationship relationship : clientRelationshipBySecondaryClient) {
      Short relationshipType = relationship.getClientRelationshipType();
      if (HOIRelationshipTypeService.isParentChildOrSiblingRelationshipType(relationshipType)) {
        String primaryClientId = relationship.getPrimaryClientId();
        clientIds.add(primaryClientId);
      }
    }
    return clientIds;
  }

  private List<String> getClientRelationshipsByPrimaryClient(String clientId) {
    ClientRelationship[] clientRelationshipsByPrimaryClient =
        clientRelationshipDao.findByPrimaryClientId(clientId);
    List<String> clientIds = new ArrayList<>(clientRelationshipsByPrimaryClient.length);
    for (ClientRelationship relationship : clientRelationshipsByPrimaryClient) {
      Short relationshipType = relationship.getClientRelationshipType();
      if (HOIRelationshipTypeService.isParentChildOrSiblingRelationshipType(relationshipType)) {
        String secondaryClientId = relationship.getSecondaryClientId();
        clientIds.add(secondaryClientId);
      }
    }
    return clientIds;
  }

  private HOISocialWorker constructAssignedSocialWorker(CmsCase cmscase) {
    StaffPerson staffPerson = cmscase.getStaffPerson();
    String staffId = staffPerson.getId();
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor(staffId, staffId, new DateTime(staffPerson.getLastUpdatedTime()),
            LegacyTable.STAFF_PERSON.getName(), LegacyTable.STAFF_PERSON.getDescription());
    return new HOISocialWorker(staffId, staffPerson.getFirstName(), staffPerson.getLastName(),
        legacyDescriptor);
  }

  private SystemCodeDescriptor constructServiceComponent(CmsCase cmscase) {
    return new SystemCodeDescriptor(cmscase.getActiveServiceComponentType(), SystemCodeCache
        .global().getSystemCodeShortDescription(cmscase.getActiveServiceComponentType()));
  }

  private SystemCodeDescriptor constructCounty(CmsCase cmscase) {
    return new SystemCodeDescriptor(cmscase.getGovernmentEntityType(),
        SystemCodeCache.global().getSystemCodeShortDescription(cmscase.getGovernmentEntityType()));
  }

  private HOIVictim constructFocusChild(Client client) {
    String clientId = client.getId();
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor(clientId, CmsKeyIdGenerator.getUIIdentifierFromKey(clientId),
            new DateTime(client.getLastUpdatedTime()), LegacyTable.CLIENT.getName(),
            LegacyTable.CLIENT.getDescription());
    return new HOIVictim(client.getId(), client.getCommonFirstName(), client.getCommonLastName(),
        legacyDescriptor);
  }

  @Override
  protected HOICaseResponse handleRequest(HOICase req) {
    LOGGER.info("HOICaseService handle request not implemented");
    throw new NotImplementedException("handle request not implemented");
  }

  String authorizeClient(String clientId) {
    authorizationService.ensureClientAccessAuthorized(clientId);
    return clientId;
  }

}
