package gov.ca.cwds.rest.resources.hoi;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
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
public class HoiScreeningResourceTest {

  private static final HOIScreeningService hoiScreeningService = mock(HOIScreeningService.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new HoiScreeningResource(hoiScreeningService)).build();

  @Before
  public void setup() {
    reset(hoiScreeningService);
  }

  @Test
  public void findHoiScreeningsByClientIds() {
    List<String> clientIds = new ArrayList<>();
    clientIds.add("1zxcydd");
    inMemoryResource.client().target("/hoi_screenings").queryParam("clientIds", "1zxcydd").request()
        .accept(MediaType.APPLICATION_JSON).get();
    verify(hoiScreeningService, atLeastOnce()).handleFind(new HOIRequest(clientIds));
  }

}
