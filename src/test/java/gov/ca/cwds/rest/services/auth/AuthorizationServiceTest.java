package gov.ca.cwds.rest.services.auth;

import java.util.Arrays;

import org.junit.Test;

import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.fixture.ParticipantEntityBuilder;
import gov.ca.cwds.fixture.ScreeningEntityBuilder;
import gov.ca.cwds.rest.api.domain.auth.AuthorizationRequest;

/**
 * Unit tests for AuthorizationCheckingService
 * 
 * @author CWDS API Team
 */
public class AuthorizationServiceTest {

  @Test
  public void testHandleRequest() {
    AuthorizationRequest request = new AuthorizationRequest("1234");
    AuthorizationService service = new AuthorizationService();
    service.handleRequest(request);
  }

  @Test
  public void testHandleRequestWithNullRequest() {
    AuthorizationService service = new AuthorizationService();
    service.handleRequest(null);
  }

  @Test
  public void testHandleRequestWithEmptyId() {
    AuthorizationRequest request = new AuthorizationRequest("");
    AuthorizationService service = new AuthorizationService();
    service.handleRequest(request);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testHandleFind() {
    AuthorizationService service = new AuthorizationService();
    service.handleFind("1234");
  }

  @Test
  public void testEnsureClientAccessAuthorized() {
    AuthorizationService service = new AuthorizationService();
    service.ensureClientAccessAuthorized("123");
  }

  @Test
  public void testEnsureClientCollectionAccessAuthorized() {
    AuthorizationService service = new AuthorizationService();
    service.ensureClientAccessAuthorized(Arrays.asList("a", "b"));
  }

  @Test
  public void testEnsureNullScreeningAccessAuthorized() {
    AuthorizationService service = new AuthorizationService();
    service.ensureScreeningAccessAuthorized(null);
  }

  @Test
  public void testEnsureScreeningAccessAuthorized() {
    AuthorizationService service = new AuthorizationService();

    ScreeningEntity screening = new ScreeningEntityBuilder()
        .addParticipant(new ParticipantEntityBuilder().setLegacyId("abc").build())
        .addParticipant(new ParticipantEntityBuilder().setLegacyId("123").build()).build();
    screening.setAccessRestrictions("sealed");
    service.ensureScreeningAccessAuthorized(screening);
  }
}
