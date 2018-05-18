package gov.ca.cwds.api;

import java.io.FileNotFoundException;

import org.junit.Before;

import gov.ca.cwds.authenticate.config.ConfigImpl;
import gov.ca.cwds.config.CwdsAuthenticationClientConfig;
import gov.ca.cwds.rest.authenticate.AuthenticationUtils;
import gov.ca.cwds.rest.authenticate.UserGroup;
import gov.ca.cwds.rest.authenticate.UserInfo;

/**
 * @author CWDS API Team
 */
public class FunctionalTest {

  CwdsAuthenticationClientConfig config;
  String url;

  /**
   * 
   */
  public String token;

  /**
   * 
   */
  public UserInfo userInfo;

  /**
   * @throws FileNotFoundException - FileNotFoundException
   */
  @Before
  public void init() throws FileNotFoundException {
    ConfigImpl configImpl = new ConfigImpl();
    config = configImpl.readConfig();
    url = config.getTestUrl().getBaseUrl();
    token = login(configImpl);
    userInfo = getStaffpersonInfo(configImpl);
  }

  private String login(ConfigImpl configImpl) {
    return new AuthenticationUtils(configImpl).getToken(UserGroup.SOCIAL_WORKER);
  }

  private UserInfo getStaffpersonInfo(ConfigImpl configImpl) {
    return new AuthenticationUtils(configImpl).getStaffPersonDetails(token);
  }

  protected String getResourceUrlFor(String resource) {
    return url + resource;
  }

}
