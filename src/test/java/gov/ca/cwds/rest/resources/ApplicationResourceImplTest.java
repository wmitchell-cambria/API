package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import gov.ca.cwds.rest.core.ApiPoc;
import io.dropwizard.testing.junit.ResourceTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class ApplicationResourceImplTest {
	private static final String APP_NAME = "my app";
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new ApplicationResourceImpl(APP_NAME)).build();

	@Before
	public void setup() {
	}

	@Test
	public void applicationGetReturns200() {
		assertThat(resources.client().target("/application").request().accept(ApiPoc.Version.JSON_VERSION_1.getMediaType()).get().getStatus(), is(equalTo(200)));
	}

	@Test
	public void applicationGetReturnsCorrectName() {
		assertThat(resources.client().target("/application").request().accept(ApiPoc.Version.JSON_VERSION_1.getMediaType()).get().readEntity(String.class), is(equalTo(APP_NAME)));
	}
	
	@Test
	public void applicationGetReturnsV1JsonContentType() {
		assertThat(resources.client().target("/application").request().accept(ApiPoc.Version.JSON_VERSION_1.getMediaType()).get().getMediaType().toString(), is(equalTo(ApiPoc.MEDIA_TYPE_JSON_V1)));
	}
}
