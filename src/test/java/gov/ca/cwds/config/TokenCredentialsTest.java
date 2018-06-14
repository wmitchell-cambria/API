package gov.ca.cwds.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author CWDS API Team
 */
public class TokenCredentialsTest {

  private String baseUrl = "https://ferbapi.preint.cwds.io";
  private String perryLoginUrl = "https://localhost:8090/perry/login";
  private String authLoginUrl = "https://localhost:8090/perry/authn/login";
  private String tokenUrl = "https://localhost:8090/perry/authn/token";
  private String callBackUrl = "https://ferbapi.preint.cwds.io/swagger";
  private String logOutUrl = "https://localhost:8090/perry/authn/logout";
  private String validateUrl = "https://localhost:8090/perry/authn/validate";

  /**
   * Test for TokenCredentials class getters and setters.
   */
  @Test
  public void testForSettersGetters() {
    final TokenCredentials testUrl = new TokenCredentials();
    testUrl.setAuthLoginUrl(authLoginUrl);
    testUrl.setBaseUrl(baseUrl);
    testUrl.setCallBackUrl(callBackUrl);
    testUrl.setLogOutUrl(logOutUrl);
    testUrl.setPerryLoginUrl(perryLoginUrl);
    testUrl.setTokenUrl(tokenUrl);
    testUrl.setValidateUrl(validateUrl);

    assertEquals(authLoginUrl, testUrl.getAuthLoginUrl());
    assertEquals(baseUrl, testUrl.getBaseUrl());
    assertEquals(callBackUrl, testUrl.getCallBackUrl());
    assertEquals(perryLoginUrl, testUrl.getPerryLoginUrl());
    assertEquals(tokenUrl, testUrl.getTokenUrl());
    assertEquals(validateUrl, testUrl.getValidateUrl());
    assertEquals(logOutUrl, testUrl.getLogOutUrl());
  }

}
