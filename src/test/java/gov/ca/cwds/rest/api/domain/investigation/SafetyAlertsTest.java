package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.fixture.investigation.SafetyAlertsEntityBuilder;

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
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testEmptyConstructorSuccess() {
    SafetyAlerts safetyAlerts = new SafetyAlerts();
    assertNotNull(safetyAlerts);
  }

  @Test
  public void testDomainConstructor() {
    SafetyAlerts safetyAlerts = new SafetyAlerts(alerts, alertInformation);
    assertThat(alerts, is(equalTo(safetyAlerts.getSafetyAlerts())));
    assertThat(alertInformation, is(equalTo(safetyAlerts.getAlertInformation())));
  }

  @Test
  public void testValidationSuccess() {
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().setAlerts(alerts).build();
    Set<ConstraintViolation<SafetyAlerts>> constraintViolations = validator.validate(safetyAlerts);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testValidationWithEmptyAlertInformation() {
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().setAlertInformation("").build();
    Set<ConstraintViolation<SafetyAlerts>> constraintViolations = validator.validate(safetyAlerts);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldValidateEmptyAlertsSafetyAlerts() {
    final Set<String> alerts = new HashSet<>();
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().setAlerts(alerts).build();
    Set<ConstraintViolation<SafetyAlerts>> constraintViolations = validator.validate(safetyAlerts);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  @Ignore
  public void testSerilizedOutput()
      throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().setAlerts(alerts).build();
    final String expected =
        MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(safetyAlerts);
    System.out.println(expected);
  }

}
