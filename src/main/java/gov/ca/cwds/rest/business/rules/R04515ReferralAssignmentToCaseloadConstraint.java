package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.rest.business.RuleValidator;

/**
 * <p>
 * BUSINESS RULE: "R - 04515"
 * 
 * New Case/Referral Assignments can only be made to active caseloads that are not on hold.
 * 
 * This Rule is implemented as part of R - 02473 , this class is for tracking purposes only.
 * 
 * <p>
 * 
 * @author CWS-NS2
 * 
 * @see Assignment
 *
 */
public class R04515ReferralAssignmentToCaseloadConstraint implements RuleValidator {

  public R04515ReferralAssignmentToCaseloadConstraint() {
    super();
  }

  @Override
  public boolean isValid() {
    // This Rule is implemented as part of R - 02473
    return false;
  }


}
