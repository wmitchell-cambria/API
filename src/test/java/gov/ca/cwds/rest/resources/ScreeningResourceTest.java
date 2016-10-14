package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.Screening;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link CrudsResourceImpl}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class ScreeningResourceTest {
  private static final String ROOT_RESOURCE = "/screenings/";
  private static final String FOUND_RESOURCE = "/screenings/1";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings({"unchecked"})
  private static CrudsResource<Screening> mockedCrudsResource = mock(CrudsResource.class);

  @ClassRule
  public static final ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new ScreeningResource(mockedCrudsResource)).build();

  /*
   * Get Tests
   */

  @Test
  public void getDelegatesToCrudsResource() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get().getStatus();
    verify(mockedCrudsResource).get("1", MediaType.APPLICATION_JSON);
  }

  /*
   * Create Tests
   */
  // @Test
  // public void createDelegatesToCrudsResource() throws Exception {
  // Screening screening = new Screening((long) 2, "X5HNJK", "2016-10-13T01:07", "amador",
  // "2016-10-13", "Relative's Home", "email", "first screening", "immediate",
  // "accept_for_investigation", "2016-10-05T01:01", "first narrative", null, null);
  // inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
  // .post(Entity.entity(screening, MediaType.APPLICATION_JSON)).getStatus();
  // verify(mockedCrudsResource).create(eq(screening), eq(MediaType.APPLICATION_JSON),
  // any(UriInfo.class), any(HttpServletResponse.class));
  // }

  /*
   * Delete Tests
   */
  @Test
  public void deleteReturns501() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    int expectedStatus = 501;
    assertThat(receivedStatus, is(expectedStatus));
  }

  /*
   * Update Tests
   */
  @Test
  public void udpateReturns501() throws Exception {
    Screening screening = new Screening((long) 2, "X5HNJK", "2016-10-13T01:07", "amador",
        "2016-10-13", "Relative's Home", "email", "first screening", "immediate",
        "accept_for_investigation", "2016-10-05T01:01", "first narrative", null, null);
    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .put(Entity.entity(screening, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(501));
  }
}
