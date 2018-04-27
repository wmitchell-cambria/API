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

import gov.ca.cwds.rest.services.hoi.HOICaseService;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * @author CWDS API Team
 *
 */
public class HoiCasesResourceTest {

  private static final HOICaseService hoiCaseService = mock(HOICaseService.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new HoiCaseResource(hoiCaseService)).build();

  /**
   * 
   */
  @Before
  public void setup() {
    reset(hoiCaseService);
  }

  /**
   * test to find the hoi cases using clientIds
   */
  @Test
  public void findHoiCasesbyClientIds() {
    List<String> clientIds = new ArrayList<>();
    clientIds.add("1zxcydd");
    inMemoryResource.client().target("/hoi_cases").queryParam("clientIds", "1zxcydd").request()
        .accept(MediaType.APPLICATION_JSON).get();
    verify(hoiCaseService, atLeastOnce()).handleFind(new HOIRequest(clientIds));
  }

}
