package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.business.RuleValidator;

/**
 * 
 * <p>
 * BUSINESS RULE: "R - 03731 Start/End Time Setting/Default"
 * 
 * The combination of Start Date and Start Time must be less than or equal to the current system
 * date and time. If there is not already an existing Assignment Start Date and Start Time, and the
 * Assignment is for a Referral, default the Start Date and Start Time to the Referral Start_Date
 * and Start_Time. No Assignment can exist prior to the Referral Start Date/Time. The Start Date and
 * Time of the first Primary Assignment and the Referral Received Date and Time must be the same.
 * However, if the Assignment is for a Case default the Start Date and Time to the Case Start Date
 * and System Time. The Start Date of the first Primary Assignment and the Case Start Date must be
 * the same. These fields become read only after a save to database.
 * <p>
 * 
 * @author CWDS API Team
 */
public class R03731StartTimeSetting implements RuleValidator {
  private Referral referral;
  private Assignment assignment;

  /**
   * @param referral - referral
   * @param assignment - assignment
   */
  public R03731StartTimeSetting(Referral referral, Assignment assignment) {
    this.referral = referral;
    this.assignment = assignment;
  }


  @Override
  public boolean isValid() {
    // DRS: WARNING: default assignment could have null start date and time.
    return (referral.getReceivedDate().equals(assignment.getStartDate())
        && referral.getReceivedTime().equals(assignment.getStartTime()));
  }
}
