package gov.ca.cwds.rest.business.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gov.ca.cwds.data.cms.CaseDao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.business.RuleAction;
import gov.ca.cwds.rest.services.cms.ClientService;

public class R04466ClientSensitivityIndicator implements RuleAction {

  private ClientService clientService;
  private CaseDao caseDao;
  private ClientRelationshipDao clientRelationshipDao;
  private ReferralClientDao referralClientDao;
  private String clientId;

  public R04466ClientSensitivityIndicator(String clientId, ClientService clientService,
      CaseDao caseDao, ClientRelationshipDao clientRelationshipDao,
      ReferralClientDao referralClientDao) {
    this.clientId = clientId;
    this.clientService = clientService;
    this.caseDao = caseDao;
    this.clientRelationshipDao = clientRelationshipDao;
    this.referralClientDao = referralClientDao;
  }

  @Override
  public void execute() {
    Client persistentClient = clientService.find(clientId);
    if (persistentClient == null) {
      return;
    }

    //
    // If SOC158SealedClientIndicator is true the we can not change client's sensitivity
    //
    Boolean soc158SealedClientIndicator = persistentClient.getSoc158SealedClientIndicator();
    if (soc158SealedClientIndicator != null && soc158SealedClientIndicator) {
      return;
    }

    //
    // If client's sensitivity is already at highest level then don't change it.
    //
    String clientSensitivityIndicator = persistentClient.getSensitivityIndicator();
    if ("R".equals(clientSensitivityIndicator)) {
      return;
    }

    LimitedAccessType currentHighestLimitedAccessCode = LimitedAccessType.NONE;

    LimitedAccessType caseHighestLimitedAccessCode = findHighestCaseLimitedAccessCode(clientId);
    if (caseHighestLimitedAccessCode.getPriority() > currentHighestLimitedAccessCode
        .getPriority()) {
      currentHighestLimitedAccessCode = caseHighestLimitedAccessCode;
    }

    boolean isHighest = LimitedAccessType.isHighestPriority(currentHighestLimitedAccessCode);

    if (!isHighest) {
      //
      // Traverse related client cases
      //
      List<String> relatedClientIds = findRelatedClientIds(clientId);
      for (String relatedClientId : relatedClientIds) {
        caseHighestLimitedAccessCode = findHighestCaseLimitedAccessCode(relatedClientId);
        if (caseHighestLimitedAccessCode.getPriority() > currentHighestLimitedAccessCode
            .getPriority()) {
          currentHighestLimitedAccessCode = caseHighestLimitedAccessCode;
        }

        isHighest = LimitedAccessType.isHighestPriority(currentHighestLimitedAccessCode);
        if (isHighest) {
          break;
        }
      }
    }

    if (!isHighest) {
      //
      // Traverse referrals
      //
      List<ReferralClient> referralClientList = fetchReferralClients(clientId);
      for (ReferralClient referralClient : referralClientList) {
        Referral referral = referralClient.getReferral();
        LimitedAccessType referralHighestLimitedAccessCode =
            LimitedAccessType.valueOf(referral.getLimitedAccessCode());
        if (referralHighestLimitedAccessCode == null) {
          referralHighestLimitedAccessCode = LimitedAccessType.NONE;
        }

        if (referralHighestLimitedAccessCode.getPriority() > currentHighestLimitedAccessCode
            .getPriority()) {
          currentHighestLimitedAccessCode = referralHighestLimitedAccessCode;
        }

        isHighest = LimitedAccessType.isHighestPriority(currentHighestLimitedAccessCode);
        if (isHighest) {
          break;
        }
      }
    }

    //
    // Found highest limited access code, apply it to client
    //
    persistentClient.getSensitivityIndicator();
    clientService.update(clientId, persistentClient);
  }

  private LimitedAccessType findHighestCaseLimitedAccessCode(String clientId) {
    LimitedAccessType currentHighestLimitedAccessCode = LimitedAccessType.NONE;

    CmsCase[] cmsCases = caseDao.findByClientId(clientId);
    for (CmsCase cmsCase : cmsCases) {
      String limitedAccessCodeValue = cmsCase.getLimitedAccessCode();
      LimitedAccessType caseLimitedAccessCode =
          LimitedAccessType.getByValue(limitedAccessCodeValue);
      if (caseLimitedAccessCode != null) {
        caseLimitedAccessCode = LimitedAccessType.NONE;
      }

      if (caseLimitedAccessCode.getPriority() > currentHighestLimitedAccessCode.getPriority()) {
        currentHighestLimitedAccessCode = caseLimitedAccessCode;
      }

      if (LimitedAccessType.isHighestPriority(currentHighestLimitedAccessCode)) {
        break;
      }
    }

    return currentHighestLimitedAccessCode;
  }

  private List<String> findRelatedClientIds(String clientid) {
    List<String> clientIds = new ArrayList<>();
    ClientRelationship[] clientRelationshipsByPrimaryClient =
        clientRelationshipDao.findByPrimaryClientId(clientid);
    for (ClientRelationship relationship : clientRelationshipsByPrimaryClient) {
      clientIds.add(relationship.getSecondaryClientId());
    }

    ClientRelationship[] clientRelationshipBySecondaryClient =
        clientRelationshipDao.findBySecondaryClientId(clientid);
    for (ClientRelationship relation : clientRelationshipBySecondaryClient) {
      clientIds.add(relation.getPrimaryClientId());
    }
    return clientIds;
  }

  private List<ReferralClient> fetchReferralClients(String clientId) {
    ReferralClient[] referralClients = referralClientDao.findByClientId(clientId);
    return Arrays.asList(referralClients);
  }
}
