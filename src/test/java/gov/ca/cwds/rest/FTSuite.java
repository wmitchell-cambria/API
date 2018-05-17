package gov.ca.cwds.rest;

import gov.ca.cwds.rest.resources.ScreeningIntakeResourceFT;
import gov.ca.cwds.rest.resources.hoi.HoiCaseResourceFT;
import gov.ca.cwds.rest.resources.hoi.HoiScreeningResourceFT;
import gov.ca.cwds.rest.resources.hoi.InvolvementHistoryResourceFT;
import gov.ca.cwds.test.support.BaseDropwizardApplication;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        HoiCaseResourceFT.class,
        HoiScreeningResourceFT.class,
        InvolvementHistoryResourceFT.class,
        ScreeningIntakeResourceFT.class
})
public class FTSuite {

  @ClassRule
  public static final BaseDropwizardApplication<ApiConfiguration> application =
          new BaseDropwizardApplication<>(ApiApplication.class, "config/test-api.yml");

  protected BaseDropwizardApplication<ApiConfiguration> getApplication() {
    return application;
  }
}
