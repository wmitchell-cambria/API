package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.hamcrest.junit.ExpectedException;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.Person;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource
 * functions to {@link CrudsResourceImpl}. As such the tests in here reflect
 * that assumption.
 * 
 * @author CWDS API Team
 */
public class PersonResourceTest {
	private static final String ROOT_RESOURCE = "/people/";
	private static final String FOUND_RESOURCE = "/people/1";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@SuppressWarnings({ "unchecked" })
	private static CrudsResource<Person> mockedCrudsResource = mock(CrudsResource.class);

	@ClassRule
	public static final ResourceTestRule inMemoryResource = ResourceTestRule.builder().addResource(new PersonResource(mockedCrudsResource))
			.build();

	/*
	 * Get Tests
	 */

	@Test
	public void getDelegatesToCrudsResource() throws Exception {
		inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON).get().getStatus();
		verify(mockedCrudsResource).get("1", MediaType.APPLICATION_JSON);
	}

	/*
	 * Create Tests
	 */
	@Test
	public void createDelegatesToCrudsResource() throws Exception {
		Person person = new Person("firstname", "last", "gender", "11/22/1973", "000000000", null);
		inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(person, MediaType.APPLICATION_JSON)).getStatus();
		verify(mockedCrudsResource).create(eq(person), eq(MediaType.APPLICATION_JSON), any(UriInfo.class),
				any(HttpServletResponse.class));
	}

	/*
	 * Delete Tests
	 */
	@Test
	public void deleteReturns501() throws Exception {
		int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
				.accept(MediaType.APPLICATION_JSON).delete().getStatus();
		int expectedStatus = 501;
		assertThat(receivedStatus, is(expectedStatus));
	}

	/*
	 * Update Tests
	 */
	@Test
	public void udpateReturns501() throws Exception {
		Person person = new Person("firstname", "last", "gender", "11/22/1973", "000000000", null);
		int status = inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
				.put(Entity.entity(person, MediaType.APPLICATION_JSON)).getStatus();
		assertThat(status, is(501));
	}
}