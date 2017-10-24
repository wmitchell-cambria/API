package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.fixture.investigation.SafetyAlerts;

@SuppressWarnings("javadoc")
public class SafetyAlertsTest {
  private ObjectMapper MAPPER = new ObjectMapper();
  private Validator validator;

  private Set<String> alerts = new HashSet<>();
  private String alertInformation = "information about the safety alert on this referral";

  @Before
  public void setup() {
    alerts.add("6401");
    alerts.add("6402");
  }

  @Test
  public void testEmptyConstructorSuccess() {
    SafetyAlerts safetyAlerts = new SafetyAlerts();
    assertNotNull(safetyAlerts);

  }


  @Test
  public void testDomainConstructory() {
    SafetyAlerts safetyAlerts = new SafetyAlerts(alerts, alertInformation);
    assertThat(alerts, is(equalTo(safetyAlerts.getSafetyAlerts())));
    assertThat(alertInformation, is(equalTo(safetyAlerts.getAlertInformation())));
  }
}
