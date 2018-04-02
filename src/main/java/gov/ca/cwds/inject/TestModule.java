package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import gov.ca.cwds.config.TestConfig;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.server.TestingYmlConfig;

public class TestModule extends AbstractModule {
  public TestModule()  {
  }

  @Override
  protected void configure() {
  }

  @Named("testConfig")
  @Provides
  public TestConfig getTestConfig(ApiConfiguration configuration){
    return new TestingYmlConfig(configuration).createTestConfig();
  }
}
