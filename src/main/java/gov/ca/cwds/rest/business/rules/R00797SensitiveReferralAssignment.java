package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.rest.business.RuleValidator;

/**
 * <p>
 * BUSINESS RULE: "R - 00797" Sensitive Case Assignment
 * 
 * A Sealed or Sensitive CASE or REFERRAL may be assigned to any Staff Person, whether they have
 * sealed or sensitive authority or not.
 * 
 * This class is for tracking purposes only. We are not checking for sealed or sensitive authority
 * of a Staff Person when assigning a CASE or REFERRAL
 * 
 * Note that that when Authorization Rules are implemented, there should not be an authorization
 * change that impacts the case worker assignments for sealed and sensitive.
 * 
 * <p>
 * 
 * @author CWDS API Team
 *
 */
public class R00797SensitiveReferralAssignment implements RuleValidator {

  public R00797SensitiveReferralAssignment() {
    super();
  }

  @Override
  public boolean isValid() {
    return true;
  }

}
