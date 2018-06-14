package gov.ca.cwds.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.persistence.XADataSourceFactory;
import io.dropwizard.db.DataSourceFactory;

/**
 * 
 * 
 * @author CWDS API Team
 */
public class ApiConfiguration extends BaseApiConfiguration {

  private XADataSourceFactory rsDataSourceFactory;
  private TestingConfiguration testConfig;
  private boolean upgradeDbOnStart = false;

  public void setRsDataSourceFactory(XADataSourceFactory rsDataSourceFactory) {
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

}
