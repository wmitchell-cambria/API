package gov.ca.cwds;

import org.junit.BeforeClass;
import org.junit.ClassRule;

import gov.ca.cwds.rest.ApiApplication;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.test.support.BaseApiTest;
import gov.ca.cwds.test.support.BaseDropwizardApplication;
import gov.ca.cwds.test.support.DatabaseHelper;

/**
 * CWDS API Team
 */
public abstract class IntakeBaseTest extends BaseApiTest<ApiConfiguration> {

  @ClassRule
  public static final BaseDropwizardApplication<ApiConfiguration> application =
      new BaseDropwizardApplication<>(ApiApplication.class, "config/test-api.yml");

  @Override
  protected BaseDropwizardApplication<ApiConfiguration> getApplication() {
    return application;
  }

  @BeforeClass
  public static void beforeClass() throws Exception {
    ApiConfiguration configuration = application.getConfiguration();

    // DRS: Please document **why** this code is commented out.
    // Telepathy not working. Too much tin foil in hat.
     new DatabaseHelper( configuration.getNsDataSourceFactory().getUrl(),
       configuration.getNsDataSourceFactory().getUser(),
       configuration.getNsDataSourceFactory().getPassword())
         .runScript("liquibase/api/api_intake_ns_database_master.xml");

    new DatabaseHelper(configuration.getCmsDataSourceFactory().getUrl(),
        configuration.getCmsDataSourceFactory().getUser(),
        configuration.getCmsDataSourceFactory().getPassword())
            .runScript("liquibase/api/api_cwsint_database_master.xml");

    new DatabaseHelper(configuration.getRsDataSourceFactory().getUrl(),
        configuration.getRsDataSourceFactory().getUser(),
        configuration.getRsDataSourceFactory().getPassword())
            .runScript("liquibase/api/api_cwsrs_database_master.xml");
  }
}
