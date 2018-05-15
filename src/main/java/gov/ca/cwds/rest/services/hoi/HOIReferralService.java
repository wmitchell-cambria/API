package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.resources.SimpleResourceService;
import gov.ca.cwds.rest.services.auth.AuthorizationService;

/**
 * <p>
 * This service handles user requests to fetch all the clients' referrals.
 * <p>
 * 
 * @author CWDS API Team
 */
public class HOIReferralService
    extends SimpleResourceService<HOIRequest, HOIReferral, HOIReferralResponse> {

  private ClientDao clientDao;
  private ReferralClientDao referralClientDao;
  private AuthorizationService authorizationService;

  /**
   * @param clientDao - clientDao
   * @param referralClientDao - referralClientDao
   * @param authorizationService - authorizationService
   */
  @Inject
  public HOIReferralService(ClientDao clientDao, ReferralClientDao referralClientDao,
      AuthorizationService authorizationService) {
    super();
    this.clientDao = clientDao;
    this.referralClientDao = referralClientDao;
    this.authorizationService = authorizationService;
  }

  @Override
  public HOIReferralResponse handleFind(HOIRequest hoiRequest) {
    List<ReferralClient> referralClientList = new ArrayList<>();
    if (!hoiRequest.getClientIds().isEmpty()) {
      referralClientList = fetchReferralClients(hoiRequest.getClientIds());
    }
    if (referralClientList.isEmpty()) {
      return new HOIReferralResponse();
    }
    // eliminate rows with duplicate referral Id's from referralClientArrayList
    ArrayList<ReferralClient> referralClientArrayList = new ArrayList<>(referralClientList);
    HashMap<String, ReferralClient> uniqueReferralIds = new HashMap<>();
    for (ReferralClient referralClient : referralClientArrayList) {
      uniqueReferralIds.put(referralClient.getReferralId(), referralClient);
    }
    referralClientArrayList.clear();

    for (Map.Entry<String, ReferralClient> uniqueReferral : uniqueReferralIds.entrySet()) {
      referralClientArrayList.add(uniqueReferral.getValue());
    }

    List<HOIReferral> hoiReferrals = new ArrayList<>(referralClientArrayList.size());
    for (ReferralClient referralClient : referralClientArrayList) {
      hoiReferrals.add(createHOIReferral(referralClient));
    }

    hoiReferrals.sort((r1, r2) -> r2.getStartDate().compareTo(r1.getStartDate()));
    return new HOIReferralResponse(hoiReferrals);
  }

  private HOIReferral createHOIReferral(ReferralClient referralClient) {
    final Referral referral = referralClient.getReferral();
    final StaffPerson staffPerson = referral.getStaffPerson();
    final Reporter reporter = referral.getReporter();
    final Role role = fetchForReporterRole(referral, referralClient, reporter);

    final Map<Allegation, List<Client>> allegationMap = fetchForAllegation(referral);
    return new HOIReferralFactory().createHOIReferral(referral, staffPerson, reporter,
        allegationMap, role);
  }

  private List<ReferralClient> fetchReferralClients(Collection<String> clientIds) {
    authorizeClients(clientIds);
    ReferralClient[] referralClients = referralClientDao.findByClientIds(clientIds);
    return Arrays.asList(referralClients);
  }

  /**
   * SNAP-49: Production: no HOI shown for client.
   * 
   * <p>
   * Sometimes Cases or Referrals link to clients that the current is user is not authorized to
   * view, either due to sealed/sensitivity restriction, county access privileges, or a short-coming
   * with an the authorization rule. The the client authorizer throws an UnauthorizedException, then
   * skip that client and move on. Don't bomb all History of Involvement because the user is not
   * authorized to view a client's half-sister's foster sibling. Really.
   * </p>
   * 
   * @param clientIds client keys to authorize
   */
  private void authorizeClients(Collection<String> clientIds) {
    for (String clientId : clientIds) {
      try {
        authorizeClient(clientId);
      } catch (Exception e) {
        // TODO: handle exception
      }
    }
  }

  private Role fetchForReporterRole(Referral referral, ReferralClient referralClient,
      Reporter reporter) {
    Role role = null;
    if (reporter == null) {
      if ("Y".equals(referral.getAnonymousReporterIndicator())) {
        role = Role.ANONYMOUS_REPORTER;
      } else if ("Y".equals(referralClient.getSelfReportedIndicator())) {
        role = Role.SELF_REPORTER;
      }
    } else {
      if ("Y".equals(reporter.getMandatedReporterIndicator())) {
        role = Role.MANDATED_REPORTER;
      } else {
        role = Role.NON_MANDATED_REPORTER;
      }
    }
    return role;
  }

  private Map<Allegation, List<Client>> fetchForAllegation(Referral referral) {
    Set<Allegation> allegations = referral.getAllegations();

    Set<String> allegationsClientsIds = new HashSet<>();
    for (Allegation allegation : allegations) {
      if (allegation.getVictimClientId() != null) {
        allegationsClientsIds.add(allegation.getVictimClientId());
      }
      if (allegation.getPerpetratorClientId() != null) {
        allegationsClientsIds.add(allegation.getPerpetratorClientId());
      }
    }
    Map<String, Client> clientMap = clientDao.findClientsByIds(allegationsClientsIds);

    Map<Allegation, List<Client>> allegationMap = new HashMap<>();
    for (Allegation allegation : allegations) {
      List<Client> allegationClients = new ArrayList<>();
      if (allegation.getVictimClientId() != null) {
        allegationClients.add(clientMap.get(allegation.getVictimClientId()));
      }
      if (allegation.getPerpetratorClientId() != null) {
        allegationClients.add(clientMap.get(allegation.getPerpetratorClientId()));
      }
      allegationMap.put(allegation, allegationClients);
    }
    return allegationMap;
  }

  @Override
  protected HOIReferralResponse handleRequest(HOIReferral req) {
    throw new NotImplementedException("handle request not implemented");
  }

  void authorizeClient(String clientId) {
    authorizationService.ensureClientAccessAuthorized(clientId);
  }

}
