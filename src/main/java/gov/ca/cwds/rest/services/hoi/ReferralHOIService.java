package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import gov.ca.cwds.rest.api.domain.hoi.ReferralHOI;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * @author CWDS API Team
 *
 */
public class ReferralHOIService implements TypedCrudsService<String, ReferralHOI, ReferralHOI> {

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
  public ReferralHOI find(String primaryKey) {
    List<ReferralHOI> referralHOIs = new ArrayList<>();

    Set<Referral> referralSet = new HashSet<>();
    Client client = clientdao.find(primaryKey);
    ReferralClient[] referralClients = referralClientDao.findByClientId(primaryKey);
    for (int i = 0; i < referralClients.length; i++) {
      ReferralClient referralClient = referralClients[i];
      referralSet.add(referralClient.getReferral());
    }
    for (Referral referral : referralSet) {

      StaffPerson staffPerson = referral.getStaffPerson();
      Reporter reporter = referral.getReporters().iterator().next();
      Set<Allegation> allegations = referral.getAllegations();
      referralHOIs
          .add(new ReferralHOI(primaryKey, client, referral, staffPerson, reporter, allegations));

    }

    return referralHOIs.get(0);
  }

  @Override
  public ReferralHOI delete(String primaryKey) {
    throw new NotImplementedException("Delete is not implemented");
  }

  @Override
  public ReferralHOI create(ReferralHOI request) {
    throw new NotImplementedException("Create is not implemented");
  }

  @Override
  public ReferralHOI update(String primaryKey, ReferralHOI request) {
    throw new NotImplementedException("Update is not implemented");
  }

}
