package gov.ca.cwds.rest.services.auth;

import org.junit.Test;

import gov.ca.cwds.rest.api.domain.auth.AuthorizationCheckingRequest;

/**
 * Unit tests for AuthorizationCheckingService
 * 
 * @author CWDS API Team
 */
public class AuthorizationCheckingServiceTest {

  @Test
  public void testHandleRequest() {
    AuthorizationCheckingRequest request = new AuthorizationCheckingRequest("1234");
    AuthorizationCheckingService service = new AuthorizationCheckingService();
    service.handleRequest(request);
  }

  @Test
  public void testHandleRequestWithNullRequest() {
    AuthorizationCheckingService service = new AuthorizationCheckingService();
    service.handleRequest(null);
  }

  @Test
  public void testHandleRequestWithEmptyId() {
    AuthorizationCheckingRequest request = new AuthorizationCheckingRequest("");
    AuthorizationCheckingService service = new AuthorizationCheckingService();
    service.handleRequest(request);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testHandleFind() {
    AuthorizationCheckingService service = new AuthorizationCheckingService();
    service.handleFind("1234");
  }

}
