package gov.ca.cwds.rest;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayFactory;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiConfiguration extends Configuration {
  /**
   * The application name
   */
  @NotEmpty
  private String applicationName;

  private SwaggerConfiguration swaggerConfiguration;

  private DataSourceFactory nsDataSourceFactory;

  private FlywayFactory flywayFactory;

  private DataSourceFactory cmsDataSourceFactory;

  private ElasticsearchConfiguration elasticsearchConfiguration;

  @JsonProperty
  public String getApplicationName() {
    return applicationName;
  }

  @JsonProperty
  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  @JsonProperty
  public DataSourceFactory getNsDataSourceFactory() {
    return nsDataSourceFactory;
  }

  @JsonProperty
  public void setNsDataSourceFactory(DataSourceFactory dataSourceFactory) {
    this.nsDataSourceFactory = dataSourceFactory;
  }

  @JsonProperty
  public DataSourceFactory getCmsDataSourceFactory() {
    return cmsDataSourceFactory;
  }

  @JsonProperty
  public void setDataSourceFactoryLegacy(DataSourceFactory dataSourceFactory) {
    this.cmsDataSourceFactory = dataSourceFactory;
  }

  @JsonProperty
  public FlywayFactory getFlywayFactory() {
    return flywayFactory;
  }

  @JsonProperty
  public void setFlywayFactory(FlywayFactory flywayFactory) {
    this.flywayFactory = flywayFactory;
  }

  @JsonProperty(value = "swagger")
  public SwaggerConfiguration getSwaggerConfiguration() {
    return swaggerConfiguration;
  }

  @JsonProperty
  public void setSwaggerConfiguration(SwaggerConfiguration swaggerConfiguration) {
    this.swaggerConfiguration = swaggerConfiguration;
  }


  @JsonProperty(value = "elasticsearch")
  public ElasticsearchConfiguration getElasticsearchConfiguration() {
    return elasticsearchConfiguration;
  }

  @JsonProperty
  public void setElasticsearchConfiguration(ElasticsearchConfiguration elasticsearchConfiguration) {
    this.elasticsearchConfiguration = elasticsearchConfiguration;
  }

}
