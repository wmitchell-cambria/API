package gov.ca.cwds.rest.resources;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.services.RelationshipsService;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * @author CWDS API Team
 *
 */
@Ignore
public class ClientRelationshipResourceTest extends IntakeBaseTest {

  private static final RelationshipsService service = mock(RelationshipsService.class);

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(new ClientRelationshipResource(service)).build();

  /**
   * 
   */
  @Before
  public void setup() {
    reset(service);
  }

  /**
   * Test to get the relationships by Id
   */
  @Test
  public void shouldCallGetRelationsipsByClientIdResourceEndpoint() {
    resources.target("/clients/1/relationships").request().accept(MediaType.APPLICATION_JSON).get();
    verify(service).find("1");
  }

  /**
   * Test to see the relationship for a list of clients
   */
  @Test
  public void shouldCallGetReleationshipsByClientIdsResourceEndpoint() {
    List<String> clientIds = Arrays.asList("1zxcydd");
    resources.client().target("/clients/relationships").queryParam("clientIds", "1zxcydd").request()
        .accept(MediaType.APPLICATION_JSON).get();
    verify(service).findForIds(clientIds);
  }
}
