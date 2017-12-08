package gov.ca.cwds.rest.resources.hoi;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

import org.hamcrest.junit.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.SimpleResourceDelegate;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import gov.ca.cwds.rest.services.hoi.ReferralHOIService;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * @author CWDS API Team
 *
 */
public class ReferralHOIResourceTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT;

  /**
   * 
   */
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static SimpleResourceDelegate<String, HOIReferral, HOIReferralResponse, ReferralHOIService> simpleResourceDelegate =
      mock(SimpleResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource = ResourceTestRule.builder()
      .addResource(new ReferralHOIResource(simpleResourceDelegate)).build();

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
    assertThat(ReferralHOIResource.class, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void instantiation() throws Exception {
    ReferralHOIResource target = new ReferralHOIResource(simpleResourceDelegate);
    assertThat(target, notNullValue());
  }

}
