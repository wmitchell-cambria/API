package gov.ca.cwds.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author CWDS API team
 *
 */
public class TestingConfigurationTest {

  private String configFile = "config/testConfig.yml";

  /**
   * Test for TestingConfiguration class getters and setters.
   */
  @Test
  public void testForSettersGetters() {
    final TestingConfiguration testingConfiguration = new TestingConfiguration();
    testingConfiguration.setConfigFile(configFile);
    assertEquals(configFile, testingConfiguration.getConfigFile());
    assertThat(configFile, is(notNullValue()));
  }

}
