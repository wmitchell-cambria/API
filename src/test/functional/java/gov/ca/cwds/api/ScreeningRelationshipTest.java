package gov.ca.cwds.api;

import static com.jayway.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.core.Api;

public class ScreeningRelationshipTest extends FunctionalTest {

  public static final int RELATIONSHIP_TYPE = 190;
  String resourcePath;
  ScreeningRelationship relationship;

  @Before
  public void setup() {
    resourcePath = getResourceUrlFor("/" + Api.SCREENING_RELATIONSHIPS);
    relationship = new ScreeningRelationship("id", "Client1", "Client2", RELATIONSHIP_TYPE);
  }

  @Test
  public void whenCreatingRelationshipWithValidReferralThenCreatedCodeIsReturned() {
    given().queryParam("token", token).body(relationship).header("Content-Type", "application/json")
        .header("Accept", "application/json").when().post(resourcePath).then().statusCode(201);
  }

  @Test
  public void whenUpdatingReferralWithInvalidJsonThenErrorIsReturned() {
    given().queryParam("token", token).body("{bad object}")
        .header("Content-Type", "application/json").header("Accept", "application/json").when()
        .post(resourcePath).then().statusCode(400);
  }

  @Test
  public void foo() {
    given().queryParam("token", token).body("{bad object}")
        .header("Content-Type", "application/json").header("Accept", "application/json").when()
        .post(resourcePath).then().statusCode(400);
  }

}
