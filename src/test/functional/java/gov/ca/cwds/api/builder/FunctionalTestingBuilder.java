package gov.ca.cwds.api.builder;

import static io.restassured.RestAssured.given;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * Functional Test Builder to used handle request for test, make it cleaner and easier to build the
 * test classes.
 * 
 * @author CWDS API Team
 */
public class FunctionalTestingBuilder {

  private static final Logger LOGGER = LoggerFactory.getLogger(FunctionalTestingBuilder.class);

  /**
   * This method process the POST processing and return the appropriate body or JSON response.
   * 
   * @param object - object
   * @param resourcePath - resourcePath
   * @param token - token
   * @return the response
   */
  public Response processPostRequest(Object object, String resourcePath, String token) {
    return given().queryParam("token", token).header("Content-Type", "application/json")
        .header("Accept", "application/json").body(object).when().post(resourcePath).then()
        .contentType(ContentType.JSON).extract().response();
  }

  /**
   * This methods process the GET processing.
   * 
   * @param resourcePath - resourcePath
   * @param parameter - parameter
   * @param ParameterValue - ParameterValue
   * @param token - token
   * @return the response
   */
  public Response processGetRequest(String resourcePath, String parameter, String ParameterValue,
      String token) {
    LOGGER.info("processGetRequest: resourcePath: {}, parameter: {}, token: {}", resourcePath,
        parameter, token);
    return given().queryParam(parameter, ParameterValue).queryParam("token", token)
        .get(resourcePath).then().contentType(ContentType.JSON).extract().response();
  }

}
