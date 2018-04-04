package gov.ca.cwds.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;

import gov.ca.cwds.fixture.ScreeningEntityBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import org.junit.Before;
import org.junit.Test;


public class ScreeningToReferralTest extends FunctionalTest {
  String referralPath;

  @Before
  public void setup() {
    referralPath = getResourceUrlFor("/referrals");
  }

  @Test
  public void returnErrorWhenNoHeaderIsProvided(){
    given().queryParam("token", token)
        .when().post(referralPath)
        .then().statusCode(500);
  }

  @Test
  public void return500ErrorWhenIncorrectStaffPerson(){
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setAssigneeStaffId("0x5").createScreeningToReferral();

    given().queryParam("token", token)
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .body(referral)
        .when().post(referralPath)
        .then().body("issue_details[0].technical_message", equalTo("Assigned Staff Person County must be the same as the Incident County"))
        .and().statusCode(500);
  }

  @Test
  public void return500ErrorWhenNoVictimIsPresent(){
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(null).createScreeningToReferral();

    given().queryParam("token", token)
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .body(referral)
        .when().post(referralPath)
        .then().body("issue_details.user_message", hasItem("must contain at least one victim, only one reporter, and compatible roles"))
        .and().statusCode(422);
  }

}
