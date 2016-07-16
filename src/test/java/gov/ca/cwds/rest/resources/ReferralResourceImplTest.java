package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import gov.ca.cwds.rest.core.MediaType;
import io.dropwizard.testing.junit.ResourceTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class ReferralResourceImplTest {
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new ReferralResourceImpl()).build();

	@Before
	public void setup() {
	}

	@Test
	public void referralSummaryGetReturns200() {
		assertThat(resources.client().target("/referral/1/summary").request().get().getStatus(), is(equalTo(200)));
	}

	@Test
	public void applicationGetReturnsV1JsonContentType() {
		assertThat(resources.client().target("/referral/1/summary").request().get().getMediaType().toString(), is(equalTo(MediaType.APPLICATION_JSON_V1)));
	}

}
