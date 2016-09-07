package gov.ca.cwds.rest.resources.legacy;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.UriInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.legacy.Allegation;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.CrudsResource;
import gov.ca.cwds.rest.resources.CrudsResourceImplTest;
import gov.ca.cwds.rest.resources.legacy.AllegationResourceImpl;
import gov.ca.cwds.rest.services.legacy.AllegationService;
import gov.ca.cwds.rest.services.legacy.AllegationServiceImpl;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * HTTP Status codes are tested in {@link CrudsResourceImplTest}
 */
public class AllegationResourceImplTest {
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

	private static final String ID_FOUND = "1";
	private static final String ID_VERIFY = "Aaeae9r0F4";

	private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_ALLEGATION + "/";

	private static final String FOUND_RESOURCE = ROOT_RESOURCE + ID_FOUND;
	private static final String VERIFY_RESOURCE = ROOT_RESOURCE + ID_VERIFY;

	private static final AllegationServiceImpl allegationService = mock(AllegationServiceImpl.class);
	private static final ServiceEnvironment serviceEnvironment = mock(ServiceEnvironment.class);
	@SuppressWarnings("unchecked")
	private static final CrudsResource<Allegation> crudsResource = mock(CrudsResource.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new AllegationResourceImpl(serviceEnvironment, crudsResource)).build();

	@Before
	public void setup() {
		when(serviceEnvironment.getService(AllegationService.class, Api.Version.JSON_VERSION_1.getMediaType()))
				.thenReturn(allegationService);
	}

	@SuppressWarnings("unchecked")
	@After
	public void tearDown() {
		reset(crudsResource);
	}

	/*
	 * delegation check tests
	 */
	@Test
	public void getDelegatestoCrudsResource() throws Exception {
		Allegation toVerify = MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/valid.json"),
				Allegation.class);
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
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/valid.json"),
				Allegation.class);
		resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		verify(crudsResource, times(1)).create(any(Allegation.class), any(String.class), any(UriInfo.class));
	}

	@Test
	public void updateDelegatestoCrudsResource() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/valid.json"),
				Allegation.class);
		resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType())
				.put(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		verify(crudsResource, times(1)).update(any(Allegation.class), any(String.class));
	}

	@Test
	public void createValidatesAllegation() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/id/missing.json"),
				Allegation.class);
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(422)));
	}

	@Test
	public void updateValidatesAllegation() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/id/missing.json"),
				Allegation.class);
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType())
				.put(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(422)));
	}
}
