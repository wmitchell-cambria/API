package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.persistence.cms.*;
import gov.ca.cwds.fixture.*;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * CWDS API Team
 */
public class R06560CaseloadRequiredForFirstPrimaryAssignmentTest {
  @Test
  public void testPrimaryReferralAssignment() {
    Assignment assignment = createValidAssignment();
    assertTrue(new R06560CaseloadRequiredForFirstPrimaryAssignment(assignment).isValid());
  }

  @Test
  public void testNullFkCaseLoad() {
    Assignment assignment = createNullFkCaseLoadAssignment();
    assertFalse(new R06560CaseloadRequiredForFirstPrimaryAssignment(assignment).isValid());
  }

  @Test
  public void testNotReferralAssignment() {
    Assignment assignment = createNotReferralAssignment();
    assertTrue(new R06560CaseloadRequiredForFirstPrimaryAssignment(assignment).isValid());
  }

  @Test
  public void testNotPrimaryAssignment() {
    Assignment assignment = createNotPrimaryAssignment();
    assertTrue(new R06560CaseloadRequiredForFirstPrimaryAssignment(assignment).isValid());
  }

  private Assignment createValidAssignment() {
    return new AssignmentEntityBuilder().
        setEstablishedForCode("R").
        setTypeOfAssignmentCode("P").
        setFkCaseLoad("-1").build();
  }

  private Assignment createNullFkCaseLoadAssignment() {
    return new AssignmentEntityBuilder().
        setEstablishedForCode("R").
        setTypeOfAssignmentCode("P").
        setFkCaseLoad(null).build();
  }

  private Assignment createNotReferralAssignment() {
    return new AssignmentEntityBuilder().
        setEstablishedForCode("C").
        setTypeOfAssignmentCode("P").
        setFkCaseLoad(null).build();
  }

  private Assignment createNotPrimaryAssignment() {
    return new AssignmentEntityBuilder().
        setEstablishedForCode("R").
        setTypeOfAssignmentCode("N").
        setFkCaseLoad(null).build();
  }
}