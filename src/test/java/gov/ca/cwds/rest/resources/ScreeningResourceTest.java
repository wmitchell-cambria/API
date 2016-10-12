package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Screening;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class ScreeningResourceTest {
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

	private static final String ROOT_RESOURCE = "/screenings/";
	private static final String FOUND_RESOURCE = "/screenings/1";
	private static final String NOT_FOUND_RESOURCE = "/screenings/2";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@SuppressWarnings({ "unchecked", "unused" })
	private CrudsResource<Screening> mockedCrudsResource = mock(CrudsResource.class);

	@ClassRule
	public static final ResourceTestRule inMemoryResource = ResourceTestRule.builder().addResource(new ScreeningResource())
			.build();

	@ClassRule
	public static final ResourceTestRule grizzlyResource = ResourceTestRule.builder()
			.setTestContainerFactory(new GrizzlyWebTestContainerFactory()).addResource(new ScreeningResource()).build();

	@Before
	public void setup() throws Exception {

	}

	/*
	 * Get Tests
	 */
	@Test
	public void getReturns200OnSuccess() throws Exception {
		int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
				.accept(MediaType.APPLICATION_JSON).get().getStatus();
		int expectedStatus = 200;
		assertThat(receivedStatus, is(expectedStatus));
	}

	@Test
	public void getReturns406OnWrongAcceptType() throws Exception {
		int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request().accept("UNSUPPORTED_VERSION")
				.get().getStatus();
		int expectedStatus = 406;
		assertThat(receivedStatus, is(equalTo(expectedStatus)));
	}

	@Test
	public void getReturns404WhenNotFound() throws Exception {
		int receivedStatus = inMemoryResource.client().target(NOT_FOUND_RESOURCE).request()
				.accept(MediaType.APPLICATION_JSON).get().getStatus();
		int expectedStatus = 404;
		assertThat(receivedStatus, is(equalTo(expectedStatus)));
	}

	// @Test
	// public void getDelegatesToCrudsResourceImpl() throws Exception {
	// inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON).get().getStatus();
	// verify(mockedCrudsResource).get("1", MediaType.APPLICATION_JSON);
	// }

	@Test
	public void getReturnsJsonWhichCanBeSerializedIntoScreening() throws Exception {
		String json = inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(String.class);
		Screening getresult = MAPPER.readValue(json, Screening.class);
		Screening expected = new Screening((long)101,"reference", "12/22/2001", "county", "11/22/2010", "home", "method","name","time","decision","11/22/2010","narrative");

		assertThat(getresult, is(expected));
	}

	/*
	 * Create Tests
	 */
	@Test
	public void createReturns201OnSuccess() throws Exception {
		Screening screening = new Screening((long)101,"reference", "12/22/2001", "county", "11/22/2010", "home", "method","name","time","decision","11/22/2010","narrative");
		int status = grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(screening, MediaType.APPLICATION_JSON)).getStatus();
		assertThat(status, is(201));
	}

	@Test
	public void createReturns400WhenCannotProcessJson() throws Exception {
		// create expects to deserialize the payload to a screening - lets give it
		// an address instead.
		Address address = new Address("street", "city", "state", 12345);
		int status = grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(address, MediaType.APPLICATION_JSON)).getStatus();
		assertThat(status, is(400));

	}

	@Test
	public void createReturns406OnWrongAcceptType() throws Exception {
		Screening screening = new Screening((long)101,"reference", "12/22/2001", "county", "11/22/2010", "home", "method","name","time","decision","11/22/2010","narrative");
		int status = grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request().accept("UNSUPPORTED_TYPE")
				.post(Entity.entity(screening, MediaType.APPLICATION_JSON)).getStatus();
		assertThat(status, is(406));
	}

	@Test
	public void createReturns422WhenCannotValidateScreening() throws Exception {
		Screening screening = new Screening((long)101,"reference", "12/22/2001", "county", "22/11/2010", "home", "method","name","time","decision","11/22/2010", "narrative");
		int status = grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(screening, MediaType.APPLICATION_JSON)).getStatus();
		assertThat(status, is(422));

	}

	@Test
	public void createReturnsLocationHeader() throws Exception {
		Screening screening = new Screening((long)101,"reference", "12/22/2001", "county", "11/22/2010", "home", "method","name","time","decision","11/22/2010","narrative");
		Object o = grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(screening, MediaType.APPLICATION_JSON)).getHeaders().get("Location");
		assertThat(o, is(notNullValue()));
	}

	@Test
	public void createReturnsProperJson() throws Exception {
		String expected = "{ \"id\": \"1\", \"Screening\": { \"id\": 101, \"reference\": \"reference\", \"ended_at\": \"12/22/2001\", "
				+ "\"incident_county\": \"county\", \"incident_date\": \"11/22/2010\", \"location_type\": \"home\","
				+ "\"method_of_referral\": \"method\", \"name\": \"name\", \"response_time\": \"time\","
                + "\"screening_decision\": \"decision\", \"started_at\": \"11/22/2010\", \"narrative\": \"narrative\" } }";
		Screening screening = new Screening((long)101,"reference", "12/22/2001", "county", "11/22/2010", "home", "method","name","time","decision","11/22/2010","narrative");
		String json = grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(screening, MediaType.APPLICATION_JSON)).readEntity(String.class);

		expected = expected.replaceAll("\\s+","");
		json = json.replaceAll("\\s+","");
		
		assertThat(json, is(expected));
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
		Screening screening = new Screening((long)101,"reference", "12/22/2001", "county", "11/22/2010", "home", "method","name","time","decision","11/22/2010","narrative");
		int status = grizzlyResource.getJerseyTest().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
				.put(Entity.entity(screening, MediaType.APPLICATION_JSON)).getStatus();
		assertThat(status, is(501));		
	}
}