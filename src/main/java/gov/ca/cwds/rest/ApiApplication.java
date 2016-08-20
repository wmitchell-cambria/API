package gov.ca.cwds.rest;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.rest.api.persistence.legacy.Allegation;
import gov.ca.cwds.rest.api.persistence.legacy.CrossReport;
import gov.ca.cwds.rest.api.persistence.legacy.Referral;
import gov.ca.cwds.rest.api.persistence.legacy.ReferralClient;
import gov.ca.cwds.rest.api.persistence.legacy.Reporter;
import gov.ca.cwds.rest.api.persistence.legacy.StaffPerson;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;
import gov.ca.cwds.rest.jdbi.legacy.AllegationDao;
import gov.ca.cwds.rest.jdbi.legacy.CrossReportDao;
import gov.ca.cwds.rest.jdbi.legacy.ReferralClientDao;
import gov.ca.cwds.rest.jdbi.legacy.ReferralDao;
import gov.ca.cwds.rest.jdbi.legacy.ReporterDao;
import gov.ca.cwds.rest.jdbi.legacy.StaffPersonDao;
import gov.ca.cwds.rest.resources.AllegationResource;
import gov.ca.cwds.rest.resources.AllegationResourceImpl;
import gov.ca.cwds.rest.resources.ApplicationResource;
import gov.ca.cwds.rest.resources.ApplicationResourceImpl;
import gov.ca.cwds.rest.resources.CrossReportResource;
import gov.ca.cwds.rest.resources.CrossReportResourceImpl;
import gov.ca.cwds.rest.resources.CrudsResource;
import gov.ca.cwds.rest.resources.CrudsResourceImpl;
import gov.ca.cwds.rest.resources.ReferralClientResource;
import gov.ca.cwds.rest.resources.ReferralClientResourceImpl;
import gov.ca.cwds.rest.resources.ReferralResource;
import gov.ca.cwds.rest.resources.ReferralResourceImpl;
import gov.ca.cwds.rest.resources.ReporterResource;
import gov.ca.cwds.rest.resources.ReporterResourceImpl;
import gov.ca.cwds.rest.resources.StaffPersonResource;
import gov.ca.cwds.rest.resources.StaffPersonResourceImpl;
import gov.ca.cwds.rest.resources.SwaggerResource;
import gov.ca.cwds.rest.services.AllegationService;
import gov.ca.cwds.rest.services.AllegationServiceImpl;
import gov.ca.cwds.rest.services.CrossReportService;
import gov.ca.cwds.rest.services.CrossReportServiceImpl;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.CrudsServiceImpl;
import gov.ca.cwds.rest.services.ReferralClientService;
import gov.ca.cwds.rest.services.ReferralClientServiceImpl;
import gov.ca.cwds.rest.services.ReferralService;
import gov.ca.cwds.rest.services.ReferralServiceImpl;
import gov.ca.cwds.rest.services.ReporterService;
import gov.ca.cwds.rest.services.ReporterServiceImpl;
import gov.ca.cwds.rest.services.StaffPersonService;
import gov.ca.cwds.rest.services.StaffPersonServiceImpl;
import gov.ca.cwds.rest.setup.ApiEnvironment;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.flyway.FlywayFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
//import io.federecio.dropwizard.swagger.SwaggerBundle;
//import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;

public class ApiApplication extends Application<ApiConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiApplication.class);

    
    private final HibernateBundle<ApiConfiguration> hibernateBundle = new HibernateBundle<ApiConfiguration>(StaffPerson.class, Referral.class, Allegation.class, CrossReport.class, ReferralClient.class, Reporter.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };
    
    private final FlywayBundle<ApiConfiguration> flywayBundle = new FlywayBundle<ApiConfiguration>() {
        @Override
        public DataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }

        @Override
        public FlywayFactory getFlywayFactory(ApiConfiguration configuration) {
            return configuration.getFlywayFactory();
        }
    };
    
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
        bootstrap.addBundle(new ViewBundle<ApiConfiguration>());
        bootstrap.addBundle(flywayBundle);
        bootstrap.addBundle(hibernateBundle);
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

        LOGGER.info("Registering Health Checks");
        registerHealthChecks(apiEnvironment);
        
        LOGGER.info("Configuring CORS: Cross-Origin Resource Sharing");
        configureCors(apiEnvironment);
        
        LOGGER.info("Configuring SWAGGER");
        configureSwagger(configuration, environment);
    }
    
    private void registerHealthChecks(final ApiEnvironment apiEnvironment) {}
    
    private void registerServices(final ApiConfiguration configuration, final ApiEnvironment apiEnvironment) {
    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(), ReferralService.class.getName());
    	final ReferralDao referralDao = new ReferralDao(hibernateBundle.getSessionFactory());
    	LOGGER.info("DAO:{} for {} of {}", CrudsDaoImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), ReferralService.class.getName());
    	final CrudsService<Referral> referralCrudsService = new CrudsServiceImpl<Referral>(referralDao);
    	LOGGER.info("CrudsService:{} for {} of {}", CrudsServiceImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());	    	
    	final ReferralService referralService = new ReferralServiceImpl(referralCrudsService);
    	apiEnvironment.services().register(ReferralService.class, Api.Version.JSON_VERSION_1, referralService);
    	LOGGER.info("ReferralService:{} for {} of {}", ReferralServiceImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());
    	
    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());
    	final StaffPersonDao staffPersonDao = new StaffPersonDao(hibernateBundle.getSessionFactory());   
    	LOGGER.info("DAO:{} for {} of {}", CrudsDaoImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());
    	final CrudsService<StaffPerson> staffPersonCrudsService = new CrudsServiceImpl<StaffPerson>(staffPersonDao);
    	LOGGER.info("CrudsService:{} for {} of {}", CrudsServiceImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());		
    	final StaffPersonService staffPersonService = new StaffPersonServiceImpl(staffPersonCrudsService);
    	apiEnvironment.services().register(StaffPersonService.class, Api.Version.JSON_VERSION_1, staffPersonService);
    	LOGGER.info("StaffPersonService:{} for {} of {}", StaffPersonServiceImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());
    	
    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(),
				AllegationService.class.getName());
		final AllegationDao allegationDao = new AllegationDao(hibernateBundle.getSessionFactory()); 
		LOGGER.info("DAO:{} for {} of {}", CrudsDaoImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(),
				AllegationService.class.getName());
		final CrudsService<Allegation> allegationCrudsService = new CrudsServiceImpl<Allegation>(allegationDao);
		LOGGER.info("CrudsService:{} for {} of {}", CrudsServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), AllegationService.class.getName());
		final AllegationService allegationService = new AllegationServiceImpl(allegationCrudsService);
		apiEnvironment.services().register(AllegationService.class, Api.Version.JSON_VERSION_1, allegationService);
		LOGGER.info("AllegationService:{} for {} of {}", AllegationServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), AllegationService.class.getName());
		
    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(),
				ReporterService.class.getName());
		final ReporterDao reporterDao = new ReporterDao(hibernateBundle.getSessionFactory()); 
		LOGGER.info("DAO:{} for {} of {}", CrudsDaoImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(),
				ReporterService.class.getName());
		final CrudsService<Reporter> reporterCrudsService = new CrudsServiceImpl<Reporter>(reporterDao);
		LOGGER.info("CrudsService:{} for {} of {}", CrudsServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), ReporterService.class.getName());
		final ReporterService reporterService = new ReporterServiceImpl(reporterCrudsService);
		apiEnvironment.services().register(ReporterService.class, Api.Version.JSON_VERSION_1, reporterService);
		LOGGER.info("ReporterService:{} for {} of {}", ReporterServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), ReporterService.class.getName());

    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(),
				CrossReportService.class.getName());
		final CrossReportDao crossReportDao = new CrossReportDao(hibernateBundle.getSessionFactory()); 
		LOGGER.info("DAO:{} for {} of {}", CrudsDaoImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(),
				CrossReportService.class.getName());
		final CrudsService<CrossReport> crossReportCrudsService = new CrudsServiceImpl<CrossReport>(crossReportDao);
		LOGGER.info("CrudsService:{} for {} of {}", CrudsServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), CrossReportService.class.getName());
		final CrossReportService crossReportService = new CrossReportServiceImpl(crossReportCrudsService);
		apiEnvironment.services().register(CrossReportService.class, Api.Version.JSON_VERSION_1, crossReportService);
		LOGGER.info("CrossReportService:{} for {} of {}", CrossReportServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), CrossReportService.class.getName());
		
    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(),
				ReferralClientService.class.getName());
		final ReferralClientDao referralClientDao = new ReferralClientDao(hibernateBundle.getSessionFactory()); 
		LOGGER.info("DAO:{} for {} of {}", CrudsDaoImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(),
				ReferralClientService.class.getName());
		final CrudsService<ReferralClient> referralClientCrudsService = new CrudsServiceImpl<ReferralClient>(referralClientDao);
		LOGGER.info("CrudsService:{} for {} of {}", CrudsServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), ReferralClientService.class.getName());
		final ReferralClientService referralClientService = new ReferralClientServiceImpl(referralClientCrudsService);
		apiEnvironment.services().register(ReferralClientService.class, Api.Version.JSON_VERSION_1, referralClientService);
		LOGGER.info("ReferralClientService:{} for {} of {}", ReferralClientServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), ReferralClientService.class.getName());
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
        
        LOGGER.info("Registering CrossReportResource");
        CrudsResource<CrossReport> crossReportCrudsResource = new CrudsResourceImpl<CrossReport, CrossReportService>(apiEnvironment.services(), CrossReportService.class);
        final CrossReportResource crossReportResource = new CrossReportResourceImpl(apiEnvironment.services(), crossReportCrudsResource);
        apiEnvironment.jersey().register(crossReportResource);
        
        LOGGER.info("Registering ReferralClientResource");
        CrudsResource<ReferralClient> referralClientCrudsResource = new CrudsResourceImpl<ReferralClient, ReferralClientService>(apiEnvironment.services(), ReferralClientService.class);
        final ReferralClientResource referralClientResource = new ReferralClientResourceImpl(apiEnvironment.services(), referralClientCrudsResource);
        apiEnvironment.jersey().register(referralClientResource);
        
        LOGGER.info("Registering ReporterResource");
        CrudsResource<Reporter> reporterCrudsResource = new CrudsResourceImpl<Reporter, ReporterService>(apiEnvironment.services(), ReporterService.class);
        final ReporterResource reporterResource = new ReporterResourceImpl(apiEnvironment.services(), reporterCrudsResource);
        apiEnvironment.jersey().register(reporterResource);
        
        LOGGER.info("Registering ApiListingResource");
     	apiEnvironment.jersey().register(new ApiListingResource());
     	
        LOGGER.info("Registering SwaggerResource");
        final SwaggerResource swaggerResource = new SwaggerResource(configuration.getSwaggerConfiguration());
     	apiEnvironment.jersey().register(swaggerResource);
     	
     	LOGGER.info("Registering AllegationResource");
		CrudsResource<Allegation> allegationCrudsResource = new CrudsResourceImpl<Allegation, AllegationService>(
				apiEnvironment.services(), AllegationService.class);
		final AllegationResource allegationResource = new AllegationResourceImpl(apiEnvironment.services(),
				allegationCrudsResource);
		apiEnvironment.jersey().register(allegationResource);
    }
    
    private void configureCors(final ApiEnvironment apiEnvironment) {
        FilterRegistration.Dynamic filter = apiEnvironment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,X-Auth-Token");
        filter.setInitParameter("allowCredentials", "true");
    }
    
    private void configureSwagger(final ApiConfiguration apiConfiguration, final Environment environment) {
        BeanConfig config = new BeanConfig();
        config.setTitle(apiConfiguration.getSwaggerConfiguration().getTitle());
        config.setDescription(apiConfiguration.getSwaggerConfiguration().getDescription());
        config.setResourcePackage(apiConfiguration.getSwaggerConfiguration().getResourcePackage());
        config.setScan(true);
        
        new AssetsBundle(apiConfiguration.getSwaggerConfiguration().getAssetsPath(), apiConfiguration.getSwaggerConfiguration().getAssetsPath(), null, "swagger").run(environment);
        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        environment.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }
}
