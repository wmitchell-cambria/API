package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.Address;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class AddressResourceTest {

  private static final String ROOT_RESOURCE = "/addresses/";
  private static final String FOUND_RESOURCE = "/addresses/1";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new AddressResource(resourceDelegate)).build();

  @Before
  public void setup() throws Exception {}

  /*
   * Get Tests
   */
  @Test
  public void getDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get().getStatus();
    verify(resourceDelegate).get(1L);
  }

  /*
   * Create Tests
   */
  @Test
  public void createDelegatesToResourceDelegate() throws Exception {
    Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(address, MediaType.APPLICATION_JSON)).getStatus();
    verify(resourceDelegate).create(eq(address));
  }

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
    Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .put(Entity.entity(address, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(501));
  }
}
