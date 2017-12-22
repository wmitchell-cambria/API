package gov.ca.cwds.rest.business.rules.doctool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.rest.business.rules.R04515ReferralAssignmentToCaseloadConstraint;

/**
 * @author CWDS API Team
 */
public class R04515ReferralAssignmentToCaseloadConstraintTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * <pre>
   * The Rule R - 04515 is implemented as part of R - 02473 , the corresponding class
   * R04515ReferralAssignmentToCaseloadConstraint is for tracking purposes only.
   * 
   * Hence the isValid() method should always return false
   * </pre>
   * 
   */
  @Test
  public void isValidReturnsFalse() throws Exception {
    R04515ReferralAssignmentToCaseloadConstraint r04515ReferralAssignmentToCaseloadConstraint =
        new R04515ReferralAssignmentToCaseloadConstraint();
    assertThat(r04515ReferralAssignmentToCaseloadConstraint.isValid(), is(equalTo(Boolean.FALSE)));
  }

}
