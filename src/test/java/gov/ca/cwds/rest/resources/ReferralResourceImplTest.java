package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.api.persistence.Referral;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.services.ReferralService;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.util.Date;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;


public class ReferralResourceImplTest {
	private static final String ID_NOT_FOUND = "-1";
	private static final String ID_FOUND = "1";
	
	private static final String SUMMARY_FOUND_RESOURCE = "/referral/" + ID_FOUND + "/summary";
	private static final String SUMMARY_NOT_FOUND_RESOURCE = "/referral/" + ID_NOT_FOUND + "/summary";

	private static final String FOUND_RESOURCE = "/referral/" + ID_FOUND ;
	private static final String NOT_FOUND_RESOURCE = "/referral/" + ID_NOT_FOUND;

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
	public void getHasReferralSummaryWhenFound() {
		ReferralSummary referralSummary = resources.client().target(FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get().readEntity(ReferralSummary.class);
		assertThat(referralSummary, is(notNullValue()));
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

	@Test
	public void deleteHasReferralSummaryWhenFound() {
		ReferralSummary referralSummary = resources.client().target(FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get().readEntity(ReferralSummary.class);
		assertThat(referralSummary, is(notNullValue()));
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


}
