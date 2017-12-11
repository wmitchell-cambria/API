package gov.ca.cwds.rest.business.rules;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gov.ca.cwds.fixture.AssignmentResourceBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.api.domain.cms.Referral;

@SuppressWarnings("javadoc")
public class R03731StartTimeSettingTest {

  @Test
  public void isValidWhenStartDateTimeAreEqual() throws Exception {
    Assignment assignment = new AssignmentResourceBuilder().setStartDate("2017-12-08")
        .setStartTime("16:01:01").buildAssignment();
    Referral referral = new ReferralResourceBuilder().setReceivedDate("2017-12-08")
        .setReceivedTime("16:01:01").build();
    assertEquals(new R03731StartTimeSetting(referral, assignment).isValid(), Boolean.TRUE);
  }

  @Test
  public void isNotValidWhenStartDateNotEqual() throws Exception {
    Assignment assignment = new AssignmentResourceBuilder().setStartDate("2017-12-08")
        .setStartTime("16:01:01").buildAssignment();
    Referral referral = new ReferralResourceBuilder().setReceivedDate("2016-12-08")
        .setReceivedTime("16:01:01").build();
    assertEquals(new R03731StartTimeSetting(referral, assignment).isValid(), Boolean.FALSE);
  }

  @Test
  public void isNotValidWhenStartTimeNotEqual() throws Exception {
    Assignment assignment = new AssignmentResourceBuilder().setStartDate("2017-12-08")
        .setStartTime("15:01:01").buildAssignment();
    Referral referral = new ReferralResourceBuilder().setReceivedDate("2017-12-08")
        .setReceivedTime("16:01:01").build();
    assertEquals(new R03731StartTimeSetting(referral, assignment).isValid(), Boolean.FALSE);
  }

  @Test
  public void isNotValidWhenStartDateTimeNotEqual() throws Exception {
    Assignment assignment = new AssignmentResourceBuilder().setStartDate("2017-12-08")
        .setStartTime("15:01:01").buildAssignment();
    Referral referral = new ReferralResourceBuilder().setReceivedDate("2016-12-08")
        .setReceivedTime("16:01:01").build();
    assertEquals(new R03731StartTimeSetting(referral, assignment).isValid(), Boolean.FALSE);
  }
}
