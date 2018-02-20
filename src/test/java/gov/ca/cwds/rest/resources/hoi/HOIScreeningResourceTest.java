package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_HOI_SCREENINGS;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningResponse;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.SimpleResourceDelegate;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import gov.ca.cwds.rest.services.hoi.HOIScreeningService;
import io.dropwizard.testing.junit.ResourceTestRule;

/****
 * NOTE:The CWDS API Team has taken the pattern of delegating Resource functions to
 *
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 *
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class HOIScreeningResourceTest {

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("unchecked")
  private final static SimpleResourceDelegate<HOIRequest, HOIScreening, HOIScreeningResponse, HOIScreeningService> simpleResourceDelegate =
      mock(SimpleResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource = ResourceTestRule.builder()
      .addResource(new HOIScreeningResource(simpleResourceDelegate)).build();

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void findDelegatesToResourceDelegate() {
    Set<String> clientIds = new HashSet<>();
    clientIds.add("1");
    HOIRequest hoiScreeningRequest = new HOIRequest();
    hoiScreeningRequest.setClientIds(clientIds);
    Entity requestEntity = Entity.entity(hoiScreeningRequest, MediaType.APPLICATION_JSON_TYPE);

    inMemoryResource.client().target("/" + RESOURCE_HOI_SCREENINGS).request()
        .accept(MediaType.APPLICATION_JSON).post(requestEntity).getStatus();
    verify(simpleResourceDelegate, atLeastOnce()).find(hoiScreeningRequest);
  }

}
