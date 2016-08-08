package gov.ca.cwds.rest.api.persistence.legacy;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.StaffPersonResourceImpl;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.math.BigDecimal;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StaffPersonTest {

	private static final String ROOT_RESOURCE = "/staffperson/";
	
	private static final StaffPersonResourceImpl mockedStaffPersonResource = mock(StaffPersonResourceImpl.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(mockedStaffPersonResource).build();

	
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private StaffPerson validStaffPerson = validStaffPerson();

	@Before
	public void setup() {
		when(mockedStaffPersonResource.create(eq(validStaffPerson), eq(Api.Version.JSON_VERSION_1.getMediaType()), any(UriInfo.class))).thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
	}
	
    @Test
    public void serializesToJSON() throws Exception {
        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/valid.json"), StaffPerson.class));

        assertThat(MAPPER.writeValueAsString(validStaffPerson()), is(equalTo(expected)));
    }
    
    @Test
    public void deserializesFromJSON() throws Exception {
        assertThat(MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/valid.json"), StaffPerson.class), is(equalTo(validStaffPerson())));
    }
    
	/*
	 * Successful Tests
	 */
	@Test
	public void successfulWithValid() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
	}

	@Test
	public void successfulWithOptionalsNotIncluded() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/optionalsNotIncluded.json"), StaffPerson.class);
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
	}

	/*
	 * id tests 
	 */	
	@Test
	public void failsWhenIdMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/id/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("id may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenIdEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/id/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("id may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenIdNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/id/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("id may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenIdTooShort() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/id/tooShort.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("id length must be 3"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenIdTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/id/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("id length must be 3"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * endDate tests
	 */
	@Test
	public void successWhenEndDateEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/endDate/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	
	@Test
	public void successWhenEndDateNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/endDate/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	
	@Test
	public void failsWhenEndDateWrongFormat() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/endDate/wrongFormat.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("endDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
	}

	/*
	 * firstName tests 
	 */	
	@Test
	public void failsWhenFirstNameMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/firstName/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("firstName may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenFirstNameEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/firstName/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("firstName may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenFirstNameNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/firstName/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("firstName may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenFirstNameTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/firstName/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("firstName length must be between 1 and 20"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * jobTitle tests 
	 */	
	@Test
	public void failsWhenJobTitleMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/jobTitle/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("jobTitle may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenJobTitleEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/jobTitle/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("jobTitle may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenJobTitleNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/jobTitle/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("jobTitle may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenJobTitleTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/jobTitle/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("jobTitle length must be between 1 and 30"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * lastName tests 
	 */	
	@Test
	public void failsWhenLastNameMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastName/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("lastName may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenLastNameEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastName/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("lastName may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenLastNameNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastName/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("lastName may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenLastNameTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastName/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("lastName length must be between 1 and 25"), is(greaterThanOrEqualTo(0)));
	}
	
	
	/*
	 * middleInitial tests 
	 */	
	@Test
	public void failsWhenMiddleInitialMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/middleInitial/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("middleInitial may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenMiddleInitialEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/middleInitial/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("middleInitial may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenMiddleInitialNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/middleInitial/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("middleInitial may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenMiddleInitialTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/middleInitial/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("middleInitial length must be 1"), is(greaterThanOrEqualTo(0)));
	}

	/*
	 * namePrefix tests 
	 */	
	@Test
	public void failsWhenNamePrefixMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/namePrefix/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("namePrefix may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenNamePrefixEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/namePrefix/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("namePrefix may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenNamePrefixNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/namePrefix/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("namePrefix may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenNamePrefixTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/namePrefix/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("namePrefix length must be between 1 and 6"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * phoneNumber tests 
	 */	
	@Test
	public void failsWhenPhoneNumberMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/phoneNumber/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("phoneNumber may not be null"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenPhoneNumberNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/phoneNumber/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("phoneNumber may not be null"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * startDate tests 
	 */	
	@Test
	public void failsWhenStartDateMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/startDate/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("startDate may not be null"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenStartDateNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/startDate/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("startDate may not be null"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenStartDateWrongFormat() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/startDate/wrongFormat.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("startDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * sufxTldsc tests 
	 */	
	@Test
	public void failsWhenSufxTldscMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/sufxTldsc/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("sufxTldsc may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenSufxTldscEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/sufxTldsc/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("sufxTldsc may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenSufxTldscNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/sufxTldsc/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("sufxTldsc may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenSufxTldscTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/sufxTldsc/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("sufxTldsc length must be between 1 and 4"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * tlcmtrInd tests 
	 */	
	@Test
	public void failsWhenTlcmtrIndMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/tlcmtrInd/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("tlcmtrInd may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenTlcmtrIndEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/tlcmtrInd/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("tlcmtrInd may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenTlcmtrIndNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/tlcmtrInd/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("tlcmtrInd may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenTlcmtrIndAllWhitespace() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/tlcmtrInd/allWhitespace.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("tlcmtrInd must be one of [Y, N]"), is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenTlcmtrIndTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/tlcmtrInd/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("tlcmtrInd length must be 1"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void succeedsWhenTlcmtrIndIsY() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/tlcmtrInd/Y.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	
	@Test
	public void succeedsWhenTlcmtrIndIsN() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/tlcmtrInd/N.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}

	@Test
	public void succeedsWhenTlcmtrIndIsSmallY() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/tlcmtrInd/smallY.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	
	@Test
	public void succeedsWhenTlcmtrIndIsSmallN() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/tlcmtrInd/smallN.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	
	/*
	 * lastUpdatedTime tests 
	 */	
	@Test
	public void failsWhenLastUpdatedTimeMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastUpdatedTime/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("lastUpdatedTime may not be null"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenLastUpdatedTimeNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastUpdatedTime/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("lastUpdatedTime may not be null"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenLastUpdatedTimeWrongFormat() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastUpdatedTime/wrongFormat.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("lastUpdatedTime must be in the format of yyyy-MM-dd-HH.mm.ss.SSS"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * lastUpdatedId tests 
	 */	
	@Test
	public void failsWhenLastUpdatedIdMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastUpdatedId/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("lastUpdatedId may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenLastUpdatedIdEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastUpdatedId/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("lastUpdatedId may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenLastUpdatedIdNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastUpdatedId/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("lastUpdatedId may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenLastUpdatedIdTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastUpdatedId/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("lastUpdatedId length must be between 1 and 3"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * fkcwsOfft tests 
	 */	
	@Test
	public void failsWhenFkcwsOfftMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/fkcwsOfft/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkcwsOfft may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenFkcwsOfftEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/fkcwsOfft/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkcwsOfft may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenFkcwsOfftNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/fkcwsOfft/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkcwsOfft may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenFkcwsOfftTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/fkcwsOfft/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkcwsOfft length must be between 1 and 10"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * avlocDsc tests 
	 */	
	@Test
	public void failsWhenAvlocDscMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/avlocDsc/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("avlocDsc may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenAvlocDscEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/avlocDsc/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("avlocDsc may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenAvlocDscNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/avlocDsc/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("avlocDsc may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenAvlocDscTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/avlocDsc/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("avlocDsc length must be between 1 and 160"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * ssrsWkrid tests 
	 */	
	@Test
	public void failsWhenSsrsWkridMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/ssrsWkrid/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("ssrsWkrid may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenSsrsWkridEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/ssrsWkrid/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("ssrsWkrid may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenSsrsWkridNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/ssrsWkrid/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("ssrsWkrid may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenSsrsWkridTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/ssrsWkrid/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("ssrsWkrid length must be between 1 and 4"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * countySpfcd tests 
	 */	
	@Test
	public void failsWhenCountySpfcdMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/countySpfcd/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("countySpfcd may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenCountySpfcdEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/countySpfcd/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("countySpfcd may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenCountySpfcdNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/countySpfcd/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("countySpfcd may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenCountySpfcdTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/countySpfcd/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("countySpfcd length must be between 1 and 2"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * dutyWorkerInd tests 
	 */	
	@Test
	public void failsWhenDutyWorkerIndMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/dutyWorkerInd/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("dutyWorkerInd may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenDutyWorkerIndEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/dutyWorkerInd/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("dutyWorkerInd may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenDutyWorkerIndNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/dutyWorkerInd/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("dutyWorkerInd may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenDutyWorkerIndAllWhitespace() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/dutyWorkerInd/allWhitespace.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("dutyWorkerInd must be one of [Y, N]"), is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenDutyWorkerIndTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/dutyWorkerInd/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("dutyWorkerInd length must be 1"), is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void succeedsWhenDutyWorkerIndIsY() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/dutyWorkerInd/Y.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	
	@Test
	public void succeedsWhenDutyWorkerIndIsN() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/dutyWorkerInd/N.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}

	@Test
	public void succeedsWhenDutyWorkerIndIsSmallY() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/dutyWorkerInd/smallY.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	
	@Test
	public void succeedsWhenDutyWorkerIndIsSmallN() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/dutyWorkerInd/smallN.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	
	/*
	 * fkcwsaddrt tests 
	 */	
	@Test
	public void failsWhenFkcwsaddrtMissing() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/fkcwsaddrt/missing.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkcwsaddrt may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenFkcwsaddrtEmpty() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/fkcwsaddrt/empty.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkcwsaddrt may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenFkcwsaddrtNull() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/fkcwsaddrt/null.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkcwsaddrt may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenFkcwsaddrtTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/fkcwsaddrt/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkcwsaddrt length must be between 1 and 10"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * emailAddress tests 
	 */	
	@Test
	public void failsWhenEmailAddressTooLong() throws Exception {
		StaffPerson toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/emailAddress/tooLong.json"), StaffPerson.class);
		Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("emailAddress length must be between 1 and 50"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	 * Utils
	 */
    private StaffPerson validStaffPerson() {
    	return new StaffPerson("ABC", 
				"2016-08-07",
				"John",
				"CEO",
				"Doe",
				"C",
				"Mr",
				new BigDecimal(9165551212L),
				22,
				"2001-01-02",
				"sufx",
				"Y",
				"q38",
				"2016-08-07-16.41.49.214",
				"MIZN02k11B",
				"abc",
				"def",
				"99",
				"N",
				"3XPCP92b24",
				"john.doe@anyco.com"
				);
    }
}