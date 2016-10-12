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
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.ApiResponse;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import io.dropwizard.testing.junit.ResourceTestRule;

public class CrudsResourceImplTest {
	private static final String ID_NOT_FOUND = "-1";
	private static final String ID_FOUND = "1";

	private static final String ROOT_RESOURCE = "/crudsTest/";

	private static final String FOUND_RESOURCE = ROOT_RESOURCE + ID_FOUND;
	private static final String NOT_FOUND_RESOURCE = ROOT_RESOURCE + ID_NOT_FOUND;

	@SuppressWarnings("unchecked")
	private static final CrudsService<CrudsResourceImplTestDomainObject> crudsService = mock(CrudsService.class);

	private static CrudsResourceImplTestDomainObject nonUniqueDomainObject;
	private static CrudsResourceImplTestDomainObject uniqueDomainObject;

	@ClassRule
	public static final ResourceTestRule inMemoryResource = ResourceTestRule.builder().addResource(new TestCrudsResourceImpl())
			.build();

	@ClassRule
	public static final ResourceTestRule grizzlyResource = ResourceTestRule.builder()
			.setTestContainerFactory(new GrizzlyWebTestContainerFactory()).addResource(new TestCrudsResourceImpl())
			.build();
	
	@Before
	public void setup() throws Exception {

		nonUniqueDomainObject = new CrudsResourceImplTestDomainObject(ID_FOUND);

		uniqueDomainObject = new CrudsResourceImplTestDomainObject(ID_NOT_FOUND);

		when(crudsService.find(ID_NOT_FOUND)).thenReturn(null);
		when(crudsService.find(ID_FOUND)).thenReturn(nonUniqueDomainObject);
		when(crudsService.delete(ID_NOT_FOUND)).thenReturn(null);
		when(crudsService.delete(ID_FOUND)).thenReturn(nonUniqueDomainObject);
		when(crudsService.create(eq(uniqueDomainObject))).thenReturn(nonUniqueDomainObject.getId());
		when(crudsService.create(eq(nonUniqueDomainObject)))
				.thenThrow(new ServiceException(new EntityExistsException()));
		when(crudsService.update(eq(uniqueDomainObject)))
				.thenThrow(new ServiceException(new EntityNotFoundException()));
		when(crudsService.update(eq(nonUniqueDomainObject))).thenReturn(nonUniqueDomainObject.getId());
	}

	/*
	 * get Tests
	 */

	@Test
	public void getReturns200WhenFound() {
		assertThat(inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON).get()
				.getStatus(), is(equalTo(200)));
	}

	@Test
	public void getReturns404WhenNotFound() {
		assertThat(inMemoryResource.client().target(NOT_FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON).get()
				.getStatus(), is(equalTo(404)));
	}

	@Test
	public void getReturns406WhenVersionNotSupport() {
		assertThat(
				inMemoryResource.client().target(NOT_FOUND_RESOURCE).request().accept("UNSUPPORTED_VERSION").get().getStatus(),
				is(equalTo(406)));
	}

	@Test
	public void getReturnsNonNullEntity() {
		Object entity = inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON).get()
				.getEntity();
		assertThat(entity, is(notNullValue()));
	}

	/*
	 * delete Tests
	 */
	@Test
	public void deleteReturns200WhenDeleted() {
		assertThat(inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON).delete()
				.getStatus(), is(equalTo(200)));
	}

	@Test
	public void deleteReturns404WhenNotFound() {
		assertThat(inMemoryResource.client().target(NOT_FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON).get()
				.getStatus(), is(equalTo(404)));
	}

	@Test
	public void deleteReturns406WhenVersionNotSupport() {
		assertThat(
				inMemoryResource.client().target(NOT_FOUND_RESOURCE).request().accept("UNSUPPORTED_VERSION").get().getStatus(),
				is(equalTo(406)));
	}

	/*
	 * create Tests
	 */
//	@Test
//	public void createReturns201WhenCreated() {
//		assertThat(
//				grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
//						.post(Entity.entity(uniqueDomainObject, MediaType.APPLICATION_JSON)).getStatus(),
//				is(equalTo(201)));
//	}
//
//	@Test
//	public void createReturnsLocationHeaderWhenCreated() {
//		assertThat(grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
//				.post(Entity.entity(uniqueDomainObject, MediaType.APPLICATION_JSON)).getHeaders().get("Location"),
//				is(notNullValue()));
//	}
//
//	@Test
//	public void createReturns406WhenVersionNotSupport() {
//		assertThat(
//				grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request().accept("UNSUPPORTED_VERSION")
//						.post(Entity.entity(uniqueDomainObject, MediaType.APPLICATION_JSON)).getStatus(),
//				is(equalTo(406)));
//	}
//
//	@Test
//	public void createReturns409WhenNonUnique() {
//		assertThat(
//				grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
//						.post(Entity.entity(nonUniqueDomainObject, MediaType.APPLICATION_JSON)).getStatus(),
//				is(equalTo(409)));
//	}
//	
//	@Test
//	public void createReturns400WhenCannotProcessJson() throws Exception {
//		//create expects to deserialize the payload to a CrudsResourceImplTestDomainObject - lets give it something else instead.
//		Address address = new Address("street", "city", "state", 12345);
//		int status = grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(address, MediaType.APPLICATION_JSON)).getStatus();
//		assertThat(status, is(400));
//		
//	}

	/*
	 * update Tests
	 */
	@Test
	public void updateReturns204WhenUpdated() {
		assertThat(
				inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
						.put(Entity.entity(nonUniqueDomainObject, MediaType.APPLICATION_JSON)).getStatus(),
				is(equalTo(204)));
	}

	@Test
	public void updateReturns406WhenVersionNotSupport() {
		assertThat(
				inMemoryResource.client().target(ROOT_RESOURCE).request().accept("UNSUPPORTED_VERSION")
						.put(Entity.entity(nonUniqueDomainObject, MediaType.APPLICATION_JSON)).getStatus(),
				is(equalTo(406)));
	}

	@Test
	public void updateReturns404WhenNotFound() {
		assertThat(
				inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
						.put(Entity.entity(uniqueDomainObject, MediaType.APPLICATION_JSON)).getStatus(),
				is(equalTo(404)));
	}

	/*
	 * Helpers
	 */
	@Path(value = ROOT_RESOURCE)
	static class TestCrudsResourceImpl implements CrudsResource<CrudsResourceImplTestDomainObject> {

		CrudsResourceImpl<CrudsResourceImplTestDomainObject> crudsResourceImpl;

		public TestCrudsResourceImpl() {
			this.crudsResourceImpl = new CrudsResourceImpl<CrudsResourceImplTestDomainObject>(crudsService);
		}

		@Override
		public Response get(String id, String acceptHeader) {
			return crudsResourceImpl.get(id, acceptHeader);
		}

		@Override
		public Response delete(String id, String acceptHeader) {
			return crudsResourceImpl.delete(id, acceptHeader);
		}

		@Override
		public ApiResponse<CrudsResourceImplTestDomainObject> create(CrudsResourceImplTestDomainObject object,
				String acceptHeader, UriInfo uriInfo, HttpServletResponse response) {
			return crudsResourceImpl.create(object, acceptHeader, uriInfo, response);
		}

		@Override
		public Response update(CrudsResourceImplTestDomainObject object, String acceptHeader) {
			return crudsResourceImpl.update(object, acceptHeader);
		}
	}

	public static class MyBinder extends AbstractBinder {

		@Override
		protected void configure() {
			// request scope binding
			bind(HttpServletResponse.class).to(HttpServletResponse.class).in(RequestScoped.class);
		}

	}
}
