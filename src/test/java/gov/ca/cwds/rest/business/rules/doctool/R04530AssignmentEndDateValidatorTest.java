package gov.ca.cwds.rest.business.rules.doctool;

import static org.junit.Assert.assertEquals;
import java.util.Date;
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
    String endDate = "2018-06-01";
    String endTime = "12:01:00-0800";
    Assignment assignment = new AssignmentResourceBuilder().setStartDate(startDate)
        .setStartTime(startTime).setEndDate(endDate).setEndTime(endTime).buildAssignment();
    Boolean actual = new R04530AssignmentEndDateValidator(assignment).isValid();
    assertEquals(actual, true);
  }

  @Test
  public void testInValidAssignmentEndDateWhichIsBeforeCurrentDate() {
    String startDate = "2017-06-20";
    String startTime = "16:41:49-0800";
    String endDate = "2017-06-26";
    String endTime = "12:01:00-0800";
    Assignment assignment = new AssignmentResourceBuilder().setStartDate(startDate)
        .setStartTime(startTime).setEndDate(endDate).setEndTime(endTime).buildAssignment();

    Boolean actual = new R04530AssignmentEndDateValidator(assignment).isValid();
    assertEquals(actual, false);
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
    Date d = new Date();
    String startDate = DomainChef.cookDate(d);
    String startTime = DomainChef.cookTime(d);
    String endDate = DomainChef.cookDate(d);
    String endTime = DomainChef.cookTime(d);
    Assignment assignment = new AssignmentResourceBuilder().setStartDate(startDate)
        .setStartTime(startTime).setEndDate(endDate).setEndTime(endTime).buildAssignment();

    Boolean actual = new R04530AssignmentEndDateValidator(assignment).isValid();
    assertEquals(actual, false);
  }

}
