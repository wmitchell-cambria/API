package gov.ca.cwds.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.api.builder.FunctionalTestingBuilder;
import gov.ca.cwds.api.builder.ResourceEndPoint;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;

/**
 * @author CWDS API Team
 *
 */
public class ScreeningToReferralTest extends FunctionalTest {
  String referralPath;
  private FunctionalTestingBuilder functionalTestingBuilder;

  /**
   * 
   */
  @Before
  public void setup() {
    referralPath = getResourceUrlFor(ResourceEndPoint.REFERRALS.getResourcePath());
    functionalTestingBuilder = new FunctionalTestingBuilder();
  }

  /**
   * 
   */
  @Test
  public void returnErrorWhenNoHeaderIsProvided() {
    given().queryParam("token", token).when().post(referralPath).then().statusCode(500);
  }

  /**
   * 
   */
  @Test
  public void return500ErrorWhenIncorrectStaffPerson() {
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setAssigneeStaffId(userInfo.getStaffId()).createScreeningToReferral();

    functionalTestingBuilder.processPostRequest(referral, referralPath, token).then()
        .body("issue_details[0].technical_message",
            equalTo("Assigned Staff Person County must be the same as the Incident County"))
        .and().statusCode(500);
  }

  /**
   * 
   */
  @Test
  public void return500ErrorWhenNoVictimIsPresent() {
    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setParticipants(null).createScreeningToReferral();

    functionalTestingBuilder.processPostRequest(referral, referralPath, token).then()
        .body("issue_details.user_message",
            hasItem("must contain at least one victim, only one reporter, and compatible roles"))
        .and().statusCode(422);
  }

  /**
   * 
   */
  @Test
  public void return201SuccessForValidReferrals() {
    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setAssigneeStaffId(userInfo.getStaffId())
            .setIncidentCounty(userInfo.getIncidentCounty()).createScreeningToReferral();

    functionalTestingBuilder.processPostRequest(referral, referralPath, token).then()
        .statusCode(201).and().body("legacy_id", notNullValue());
  }

}
