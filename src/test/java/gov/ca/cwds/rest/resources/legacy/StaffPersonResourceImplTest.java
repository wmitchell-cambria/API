package gov.ca.cwds.rest.resources.legacy;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.legacy.StaffPerson;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.CrudsResource;
import gov.ca.cwds.rest.resources.CrudsResourceImplTest;
import gov.ca.cwds.rest.resources.legacy.StaffPersonResourceImpl;
import gov.ca.cwds.rest.services.legacy.StaffPersonService;
import gov.ca.cwds.rest.services.legacy.StaffPersonServiceImpl;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * HTTP Status codes are tested in {@link CrudsResourceImplTest}
 */
public class StaffPersonResourceImplTest {
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	
	private static final String ID_FOUND = "1";
	private static final String ID_VERIFY = "ABC";
	
	private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_STAFF_PERSON + "/";
	
	private static final String FOUND_RESOURCE = ROOT_RESOURCE + ID_FOUND ;
	private static final String VERIFY_RESOURCE = ROOT_RESOURCE + ID_VERIFY ;
	
	private static final StaffPersonServiceImpl staffPersonService = mock(StaffPersonServiceImpl.class);
	private static final ServiceEnvironment serviceEnvironment = mock(ServiceEnvironment.class);
	@SuppressWarnings("unchecked")
	private static final CrudsResource<StaffPerson> crudsResource = mock(CrudsResource.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new StaffPersonResourceImpl(serviceEnvironment, crudsResource)).build();
	
	private StaffPerson valid;

	@Before
	public void setup() throws Exception {
		valid = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
		when(serviceEnvironment.getService(StaffPersonService.class, Api.Version.JSON_VERSION_1.getMediaType())).thenReturn(staffPersonService);
		when(crudsResource.get( eq(valid.getId()), eq(Api.Version.JSON_VERSION_1.getMediaType()))).thenReturn(Response.ok(valid).build());
	}
	
	@SuppressWarnings("unchecked")
	@After
	public void tearDown() {
		reset(crudsResource);
	}
	
	/*
	 *  delegation check tests
	 */
	@Test
	public void getDelegatestoCrudsResource() throws Exception {
		StaffPerson toVerify = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
		resources.client().target(VERIFY_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get();
		verify(crudsResource, times(1)).get(toVerify.getId(), Api.MEDIA_TYPE_JSON_V1);

	}

	@Test
	public void deleteDelegatestoCrudsResource() {
		resources.client().target(FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).delete();
		verify(crudsResource, times(1)).delete(any(String.class), any(String.class));
	}

	@Test
	public void createDelegatestoCrudsResource() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
		resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		verify(crudsResource, times(1)).create(any(StaffPerson.class), any(String.class), any(UriInfo.class) );
	}

	
	@Test
	public void updateDelegatestoCrudsResource() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
		resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).put(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		verify(crudsResource, times(1)).update(any(StaffPerson.class), any(String.class));
	}
	
	@Test
	public void createValidatesStaffPerson() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/id/missing.json"), StaffPerson.class);
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(422)));
	}

	
	@Test
	public void updateValidatesStaffPerson() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/id/missing.json"), StaffPerson.class);
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).put(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(422)));
	}
}	
