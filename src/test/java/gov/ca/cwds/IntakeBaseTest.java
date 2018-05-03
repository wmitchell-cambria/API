package gov.ca.cwds;

import gov.ca.cwds.rest.FTSuite;
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

}
