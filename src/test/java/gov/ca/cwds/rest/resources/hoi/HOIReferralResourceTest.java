package gov.ca.cwds.rest.resources.hoi;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.resources.SimpleResourceDelegate;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import gov.ca.cwds.rest.services.hoi.HOIReferralService;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * @author CWDS API Team
 *
 */
public class HOIReferralResourceTest {

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static SimpleResourceDelegate<HOIRequest, HOIReferral, HOIReferralResponse, HOIReferralService> simpleResourceDelegate =
      mock(SimpleResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource = ResourceTestRule.builder()
      .addResource(new HOIReferralResource(simpleResourceDelegate)).build();

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
    assertThat(HOIReferralResource.class, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void instantiation() throws Exception {
    HOIReferralResource target = new HOIReferralResource(simpleResourceDelegate);
    assertThat(target, notNullValue());
  }

}
