package gov.ca.cwds.rest.resources.auth;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import gov.ca.cwds.rest.api.domain.auth.AuthorizationCheckingRequest;
import gov.ca.cwds.rest.api.domain.auth.AuthorizationCheckingResponse;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.SimpleResourceDelegate;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import gov.ca.cwds.rest.services.auth.AuthorizationCheckingService;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * Unit tests for AuthorizationCheckingResource
 * 
 * @author CWDS API Team
 *
 */
public class AuthorizationCheckingResourceTest {

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static SimpleResourceDelegate<String, AuthorizationCheckingRequest, AuthorizationCheckingResponse, AuthorizationCheckingService> simpleResourceDelegate =
      mock(SimpleResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource = ResourceTestRule.builder()
      .addResource(new AuthorizationCheckingResource(simpleResourceDelegate)).build();

  /**
   * @throws Exception - Exception
   */
  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    Mockito.reset(simpleResourceDelegate);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void type() throws Exception {
    assertThat(AuthorizationCheckingResource.class, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void instantiation() throws Exception {
    AuthorizationCheckingResource target =
        new AuthorizationCheckingResource(simpleResourceDelegate);
    assertThat(target, notNullValue());
  }

  @Test
  public void testGetClientAuthorizeSuccess() throws Exception {
    String target = "/" + Api.RESOURCE_AUTHORIZE + "/client/1234";
    int status = inMemoryResource.client().target(target).request().get().getStatus();
    assertThat(status, is(200));
  }
}
