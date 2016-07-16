package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.core.MediaType;
import gov.ca.cwds.rest.services.ReferralServiceImpl;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.util.Date;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;


public class ReferralResourceImplTest {
	private static final String ID_NOT_FOUND = "-1";
	private static final String ID_FOUND = "1";
	
	private static final String FOUND_RESOURCE = "/referral/" + ID_FOUND + "/summary";
	private static final String NOT_FOUND_RESOURCE = "/referral/" + ID_NOT_FOUND + "/summary";
	
	private static final ReferralServiceImpl referralService = mock(ReferralServiceImpl.class);
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new ReferralResourceImpl(referralService)).build();

	@Before
	public void setup() {
		when(referralService.findReferralSummary(ID_NOT_FOUND)).thenReturn(null);
		when(referralService.findReferralSummary(ID_FOUND)).thenReturn(createReferralSummary());
	}

	@Test
	public void referralSummaryGetReturns200WhenFound() {
		assertThat(resources.client().target(FOUND_RESOURCE).request().get().getStatus(), is(equalTo(200)));
	}

	@Test
	public void referralSummaryGetReturns404WhenNotFound() {
		assertThat(resources.client().target(NOT_FOUND_RESOURCE).request().get().getStatus(), is(equalTo(404)));
	}
	
	@Test
	public void referralSummaryGetReturnsCorrectMessageWhenNotFound() {
		assertThat(resources.client().target(NOT_FOUND_RESOURCE).request().get().readEntity(String.class), is(equalTo("ReferralSummary not found")));
	}

	@Test
	public void applicationGetReturnsV1JsonContentType() {
		assertThat(resources.client().target(FOUND_RESOURCE).request().get().getMediaType().toString(), is(equalTo(MediaType.APPLICATION_JSON_V1)));
	}
	
	private ReferralSummary createReferralSummary() {
		return new ReferralSummary(ID_FOUND, "some name", new Date());
	}

}
