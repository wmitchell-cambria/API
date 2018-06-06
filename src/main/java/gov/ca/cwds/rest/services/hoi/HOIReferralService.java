package gov.ca.cwds.rest.services.hoi;

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import java.util.stream.Collectors;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class HOIReferralService extends
    SimpleResourceService<HOIRequest, HOIReferral, HOIReferralResponse> implements HOIBaseService {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(HOIReferralService.class);

  private transient AuthorizationService authorizationService;
  private transient ClientDao clientDao;
  private transient ReferralClientDao referralClientDao;
  private transient ReferralDao referralDao;
  private transient ReporterDao reporterDao;
  private transient StaffPersonDao staffPersonDao;
  private transient AllegationDao allegationDao;

  /**
   * Preferred constructor.
   *
   * @param authorizationService - authorizationService
   * @param clientDao - clientDao
   * @param referralClientDao - referralClientDao
   * @param referralDao - referralDao
   * @param staffPersonDao - staffPersonDao
   * @param allegationDao - allegationDao
   */
  @Inject
  public HOIReferralService(AuthorizationService authorizationService, ClientDao clientDao,
      ReferralClientDao referralClientDao, ReferralDao referralDao, ReporterDao reporterDao,
      StaffPersonDao staffPersonDao, AllegationDao allegationDao) {
    super();
    this.authorizationService = authorizationService;
    this.clientDao = clientDao;
    this.referralClientDao = referralClientDao;
    this.referralDao = referralDao;
    this.reporterDao = reporterDao;
    this.staffPersonDao = staffPersonDao;
    this.allegationDao = allegationDao;
  }

  @Override
  public HOIReferralResponse handleFind(HOIRequest hoiRequest) {
    final List<String> authorizedClientIds = authorizeClients(hoiRequest.getClientIds());
    List<ReferralClient> referralClientList = new ArrayList<>();
    if (!authorizedClientIds.isEmpty()) {
      referralClientList = Arrays.asList(referralClientDao.findByClientIds(authorizedClientIds));
    }
    if (referralClientList.isEmpty()) {
      return new HOIReferralResponse();
    }

    // Eliminate Referral Clients with duplicate Referral ID
    final Map<String, ReferralClient> uniqueReferralIds = new HashMap<>();
    for (ReferralClient referralClient : referralClientList) {
      uniqueReferralIds.put(referralClient.getReferralId(), referralClient);
    }

    final List<ReferralClient> referralClientArrayList = new ArrayList<>();
    for (Map.Entry<String, ReferralClient> uniqueReferral : uniqueReferralIds.entrySet()) {
      referralClientArrayList.add(uniqueReferral.getValue());
    }

    HOIReferralsData hrd = new HOIReferralsData();
    hrd.setReferralClients(referralClientArrayList);

    loadReferrals(hrd);
    loadReporters(hrd);
    loadStaffPersons(hrd);
    loadAllegations(hrd);
    loadAllegationsClients(hrd);

    final List<HOIReferral> hoiReferrals = new ArrayList<>(referralClientArrayList.size());
    HOIReferralFactory hoiReferralFactory = new HOIReferralFactory();
    for (ReferralClient referralClient : referralClientArrayList) {
      Referral referral = hrd.getReferrals().get(referralClient.getReferralId());
      SystemCodeDescriptor county = constructCounty(referral.getGovtEntityType());
      HOIReferral hoiReferral = hoiReferralFactory
          .createHOIReferral(referral, referralClient, county);
      hoiReferrals.add(hoiReferral);
    }

    hoiReferrals.sort((r1, r2) -> r2.getStartDate().compareTo(r1.getStartDate()));
    return new HOIReferralResponse(hoiReferrals);
  }

  private void loadReferrals(HOIReferralsData hrd) {
    Collection<String> referralIds = hrd.getReferralClients().stream()
        .map(ReferralClient::getReferralId).filter(Objects::nonNull).collect(Collectors.toSet());
    hrd.setReferralIds(referralIds);
    hrd.setReferrals(referralDao.findByIds(referralIds));
  }

  private void loadReporters(HOIReferralsData hrd) {
    Map<String, Reporter> reporters = reporterDao.findByReferralIds(hrd.getReferralIds());
    for (Referral referral : hrd.getReferrals().values()) {
      if (reporters.containsKey(referral.getId())) {
        referral.setReporter(reporters.get(referral.getId()));
      }
    }
  }

  private void loadStaffPersons(HOIReferralsData hrd) {
    Collection<String> staffPersonIds = hrd.getReferrals().values().stream()
        .map(Referral::getPrimaryContactStaffPersonId).filter(Objects::nonNull)
        .collect(Collectors.toSet());
    Map<String, StaffPerson> staffPersons = staffPersonDao.findByIds(staffPersonIds);
    for (Referral referral : hrd.getReferrals().values()) {
      String staffPersonId = referral.getPrimaryContactStaffPersonId();
      if (staffPersonId != null && staffPersons.containsKey(staffPersonId)) {
        referral.setStaffPerson(staffPersons.get(staffPersonId));
      }
    }
  }

  private void loadAllegations(HOIReferralsData hrd) {
    Map<String, Set<Allegation>> allegations = allegationDao
        .findByReferralIds(hrd.getReferralIds());
    for (Referral referral : hrd.getReferrals().values()) {
      if (allegations.containsKey(referral.getId())) {
        referral.setAllegations(allegations.get(referral.getId()));
      }
    }
  }

  private void loadAllegationsClients(HOIReferralsData hrd) {
    // load all Allegations Clients
    Map<String, Client> clientMap = clientDao.findClientsByIds(getAllegationsClientsIds(hrd));
    // assign Clients to Allegations
    for (Referral referral : hrd.getReferrals().values()) {
      for (Allegation allegation : referral.getAllegations()) {
        if (allegation.getVictimClientId() != null
            && clientMap.containsKey(allegation.getVictimClientId())) {
          allegation.setVictim(clientMap.get(allegation.getVictimClientId()));
        }
        if (allegation.getPerpetratorClientId() != null
            && clientMap.containsKey(allegation.getPerpetratorClientId())) {
          allegation.setPerpetrator(clientMap.get(allegation.getPerpetratorClientId()));
        }
      }
    }
  }

  private Collection<String> getAllegationsClientsIds(HOIReferralsData hrd) {
    Collection<String> allegationsClientsIds = new LinkedHashSet<>();
    // collect all Allegations Clients ID-s
    for (Referral referral : hrd.getReferrals().values()) {
      for (Allegation allegation : referral.getAllegations()) {
        if (allegation.getVictimClientId() != null) {
          allegationsClientsIds.add(allegation.getVictimClientId());
        }
        if (allegation.getPerpetratorClientId() != null) {
          allegationsClientsIds.add(allegation.getPerpetratorClientId());
        }
      }
    }
    return allegationsClientsIds;
  }

  @Override
  public HOIReferralResponse handleRequest(HOIReferral req) {
    throw new NotImplementedException("handle request not implemented");
  }

  @Override
  public AuthorizationService getAuthorizationService() {
    return authorizationService;
  }

  @Override
  public Logger getLogger() {
    return LOGGER;
  }

}
