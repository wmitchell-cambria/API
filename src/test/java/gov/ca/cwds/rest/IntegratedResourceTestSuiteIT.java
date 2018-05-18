package gov.ca.cwds.rest;

import gov.ca.cwds.rest.resources.ScreeningIntakeResourceIRT;
import gov.ca.cwds.rest.resources.hoi.HoiCaseResourceIRT;
import gov.ca.cwds.rest.resources.hoi.HoiScreeningResourceIRT;
import gov.ca.cwds.rest.resources.hoi.InvolvementHistoryResourceIRT;
import gov.ca.cwds.test.support.BaseDropwizardApplication;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        HoiCaseResourceIRT.class,
        HoiScreeningResourceIRT.class,
        InvolvementHistoryResourceIRT.class,
        ScreeningIntakeResourceIRT.class
})
public class IntegratedResourceTestSuiteIT {

  @ClassRule
  public static final BaseDropwizardApplication<ApiConfiguration> application =
          new BaseDropwizardApplication<>(ApiApplication.class, "config/test-api.yml");

  protected BaseDropwizardApplication<ApiConfiguration> getApplication() {
    return application;
  }
}
