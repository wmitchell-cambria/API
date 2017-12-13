package gov.ca.cwds.rest.business.rules.doctool;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import org.joda.time.DateTime;
import org.junit.Test;
import gov.ca.cwds.fixture.AssignmentResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.business.rules.R04530AssignmentEndDateValidator;

public class R04530AssignmentEndDateValidatorTest {

  @Test
  public void testValidAssignmentStartAndEndDate() {
    String startDate = "2017-06-20";
    String startTime = "16:41:49-0800";
    String endDate = "2017-12-01";
    String endTime = "12:01:00-0800";
    Assignment assignment = new AssignmentResourceBuilder().setStartDate(startDate)
        .setStartTime(startTime).setEndDate(endDate).setEndTime(endTime).buildAssignment();
    Boolean actual = new R04530AssignmentEndDateValidator(assignment).isValid();
    assertEquals(actual, true);
  }

  /**
   * assignment state/end dates both are same and end time is after start time
   */
  @Test
  public void testValidAssignmentEndDateWhichIsAfterEndDateTime() {
    String startDate = "2017-06-20";
    String startTime = "16:41:49-0800";
    String endDate = "2017-06-20";
    String endTime = "16:42:49-0800";
    Assignment assignment = new AssignmentResourceBuilder().setStartDate(startDate)
        .setStartTime(startTime).setEndDate(endDate).setEndTime(endTime).buildAssignment();

    Boolean actual = new R04530AssignmentEndDateValidator(assignment).isValid();
    assertEquals(actual, true);
  }

  @Test
  public void testInValidAssignmentEndDateWhichIsAfterStartDate() {
    String startDate = "2017-07-20";
    String startTime = "16:41:49-0800";
    String endDate = "2017-06-26";
    String endTime = "12:01:00-0800";
    Assignment assignment = new AssignmentResourceBuilder().setStartDate(startDate)
        .setStartTime(startTime).setEndDate(endDate).setEndTime(endTime).buildAssignment();

    Boolean actual = new R04530AssignmentEndDateValidator(assignment).isValid();
    assertEquals(actual, false);
  }

  @Test
  public void testInValidAssignmentStartDateWhichIsAfterEndTime() {
    String startDate = "2017-11-27";
    String startTime = "16:41:49-0800";
    String endDate = "2017-11-27";
    String endTime = "12:01:00-0800";
    Assignment assignment = new AssignmentResourceBuilder().setStartDate(startDate)
        .setStartTime(startTime).setEndDate(endDate).setEndTime(endTime).buildAssignment();

    Boolean actual = new R04530AssignmentEndDateValidator(assignment).isValid();
    assertEquals(actual, false);

  }

  @Test
  public void testInValidateEndDateAfterCurrentDateTime() {
    DateTime currentDateTine = new DateTime();
    Date adjustedDate = currentDateTine.minusSeconds(3).toDate();
    String startDate = DomainChef.cookDate(adjustedDate);
    String startTime = DomainChef.cookTime(adjustedDate);
    String endDate = DomainChef.cookDate(adjustedDate);
    String endTime = DomainChef.cookTime(adjustedDate);
    Assignment assignment = new AssignmentResourceBuilder().setStartDate(startDate)
        .setStartTime(startTime).setEndDate(endDate).setEndTime(endTime).buildAssignment();

    Boolean actual = new R04530AssignmentEndDateValidator(assignment).isValid();
    assertEquals(actual, true);
  }

}
