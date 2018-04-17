package gov.ca.cwds.api.hoi;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.api.builder.FunctionalTestingBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;

public class HoiReferralsForSocialWorkerTest extends FunctionalTest {

  String hoiReferrals;
  String referralPath;
  private FunctionalTestingBuilder functionalTestingBuilder;

  /**
   * 
   */
  @Before
  public void setup() {
    referralPath = getResourceUrlFor("/referrals");
    hoiReferrals = getResourceUrlFor("/hoi_referrals");
    functionalTestingBuilder = new FunctionalTestingBuilder();
  }

  @Test
  public void test() {
    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setAssigneeStaffId(userInfo.getStaffId())
            .setIncidentCounty(userInfo.getIncidentCounty()).createScreeningToReferral();
    functionalTestingBuilder.processPostRequest(referral, referralPath, token);
  }

}
