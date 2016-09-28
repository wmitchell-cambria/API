package gov.ca.cwds.rest.resources.intake;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import javax.persistence.EntityExistsException;

import org.junit.Before;
import org.junit.ClassRule;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.intake.IntakeReferral;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.intake.ReferralService;
import gov.ca.cwds.rest.services.intake.ReferralServiceImpl;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class ReferralResourceImplTest {
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

	
	private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_INTAKE_REFERRAL + "/";
	
	private static final ReferralServiceImpl referralService = mock(ReferralServiceImpl.class);
	private static final ServiceEnvironment serviceEnvironment = mock(ServiceEnvironment.class);

	private static IntakeReferral uniqueIntakeReferral;
	private static IntakeReferral nonUniqueIntakeReferral;
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new ReferralResourceImpl(serviceEnvironment)).build();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Before
	public void setup() throws Exception {
		uniqueIntakeReferral = MAPPER.readValue(fixture("fixtures/intake/IntakeReferral/valid/valid.json"), IntakeReferral.class);
		nonUniqueIntakeReferral = MAPPER.readValue(fixture("fixtures/intake/IntakeReferral/valid/validNonUnique.json"), IntakeReferral.class);
		
		when(referralService.create(eq(uniqueIntakeReferral))).thenReturn(new HashMap());
		when(referralService.create(eq(nonUniqueIntakeReferral)))
				.thenThrow(new ServiceException(new EntityExistsException()));
		
		
		when(serviceEnvironment.getService(ReferralService.class, Api.Version.JSON_VERSION_1.getMediaType())).thenReturn(referralService);
	}

	/*
	 * create Tests
	 */
//	@Test
//	public void createReturns201WhenCreated() {
//		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType())
//				.post(Entity.entity(uniqueIntakeReferral, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(201)));
//	}
//
//	@Test
//	public void createReturnsLocationHeaderWhenCreated() {
//		//RDB Assert.fail("Implement This Test");
//	}
//
//	@Test
//	public void createReturns406WhenVersionNotSupport() {
//		assertThat(resources.client().target(ROOT_RESOURCE).request().accept("UNSUPPORTED_VERSION")
//				.post(Entity.entity(uniqueIntakeReferral, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(406)));
//	}
//
//	@Test
//	public void createReturns409WhenNonUnique() {
//		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType())
//				.post(Entity.entity(nonUniqueIntakeReferral, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(409)));
//	}
}
