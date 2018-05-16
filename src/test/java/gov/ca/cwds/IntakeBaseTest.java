package gov.ca.cwds;

import gov.ca.cwds.rest.FTSuite;
import gov.ca.cwds.test.support.DatabaseHelper;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.test.support.BaseApiTest;
import gov.ca.cwds.test.support.BaseDropwizardApplication;

/**
 * CWDS API Team
 */
public abstract class IntakeBaseTest extends BaseApiTest<ApiConfiguration> {

  @ClassRule
  public static final BaseDropwizardApplication<ApiConfiguration> application =
          FTSuite.application;

  protected BaseDropwizardApplication<ApiConfiguration> getApplication() {
    return application;
  }

  @BeforeClass
  public static void beforeClass() throws Exception {
    ApiConfiguration configuration = application.getConfiguration();

    new gov.ca.cwds.test.support.DatabaseHelper( configuration.getNsDataSourceFactory().getUrl(),
        configuration.getNsDataSourceFactory().getUser(),
        configuration.getNsDataSourceFactory().getPassword())
        .runScript("liquibase/api/api_intake_ns_database_master.xml");

    new gov.ca.cwds.test.support.DatabaseHelper(configuration.getCmsDataSourceFactory().getUrl(),
        configuration.getCmsDataSourceFactory().getUser(),
        configuration.getCmsDataSourceFactory().getPassword())
        .runScript("liquibase/api/api_cwsint_database_master.xml");

    new DatabaseHelper(configuration.getRsDataSourceFactory().getUrl(),
        configuration.getRsDataSourceFactory().getUser(),
        configuration.getRsDataSourceFactory().getPassword())
        .runScript("liquibase/api/api_cwsrs_database_master.xml");
  }
}
