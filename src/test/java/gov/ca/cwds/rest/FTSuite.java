package gov.ca.cwds.rest;

import gov.ca.cwds.rest.resources.ScreeningIntakeResourceFT;
import gov.ca.cwds.rest.resources.hoi.HoiCaseResourceFT;
import gov.ca.cwds.test.support.BaseDropwizardApplication;
import gov.ca.cwds.test.support.DatabaseHelper;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        HoiCaseResourceFT.class,
        ScreeningIntakeResourceFT.class
})
public class FTSuite {

  @ClassRule
  public static final BaseDropwizardApplication<ApiConfiguration> application =
          new BaseDropwizardApplication<>(ApiApplication.class, "config/test-api.yml");

  protected BaseDropwizardApplication<ApiConfiguration> getApplication() {
    return application;
  }

  @BeforeClass
  public static void beforeClass() throws Exception {
    ApiConfiguration configuration = application.getConfiguration();
    // DRS: Please document **why** this code is commented out.
    // Telepathy not working. Too much tin foil in hat.
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
