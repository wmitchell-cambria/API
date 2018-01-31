package gov.ca.cwds.rest.business.rules;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.data.cms.CaseDao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.business.RuleAction;

/**
 * Apply Doctool rule R - 04466: Set client sensitivity indicator
 * 
 * @author CWDS API Team
 *
 */
public class R04466ClientSensitivityIndicator implements RuleAction {

  private CaseDao caseDao;
  private ClientRelationshipDao clientRelationshipDao;
  private ReferralClientDao referralClientDao;
  private Client client;
  private LimitedAccessType screeningToReferralLimmitedAccessCode;

  /**
   * Constructor
   * 
   * @param client The client that must be updated
   * @param screeningToReferralLimmitedAccessCode The current limited access code requested with
   *        referral
   * @param caseDao Case DAO
   * @param clientRelationshipDao Client relationship DAO
   * @param referralClientDao Referral client DAO
   */
  public R04466ClientSensitivityIndicator(Client client,
      LimitedAccessType screeningToReferralLimmitedAccessCode, CaseDao caseDao,
      ClientRelationshipDao clientRelationshipDao, ReferralClientDao referralClientDao) {
    this.client = client;
    this.screeningToReferralLimmitedAccessCode = screeningToReferralLimmitedAccessCode;
    this.caseDao = caseDao;
    this.clientRelationshipDao = clientRelationshipDao;
    this.referralClientDao = referralClientDao;
  }

  @Override
  public void execute() {
    /*
     * If SOC158SealedClientIndicator is true the we can not change client's sensitivity
     */
    Boolean soc158SealedClientIndicator = client.getSoc158SealedClientIndicator();
    if (soc158SealedClientIndicator != null && soc158SealedClientIndicator) {
      return;
    }

    LimitedAccessType currentHighestLimitedAccessCode =
        screeningToReferralLimmitedAccessCode == null ? LimitedAccessType.NONE
            : screeningToReferralLimmitedAccessCode;

    /*
     * Apply current highest limited access code
     */
    client.applySensitivityIndicator(currentHighestLimitedAccessCode);

    String clientId = client.getExistingClientId();

    /*
     * Is it a new client (does not exist in db)
     */
    if (StringUtils.isBlank(clientId)) {
      client.applySensitivityIndicator(currentHighestLimitedAccessCode);
      return;
    }

    /*
     * Find highest limited access code from cases
     */
    currentHighestLimitedAccessCode = findHighestCaseLimitedAccessCode(clientId);

    /*
     * If limited access code determined from cases is not the highest then traverse referrals
     */
    if (!LimitedAccessType.isHighestPriority(currentHighestLimitedAccessCode)) {
      LimitedAccessType referralLimitedAccessCode = findHighestReferralLimitedAccessCode(clientId);
      if (referralLimitedAccessCode.getPriority() > currentHighestLimitedAccessCode.getPriority()) {
        currentHighestLimitedAccessCode = referralLimitedAccessCode;
      }
    }

    /*
     * Found highest limited access code, apply it to client
     */
    client.applySensitivityIndicator(currentHighestLimitedAccessCode);
  }

  private LimitedAccessType findHighestCaseLimitedAccessCode(String clientId) {
    LimitedAccessType currentHighestLimitedAccessCode = LimitedAccessType.NONE;
    // List<String> clientIds = findAllRelatedClientIds(clientId);

    CmsCase[] cmsCases = caseDao.findAllRelatedByVictimClientId(clientId);
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

  private LimitedAccessType findHighestReferralLimitedAccessCode(String clientId) {
    LimitedAccessType currentHighestLimitedAccessCode = LimitedAccessType.NONE;
    List<String> clientIds = new ArrayList<>();
    clientIds.add(clientId);
    ReferralClient[] referralClients = referralClientDao.findByClientIds(clientIds);

    for (ReferralClient referralClient : referralClients) {
      Referral referral = referralClient.getReferral();
      LimitedAccessType referralLimitedAccessCode =
          LimitedAccessType.getByValue(referral.getLimitedAccessCode());
      if (referralLimitedAccessCode == null) {
        referralLimitedAccessCode = LimitedAccessType.NONE;
      }

      if (referralLimitedAccessCode.getPriority() > currentHighestLimitedAccessCode.getPriority()) {
        currentHighestLimitedAccessCode = referralLimitedAccessCode;
      }

      if (LimitedAccessType.isHighestPriority(currentHighestLimitedAccessCode)) {
        break;
      }
    }

    return currentHighestLimitedAccessCode;
  }

  // private List<String> findAllRelatedClientIds(String clientId) {
  // List<String> clientIds = new ArrayList<>();
  // clientIds.add(clientId);
  //
  // ClientRelationship[] clientRelationshipsByPrimaryClient =
  // clientRelationshipDao.findByPrimaryClientId(clientId);
  // for (ClientRelationship relationship : clientRelationshipsByPrimaryClient) {
  // clientIds.add(relationship.getSecondaryClientId());
  // }
  //
  // ClientRelationship[] clientRelationshipBySecondaryClient =
  // clientRelationshipDao.findBySecondaryClientId(clientId);
  // for (ClientRelationship relation : clientRelationshipBySecondaryClient) {
  // clientIds.add(relation.getPrimaryClientId());
  // }
  // return clientIds;
  // }
}
