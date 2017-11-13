package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import gov.ca.cwds.fixture.AssignmentEntityBuilder;

@SuppressWarnings("javadoc")
public class CaseAssignmentTest {
  private static final String FOLDED_KEY_CODE = "C";

  @Test
  public void testDefaultConstructor() throws Exception {
    CaseAssignment assignment = new CaseAssignment();
    assertThat(assignment.getEstablishedForCode(), is(equalTo(FOLDED_KEY_CODE)));
  }

  @Test
  public void testConstructor() throws Exception {
    Assignment domainAssignment = new AssignmentEntityBuilder().build();
    CaseAssignment caseAssignment = new CaseAssignment(domainAssignment.getCountySpecificCode(),
        domainAssignment.getEndDate(), domainAssignment.getEndTime(), domainAssignment.getId(),
        domainAssignment.getFkCaseLoad(), domainAssignment.getFkOutOfStateContactParty(),
        domainAssignment.getResponsibilityDescription(),
        domainAssignment.getSecondaryAssignmentRoleType(), domainAssignment.getStartDate(),
        domainAssignment.getStartTime(), domainAssignment.getTypeOfAssignmentCode(),
        domainAssignment.getWeightingNumber());
    assertThat(caseAssignment.getCountySpecificCode(),
        is(equalTo(domainAssignment.getCountySpecificCode())));
    assertThat(caseAssignment.getEndDate(), is(equalTo(domainAssignment.getEndDate())));
    assertThat(caseAssignment.getEndTime(), is(equalTo(domainAssignment.getEndTime())));
    assertThat(caseAssignment.getFkCaseLoad(), is(equalTo(domainAssignment.getFkCaseLoad())));
    assertThat(caseAssignment.getFkOutOfStateContactParty(),
        is(equalTo(domainAssignment.getFkOutOfStateContactParty())));
    assertThat(caseAssignment.getResponsibilityDescription(),
        is(equalTo(domainAssignment.getResponsibilityDescription())));
    assertThat(caseAssignment.getSecondaryAssignmentRoleType(),
        is(equalTo(domainAssignment.getSecondaryAssignmentRoleType())));
    assertThat(caseAssignment.getStartDate(), is(equalTo(domainAssignment.getStartDate())));
    assertThat(caseAssignment.getStartTime(), is(equalTo(domainAssignment.getStartTime())));
    assertThat(caseAssignment.getTypeOfAssignmentCode(),
        is(equalTo(domainAssignment.getTypeOfAssignmentCode())));
    assertThat(caseAssignment.getWeightingNumber(),
        is(equalTo(domainAssignment.getWeightingNumber())));
  }
}
