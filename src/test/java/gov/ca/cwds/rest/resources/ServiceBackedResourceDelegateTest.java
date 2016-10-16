package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import gov.ca.cwds.rest.api.PrimaryKeyResponse;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import io.dropwizard.testing.junit.ResourceTestRule;

public class ServiceBackedResourceDelegateTest {
  private static final Long ID_NOT_FOUND = new Long(-1);
  private static final Long ID_FOUND = new Long(1);

  private static final String ROOT_RESOURCE = "/resource/";

  private static final String FOUND_RESOURCE = ROOT_RESOURCE + ID_FOUND;
  private static final String NOT_FOUND_RESOURCE = ROOT_RESOURCE + ID_NOT_FOUND;

  private static final CrudsService crudsService = mock(CrudsService.class);

  private static ResourceDelegateTestDomainObject nonUniqueDomainObject;
  private static ResourceDelegateTestDomainObject uniqueDomainObject;
  private static ResourceDelegateTestDomainObject unexpectedExceptionDomainObject;

  @ClassRule
  public static final ResourceTestRule inMemoryResource = ResourceTestRule.builder()
      .addResource(new WrapperResource(new ServiceBackedResourceDelegate(crudsService))).build();

  @ClassRule
  public static final ResourceTestRule grizzlyResource = ResourceTestRule.builder()
      .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
      .addResource(new WrapperResource(new ServiceBackedResourceDelegate(crudsService))).build();

  @Before
  public void setup() throws Exception {
    nonUniqueDomainObject = new ResourceDelegateTestDomainObject(ID_FOUND);
    uniqueDomainObject = new ResourceDelegateTestDomainObject(ID_NOT_FOUND);
    unexpectedExceptionDomainObject = new ResourceDelegateTestDomainObject(new Long(13));

    when(crudsService.find(ID_NOT_FOUND)).thenReturn(null);
    when(crudsService.find(ID_FOUND)).thenReturn(nonUniqueDomainObject);
    when(crudsService.delete(ID_NOT_FOUND)).thenReturn(null);
    when(crudsService.delete(ID_FOUND)).thenReturn(nonUniqueDomainObject);
    when(crudsService.create(eq(uniqueDomainObject)))
        .thenReturn(new PrimaryKeyResponse(nonUniqueDomainObject.getId()));
    when(crudsService.create(eq(nonUniqueDomainObject)))
        .thenThrow(new ServiceException(new EntityExistsException()));
    when(crudsService.create(eq(unexpectedExceptionDomainObject)))
        .thenThrow(new ServiceException(new RuntimeException()));
    when(crudsService.update(eq(1L), eq(unexpectedExceptionDomainObject)))
        .thenThrow(new ServiceException(new RuntimeException()));
    when(crudsService.update(eq(-1L), eq(uniqueDomainObject)))
        .thenThrow(new ServiceException(new EntityNotFoundException()));
    when(crudsService.update(eq(1L), eq(nonUniqueDomainObject)))
        .thenReturn(new PrimaryKeyResponse(nonUniqueDomainObject.getId()));
  }

  /*
   * get Tests
   */

  @Test
  public void getReturns200WhenFound() {
    assertThat(inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).get().getStatus(), is(equalTo(200)));
  }

  @Test
  public void getReturns404WhenNotFound() {
    assertThat(inMemoryResource.client().target(NOT_FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).get().getStatus(), is(equalTo(404)));
  }

  @Test
  public void getReturns406WhenVersionNotSupport() {
    assertThat(inMemoryResource.client().target(NOT_FOUND_RESOURCE).request()
        .accept("UNSUPPORTED_VERSION").get().getStatus(), is(equalTo(406)));
  }

  @Test
  public void getReturnsNonNullEntity() {
    Object entity = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).get().getEntity();
    assertThat(entity, is(notNullValue()));
  }

  /*
   * delete Tests
   */
  @Test
  public void deleteReturns200WhenDeleted() {
    assertThat(inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus(), is(equalTo(200)));
  }

  @Test
  public void deleteReturns404WhenNotFound() {
    assertThat(inMemoryResource.client().target(NOT_FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus(), is(equalTo(404)));
  }

  @Test
  public void deleteReturns406WhenVersionNotSupport() {
    assertThat(inMemoryResource.client().target(NOT_FOUND_RESOURCE).request()
        .accept("UNSUPPORTED_VERSION").delete().getStatus(), is(equalTo(406)));
  }

  /*
   * create Tests
   */
  @Test
  public void createReturns200WhenCreated() {
    assertThat(
        grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request()
            .accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(uniqueDomainObject, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(200)));
  }

  @Test
  public void createReturns406WhenVersionNotSupport() {
    assertThat(
        grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request()
            .accept("UNSUPPORTED_VERSION")
            .post(Entity.entity(uniqueDomainObject, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(406)));
  }

  @Test
  public void createReturns409WhenNonUnique() {
    assertThat(
        grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request()
            .accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(nonUniqueDomainObject, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(409)));
  }

  @Test
  public void createReturns400WhenCannotProcessJson() throws Exception {
    // create expects to deserialize the payload to a
    // CrudsResourceImplTestDomainObject - lets give it something else
    // instead.
    Address address = new Address("street", "city", "state", 12345);
    int status = grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).post(Entity.entity(address, MediaType.APPLICATION_JSON))
        .getStatus();
    assertThat(status, is(400));

  }

  @Test
  public void createReturns503OnUnexpectedException() throws Exception {
    int status = grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(unexpectedExceptionDomainObject, MediaType.APPLICATION_JSON))
        .getStatus();
    assertThat(status, is(503));
  }

  /*
   * update Tests
   */
  @Test
  public void updateReturns200WhenUpdated() {
    assertThat(
        inMemoryResource.client().target(FOUND_RESOURCE).request()
            .accept(MediaType.APPLICATION_JSON)
            .put(Entity.entity(nonUniqueDomainObject, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(200)));
  }

  @Test
  public void updateReturns406WhenVersionNotSupport() {
    assertThat(
        inMemoryResource.client().target(FOUND_RESOURCE).request().accept("UNSUPPORTED_VERSION")
            .put(Entity.entity(nonUniqueDomainObject, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(406)));
  }

  @Test
  public void updateReturns404WhenNotFound() {
    assertThat(
        inMemoryResource.client().target(NOT_FOUND_RESOURCE).request()
            .accept(MediaType.APPLICATION_JSON)
            .put(Entity.entity(uniqueDomainObject, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(404)));
  }

  @Test
  public void updateReturns503OnUnexpectedException() throws Exception {
    int status = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(unexpectedExceptionDomainObject, MediaType.APPLICATION_JSON))
        .getStatus();
    assertThat(status, is(503));
  }

  /*
   * Helpers
   */
  @Path(ROOT_RESOURCE)
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public static class WrapperResource {
    private ResourceDelegate resourceDelegate;

    public WrapperResource(ResourceDelegate resourceDelegate) {
      this.resourceDelegate = resourceDelegate;
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") long id) {
      return resourceDelegate.get(id);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
      return resourceDelegate.delete(id);
    }

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response create(ResourceDelegateTestDomainObject object) {
      return resourceDelegate.create(object);
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id,
        @Valid ResourceDelegateTestDomainObject object) {
      return resourceDelegate.update(id, object);
    }

  }
}
