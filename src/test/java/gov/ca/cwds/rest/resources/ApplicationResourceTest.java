package gov.ca.cwds.rest.resources;

import gov.ca.cwds.rest.core.MediaType;
import io.dropwizard.testing.junit.ResourceTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

public class ApplicationResourceTest {
	private static final String APP_NAME = "my app";
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new ApplicationResource(APP_NAME)).build();

	@Before
	public void setup() {
	}

	@Test
	public void applicationGetReturns200() {
		assertThat(resources.client().target("/application").request().get().getStatus(), is(equalTo(200)));
	}

	@Test
	public void applicationGetReturnsCorrectName() {
		assertThat(resources.client().target("/application").request().get().readEntity(String.class), is(equalTo(APP_NAME)));
	}
	
	@Test
	public void applicationGetReturnsV1JsonContentType() {
		assertThat(resources.client().target("/application").request().get().getMediaType().toString(), is(equalTo(MediaType.APPLICATION_JSON_V1)));
	}
}
