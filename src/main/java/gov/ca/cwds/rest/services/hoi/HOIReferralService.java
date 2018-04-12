package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.Arrays;
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
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.resources.SimpleResourceService;
import gov.ca.cwds.rest.services.auth.AuthorizationService;

/**
 * <p>
 * This service handle request from the user to get all the referral involved for the client given.
 * <p>
 * 
 * @author CWDS API Team
 *
 */
public class HOIReferralService
    extends SimpleResourceService<HOIRequest, HOIReferral, HOIReferralResponse> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

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
  protected HOIReferralResponse handleFind(HOIRequest hoiRequest) {
    HOIReferralResponse hoiReferralResponse = new HOIReferralResponse();
    List<ReferralClient> referralClientList = new ArrayList<>();
    if (!hoiRequest.getClientIds().isEmpty()) {
      referralClientList = fetchReferralClient(hoiRequest.getClientIds());
    }
    if (referralClientList.isEmpty()) {
      return emptyHoiReferralResponse();
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

    hoiReferralResponse.setHoiReferrals(hoiReferrals);
    return hoiReferralResponse;
  }

  /**
   * @param clientIds - clientIds
   * @return the list of referrals using clientIds
   */
  public Response findHoiReferralbyClientIds(List<String> clientIds) {
    HOIRequest hoiRequest = new HOIRequest();
    hoiRequest.setClientIds(new HashSet<>(clientIds));
    return handleFind(hoiRequest);
  }

  private HOIReferral createHOIReferral(ReferralClient referralClient) {
    Role role = null;
    Referral referral = referralClient.getReferral();

    StaffPerson staffPerson = referral.getStaffPerson();
    Reporter reporter = referral.getReporter();
    role = fetchForReporterRole(role, referral, referralClient, reporter);

    Map<Allegation, List<Client>> allegationMap = fetchForAllegation(referral);
    return new HOIReferralFactory().createHOIReferral(referral, staffPerson, reporter,
        allegationMap, role);
  }

  private HOIReferralResponse emptyHoiReferralResponse() {
    HOIReferralResponse hoiReferralResponse = new HOIReferralResponse();
    hoiReferralResponse.setHoiReferrals(new ArrayList<>());
    return hoiReferralResponse;
  }

  private List<ReferralClient> fetchReferralClient(Set<String> clientIds) {
    authorizeClients(clientIds);
    ReferralClient[] referralClients = referralClientDao.findByClientIds(clientIds);
    return Arrays.asList(referralClients);
  }

  private void authorizeClients(Set<String> clientIds) {
    for (String clientId : clientIds) {
      authorizeClient(clientId);
    }
  }

  private Role fetchForReporterRole(Role role, Referral referral, ReferralClient referralClient,
      Reporter reporter) {
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
    Map<Allegation, List<Client>> allegationMap = new HashMap<>();
    for (Allegation allegation : allegations) {
      List<Client> clients = new ArrayList<>();
      fetchForClients(allegation, clients);
      allegationMap.put(allegation, clients);
    }
    return allegationMap;
  }

  private void fetchForClients(Allegation allegation, List<Client> clients) {
    if (allegation.getVictimClientId() != null) {
      clients.add(clientDao.find(authorizeClient(allegation.getVictimClientId())));
    }
    if (allegation.getPerpetratorClientId() != null) {
      clients.add(clientDao.find(allegation.getPerpetratorClientId()));
    }
  }

  @Override
  protected HOIReferralResponse handleRequest(HOIReferral req) {
    throw new NotImplementedException("handle request not implemented");
  }

  String authorizeClient(String clientId) {
    authorizationService.ensureClientAccessAuthorized(clientId);
    return clientId;
  }

}
