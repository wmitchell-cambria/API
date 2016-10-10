package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.ResourceTestRule;

public class PeopleResourceTest {
	private static final String ROOT_RESOURCE = "/people/";
	private static final String FOUND_RESOURCE = "/people/1";

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new PeopleResource())
			.build();

	@Before
	public void setup() throws Exception {
		
	}

	@Test
	public void returns200OnSuccess() throws Exception {
		int receivedStatus = resources.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON).get()
				.getStatus();
		int expectedStatus = 200;
		assertThat( receivedStatus, is(expectedStatus));
	}
	
//	@Test
//	public void returns406OnWrongAcceptType() throws Exception {
//		int receivedStatus = resources.client().target(FOUND_RESOURCE).request().accept("UNSUPPORTED_VERSION").get()
//				.getStatus();
//		int expectedStatus = 406;
//		assertThat( receivedStatus, is(equalTo(expectedStatus)));
//	}
	
	//TODO : RDB - more tests
}
