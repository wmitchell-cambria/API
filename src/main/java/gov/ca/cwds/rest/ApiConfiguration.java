package gov.ca.cwds.rest;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayFactory;

public class ApiConfiguration extends Configuration {
    /**
     * The application name
     */
    @NotEmpty
    private String applicationName;

//    @JsonProperty("swagger")
//    public SwaggerBundleConfiguration swaggerBundleConfiguration;
    
    private DataSourceFactory dataSourceFactory;

    private FlywayFactory flywayFactory;
    
    @JsonProperty
    public String getApplicationName() {
        return applicationName;
    }

    @JsonProperty
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
    
    @JsonProperty
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    @JsonProperty
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    @JsonProperty
    public FlywayFactory getFlywayFactory() {
        return flywayFactory;
    }

    @JsonProperty
    public void setFlywayFactory(FlywayFactory flywayFactory) {
        this.flywayFactory = flywayFactory;
    }


}
