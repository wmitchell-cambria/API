package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.ReferralAssignment;
import gov.ca.cwds.rest.business.RuleValidator;

/**
 * CWDS API Team<br>
 * <p>
 * R - 06560 Caseload Required For First Primary Asg<br>
 * <p>
 * Rule Text<br>
 * The first primary assignment for a Zippy Referral must have an associated caseload defined before saving to the database.<br>
 * <p>
 * Access Logic<br>
 * Before saving to the database, Count [REFERRAL &gt; ASSIGNMENT.Type_Of_Assignment_Code = 'P' (first created) and FKCASE_LDT is null] must not exist.<br>
 */
public class R06560CaseloadRequiredForFirstPrimaryAssignment implements RuleValidator {
  private Assignment assignment;

  public R06560CaseloadRequiredForFirstPrimaryAssignment(Assignment assignment) {
    this.assignment = assignment;
  }

  @Override
  public boolean isValid() {
    return !(ReferralAssignment.FOLDED_KEY_CODE.equals(assignment.getEstablishedForCode()) && "P"
        .equals(assignment.getTypeOfAssignmentCode()) && assignment.getFkCaseLoad() == null);
  }
}
