package gov.ca.cwds.rest.resources.hoi;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import gov.ca.cwds.rest.services.hoi.HoiUsingClientIdService;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * @author CWDS API Team
 *
 */
public class HoiUsingClientIdsResourceTest {

  private static final HoiUsingClientIdService hoiUsingClientIdService =
      mock(HoiUsingClientIdService.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource = ResourceTestRule.builder()
      .addResource(new HoiUsingClientIdResource(hoiUsingClientIdService)).build();

  /**
   * 
   */
  @Before
  public void setup() {
    reset(hoiUsingClientIdService);
  }

  /**
   * test to find the history of involvement using clientIds
   */
  @Test
  public void findByClientIds() {
    List<String> clientIds = new ArrayList<String>();
    clientIds.add("1zxcydd");
    inMemoryResource.client().target("/clients/history_of_involvements")
        .queryParam("clientIds", "1zxcydd").request().accept(MediaType.APPLICATION_JSON).get();
    verify(hoiUsingClientIdService, atLeastOnce()).findByClientIds(clientIds);
  }

}
