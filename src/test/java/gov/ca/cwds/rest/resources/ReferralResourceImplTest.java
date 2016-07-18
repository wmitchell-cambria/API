package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.api.persistence.Referral;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.services.ReferralService;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.util.Date;

import javax.ws.rs.client.Entity;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;


public class ReferralResourceImplTest {
	private static final String ID_NOT_FOUND = "-1";
	private static final String ID_FOUND = "1";
	
	private static final String ROOT_RESOURCE = "/referral/";
	private static final String SUMMARY_RESOURCE = "/summary";
	
	private static final String SUMMARY_FOUND_RESOURCE = ROOT_RESOURCE + ID_FOUND + SUMMARY_RESOURCE;
	private static final String SUMMARY_NOT_FOUND_RESOURCE = ROOT_RESOURCE + ID_NOT_FOUND + SUMMARY_RESOURCE;

	private static final String FOUND_RESOURCE = ROOT_RESOURCE + ID_FOUND ;
	private static final String NOT_FOUND_RESOURCE = ROOT_RESOURCE + ID_NOT_FOUND;

	private static final ReferralService referralService = mock(ReferralService.class);
	private static final ServiceEnvironment serviceEnvironment = mock(ServiceEnvironment.class);
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new ReferralResourceImpl(serviceEnvironment)).build();

	@Before
	public void setup() {
		when(referralService.findReferralSummary(ID_NOT_FOUND)).thenReturn(null);
		when(referralService.findReferralSummary(ID_FOUND)).thenReturn(createReferralSummary());
		when(referralService.find(ID_NOT_FOUND)).thenReturn(null);
		when(referralService.find(ID_FOUND)).thenReturn(createReferral());
		when(referralService.delete(ID_NOT_FOUND)).thenReturn(null);
		when(referralService.delete(ID_FOUND)).thenReturn(createReferral());
		when(referralService.create(any(Referral.class))).thenReturn(createReferral());
		
		when(serviceEnvironment.getService(ReferralService.class, Api.Version.JSON_VERSION_1.getMediaType())).thenReturn(referralService);
		
	}

	/*
	 * getReferralSummary Tests
	 */
	@Test
	public void getReferralSummaryReturns200WhenFound() {
		assertThat(resources.client().target(SUMMARY_FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get().getStatus(), is(equalTo(200)));
	}

	@Test
	public void getReferralSummaryReturns404WhenNotFound() {
		assertThat(resources.client().target(SUMMARY_NOT_FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get().getStatus(), is(equalTo(404)));
	}
	
	@Test
	public void getReferralSummaryReturns406WhenVersionNotSupport() {
		assertThat(resources.client().target(SUMMARY_NOT_FOUND_RESOURCE).request().accept("UNSUPPORTED_VERSION").get().getStatus(), is(equalTo(406)));
	}

	/*
	 * get Tests
	 */

	@Test
	public void getReturns200WhenFound() {
		assertThat(resources.client().target(FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get().getStatus(), is(equalTo(200)));
	}

	@Test
	public void getReturns404WhenNotFound() {
		assertThat(resources.client().target(NOT_FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get().getStatus(), is(equalTo(404)));
	}
	
	@Test
	public void getReturns406WhenVersionNotSupport() {
		assertThat(resources.client().target(NOT_FOUND_RESOURCE).request().accept("UNSUPPORTED_VERSION").get().getStatus(), is(equalTo(406)));
	}

	@Test
	public void getHasReferralWhenFound() {
		Referral referral = resources.client().target(FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get().readEntity(Referral.class);
		assertThat(referral, is(notNullValue()));
	}
	
	/*
	 * delete Tests
	 */
	@Test
	public void deleteReturns200WhenDeleted() {
		assertThat(resources.client().target(FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).delete().getStatus(), is(equalTo(200)));
	}

	@Test
	public void deleteReturns404WhenNotFound() {
		assertThat(resources.client().target(NOT_FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get().getStatus(), is(equalTo(404)));
	}
	
	@Test
	public void deleteReturns406WhenVersionNotSupport() {
		assertThat(resources.client().target(NOT_FOUND_RESOURCE).request().accept("UNSUPPORTED_VERSION").get().getStatus(), is(equalTo(406)));
	}

	/*
	 * create Tests
	 */
	@Test
	public void createReturns204WhenCreated() {
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(createNewReferral(), Api.MEDIA_TYPE_JSON_V1)).getStatus(),is(equalTo(204)));
	}

	@Test
	public void createReturnsLocationHeaderWhenCreated() {
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(createNewReferral(), Api.MEDIA_TYPE_JSON_V1)).getHeaders().get("Location"),is(notNullValue()));
	}
	
	@Test
	public void createReturns406WhenVersionNotSupport() {
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept("UNSUPPORTED_VERSION").post(Entity.entity(createNewReferral(), Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(406)));
	}

	/*
	 * Helpers
	 */
	private ReferralSummary createReferralSummary() {
		return new ReferralSummary(ID_FOUND, "some name", new Date());
	}
	
	private Referral createReferral() {
		return new Referral(ID_FOUND, "some name", new Date());
	}
	
	private Referral createNewReferral() {
		return new Referral(null, "some name", new Date());
	}


}
