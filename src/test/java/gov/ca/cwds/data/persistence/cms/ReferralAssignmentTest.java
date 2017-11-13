package gov.ca.cwds.data.persistence.cms;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import gov.ca.cwds.fixture.AssignmentEntityBuilder;

@SuppressWarnings("javadoc")
public class ReferralAssignmentTest {
  private static final String FOLDED_KEY_CODE = "R";

  @Test
  public void testDefaultConstructor() throws Exception {
    ReferralAssignment referralAssignment = new ReferralAssignment();
    assertThat(referralAssignment.getEstablishedForCode(), is(equalTo(FOLDED_KEY_CODE)));
  }

  @Test
  public void testConstructor() throws Exception {
    Assignment assignment = new AssignmentEntityBuilder().build();
    ReferralAssignment ra = new ReferralAssignment(assignment.getCountySpecificCode(),
        assignment.getEndDate(), assignment.getEndTime(), assignment.getEstablishedForId(),
        assignment.getFkCaseLoad(), assignment.getFkOutOfStateContactParty(),
        assignment.getResponsibilityDescription(), assignment.getSecondaryAssignmentRoleType(),
        assignment.getStartDate(), assignment.getStartTime(), assignment.getTypeOfAssignmentCode(),
        assignment.getWeightingNumber());
    assertThat(ra.getCountySpecificCode(), is(equalTo(assignment.getCountySpecificCode())));
    assertThat(ra.getEndDate(), is(equalTo(assignment.getEndDate())));
    assertThat(ra.getEndTime(), is(equalTo(assignment.getEndTime())));
    assertThat(ra.getEstablishedForId(), is(equalTo(assignment.getEstablishedForId())));
    assertThat(ra.getFkCaseLoad(), is(equalTo(assignment.getFkCaseLoad())));
    assertThat(ra.getFkOutOfStateContactParty(),
        is(equalTo(assignment.getFkOutOfStateContactParty())));
    assertThat(ra.getResponsibilityDescription(),
        is(equalTo(assignment.getResponsibilityDescription())));
    assertThat(ra.getSecondaryAssignmentRoleType(),
        is(equalTo(assignment.getSecondaryAssignmentRoleType())));
    assertThat(ra.getStartDate(), is(equalTo(assignment.getStartDate())));
    assertThat(ra.getStartTime(), is(equalTo(assignment.getStartTime())));
    assertThat(ra.getTypeOfAssignmentCode(), is(equalTo(assignment.getTypeOfAssignmentCode())));
    assertThat(ra.getWeightingNumber(), is(equalTo(assignment.getWeightingNumber())));
  }
}
