package gov.ca.cwds.rest;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.rest.api.persistence.cms.Allegation;
import gov.ca.cwds.rest.api.persistence.cms.CrossReport;
import gov.ca.cwds.rest.api.persistence.cms.CmsDocument;
import gov.ca.cwds.rest.api.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.rest.api.persistence.cms.Referral;
import gov.ca.cwds.rest.api.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.api.persistence.cms.Reporter;
import gov.ca.cwds.rest.api.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.api.persistence.ns.Address;
import gov.ca.cwds.rest.api.persistence.ns.Person;
import gov.ca.cwds.rest.api.persistence.ns.Screening;
import gov.ca.cwds.rest.jdbi.DataAccessEnvironment;
import gov.ca.cwds.rest.jdbi.cms.AllegationDao;
import gov.ca.cwds.rest.jdbi.cms.CrossReportDao;
import gov.ca.cwds.rest.jdbi.cms.CmsDocumentDao;
import gov.ca.cwds.rest.jdbi.cms.ReferralClientDao;
import gov.ca.cwds.rest.jdbi.cms.ReferralDao;
import gov.ca.cwds.rest.jdbi.cms.ReporterDao;
import gov.ca.cwds.rest.jdbi.cms.StaffPersonDao;
import gov.ca.cwds.rest.jdbi.ns.AddressDao;
import gov.ca.cwds.rest.jdbi.ns.PersonDao;
import gov.ca.cwds.rest.jdbi.ns.ScreeningDao;
import gov.ca.cwds.rest.resources.AddressResource;
import gov.ca.cwds.rest.resources.ApplicationResource;
import gov.ca.cwds.rest.resources.PersonResource;
import gov.ca.cwds.rest.resources.ScreeningResource;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.SwaggerResource;
import gov.ca.cwds.rest.resources.cms.CmsDocumentResource;
import gov.ca.cwds.rest.resources.cms.StaffPersonResource;
import gov.ca.cwds.rest.services.AddressService;
import gov.ca.cwds.rest.services.CmsDocumentService;
import gov.ca.cwds.rest.services.PersonService;
import gov.ca.cwds.rest.services.ScreeningService;
import gov.ca.cwds.rest.services.cms.StaffPersonService;
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

  private final HibernateBundle<ApiConfiguration> cmsHibernateBundle =
      new HibernateBundle<ApiConfiguration>(StaffPerson.class, Referral.class, Allegation.class,
          CrossReport.class, ReferralClient.class, Reporter.class, CmsDocument.class,
          CmsDocumentBlobSegment.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
          return configuration.getCmsDataSourceFactory();
        }

        @Override
        public String name() {
          return "cms";
        }
      };

  private final HibernateBundle<ApiConfiguration> nsHibernateBundle =
      new HibernateBundle<ApiConfiguration>(Person.class, Address.class, Screening.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
          return configuration.getNsDataSourceFactory();
        }

        @Override
        public String name() {
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
    bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
        bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor()));
    bootstrap.addBundle(new ViewBundle<ApiConfiguration>());

    LOGGER.info("Loading database bundles");
    bootstrap.addBundle(flywayBundle);
    bootstrap.addBundle(cmsHibernateBundle);
    bootstrap.addBundle(nsHibernateBundle);
  }

  @Override
  public void run(final ApiConfiguration configuration, final Environment environment)
      throws Exception {
    environment.jersey().getResourceConfig().packages(getClass().getPackage().getName())
        .register(DeclarativeLinkingFeature.class);

    LOGGER.info("Application name: {}", configuration.getApplicationName());
    ApiEnvironment apiEnvironment = new ApiEnvironment(environment);

    LOGGER.info("Preparing DAOs");
    setupDaos(configuration);

    LOGGER.info("Registering Application Resources");
    registerResources(configuration, apiEnvironment);

    LOGGER.info("Registering Health Checks");
    registerHealthChecks(apiEnvironment);

    LOGGER.info("Configuring CORS: Cross-Origin Resource Sharing");
    configureCors(apiEnvironment);

    LOGGER.info("Configuring SWAGGER");
    configureSwagger(configuration, apiEnvironment);
  }

  private void registerHealthChecks(final ApiEnvironment apiEnvironment) {}

  private void setupDaos(final ApiConfiguration configuration) {
    LOGGER.info("Setting up production DAOs");
    DataAccessEnvironment.register(Person.class,
        new PersonDao(nsHibernateBundle.getSessionFactory()));
    DataAccessEnvironment.register(Address.class,
        new AddressDao(nsHibernateBundle.getSessionFactory()));
    DataAccessEnvironment.register(Screening.class,
        new ScreeningDao(nsHibernateBundle.getSessionFactory()));

    DataAccessEnvironment.register(Referral.class,
        new ReferralDao(cmsHibernateBundle.getSessionFactory()));
    DataAccessEnvironment.register(StaffPerson.class,
        new StaffPersonDao(cmsHibernateBundle.getSessionFactory()));
    DataAccessEnvironment.register(Allegation.class,
        new AllegationDao(cmsHibernateBundle.getSessionFactory()));
    DataAccessEnvironment.register(CrossReport.class,
        new CrossReportDao(cmsHibernateBundle.getSessionFactory()));
    DataAccessEnvironment.register(ReferralClient.class,
        new ReferralClientDao(cmsHibernateBundle.getSessionFactory()));
    DataAccessEnvironment.register(Reporter.class,
        new ReporterDao(cmsHibernateBundle.getSessionFactory()));

    DataAccessEnvironment.register(CmsDocument.class,
        new CmsDocumentDao(cmsHibernateBundle.getSessionFactory()));
  }

  private void registerResources(final ApiConfiguration configuration,
      final ApiEnvironment apiEnvironment) {
    LOGGER.info("Registering ApplicationResource");
    final ApplicationResource applicationResource =
        new ApplicationResource(configuration.getApplicationName());
    apiEnvironment.jersey().register(applicationResource);

    final PersonService personService =
        new PersonService((PersonDao) DataAccessEnvironment.get(Person.class));
    final ScreeningService screeningService = new ScreeningService(
        (ScreeningDao) DataAccessEnvironment.get(Screening.class), personService);

    LOGGER.info("Registering AddressResource");
    AddressResource addressResource = new AddressResource(new ServiceBackedResourceDelegate(
        new AddressService((AddressDao) DataAccessEnvironment.get(Address.class))));
    apiEnvironment.jersey().register(addressResource);

    LOGGER.info("Registering PersonResource");
    PersonResource personResource =
        new PersonResource(new ServiceBackedResourceDelegate(personService));
    apiEnvironment.jersey().register(personResource);

    LOGGER.info("Registering ScreeningResource");
    ScreeningResource screeningResource =
        new ScreeningResource(new ServiceBackedResourceDelegate(screeningService));
    apiEnvironment.jersey().register(screeningResource);

    LOGGER.info("Registering CmsDocumentResource");
    final CmsDocumentService docService =
        new CmsDocumentService((CmsDocumentDao) DataAccessEnvironment.get(CmsDocument.class));
    CmsDocumentResource docResource =
        new CmsDocumentResource(new ServiceBackedResourceDelegate(docService));
    apiEnvironment.jersey().register(docResource);

    LOGGER.info("Registering StaffPersonResource");
    StaffPersonService staffPersonService =
        new StaffPersonService((StaffPersonDao) DataAccessEnvironment.get(StaffPerson.class));
    StaffPersonResource staffPersonResource =
        new StaffPersonResource(new ServiceBackedResourceDelegate(staffPersonService));
    apiEnvironment.jersey().register(staffPersonResource);
  }

  private void configureCors(final ApiEnvironment apiEnvironment) {
    FilterRegistration.Dynamic filter =
        apiEnvironment.servlets().addFilter("CORS", CrossOriginFilter.class);
    filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
    filter.setInitParameter("allowedHeaders",
        "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,X-Auth-Token");
    filter.setInitParameter("allowCredentials", "true");
  }

  private void configureSwagger(final ApiConfiguration apiConfiguration,
      final ApiEnvironment apiEnvironment) {
    BeanConfig config = new BeanConfig();
    config.setTitle(apiConfiguration.getSwaggerConfiguration().getTitle());
    config.setDescription(apiConfiguration.getSwaggerConfiguration().getDescription());
    config.setResourcePackage(apiConfiguration.getSwaggerConfiguration().getResourcePackage());
    config.setScan(true);

    new AssetsBundle(apiConfiguration.getSwaggerConfiguration().getAssetsPath(),
        apiConfiguration.getSwaggerConfiguration().getAssetsPath(), null, "swagger")
            .run(apiEnvironment.environment());
    apiEnvironment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    apiEnvironment.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    LOGGER.info("Registering ApiListingResource");
    apiEnvironment.jersey().register(new ApiListingResource());

    LOGGER.info("Registering SwaggerResource");
    final SwaggerResource swaggerResource =
        new SwaggerResource(apiConfiguration.getSwaggerConfiguration());
    apiEnvironment.jersey().register(swaggerResource);

  }
}
