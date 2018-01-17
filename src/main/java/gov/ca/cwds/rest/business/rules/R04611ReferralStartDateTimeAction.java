package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.business.RuleAction;
import gov.ca.cwds.rest.services.cms.AssignmentService;

/**
 * R - 04611 - Maximum Referral Start Date/Time A change to the Referral Start Date and Time will
 * automatically reset the Start Date and Time of the first Assignment and the Referral Start Date
 * and Time can not exceed the End Date and can not equal or exceed End Time of the first assignment
 * if it exists.
 *
 * @author CWDS API Team
 */
public class R04611ReferralStartDateTimeAction implements RuleAction {

  private AssignmentService assignmentService;

  private Referral domainReferral;

  private Assignment firstAssignment;

  public R04611ReferralStartDateTimeAction(AssignmentService assignmentService,
      Referral domainReferral, Assignment firstAssignment) {
    this.assignmentService = assignmentService;
    this.domainReferral = domainReferral;
    this.firstAssignment = firstAssignment;
  }

  @Override
  public void execute() {
    if (firstAssignment != null) {
      firstAssignment.setStartDate(DomainChef.uncookDateString(domainReferral.getReceivedDate()));
      firstAssignment.setStartTime(DomainChef.uncookTimeString(domainReferral.getReceivedTime()));
      assignmentService.update(firstAssignment.getId(),
          new gov.ca.cwds.rest.api.domain.cms.Assignment(firstAssignment));
    }
  }
}
