package gov.ca.cwds.config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
public class CwdsAuthenticationClientConfigTest {

  private String authenticationMode = "Test";

  /**
   * Test for CwdsAuthenticationClientConfig class getters and setters.
   */
  @Test
  public void testForSettersGetters() {
    final CwdsAuthenticationClientConfig cwdsAuthenticationClientConfig =
        new CwdsAuthenticationClientConfig();
    cwdsAuthenticationClientConfig.setAuthenticationMode("Test");
    TokenCredentials testUrl = new TokenCredentials();
    java.util.List<User> defaultUsers = new ArrayList<>();
    cwdsAuthenticationClientConfig.setTestUrl(testUrl);
    cwdsAuthenticationClientConfig.setDefaultUsers(defaultUsers);

    assertThat(testUrl, is(notNullValue()));
    assertThat(defaultUsers, is(notNullValue()));
    assertEquals(authenticationMode, cwdsAuthenticationClientConfig.getAuthenticationMode());
  }

}
