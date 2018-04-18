package gov.ca.cwds.api.builder;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

/**
 * @author CWDS API Team
 *
 */
public class FunctionalTestingBuilder {

  /**
   * @param object - object
   * @param resourcePath - resourcePath
   * @param token - token
   * @return the body
   */
  public Response processPostRequest(Object object, String resourcePath, String token) {
    return given().queryParam("token", token).header("Content-Type", "application/json")
        .header("Accept", "application/json").body(object).when().post(resourcePath);
  }

}

