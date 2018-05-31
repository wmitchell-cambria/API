package gov.ca.cwds.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.http.ContentType;
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

  @Before
  public void setup() {
    referralPath = getResourceUrlFor(ResourceEndPoint.REFERRALS.getResourcePath());
    functionalTestingBuilder = new FunctionalTestingBuilder();
  }

  @Test
  public void returnErrorWhenNoHeaderIsProvided() {
    given().queryParam("token", token).when().post(referralPath).then().statusCode(500);
  }

  @Test
  public void return500ErrorWhenIncorrectStaffPerson() {
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setName("IncorrectStaffPerson")
        .setAssigneeStaffId("bad")
        .createScreeningToReferral();

    functionalTestingBuilder.processPostRequest(referral, referralPath, token).then()
        .body("issue_details[0].user_message",
            equalTo("There was an error processing your request. It has been logged with unique incident id"))
        .and().body("issue_details[0].stack_trace",
            containsString("gov.ca.cwds.rest.business.rules.CountyOfAssignedStaffWorker.isValid(CountyOfAssignedStaffWorker.java"))
        .and().statusCode(500);
  }

  @Test
  public void return500ErrorWhenNoVictimIsPresent() {
    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder()
            .setName("return500ErrorWhenNoVictimIsPresent")
            .setassigneeStaffId("NONE")
            .setParticipants(null)
            .createScreeningToReferral();

    functionalTestingBuilder.processPostRequest(referral, referralPath, token).then()
        .body("issue_details.user_message",
            hasItem("must contain at least one victim, only one reporter, and compatible roles"))
        .and().statusCode(422);
  }

  @Test
  public void return201SuccessForValidReferrals() {
    ScreeningToReferral referral =
        new ScreeningToReferralResourceBuilder().setName("return201SuccessForValidReferrals").setAssigneeStaffId("aaw")
            .setIncidentCounty(userInfo.getIncidentCounty()).createScreeningToReferral();

    given().queryParam("token", token).header("Content-Type", "application/json")
        .header("Accept", "application/json").body(referral).when().post(referralPath).then()
        .contentType(ContentType.JSON).extract().response()
    .then()
        .statusCode(201).and().body("legacy_id", notNullValue());
  }
}
