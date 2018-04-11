package gov.ca.cwds.api;

import java.io.FileNotFoundException;

import org.junit.Before;

import gov.ca.cwds.authenticate.config.CwdsAuthenticationClientConfig;
import gov.ca.cwds.rest.authenticate.AuthenticationUtils;
import gov.ca.cwds.rest.authenticate.TokenInfo;
import gov.ca.cwds.rest.authenticate.UserGroup;

/**
 * @author CWDS API Team
 *
 */
public class FunctionalTest {

  CwdsAuthenticationClientConfig config;
  String url;
  String token;
  TokenInfo tokeninfo;

  /**
   * @throws FileNotFoundException - FileNotFoundException
   */
  @Before
  public void init() throws FileNotFoundException {
    ConfigImpl configImpl = new ConfigImpl();
    config = configImpl.readConfig();
    url = config.getTestUrl().getBaseUrl();
    token = login(configImpl);
  }

  private String login(ConfigImpl configImpl) {
    AuthenticationUtils authentication = new AuthenticationUtils(configImpl);
    return authentication.getToken(UserGroup.SOCIAL_WORKER);
  }

  protected String getResourceUrlFor(String resource) {
    return url + resource;
  }

}
