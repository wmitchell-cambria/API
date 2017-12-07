package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * @author CWDS API Team
 *
 */
public class ReferralHOIService implements TypedCrudsService<String, HOIReferral, Response> {

  private ClientDao clientdao;
  private ReferralClientDao referralClientDao;

  /**
   * @param clientdao
   * @param referralClientDao
   */
  @Inject
  public ReferralHOIService(ClientDao clientdao, ReferralClientDao referralClientDao) {
    super();
    this.clientdao = clientdao;
    this.referralClientDao = referralClientDao;
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(ReferralHOIService.class);

  @Override
  public Response find(String primaryKey) {
    List<HOIReferral> referralHOIs = new ArrayList<>();

    Map<Referral, ReferralClient> referralSet = new HashMap<>();
    Client client = clientdao.find(primaryKey);
    fetchReferralClient(primaryKey, referralSet);

    for (Map.Entry<Referral, ReferralClient> pair : referralSet.entrySet()) {
      Role reporterRole = null;
      Referral referral = pair.getKey();
      ReferralClient referralClient = pair.getValue();
      StaffPerson staffPerson = referral.getStaffPerson();

      Reporter reporter = referral.getReporters();
      reporterRole = fetchForReporterRole(reporterRole, referral, referralClient, reporter);
      Map<Allegation, List<Client>> allegationMap = fetchForAllegation(referral);
      referralHOIs.add(new HOIReferral(primaryKey, client, referral, staffPerson, reporter,
          allegationMap, reporterRole));
    }

    return referralHOIs.get(0);
  }

  private void fetchReferralClient(String primaryKey, Map<Referral, ReferralClient> referralSet) {
    ReferralClient[] referralClients = referralClientDao.findByClientId(primaryKey);
    for (int i = 0; i < referralClients.length; i++) {
      ReferralClient referralClient = referralClients[i];
      referralSet.put(referralClient.getReferral(), referralClient);
    }
  }

  private Role fetchForReporterRole(Role reporterRole, Referral referral,
      ReferralClient referralClient, Reporter reporter) {
    if (reporter == null) {
      if ("Y".equals(referral.getAnonymousReporterIndicator())) {
        reporterRole = Role.ANONYMOUS_REPORTER;
      } else if ("Y".equals(referralClient.getSelfReportedIndicator())) {
        reporterRole = Role.SELF_REPORTER;
      }
    } else {
      if ("Y".equals(reporter.getMandatedReporterIndicator())) {
        reporterRole = Role.MANDATED_REPORTER;
      } else {
        reporterRole = Role.NON_MANDATED_REPORTER;
      }
    }
    return reporterRole;
  }

  private Map<Allegation, List<Client>> fetchForAllegation(Referral referral) {
    Set<Allegation> allegations = referral.getAllegations();
    Map<Allegation, List<Client>> allegationMap = new HashMap<>();
    for (Allegation allegation : allegations) {
      List<Client> clients = new ArrayList<>();
      if (allegation.getVictimClientId() != null) {
        clients.add(clientdao.find(allegation.getVictimClientId()));
      }
      if (allegation.getPerpetratorClientId() != null) {
        clients.add(clientdao.find(allegation.getPerpetratorClientId()));
      }

      allegationMap.put(allegation, clients);

    }
    return allegationMap;
  }

  @Override
  public Response delete(String primaryKey) {
    return null;
  }

  @Override
  public Response create(HOIReferral request) {
    return null;
  }

  @Override
  public Response update(String primaryKey, HOIReferral request) {
    return null;
  }

}
