package gov.ca.cwds.rest.business.rules.doctool;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.business.rules.R00832SetStaffPersonAddedInd;

/**
 * @author CWDS API Team
 * 
 * @see R00832SetStaffPersonAddedInd
 *
 */
public class R00832SetStaffPersonAddedIndTest {

  /**
   * Test when referral response time is set then the staffPersonAddedInd to true
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testPassIsValidReturnTrue() throws Exception {
    ScreeningToReferral validScreeningToreferral = new ScreeningToReferralResourceBuilder()
        .setResponseTime((short) 1516).createScreeningToReferral();
    R00832SetStaffPersonAddedInd r00832SetStaffPersonAddedInd =
        new R00832SetStaffPersonAddedInd(validScreeningToreferral);
    assertEquals(true, r00832SetStaffPersonAddedInd.isValid());
  }

  @Test
  public void shouldNotPassIfIsValidReturnFalse() throws Exception {
    ScreeningToReferral validScreeningToreferral = new ScreeningToReferralResourceBuilder()
        .setResponseTime(null).createScreeningToReferral();
    R00832SetStaffPersonAddedInd r00832SetStaffPersonAddedInd =
        new R00832SetStaffPersonAddedInd(validScreeningToreferral);
    assertEquals(false, r00832SetStaffPersonAddedInd.isValid());
  }
}
