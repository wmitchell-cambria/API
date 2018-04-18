package gov.ca.cwds.server;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

import gov.ca.cwds.config.CwdsAuthenticationClientConfig;
import gov.ca.cwds.rest.ApiConfiguration;

/**
 * To load the Yaml file and read the values.
 * 
 * @author CWDS API Team
 *
 */
public class TestingYmlConfig {
  ApiConfiguration configuration;

  /**
   * @param configuration - configuration
   */
  public TestingYmlConfig(ApiConfiguration configuration) {
    this.configuration = configuration;
  }

  /**
   * createTestConfig.
   * 
   * @return the createdTestConfig
   */
  public CwdsAuthenticationClientConfig createTestConfig() {
    Yaml yaml = new Yaml();
    InputStream in = getClass().getResourceAsStream(configuration.getTestConfig().getConfigFile());
    return yaml.loadAs(in, CwdsAuthenticationClientConfig.class);
  }
}
