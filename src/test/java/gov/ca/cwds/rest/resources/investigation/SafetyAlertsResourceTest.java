package gov.ca.cwds.rest.resources.investigation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.investigation.SafetyAlertsEntityBuilder;
import gov.ca.cwds.rest.api.domain.investigation.SafetyAlerts;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;

/****
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class SafetyAlertsResourceTest {

  private SafetyAlertsResource resource;
  private SafetyAlerts safetyAlerts;
  private TypedResourceDelegate<String, SafetyAlerts> service;
  private String referralId = "1234567ABC";

  @SuppressWarnings("unchecked")
  @Before
  public void setup() {
    service = mock(TypedResourceDelegate.class);
    resource = new SafetyAlertsResource(service);
  }

  @Test
  public void callSafetyAlertsServiceOnCreate() throws Exception {
    safetyAlerts = new SafetyAlertsEntityBuilder().build();
    resource.create(referralId, safetyAlerts);
    verify(service).create(safetyAlerts);
  }

  @Test
  public void callSafetyAlertsServiceOnFind() throws Exception {
    resource.find(referralId);
    verify(service).get(referralId);
  }

}
