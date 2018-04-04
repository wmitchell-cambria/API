package gov.ca.cwds.server;

import gov.ca.cwds.config.TestConfig;
import gov.ca.cwds.rest.ApiConfiguration;
import java.io.InputStream;
import org.yaml.snakeyaml.Yaml;

public class TestingYmlConfig {
  ApiConfiguration configuration;

  public TestingYmlConfig(ApiConfiguration configuration) {
    this.configuration = configuration;
  }

  public TestConfig createTestConfig() {
    Yaml yaml = new Yaml();
    InputStream in = getClass().getResourceAsStream(configuration.getTestConfig().getConfigFile());
    return yaml.loadAs(in, TestConfig.class);
  }
}
