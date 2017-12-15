package gov.ca.cwds.rest.business.rules;

import org.apache.commons.lang3.StringUtils;
import gov.ca.cwds.rest.business.RuleValidatator;

/**
 * Implement R - 00785 Client Restriction - The alleged perpetrator and the alleged victim may not
 * be the same person for a given allegation.
 * 
 * @author CWDS API Team
 */
public class R00785AllegationClientRestriction implements RuleValidatator {
  private String victimClientId;
  private String perpetratorClientId;

  public R00785AllegationClientRestriction(String victimClientId, String perpetratorClientId) {
    this.victimClientId = victimClientId;
    this.perpetratorClientId = perpetratorClientId;

  }

  /**
   * return true if both values are NOT same, otherwise return false.
   */
  @Override
  public boolean isValid() {
    return !StringUtils.equals(this.victimClientId, this.perpetratorClientId);
  }

}
