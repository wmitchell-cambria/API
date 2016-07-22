package gov.ca.cwds.rest;

import gov.ca.cwds.rest.api.persistence.CrudsDao;
import gov.ca.cwds.rest.api.persistence.CrudsDaoImpl;
import gov.ca.cwds.rest.api.persistence.Referral;
import gov.ca.cwds.rest.api.persistence.StaffPerson;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.ApplicationResource;
import gov.ca.cwds.rest.resources.ApplicationResourceImpl;
import gov.ca.cwds.rest.resources.CrudsResource;
import gov.ca.cwds.rest.resources.CrudsResourceImpl;
import gov.ca.cwds.rest.resources.ReferralResource;
import gov.ca.cwds.rest.resources.ReferralResourceImpl;
import gov.ca.cwds.rest.resources.StaffPersonResource;
import gov.ca.cwds.rest.resources.StaffPersonResourceImpl;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.CrudsServiceImpl;
import gov.ca.cwds.rest.services.ReferralService;
import gov.ca.cwds.rest.services.ReferralServiceImpl;
import gov.ca.cwds.rest.services.StaffPersonService;
import gov.ca.cwds.rest.services.StaffPersonServiceImpl;
import gov.ca.cwds.rest.setup.ApiEnvironment;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import java.util.EnumSet;
import java.util.HashMap;

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
        ApiEnvironment apiEnvironment = new ApiEnvironment(environment);
        
        //NOTE : Services must be registered before Resources can be
        LOGGER.info("Registering Application Service");
        registerServices(configuration, apiEnvironment);
        
        LOGGER.info("Registering Application Resources");
        registerResources(configuration, apiEnvironment);

        LOGGER.info("Configuring CORS: Cross-Origin Resource Sharing");
        configureCors(apiEnvironment);
        
    }
    
    private void registerServices(final ApiConfiguration configuration, final ApiEnvironment apiEnvironment) {
    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(), ReferralService.class.getName());
    	HashMap<String, Referral> dummyReferralData = new HashMap<String, Referral>();
    	LOGGER.info("Dummy Data setup for {} of {}", Api.Version.JSON_VERSION_1.getMediaType(), ReferralService.class.getName());
    	final CrudsDao<Referral> referralCrudsDao = new CrudsDaoImpl<Referral>(dummyReferralData);
    	LOGGER.info("DAO:{} for {} of {}", CrudsDaoImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), ReferralService.class.getName());
    	final CrudsService<Referral> referralCrudsService = new CrudsServiceImpl<Referral>(referralCrudsDao);
    	LOGGER.info("CrudsService:{} for {} of {}", CrudsServiceImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());	    	
    	final ReferralService referralService = new ReferralServiceImpl(referralCrudsService);
    	apiEnvironment.services().register(ReferralService.class, Api.Version.JSON_VERSION_1, referralService);
    	LOGGER.info("ReferralService:{} for {} of {}", ReferralServiceImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());
    	
    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());
    	HashMap<String, StaffPerson> dummyStaffPersonData = new HashMap<String, StaffPerson>();
    	LOGGER.info("Dummy Data setup for {} of {}", Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());
    	final CrudsDao<StaffPerson> staffPersonCrudsDao = new CrudsDaoImpl<StaffPerson>(dummyStaffPersonData);
    	LOGGER.info("DAO:{} for {} of {}", CrudsDaoImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());
    	final CrudsService<StaffPerson> staffPersonCrudsService = new CrudsServiceImpl<StaffPerson>(staffPersonCrudsDao);
    	LOGGER.info("CrudsService:{} for {} of {}", CrudsServiceImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());		
    	final StaffPersonService staffPersonService = new StaffPersonServiceImpl(staffPersonCrudsService);
    	apiEnvironment.services().register(StaffPersonService.class, Api.Version.JSON_VERSION_1, staffPersonService);
    	LOGGER.info("StaffPersonService:{} for {} of {}", StaffPersonServiceImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());
    	
    }
    
    private void registerResources(final ApiConfiguration configuration, final ApiEnvironment apiEnvironment) {
        LOGGER.info("Registering ApplicationResource");
        final ApplicationResource applicationResource = new ApplicationResourceImpl(configuration.getApplicationName());
        apiEnvironment.jersey().register(applicationResource);
        
        LOGGER.info("Registering ReferralResource");
        CrudsResource<Referral> referralCrudsResource = new CrudsResourceImpl<Referral, ReferralService>(apiEnvironment.services(), ReferralService.class);
        final ReferralResource referralResource = new ReferralResourceImpl(apiEnvironment.services(), referralCrudsResource);
        apiEnvironment.jersey().register(referralResource);
        
        LOGGER.info("Registering StaffPersonResource");
        CrudsResource<StaffPerson> staffPersonCrudsResource = new CrudsResourceImpl<StaffPerson, StaffPersonService>(apiEnvironment.services(), StaffPersonService.class);
        final StaffPersonResource staffPersonResource = new StaffPersonResourceImpl(apiEnvironment.services(), staffPersonCrudsResource);
        apiEnvironment.jersey().register(staffPersonResource);
    }
    
    private void configureCors(ApiEnvironment apiEnvironment) {
        FilterRegistration.Dynamic filter = apiEnvironment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,X-Auth-Token");
        filter.setInitParameter("allowCredentials", "true");
    }
}
