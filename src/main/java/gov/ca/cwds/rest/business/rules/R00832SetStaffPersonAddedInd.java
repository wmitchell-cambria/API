package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.business.RuleValidator;

/**
 * <p>
 * BUSINESS RULE: "R - 00832"
 * 
 * If REFERRAL. Referral Response Type not blank Then set REFERRAL.REFERRAL CLIENT. Staff Person
 * Added Ind = 'Y'.
 * <p>
 * 
 * @author CWDS API Team
 *
 */
public class R00832SetStaffPersonAddedInd implements RuleValidator {

  private ScreeningToReferral screeningToReferral;

  /**
   * @param screeningToReferral - screeningToReferral
   */
  public R00832SetStaffPersonAddedInd(ScreeningToReferral screeningToReferral) {
    super();
    this.screeningToReferral = screeningToReferral;
  }

  @Override
  public boolean isValid() {
    boolean staffPersonAddedIndicator = false;
    if (screeningToReferral.getResponseTime() != null) {
      return true;
    }
    return staffPersonAddedIndicator;
  }

}
