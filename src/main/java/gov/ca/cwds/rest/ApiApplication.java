package gov.ca.cwds.rest;

import gov.ca.cwds.rest.resources.ApplicationResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiApplication extends Application<ApiConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiApplication.class);

    public static void main(final String[] args) throws Exception {
        new ApiApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ApiConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor()
                )
        );
        bootstrap.addBundle(new SwaggerBundle<ApiConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ApiConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }
    
    @Override
    public void run(final ApiConfiguration configuration, final Environment environment) throws Exception {
        LOGGER.info("Application name: {}", configuration.getApplicationName());

        LOGGER.info("Registering Application Resources");
        registerResources(configuration, environment);

        LOGGER.info("Configuring CORS: Cross-Origin Resource Sharing");
        configureCors(environment);
        
    }
    
    private void registerResources(final ApiConfiguration configuration, final Environment environment) {
        LOGGER.info("Registering ApplicationResource");
        final ApplicationResource applicationResource = new ApplicationResource(configuration.getApplicationName());
        environment.jersey().register(applicationResource);
    }
    
    private void configureCors(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,X-Auth-Token");
        filter.setInitParameter("allowCredentials", "true");
    }
}
