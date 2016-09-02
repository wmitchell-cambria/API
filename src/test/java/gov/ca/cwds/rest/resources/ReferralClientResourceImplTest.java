package gov.ca.cwds.rest.resources;

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

import java.text.MessageFormat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.UriInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.legacy.ReferralClient;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.services.ReferralClientService;
import gov.ca.cwds.rest.services.ReferralClientServiceImpl;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * HTTP Status codes are tested in {@link CrudsResourceImplTest}
 */
public class ReferralClientResourceImplTest {
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	
	private static final String ID_FOUND = "1";
	private static final String ID_VERIFY = "referralId=abc,clientId=abc";
	
	private static final String ROOT_RESOURCE = "/referralclients/";
	
	private static final String FOUND_RESOURCE = ROOT_RESOURCE + ID_FOUND ;
	private static final String VERIFY_RESOURCE = ROOT_RESOURCE + ID_VERIFY ;
	
	private static final ReferralClientServiceImpl referralClientService = mock(ReferralClientServiceImpl.class);
	private static final ServiceEnvironment serviceEnvironment = mock(ServiceEnvironment.class);
	@SuppressWarnings("unchecked")
	private static final CrudsResource<ReferralClient> crudsResource = mock(CrudsResource.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new ReferralClientResourceImpl(serviceEnvironment, crudsResource)).build();

	@Before
	public void setup() {
		when(serviceEnvironment.getService(ReferralClientService.class, Api.Version.JSON_VERSION_1.getMediaType())).thenReturn(referralClientService);
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
		ReferralClient toVerify = MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);
		resources.client().target(VERIFY_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get();
		String id = MessageFormat.format("referralId={0},clientId={1}", toVerify.getReferralId(), toVerify.getClientId());
		verify(crudsResource, times(1)).get(id, Api.MEDIA_TYPE_JSON_V1);
	}

	@Test
	public void deleteDelegatestoCrudsResource() {
		resources.client().target(FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).delete();
		verify(crudsResource, times(1)).delete(any(String.class), any(String.class));
	}

	@Test
	public void createDelegatestoCrudsResource() throws Exception {
		ReferralClient toCreate = MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);
		resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		verify(crudsResource, times(1)).create(any(ReferralClient.class), any(String.class), any(UriInfo.class) );
	}

	
	@Test
	public void updateDelegatestoCrudsResource() throws Exception {
		ReferralClient toCreate = MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);
		resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).put(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		verify(crudsResource, times(1)).update(any(ReferralClient.class), any(String.class));
	}
	
	@Test
	public void createValidatesReferralClient() throws Exception {
		ReferralClient toCreate = MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/invalid/ageNumber/missing.json"), ReferralClient.class);
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(422)));
	}

	
	@Test
	public void updateValidatesReferralClient() throws Exception {
		ReferralClient toCreate = MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/invalid/ageNumber/missing.json"), ReferralClient.class);
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).put(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(422)));
	}
}	


