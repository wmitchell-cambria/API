package gov.ca.cwds.health;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.codahale.metrics.health.HealthCheck.Result;
import gov.ca.cwds.health.resource.Pingable;
import org.junit.Before;
import org.junit.Test;

public class PingableResourceHealthCheckTest {
  private static final boolean HEALTHY = true;
  private static final boolean UNHEALTHY = false;

  Pingable pingable;
  PingableResourceHealthCheck healthCheck;

  @Before
  public void setup(){
    pingable = mock(Pingable.class);
    when(pingable.ping()).thenReturn(true);

    healthCheck = new SwaggerHealthCheck(pingable);
  }

  @Test
  public void shouldReturnHealthyWhenResourceIsSuccesfullyPinged() throws Exception {
    assertEquals(HEALTHY, healthCheck.check().isHealthy());
  }

  @Test
  public void shouldReturnUnHealthyWhenResourceIsUnSuccesfullyPinged() throws Exception {
    when(pingable.ping()).thenReturn(false);
    assertEquals(UNHEALTHY, healthCheck.check().isHealthy());
  }

  @Test
  public void shouldContainErrorMessgeWhenUnsuccessfullyPinged() throws Exception {
    String message = "Unable to reach Swagger endpoint";
    String resourceErrorMsg = "Status: 500";
    when(pingable.getMessage()).thenReturn(resourceErrorMsg);
    when(pingable.ping()).thenReturn(false);

    String expectedMessage = message + " " + resourceErrorMsg;
    assertEquals(expectedMessage, healthCheck.check().getMessage());
  }
}