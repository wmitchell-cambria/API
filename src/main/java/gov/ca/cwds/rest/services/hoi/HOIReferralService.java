package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.joda.time.DateTime;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.api.domain.AccessLimitation;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOIAllegation;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role;
import gov.ca.cwds.rest.api.domain.hoi.Perpetrator;
import gov.ca.cwds.rest.api.domain.hoi.SocialWorker;
import gov.ca.cwds.rest.api.domain.hoi.Victim;
import gov.ca.cwds.rest.resources.SimpleResourceService;

/**
 * <p>
 * This service handle request from the user to get all the referral involved for the client given.
 * <p>
 * 
 * @author CWDS API Team
 *
 */
public class HOIReferralService
    extends SimpleResourceService<String, HOIReferral, HOIReferralResponse> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private transient ClientDao clientDao;
  private transient ReferralClientDao referralClientDao;

  /**
   * @param clientDao - clientDao
   * @param referralClientDao - referralClientDao
   */
  @Inject
  public HOIReferralService(ClientDao clientDao, ReferralClientDao referralClientDao) {
    super();
    this.clientDao = clientDao;
    this.referralClientDao = referralClientDao;
  }

  @Override
  public HOIReferralResponse handleFind(String clientId) {
    HOIReferralResponse hoiReferralResponse = new HOIReferralResponse();

    Client client = clientDao.find(clientId);
    if (client != null) {
      List<ReferralClient> referralClientList = fetchReferralClient(clientId);

      for (ReferralClient referralClient : referralClientList) {
        fetchEachReferral(hoiReferralResponse, referralClient);
      }

      return hoiReferralResponse;
    }
    return null;
  }

  private void fetchEachReferral(HOIReferralResponse hoiReferralResponse,
      ReferralClient referralClient) {
    Role role = null;
    Referral referral = referralClient.getReferral();

    StaffPerson staffPerson = referral.getStaffPerson();
    Reporter reporter = referral.getReporter();
    role = fetchForReporterRole(role, referral, referralClient, reporter);

    Map<Allegation, List<Client>> allegationMap = fetchForAllegation(referral);
    hoiReferralResponse
        .addHoiReferral(createHOIReferral(referral, staffPerson, reporter, allegationMap, role));
  }

  private List<ReferralClient> fetchReferralClient(String clientId) {
    ReferralClient[] referralClients = referralClientDao.findByClientId(clientId);
    return Arrays.asList(referralClients);
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
      clients.add(clientDao.find(allegation.getVictimClientId()));
    }
    if (allegation.getPerpetratorClientId() != null) {
      clients.add(clientDao.find(allegation.getPerpetratorClientId()));
    }
  }

  private HOIReferral createHOIReferral(Referral referral, StaffPerson staffPerson,
      Reporter reporter, Map<Allegation, List<Client>> allegationMap, Role role) {
    HOIReferral hoiReferral = new HOIReferral();
    hoiReferral.setId(referral.getId());
    hoiReferral.setStartDate(referral.getReceivedDate());
    hoiReferral.setEndDate(referral.getClosureDate());
    hoiReferral.setCounty(new SystemCodeDescriptor(referral.getGovtEntityType(),
        SystemCodeCache.global().getSystemCodeShortDescription(referral.getGovtEntityType())));
    hoiReferral.setResponseTime(
        new SystemCodeDescriptor(referral.getReferralResponseType(), SystemCodeCache.global()
            .getSystemCodeShortDescription(referral.getReferralResponseType())));
    buildAssignedSocialWorkerDomain(staffPerson);
    buildReporterDomain(reporter, role);
    buildAccessLimitationDomain(referral);
    List<HOIAllegation> hoiAllegatons = new ArrayList<>();
    for (Map.Entry<Allegation, List<Client>> allegation : allegationMap.entrySet()) {
      hoiAllegatons.add(buildAllegationDomain(allegation));
    }
    hoiReferral.setAllegations(hoiAllegatons);
    hoiReferral.setLegacyDescriptor(
        new LegacyDescriptor(referral.getId(), null, new DateTime(referral.getLastUpdatedTime()),
            LegacyTable.REFERRAL.getName(), LegacyTable.REFERRAL.getDescription()));
    return hoiReferral;
  }

  private void buildAccessLimitationDomain(Referral referral) {
    HOIReferral hoiReferral = new HOIReferral();
    hoiReferral.setAccessLimitation(new AccessLimitation(LimitedAccessType.NONE,
        referral.getLimitedAccessDate(), referral.getLimitedAccessDesc(),
        new SystemCodeDescriptor(referral.getLimitedAccessGovtAgencyType(), SystemCodeCache.global()
            .getSystemCodeShortDescription(referral.getLimitedAccessGovtAgencyType()))));
  }

  private HOIAllegation buildAllegationDomain(Map.Entry<Allegation, List<Client>> allegation) {
    HOIAllegation hoiAllegation = new HOIAllegation(allegation.getKey().getId(),
        new SystemCodeDescriptor(allegation.getKey().getAllegationType(),
            SystemCodeCache.global()
                .getSystemCodeShortDescription(allegation.getKey().getAllegationType())),
        new SystemCodeDescriptor(allegation.getKey().getAllegationDispositionType(),
            SystemCodeCache.global()
                .getSystemCodeShortDescription(allegation.getKey().getAllegationDispositionType())),
        null, null,
        new LegacyDescriptor(allegation.getKey().getId(), null,
            new DateTime(allegation.getKey().getLastUpdatedTime()),
            LegacyTable.ALLEGATION.getName(), LegacyTable.ALLEGATION.getDescription()));
    allegation.getValue()
        .forEach(eachclient -> buildClientsDomain(allegation, hoiAllegation, eachclient));
    return hoiAllegation;
  }

  private void buildClientsDomain(Map.Entry<Allegation, List<Client>> allegation,
      HOIAllegation hoiAllegation, Client eachclient) {

    if (eachclient.getId().equals(allegation.getKey().getVictimClientId())) {
      hoiAllegation.setVictim(
          new Victim(eachclient.getId(), eachclient.getFirstName(), eachclient.getLastName(),
              new LegacyDescriptor(eachclient.getId(), null,
                  new DateTime(eachclient.getLastUpdatedTime()), LegacyTable.CLIENT.getName(),
                  LegacyTable.CLIENT.getDescription())));
    } else {
      hoiAllegation.setPerpetrator(
          new Perpetrator(eachclient.getId(), eachclient.getFirstName(), eachclient.getLastName(),
              new LegacyDescriptor(eachclient.getId(), null,
                  new DateTime(eachclient.getLastUpdatedTime()), LegacyTable.CLIENT.getName(),
                  LegacyTable.CLIENT.getDescription())));
    }
  }

  private void buildReporterDomain(Reporter reporter, Role role) {
    HOIReferral hoiReferral = new HOIReferral();
    if (reporter != null) {
      hoiReferral.setReporter(new HOIReporter(role, reporter.getReferralId(),
          reporter.getFirstName(), reporter.getLastName(),
          new LegacyDescriptor(reporter.getReferralId(), null,
              new DateTime(reporter.getLastUpdatedTime()), LegacyTable.REPORTER.getName(),
              LegacyTable.REPORTER.getDescription())));
    } else {
      hoiReferral.setReporter(new HOIReporter(role, null, null, null, null));
    }
  }

  private void buildAssignedSocialWorkerDomain(StaffPerson staffPerson) {
    HOIReferral hoiReferral = new HOIReferral();
    hoiReferral.setAssignedSocialWorker(
        new SocialWorker(staffPerson.getId(), staffPerson.getFirstName(), staffPerson.getLastName(),
            new LegacyDescriptor(staffPerson.getId(), null,
                new DateTime(staffPerson.getLastUpdatedTime()), LegacyTable.STAFF_PERSON.getName(),
                LegacyTable.STAFF_PERSON.getDescription())));
  }


  @Override
  protected HOIReferralResponse handleRequest(HOIReferral req) {
    throw new NotImplementedException("handle request not implemented");
  }

}
