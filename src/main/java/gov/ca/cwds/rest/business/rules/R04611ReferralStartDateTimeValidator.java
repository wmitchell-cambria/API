package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.business.RuleValidator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * R - 04611 - Maximum Referral Start Date/Time 
 * Referral Start Date and Time can not exceed the End Date and can not equal or exceed End Time of the first assignment
 * if it exists.
 *
 * @author CWDS API Team
 */
public class R04611ReferralStartDateTimeValidator implements RuleValidator {

  private DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
  private DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

  private Referral domainReferral;

  private Assignment firstAssignment;

  public R04611ReferralStartDateTimeValidator(Referral domainReferral, Assignment firstAssignment) {
    this.domainReferral = domainReferral;
    this.firstAssignment = firstAssignment;
  }

  @Override
  public boolean isValid() {
    if (firstAssignment == null) {
      return true;
    }
    if (firstAssignment.getEndDate() == null || firstAssignment.getEndTime() == null) {
    	  return true;
    }

    String firstAssignmentEndDate = dateFormatter.format(firstAssignment.getEndDate());
    String firstAssignmentEndTime = timeFormatter.format(firstAssignment.getEndTime());

    if (domainReferral.getReceivedDate().compareTo(firstAssignmentEndDate) < 0) {
      return true;
    }
    
    return domainReferral.getReceivedDate().equals(firstAssignmentEndDate)
        && domainReferral.getReceivedTime().compareTo(firstAssignmentEndTime) < 0;
  }
}
