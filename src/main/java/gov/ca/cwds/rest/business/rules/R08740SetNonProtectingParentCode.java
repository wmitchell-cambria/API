package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.business.RuleAction;
import org.apache.commons.lang3.StringUtils;

/*
 * BUSINESS RULE: R - 08740
 *
 * Rule Text
 * If ‘Perpetrator’ is selected as a Perpetrator Type,
 * set Non Protecting Parent Code to ‘N.
 * If ‘Perpetrator-SCP/Res. Facility Staff’ is selected as a Perpetrator Type,
 * set Non Protecting Parent Code to ‘P.
 * If ‘Non-Protecting Parent’ is selected as a Perpetrator Type,
 * set Non Protecting Parent Code to ‘Y.
 * If ‘Perpetrator Not Identified’ is selected as a Perpetrator Type,
 * set Non Protecting Parent Code to ‘U’.
 *
 * Access Logic
 * If grdAllegations Perpetrator Type column = ‘Perpetrator’
 * set ALLEGATION.Non_Protecting_Parent_Code to 'N'.
 * If grdAllegations Perpetrator Type column = ‘Perpetrator-SCP/Res. Facility Staff’
 * set ALLEGATION.Non_Protecting_Parent_Code to 'P'.
 * If grdAllegations Perpetrator Type column = ‘Non-Protecting Parent’
 * set ALLEGATION.Non_Protecting_Parent_Code to 'Y'.
 * If grdAllegations Perpetrator Type column = ‘Perpetrator Not Identified’
 * set ALLEGATION.Non_Protecting_Parent_Code to 'U'.
 *
 */
public class R08740SetNonProtectingParentCode implements RuleAction {

  private ClientRelationshipDao clientRelationshipDao;
  private String victimClientId;
  private String perpatratorClientId;
  private Allegation cmsAllegation;

  public R08740SetNonProtectingParentCode(
      gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation,
      ClientRelationshipDao clientRelationshipDao,
      String victimClientId,
      String perpatratorClientId) {
    this.cmsAllegation = cmsAllegation;
    this.clientRelationshipDao = clientRelationshipDao;
    this.victimClientId = victimClientId;
    this.perpatratorClientId = perpatratorClientId;
  }

  @Override
  public void execute() {
    String nonProtectingParentCode;
    if (StringUtils.isEmpty(perpatratorClientId)) {
      nonProtectingParentCode = "U";
    } else {
      nonProtectingParentCode = determineNonProtectingParentCode();
    }
    cmsAllegation.update(nonProtectingParentCode);
  }

  private String determineNonProtectingParentCode() {
    if (isResFacilityStaff()) {
      return  "P";
    } else {
      return isParent() ? "Y" : "N";
    }
  }

  private boolean isResFacilityStaff() {
    ClientRelationship[] clientRelationships =
        clientRelationshipDao.findByPrimaryClientId(victimClientId);
    for (ClientRelationship relationship : clientRelationships) {
      Short type = relationship.getClientRelationshipType();
      if (relationship.getSecondaryClientId().equals(perpatratorClientId) && type == 5993) {
        return true;
      }
    }
    clientRelationships =
            clientRelationshipDao.findBySecondaryClientId(victimClientId);
    for (ClientRelationship relationship : clientRelationships) {
      Short type = relationship.getClientRelationshipType();
      if (relationship.getPrimaryClientId().equals(perpatratorClientId) && type == 5994) {
        return true;
      }
    }
    return false;
  }

  private boolean isParent() {
    ClientRelationship[] clientRelationships =
        clientRelationshipDao.findByPrimaryClientId(victimClientId);
    for (ClientRelationship relationship : clientRelationships) {
      Short type = relationship.getClientRelationshipType();
      if (relationship.getSecondaryClientId().equals(perpatratorClientId)
          && isRelationTypeParentByPrimaryId(type)) {
        return true;
      }
    }
    clientRelationships =
            clientRelationshipDao.findBySecondaryClientId(victimClientId);
    for (ClientRelationship relationship : clientRelationships) {
      Short type = relationship.getClientRelationshipType();
      if (relationship.getPrimaryClientId().equals(perpatratorClientId)
              && isRelationTypeParentBySecondaryId(type)) {
        return true;
      }
    }
    return false;
  }

  private boolean isRelationTypeParentByPrimaryId(Short type) {
    boolean firstCondition = type <= 200 && type >= 187;
    boolean secondCondition = type <= 294 && type >= 282;
    boolean lastCondition = type == 6360;
    return firstCondition || secondCondition || lastCondition;
  }

  private boolean isRelationTypeParentBySecondaryId(Short type) {
    boolean firstCondition = type <= 214 && type >= 201;
    boolean secondCondition = type <= 254 && type >= 245;
    boolean lastCondition = type == 272 || type == 273 || type == 5620 || type == 6361;
    return firstCondition || secondCondition || lastCondition;
  }
}
