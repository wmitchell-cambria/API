package gov.ca.cwds.rest.resources;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.rest.api.persistence.legacy.Referral;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.services.ReferralService;
import gov.ca.cwds.rest.services.ReferralServiceImpl;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CrudsResourceImplTest {
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

	private static final String ID_NOT_FOUND = "-1";
	private static final String ID_FOUND = "1";

	private static final String ROOT_RESOURCE = "/referrals/";

	private static final String FOUND_RESOURCE = ROOT_RESOURCE + ID_FOUND;
	private static final String NOT_FOUND_RESOURCE = ROOT_RESOURCE
			+ ID_NOT_FOUND;

	private static final ReferralServiceImpl referralService = mock(ReferralServiceImpl.class);
	private static final ServiceEnvironment serviceEnvironment = mock(ServiceEnvironment.class);

	private static Referral nonUniqueReferral;
	private static Referral uniqueReferral;
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new TestCrudsResourceImpl()).build();
	
	@Before
	public void setup() {
		
		try {
			nonUniqueReferral = MAPPER.readValue(fixture("fixtures/legacy/Referral/valid/validNonUnique.json"), Referral.class);
		
		    uniqueReferral = MAPPER.readValue(fixture("fixtures/legacy/Referral/valid/valid.json"), Referral.class);
			
		when(referralService.find(ID_NOT_FOUND)).thenReturn(null);
		when(referralService.find(ID_FOUND)).thenReturn(nonUniqueReferral);
		when(referralService.delete(ID_NOT_FOUND)).thenReturn(null);
		when(referralService.delete(ID_FOUND)).thenReturn(nonUniqueReferral);
		when(referralService.create(eq(uniqueReferral))).thenReturn(
				nonUniqueReferral);
		when(referralService.create(eq(nonUniqueReferral))).thenThrow(
				new ServiceException(new EntityExistsException()));
		when(referralService.update(eq(uniqueReferral))).thenThrow(
				new ServiceException(new EntityNotFoundException()));
		when(referralService.update(eq(nonUniqueReferral))).thenReturn(
				nonUniqueReferral);
		when(
				serviceEnvironment.getService(ReferralService.class,
						Api.Version.JSON_VERSION_1.getMediaType())).thenReturn(
				referralService);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	/*
	 * get Tests
	 */

	@Test
	public void getReturns200WhenFound() {
		assertThat(
				resources.client().target(FOUND_RESOURCE).request()
						.accept(Api.Version.JSON_VERSION_1.getMediaType())
						.get().getStatus(), is(equalTo(200)));
	}

	@Test
	public void getReturns404WhenNotFound() {
		assertThat(resources.client().target(NOT_FOUND_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType()).get()
				.getStatus(), is(equalTo(404)));
	}

	@Test
	public void getReturns406WhenVersionNotSupport() {
		assertThat(resources.client().target(NOT_FOUND_RESOURCE).request()
				.accept("UNSUPPORTED_VERSION").get().getStatus(),
				is(equalTo(406)));
	}

	@Test
	public void getReturnsNonNullEntity() {
		Object entity = resources.client().target(FOUND_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType()).get()
				.getEntity();
		assertThat(entity, is(notNullValue()));
	}

	@Test
	public void getReturnsReferral() {
		Referral referral = resources.client().target(FOUND_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType()).get()
				.readEntity(Referral.class);
		assertThat(referral.getId(), is(equalTo("1")));
	}

	/*
	 * delete Tests
	 */
	@Test
	public void deleteReturns200WhenDeleted() {
		assertThat(
				resources.client().target(FOUND_RESOURCE).request()
						.accept(Api.Version.JSON_VERSION_1.getMediaType())
						.delete().getStatus(), is(equalTo(200)));
	}

	@Test
	public void deleteReturns404WhenNotFound() {
		assertThat(resources.client().target(NOT_FOUND_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType()).get()
				.getStatus(), is(equalTo(404)));
	}

	@Test
	public void deleteReturns406WhenVersionNotSupport() {
		assertThat(resources.client().target(NOT_FOUND_RESOURCE).request()
				.accept("UNSUPPORTED_VERSION").get().getStatus(),
				is(equalTo(406)));
	}

	/*
	 * create Tests
	 */
	@Test
	public void createReturns201WhenCreated() {
		assertThat(
				resources
						.client()
						.target(ROOT_RESOURCE)
						.request()
						.accept(Api.Version.JSON_VERSION_1.getMediaType())
						.post(Entity.entity(uniqueReferral,
								Api.MEDIA_TYPE_JSON_V1)).getStatus(),
				is(equalTo(201)));
	}

	@Test
	public void createReturnsLocationHeaderWhenCreated() {
		assertThat(
				resources
						.client()
						.target(ROOT_RESOURCE)
						.request()
						.accept(Api.Version.JSON_VERSION_1.getMediaType())
						.post(Entity.entity(uniqueReferral,
								Api.MEDIA_TYPE_JSON_V1)).getHeaders()
						.get("Location"), is(notNullValue()));
	}

	@Test
	public void createReturns406WhenVersionNotSupport() {
		assertThat(
				resources
						.client()
						.target(ROOT_RESOURCE)
						.request()
						.accept("UNSUPPORTED_VERSION")
						.post(Entity.entity(uniqueReferral,
								Api.MEDIA_TYPE_JSON_V1)).getStatus(),
				is(equalTo(406)));
	}

	@Test
	public void createReturns409WhenNonUnique() {
		assertThat(
				resources
						.client()
						.target(ROOT_RESOURCE)
						.request()
						.accept(Api.Version.JSON_VERSION_1.getMediaType())
						.post(Entity.entity(nonUniqueReferral,
								Api.MEDIA_TYPE_JSON_V1)).getStatus(),
				is(equalTo(409)));
	}

	/*
	 * update Tests
	 */
	@Test
	public void updateReturns204WhenUpdated() {
		assertThat(
				resources
						.client()
						.target(ROOT_RESOURCE)
						.request()
						.accept(Api.Version.JSON_VERSION_1.getMediaType())
						.put(Entity.entity(nonUniqueReferral,
								Api.MEDIA_TYPE_JSON_V1)).getStatus(),
				is(equalTo(204)));
	}

	@Test
	public void updateReturns406WhenVersionNotSupport() {
		assertThat(
				resources
						.client()
						.target(ROOT_RESOURCE)
						.request()
						.accept("UNSUPPORTED_VERSION")
						.put(Entity.entity(nonUniqueReferral,
								Api.MEDIA_TYPE_JSON_V1)).getStatus(),
				is(equalTo(406)));
	}

	@Test
	public void updateReturns404WhenNotFound() {
		assertThat(
				resources
						.client()
						.target(ROOT_RESOURCE)
						.request()
						.accept(Api.Version.JSON_VERSION_1.getMediaType())
						.put(Entity.entity(uniqueReferral,
								Api.MEDIA_TYPE_JSON_V1)).getStatus(),
				is(equalTo(404)));
	}

	/*
	 * Helpers
	 */
	@Path(value = gov.ca.cwds.rest.core.Api.RESOURCE_REFERRAL)
	static class TestCrudsResourceImpl implements CrudsResource<Referral>  {
		@Context
		UriInfo uriInfo;
		
		CrudsResourceImpl<Referral, ReferralService> crudsResourceImpl;
		
		public TestCrudsResourceImpl() {
			this.crudsResourceImpl = new CrudsResourceImpl<>(serviceEnvironment, ReferralService.class);
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
		public Response create(Referral persistentObject, String acceptHeader, UriInfo uriInfo) {
			return crudsResourceImpl.create(persistentObject, acceptHeader, uriInfo);
		}

		@Override
		public Response update(Referral persistentObject, String acceptHeader) {
			return crudsResourceImpl.update(persistentObject, acceptHeader);
		}
	}
}
