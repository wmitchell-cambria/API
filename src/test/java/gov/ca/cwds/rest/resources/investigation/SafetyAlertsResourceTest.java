package gov.ca.cwds.rest.resources.investigation;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.fixture.investigation.SafetyAlertsEntityBuilder;
import gov.ca.cwds.rest.api.domain.investigation.SafetyAlerts;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.testing.junit.ResourceTestRule;

/****
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class SafetyAlertsResourceTest {
  private static final String ROOT_RESOURCE = "/investigations/1/safety_alerts";

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("unchecked")
  private final static TypedResourceDelegate<String, SafetyAlerts> typedResourceDelegate =
      mock(TypedResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource = ResourceTestRule.builder()
      .addResource(new SafetyAlertsResource(typedResourceDelegate)).build();

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  // create
  @Test
  @Ignore
  public void updateDelegatesToResourceDelegate() throws Exception {
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(safetyAlerts, MediaType.APPLICATION_JSON));
    verify(typedResourceDelegate).update(eq("1"), eq(safetyAlerts));

  }

  // find
  @Test
  public void findDelgatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get().getStatus();
    verify(typedResourceDelegate, atLeastOnce()).get("1");
  }

  // update
  @Test
  public void createDelegatesToResourceDelegate() throws Exception {
    SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();
    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(safetyAlerts, MediaType.APPLICATION_JSON));
    verify(typedResourceDelegate).create(safetyAlerts);

  }

}
