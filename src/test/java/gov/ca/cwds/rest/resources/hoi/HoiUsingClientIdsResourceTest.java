package gov.ca.cwds.rest.resources.hoi;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import gov.ca.cwds.rest.services.hoi.InvolvementHistoryService;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * @author CWDS API Team
 *
 */
public class HoiUsingClientIdsResourceTest {

  private static final InvolvementHistoryService involvementHistoryService =
      mock(InvolvementHistoryService.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource = ResourceTestRule.builder()
      .addResource(new HoiUsingClientIdResource(involvementHistoryService)).build();

  /**
   * 
   */
  @Before
  public void setup() {
    reset(involvementHistoryService);
  }

  /**
   * test to find the history of involvement using clientIds
   */
  @Test
  public void findByClientIds() {
    List<String> clientIds = new ArrayList<>();
    clientIds.add("1zxcydd");
    inMemoryResource.client().target("/clients/history_of_involvements")
        .queryParam("clientIds", "1zxcydd").request().accept(MediaType.APPLICATION_JSON).get();
    verify(involvementHistoryService, atLeastOnce()).findInvolvementHistoryByClientIds(clientIds);
  }

}
