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

  public void setRsDataSourceFactory(DataSourceFactory rsDataSourceFactory) {
    this.rsDataSourceFactory = rsDataSourceFactory;
  }

  @JsonProperty
  public DataSourceFactory getRsDataSourceFactory() {
    return rsDataSourceFactory;
  }
}
