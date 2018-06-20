package gov.ca.cwds.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.db.DataSourceFactory;

/**
 * 
 * 
 * @author CWDS API Team
 */
public class ApiConfiguration extends BaseApiConfiguration {

  private DataSourceFactory rsDataSourceFactory;
  private TestingConfiguration testConfig;
  private boolean upgradeDbOnStart = false;
  private boolean loadSystemCodesAtStartup = true;

  public void setRsDataSourceFactory(DataSourceFactory rsDataSourceFactory) {
    this.rsDataSourceFactory = rsDataSourceFactory;
  }

  @JsonProperty
  public DataSourceFactory getRsDataSourceFactory() {
    return rsDataSourceFactory;
  }

  @JsonProperty
  public TestingConfiguration getTestConfig() {
    return testConfig;
  }

  @JsonProperty
  public void setTestConfig(TestingConfiguration testConfig) {
    this.testConfig = testConfig;
  }

  @JsonProperty
  public boolean isUpgradeDbOnStart() {
    return upgradeDbOnStart;
  }

  public void setUpgradeDbOnStart(boolean upgradeDbOnStart) {
    this.upgradeDbOnStart = upgradeDbOnStart;
  }

  @JsonProperty
  public boolean isLoadSystemCodesAtStartup() {
    return loadSystemCodesAtStartup;
  }

  @JsonProperty
  public void setLoadSystemCodesAtStartup(boolean loadSystemCodesAtStartup) {
    this.loadSystemCodesAtStartup = loadSystemCodesAtStartup;
  }
}
