package gov.ca.cwds.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
public class UserTest {

  /**
   * Test for User class getters and setters.
   */
  @Test
  public void testForSettersGetters() {
    final User user = new User();
    user.setUsername("username");
    user.setPassword("password");
    user.setUserType("Social Worker");
    assertEquals("username", user.getUsername());
    assertEquals("password", user.getPassword());
    assertEquals("Social Worker", user.getUserType());
  }

}
