package gov.ca.cwds.rest;

import java.util.EnumSet;
import java.util.HashMap;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
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
import gov.ca.cwds.rest.api.persistence.ns.StaffPersonNS;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.jdbi.DataAccessEnvironment;
import gov.ca.cwds.rest.jdbi.HashMapDaoImpl;
import gov.ca.cwds.rest.jdbi.legacy.AllegationDao;
import gov.ca.cwds.rest.jdbi.legacy.CrossReportDao;
import gov.ca.cwds.rest.jdbi.legacy.ReferralClientDao;
import gov.ca.cwds.rest.jdbi.legacy.ReferralDao;
import gov.ca.cwds.rest.jdbi.legacy.ReporterDao;
import gov.ca.cwds.rest.jdbi.legacy.StaffPersonDao;
import gov.ca.cwds.rest.jdbi.ns.StaffPersonNSDao;
import gov.ca.cwds.rest.resources.ApplicationResource;
import gov.ca.cwds.rest.resources.ApplicationResourceImpl;
import gov.ca.cwds.rest.resources.CrudsResource;
import gov.ca.cwds.rest.resources.CrudsResourceImpl;
import gov.ca.cwds.rest.resources.SwaggerResource;
import gov.ca.cwds.rest.resources.legacy.AllegationResource;
import gov.ca.cwds.rest.resources.legacy.AllegationResourceImpl;
import gov.ca.cwds.rest.resources.legacy.CrossReportResource;
import gov.ca.cwds.rest.resources.legacy.CrossReportResourceImpl;
import gov.ca.cwds.rest.resources.legacy.ReferralClientResource;
import gov.ca.cwds.rest.resources.legacy.ReferralClientResourceImpl;
import gov.ca.cwds.rest.resources.legacy.ReferralResource;
import gov.ca.cwds.rest.resources.legacy.ReferralResourceImpl;
import gov.ca.cwds.rest.resources.legacy.ReporterResource;
import gov.ca.cwds.rest.resources.legacy.ReporterResourceImpl;
import gov.ca.cwds.rest.resources.legacy.StaffPersonResource;
import gov.ca.cwds.rest.resources.legacy.StaffPersonResourceImpl;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.CrudsServiceCombinedImpl;
import gov.ca.cwds.rest.services.CrudsServiceImpl;
import gov.ca.cwds.rest.services.legacy.AllegationService;
import gov.ca.cwds.rest.services.legacy.AllegationServiceImpl;
import gov.ca.cwds.rest.services.legacy.CrossReportService;
import gov.ca.cwds.rest.services.legacy.CrossReportServiceImpl;
import gov.ca.cwds.rest.services.legacy.ReferralClientService;
import gov.ca.cwds.rest.services.legacy.ReferralClientServiceImpl;
import gov.ca.cwds.rest.services.legacy.ReferralService;
import gov.ca.cwds.rest.services.legacy.ReferralServiceImpl;
import gov.ca.cwds.rest.services.legacy.ReporterService;
import gov.ca.cwds.rest.services.legacy.ReporterServiceImpl;
import gov.ca.cwds.rest.services.legacy.StaffPersonService;
import gov.ca.cwds.rest.services.legacy.StaffPersonServiceImpl;
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
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;

public class ApiApplication extends Application<ApiConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiApplication.class);

    private static final boolean debug = "true".equals(System.getenv("debug"));
    
    private final HibernateBundle<ApiConfiguration> cmsHibernateBundle = new HibernateBundle<ApiConfiguration>(StaffPerson.class, Referral.class, Allegation.class, CrossReport.class, ReferralClient.class, Reporter.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
            return configuration.getCmsDataSourceFactory();
        }
        @Override
        public String name(){
          return "cms";
        }
    };
    
    private final HibernateBundle<ApiConfiguration> nsHibernateBundle = new HibernateBundle<ApiConfiguration>(StaffPersonNS.class) {
      @Override
      public DataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
          return configuration.getNsDataSourceFactory();
      }
      @Override
      public String name(){
        return "ns";
      }
    };
    
    private final FlywayBundle<ApiConfiguration> flywayBundle = new FlywayBundle<ApiConfiguration>() {
        @Override
        public DataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
            return configuration.getNsDataSourceFactory();
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

        if(!debug) {
        	LOGGER.info("Loading database bundles");
        	bootstrap.addBundle(flywayBundle);
        	bootstrap.addBundle(cmsHibernateBundle);
            bootstrap.addBundle(nsHibernateBundle);
        } else {
        	LOGGER.warn("DEBUG is on so not loading database bundles");
        }
    }
    
    @Override
    public void run(final ApiConfiguration configuration, final Environment environment) throws Exception {
    	environment.jersey().getResourceConfig().packages(getClass().getPackage().getName()).register(DeclarativeLinkingFeature.class);
    	
    	
        LOGGER.info("Application name: {}", configuration.getApplicationName());
        ApiEnvironment apiEnvironment = new ApiEnvironment(environment);
        
        LOGGER.info("Preparing DAOs");
        setupDaos(configuration);
        
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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void setupDaos(final ApiConfiguration configuration) {
    	if( debug ) {
    		LOGGER.warn("Setting up HashMap DAOs");
    		DataAccessEnvironment.register(Referral.class, new HashMapDaoImpl<>(new HashMap()));
    		DataAccessEnvironment.register(StaffPerson.class, new HashMapDaoImpl<>(new HashMap()));
    		DataAccessEnvironment.register(Allegation.class, new HashMapDaoImpl<>(new HashMap()));
    		DataAccessEnvironment.register(CrossReport.class, new HashMapDaoImpl<>(new HashMap()));
    		DataAccessEnvironment.register(ReferralClient.class, new HashMapDaoImpl<>(new HashMap()));
    		DataAccessEnvironment.register(Reporter.class, new HashMapDaoImpl<>(new HashMap()));
    		DataAccessEnvironment.register(StaffPersonNS.class, new HashMapDaoImpl<>(new HashMap()));
    	} else {
    		LOGGER.info("Setting up production DAOs");
    		DataAccessEnvironment.register(Referral.class, new ReferralDao(cmsHibernateBundle.getSessionFactory()));
    		DataAccessEnvironment.register(StaffPerson.class, new StaffPersonDao(cmsHibernateBundle.getSessionFactory()));
    		DataAccessEnvironment.register(Allegation.class, new AllegationDao(cmsHibernateBundle.getSessionFactory()));
    		DataAccessEnvironment.register(CrossReport.class, new CrossReportDao(cmsHibernateBundle.getSessionFactory()));
    		DataAccessEnvironment.register(ReferralClient.class, new ReferralClientDao(cmsHibernateBundle.getSessionFactory()));
    		DataAccessEnvironment.register(Reporter.class, new ReporterDao(cmsHibernateBundle.getSessionFactory()));
    		DataAccessEnvironment.register(StaffPersonNS.class, new StaffPersonNSDao(nsHibernateBundle.getSessionFactory()));
    	}
    }
    
    @SuppressWarnings("unchecked")
	private void registerServices(final ApiConfiguration configuration, final ApiEnvironment apiEnvironment) {
    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(), ReferralService.class.getName());  	
    	final CrudsService<gov.ca.cwds.rest.api.domain.legacy.Referral, Referral> referralCrudsService = new CrudsServiceImpl<gov.ca.cwds.rest.api.domain.legacy.Referral, Referral>(DataAccessEnvironment.get(Referral.class), gov.ca.cwds.rest.api.domain.legacy.Referral.class, Referral.class);
    	LOGGER.info("CrudsService:{} for {} of {}", CrudsServiceImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());	    	
    	final ReferralService referralService = new ReferralServiceImpl(referralCrudsService);
    	apiEnvironment.services().register(ReferralService.class, Api.Version.JSON_VERSION_1, referralService);
    	LOGGER.info("ReferralService:{} for {} of {}", ReferralServiceImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), ReferralService.class.getName());
    	
    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());
    	final CrudsService<gov.ca.cwds.rest.api.domain.legacy.StaffPerson, StaffPerson> staffPersonCrudsService = new CrudsServiceCombinedImpl<gov.ca.cwds.rest.api.domain.legacy.StaffPerson, StaffPerson, StaffPersonNS>(DataAccessEnvironment.get(StaffPerson.class), DataAccessEnvironment.get(StaffPersonNS.class),gov.ca.cwds.rest.api.domain.legacy.StaffPerson.class, StaffPerson.class, StaffPersonNS.class);
    	LOGGER.info("CrudsService:{} for {} of {}", CrudsServiceImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());		
    	final StaffPersonService staffPersonService = new StaffPersonServiceImpl(staffPersonCrudsService);
    	apiEnvironment.services().register(StaffPersonService.class, Api.Version.JSON_VERSION_1, staffPersonService);
    	LOGGER.info("StaffPersonService:{} for {} of {}", StaffPersonServiceImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), StaffPersonService.class.getName());
    	
    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(),
				AllegationService.class.getName());
		final CrudsService<gov.ca.cwds.rest.api.domain.legacy.Allegation, Allegation> allegationCrudsService = new CrudsServiceImpl<gov.ca.cwds.rest.api.domain.legacy.Allegation, Allegation>(DataAccessEnvironment.get(Allegation.class), gov.ca.cwds.rest.api.domain.legacy.Allegation.class, Allegation.class);
		LOGGER.info("CrudsService:{} for {} of {}", CrudsServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), AllegationService.class.getName());
		final AllegationService allegationService = new AllegationServiceImpl(allegationCrudsService);
		apiEnvironment.services().register(AllegationService.class, Api.Version.JSON_VERSION_1, allegationService);
		LOGGER.info("AllegationService:{} for {} of {}", AllegationServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), AllegationService.class.getName());
		
    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(),
				ReporterService.class.getName());
		final CrudsService<gov.ca.cwds.rest.api.domain.legacy.Reporter, Reporter> reporterCrudsService = new CrudsServiceImpl<gov.ca.cwds.rest.api.domain.legacy.Reporter, Reporter>(DataAccessEnvironment.get(Reporter.class), gov.ca.cwds.rest.api.domain.legacy.Reporter.class, Reporter.class);
		LOGGER.info("CrudsService:{} for {} of {}", CrudsServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), ReporterService.class.getName());
		final ReporterService reporterService = new ReporterServiceImpl(reporterCrudsService);
		apiEnvironment.services().register(ReporterService.class, Api.Version.JSON_VERSION_1, reporterService);
		LOGGER.info("ReporterService:{} for {} of {}", ReporterServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), ReporterService.class.getName());

    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(),
				CrossReportService.class.getName());
		final CrudsService<gov.ca.cwds.rest.api.domain.legacy.CrossReport, CrossReport> crossReportCrudsService = new CrudsServiceImpl<gov.ca.cwds.rest.api.domain.legacy.CrossReport, CrossReport>(DataAccessEnvironment.get(CrossReport.class), gov.ca.cwds.rest.api.domain.legacy.CrossReport.class, CrossReport.class);
		LOGGER.info("CrudsService:{} for {} of {}", CrudsServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), CrossReportService.class.getName());
		final CrossReportService crossReportService = new CrossReportServiceImpl(crossReportCrudsService);
		apiEnvironment.services().register(CrossReportService.class, Api.Version.JSON_VERSION_1, crossReportService);
		LOGGER.info("CrossReportService:{} for {} of {}", CrossReportServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), CrossReportService.class.getName());
		
    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(),
				ReferralClientService.class.getName());
		final CrudsService<gov.ca.cwds.rest.api.domain.legacy.ReferralClient, ReferralClient> referralClientCrudsService = new CrudsServiceImpl<gov.ca.cwds.rest.api.domain.legacy.ReferralClient, ReferralClient>(DataAccessEnvironment	.get(ReferralClient.class), gov.ca.cwds.rest.api.domain.legacy.ReferralClient.class, ReferralClient.class);
		LOGGER.info("CrudsService:{} for {} of {}", CrudsServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), ReferralClientService.class.getName());
		final ReferralClientService referralClientService = new ReferralClientServiceImpl(referralClientCrudsService);
		apiEnvironment.services().register(ReferralClientService.class, Api.Version.JSON_VERSION_1, referralClientService);
		LOGGER.info("ReferralClientService:{} for {} of {}", ReferralClientServiceImpl.class.getName(),
				Api.Version.JSON_VERSION_1.getMediaType(), ReferralClientService.class.getName());
		
    	LOGGER.info("Registering {} of {}", Api.Version.JSON_VERSION_1.getMediaType(), gov.ca.cwds.rest.services.intake.ReferralService.class.getName());  	
    	final gov.ca.cwds.rest.services.intake.ReferralService intakeReferralService = new gov.ca.cwds.rest.services.intake.ReferralServiceImpl(referralService, allegationService, crossReportService, referralClientService, reporterService);
    	apiEnvironment.services().register(gov.ca.cwds.rest.services.intake.ReferralService.class, Api.Version.JSON_VERSION_1, intakeReferralService);
    	LOGGER.info("ReferralService:{} for {} of {}", gov.ca.cwds.rest.services.intake.ReferralServiceImpl.class.getName(), Api.Version.JSON_VERSION_1.getMediaType(), gov.ca.cwds.rest.services.intake.ReferralService.class.getName());
    }
    
    private void registerResources(final ApiConfiguration configuration, final ApiEnvironment apiEnvironment) {    	
        LOGGER.info("Registering ApplicationResource");
        final ApplicationResource applicationResource = new ApplicationResourceImpl(configuration.getApplicationName());
        apiEnvironment.jersey().register(applicationResource);
        
        LOGGER.info("Registering Legacy ReferralResource");
        CrudsResource<gov.ca.cwds.rest.api.domain.legacy.Referral> referralCrudsResource = new CrudsResourceImpl<gov.ca.cwds.rest.api.domain.legacy.Referral, ReferralService>(apiEnvironment.services(), ReferralService.class);
        final ReferralResource referralResource = new ReferralResourceImpl(apiEnvironment.services(), referralCrudsResource);
        apiEnvironment.jersey().register(referralResource);
        
        LOGGER.info("Registering Legacy StaffPersonResource");
        CrudsResource<gov.ca.cwds.rest.api.domain.legacy.StaffPerson> staffPersonCrudsResource = new CrudsResourceImpl<gov.ca.cwds.rest.api.domain.legacy.StaffPerson, StaffPersonService>(apiEnvironment.services(), StaffPersonService.class);
        final StaffPersonResource staffPersonResource = new StaffPersonResourceImpl(apiEnvironment.services(), staffPersonCrudsResource);
        apiEnvironment.jersey().register(staffPersonResource);
        
        LOGGER.info("Registering Legacy CrossReportResource");
        CrudsResource<gov.ca.cwds.rest.api.domain.legacy.CrossReport> crossReportCrudsResource = new CrudsResourceImpl<gov.ca.cwds.rest.api.domain.legacy.CrossReport, CrossReportService>(apiEnvironment.services(), CrossReportService.class);
        final CrossReportResource crossReportResource = new CrossReportResourceImpl(apiEnvironment.services(), crossReportCrudsResource);
        apiEnvironment.jersey().register(crossReportResource);
        
        LOGGER.info("Registering Legacy ReferralClientResource");
        CrudsResource<gov.ca.cwds.rest.api.domain.legacy.ReferralClient> referralClientCrudsResource = new CrudsResourceImpl<gov.ca.cwds.rest.api.domain.legacy.ReferralClient, ReferralClientService>(apiEnvironment.services(), ReferralClientService.class);
        final ReferralClientResource referralClientResource = new ReferralClientResourceImpl(apiEnvironment.services(), referralClientCrudsResource);
        apiEnvironment.jersey().register(referralClientResource);
        
        LOGGER.info("Registering Legacy ReporterResource");
        CrudsResource<gov.ca.cwds.rest.api.domain.legacy.Reporter> reporterCrudsResource = new CrudsResourceImpl<gov.ca.cwds.rest.api.domain.legacy.Reporter, ReporterService>(apiEnvironment.services(), ReporterService.class);
        final ReporterResource reporterResource = new ReporterResourceImpl(apiEnvironment.services(), reporterCrudsResource);
        apiEnvironment.jersey().register(reporterResource);
        
        LOGGER.info("Registering Intake ReferralResource");
        final gov.ca.cwds.rest.resources.intake.ReferralResourceImpl intakeReferralResource = new gov.ca.cwds.rest.resources.intake.ReferralResourceImpl(apiEnvironment.services());
        apiEnvironment.jersey().register(intakeReferralResource);
        
        LOGGER.info("Registering ApiListingResource");
     	apiEnvironment.jersey().register(new ApiListingResource());
     	
        LOGGER.info("Registering SwaggerResource");
        final SwaggerResource swaggerResource = new SwaggerResource(configuration.getSwaggerConfiguration());
     	apiEnvironment.jersey().register(swaggerResource);
     	
     	LOGGER.info("Registering AllegationResource");
		CrudsResource<gov.ca.cwds.rest.api.domain.legacy.Allegation> allegationCrudsResource = new CrudsResourceImpl<gov.ca.cwds.rest.api.domain.legacy.Allegation, AllegationService>(
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
