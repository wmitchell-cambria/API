package gov.ca.cwds.rest;

import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiConfiguration extends Configuration {
    /**
     * The application name
     */
    @NotEmpty
    private String applicationName;

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;
    
    @JsonProperty
    public String getApplicationName() {
        return applicationName;
    }

    @JsonProperty
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
