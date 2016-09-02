
package gov.ca.cwds.rest.api.domain.legacy;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.legacy.Allegation;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.AllegationResourceImpl;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class AllegationTest {

	private static final String ROOT_RESOURCE = "/allegations/";

	private static final AllegationResourceImpl mockedAllegationResource = mock(AllegationResourceImpl.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(mockedAllegationResource)
			.build();

	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	private Allegation validAllegation = validAllegation();
	
	private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private String id = "a";
	private String abuseEndDate = "1973-11-22";
	private String abuseStartDate = "2006-09-12";
	private Integer abuseFrequency = 1;
	private String abuseFrequencyPeriodCode = "b";
	private String abuseLocationDescription = "c";
	private Integer allegationDispositionType= 2;
	private Integer allegationType = 3;
	private String dispositionDescription = "d";
	private String dispositionDate = "1963-11-22";
	private Boolean injuryHarmDetailIndicator = Boolean.TRUE;
	private String nonProtectingParentCode = "e";
	private Boolean staffPersonAddedIndicator = Boolean.FALSE;
	private String fkClientT = "f";
	private String fkClient0 = "g";
	private String fkReferralT = "h";
	private String countySpecificCode = "i";
	private Boolean zippyCrestedIndicator = Boolean.TRUE;
	private Integer placementFacilityType = 4;

	@Before
	public void setup() {
		when(mockedAllegationResource.create(eq(validAllegation), eq(Api.Version.JSON_VERSION_1.getMediaType()),
				any(UriInfo.class))).thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
	}

	/*
	 * Constructor Tests
	 */
	@Test
	public void persistentObjectConstructorTest() throws Exception {
		Allegation domain = new Allegation(id, abuseEndDate, abuseFrequency, abuseFrequencyPeriodCode,
				abuseLocationDescription, abuseStartDate, allegationDispositionType, allegationType,
				dispositionDescription, dispositionDate, injuryHarmDetailIndicator, nonProtectingParentCode,
				staffPersonAddedIndicator, fkClientT, fkClient0, fkReferralT, countySpecificCode, zippyCrestedIndicator,
				placementFacilityType);
		gov.ca.cwds.rest.api.persistence.legacy.Allegation persistent = new gov.ca.cwds.rest.api.persistence.legacy.Allegation(domain, "lastUpdatedId");
		
		Allegation totest = new Allegation(persistent);
		assertThat(totest.getId(), is(equalTo(persistent.getId())));
		assertThat(totest.getAbuseEndDate(), is(equalTo(df.format(persistent.getAbuseEndDate()))));
		assertThat(totest.getAbuseStartDate(), is(equalTo(df.format(persistent.getAbuseStartDate()))));
		assertThat(totest.getAbuseFrequency(), is(equalTo(persistent.getAbuseFrequency())));
		assertThat(totest.getAbuseFrequencyPeriodCode(), is(equalTo(persistent.getAbuseFrequencyPeriodCode())));
		assertThat(totest.getAbuseLocationDescription(), is(equalTo(persistent.getAbuseLocationDescription())));
		assertThat(totest.getAllegationDispositionType(), is(equalTo(persistent.getAllegationDispositionType())));
		assertThat(totest.getAllegationType(), is(equalTo(persistent.getAllegationType())));
		assertThat(totest.getDispositionDescription(), is(equalTo(persistent.getDispositionDescription())));
		assertThat(totest.getDispositionDate(), is(equalTo(df.format(persistent.getDispositionDate()))));
		assertThat(totest.getInjuryHarmDetailIndicator(), is(equalTo(Boolean.TRUE)));
		assertThat(totest.getNonProtectingParentCode(), is(equalTo(persistent.getNonProtectingParentCode())));
		assertThat(totest.getStaffPersonAddedIndicator(), is(equalTo(Boolean.FALSE)));
		assertThat(totest.getFkClient0(), is(equalTo(persistent.getFkClient0())));
		assertThat(totest.getFkClientT(), is(equalTo(persistent.getFkClientT())));
		assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
		assertThat(totest.getZippyCrestedIndicator(), is(equalTo(Boolean.TRUE)));
		assertThat(totest.getPlacementFacilityType(), is(equalTo(persistent.getPlacementFacilityType())));
	}

	@Test
	public void jsonCreatorConstructorTest() throws Exception {
		Allegation domain = new Allegation(id, abuseEndDate, abuseFrequency, abuseFrequencyPeriodCode,
				abuseLocationDescription, abuseStartDate, allegationDispositionType, allegationType,
				dispositionDescription, dispositionDate, injuryHarmDetailIndicator, nonProtectingParentCode,
				staffPersonAddedIndicator, fkClientT, fkClient0, fkReferralT, countySpecificCode, zippyCrestedIndicator,
				placementFacilityType);
		assertThat(domain.getId(), is(equalTo(id)));
		assertThat(domain.getAbuseEndDate(), is(equalTo(abuseEndDate)));
		assertThat(domain.getAbuseStartDate(), is(equalTo(abuseStartDate)));
		assertThat(domain.getAbuseFrequency(), is(equalTo(abuseFrequency)));
		assertThat(domain.getAbuseFrequencyPeriodCode(), is(equalTo(abuseFrequencyPeriodCode)));
		assertThat(domain.getAbuseLocationDescription(), is(equalTo(abuseLocationDescription)));
		assertThat(domain.getAllegationDispositionType(), is(equalTo(allegationDispositionType)));
		assertThat(domain.getAllegationType(), is(equalTo(allegationType)));
		assertThat(domain.getDispositionDescription(), is(equalTo(dispositionDescription)));
		assertThat(domain.getDispositionDate(), is(equalTo(dispositionDate)));
		assertThat(domain.getInjuryHarmDetailIndicator(), is(equalTo(injuryHarmDetailIndicator)));
		assertThat(domain.getNonProtectingParentCode(), is(equalTo(nonProtectingParentCode)));
		assertThat(domain.getStaffPersonAddedIndicator(), is(equalTo(staffPersonAddedIndicator)));
		assertThat(domain.getFkClient0(), is(equalTo(fkClient0)));
		assertThat(domain.getFkClientT(), is(equalTo(fkClientT)));
		assertThat(domain.getCountySpecificCode(), is(equalTo(countySpecificCode)));
		assertThat(domain.getZippyCrestedIndicator(), is(equalTo(zippyCrestedIndicator)));
		assertThat(domain.getPlacementFacilityType(), is(equalTo(placementFacilityType)));
	}

	
	
	@Test
	public void serializesToJSON() throws Exception {
		final String expected = MAPPER.writeValueAsString(
				MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/valid.json"), Allegation.class));
		assertThat(MAPPER.writeValueAsString(validAllegation()), is(equalTo(expected)));
	}

	@Test
	public void deserializesFromJSON() throws Exception {
		Allegation deserialized = MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/valid.json"), Allegation.class);
		Allegation valid = validAllegation();
		assertThat(deserialized,
				is(equalTo(valid)));
	}

	/*
	 * Successful Tests
	 */
	@Test
	public void successfulWithValid() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/valid.json"),
				Allegation.class);
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
	}

	@Test
	public void successfulWithOptionalsNotIncluded() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/optionalsNotIncluded.json"),
				Allegation.class);
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
	}

	/*
	 * id tests
	 */
	@Test
	public void failsWhenIdMissing() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/id/missing.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("id may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenIdEmpty() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/id/empty.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("id may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenIdNull() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/id/null.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("id may not be empty"), is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenIdTooLong() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/id/tooLong.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("id Size must be between 1 and 10"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * abuseEndDate tests
	 */
	@Test
	public void failsWhenAbuseEndDateWrongFormat() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/abuseEndDate/wrongFormat.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("abuseEndDate must be in the format of yyyy-MM-dd"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * abuseFrequency tests
	 */
	@Test
	public void failsWhenAbuseFrequencyMissing() throws Exception {
		Allegation toCreate = MAPPER
				.readValue(fixture("fixtures/legacy/Allegation/invalid/abuseFrequency/missing.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("abuseFrequency may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenAbuseFrequencyNull() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/abuseFrequency/null.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("abuseFrequency may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * abuseFrequencyPeriodCode tests
	 */
	@Test
	public void failsWhenAbuseFrequencyPeriodCodeMissing() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/abuseFrequencyPeriodCode/missing.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("abuseFrequencyPeriodCode may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenAbuseFrequencyPeriodCodeNull() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/abuseFrequencyPeriodCode/null.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("abuseFrequencyPeriodCode may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenAbuseFrequencyPeriodCodeEmpty() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/abuseFrequencyPeriodCode/empty.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("abuseFrequencyPeriodCode may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenAbuseFrequencyPeriodCodeTooLong() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/abuseFrequencyPeriodCode/tooLong.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("abuseFrequencyPeriodCode Size must be 1"),
				is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenAbuseFrequencyPeriodCodeNotValidValue() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/abuseFrequencyPeriodCode/notValidValue.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("abuseFrequencyPeriodCode must be one of [D, M, W, Y]"),
				is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void succeedsWhenAbuseFrequencyPeriodCodeisD() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/valid/abuseFrequencyPeriodCode/D.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}

	@Test
	public void succeedsWhenAbuseFrequencyPeriodCodeisM() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/valid/abuseFrequencyPeriodCode/M.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	
	@Test
	public void succeedsWhenAbuseFrequencyPeriodCodeisW() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/valid/abuseFrequencyPeriodCode/W.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	
	@Test
	public void succeedsWhenAbuseFrequencyPeriodCodeisY() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/valid/abuseFrequencyPeriodCode/Y.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	
	@Test
	public void succeedsWhenAbuseFrequencyPeriodCodeIsLowerCase() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/valid/abuseFrequencyPeriodCode/lowerCase.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	/*
	 * abuseLocationDescription tests
	 */
	@Test
	public void failsWhenAbuseLocationDescriptionMissing() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/abuseLocationDescription/missing.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("abuseLocationDescription may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenAbuseLocationDescriptionNull() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/abuseLocationDescription/null.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("abuseLocationDescription may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenAbuseLocationDescriptionEmpty() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/abuseLocationDescription/empty.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("abuseLocationDescription may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenAbuseLocationDescriptionTooLong() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/abuseLocationDescription/tooLong.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("abuseLocationDescription size must be between 1 and 75"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * abuseStartDate tests
	 */
	@Test
	public void failsWhenAbuseStartDateWrongFormat() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/abuseStartDate/wrongFormat.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("abuseStartDate must be in the format of yyyy-MM-dd"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * allegationDispositionType tests
	 */
	@Test
	public void failsWhenAllegationDispositionTypeMissing() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/allegationDispositionType/missing.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("allegationDispositionType may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenAllegationDispositionTypeNull() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/allegationDispositionType/null.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("allegationDispositionType may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenAllegationDispositionTypeEmpty() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/allegationDispositionType/empty.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("allegationDispositionType may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * allegationType tests
	 */
	@Test
	public void failsWhenAllegationTypeMissing() throws Exception {
		Allegation toCreate = MAPPER
				.readValue(fixture("fixtures/legacy/Allegation/invalid/allegationType/missing.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("allegationType may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenAllegationTypeNull() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/allegationType/null.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("allegationType may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenAllegationTypeEmpty() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/allegationType/empty.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("allegationType may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * dispositionDescription tests
	 */
	@Test
	public void failsWhenDispositionDescriptionMissing() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/dispositionDescription/missing.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("dispositionDescription may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenDispositionDescriptionNull() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/dispositionDescription/null.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("dispositionDescription may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenDispositionDescriptionEmpty() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/dispositionDescription/empty.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("dispositionDescription may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * @Test public void failsWhenDispositionDescriptionTooLong() throws
	 * Exception { Allegation toCreate = MAPPER.readValue(fixture(
	 * "fixtures/legacy/Allegation/invalid/dispositionDescription/tooLong.json")
	 * , Allegation.class); Response response =
	 * resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.
	 * JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate,
	 * Api.MEDIA_TYPE_JSON_V1)); assertThat(response.getStatus(),
	 * is(equalTo(422))); assertThat(response.readEntity(String.class).
	 * indexOf("dispositionDescription size must be between 1 and 254"),
	 * is(greaterThanOrEqualTo(0))); }
	 */
	/*
	 * dispositionDate tests
	 */
	@Test
	public void failsWhenDispositionDateWrongFormat() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/dispositionDate/wrongFormat.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("dispositionDate must be in the format of yyyy-MM-dd"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * injuryHarmDetailIndVar tests
	 */
	@Test
	public void failsWhenInjuryHarmDetailIndVarMissing() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/injuryHarmDetailIndicator/missing.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("injuryHarmDetailIndicator may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenInjuryHarmDetailIndVarNull() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/injuryHarmDetailIndicator/null.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("injuryHarmDetailIndicator may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenInjuryHarmDetailIndVarEmpty() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/injuryHarmDetailIndicator/empty.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("injuryHarmDetailIndicator may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * nonProtectingParentCode tests
	 */
	@Test
	public void failsWhenNonProtectingParentCodeMissing() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/nonProtectingParentCode/missing.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("nonProtectingParentCode may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenNonProtectingParentCodeNull() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/nonProtectingParentCode/null.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("nonProtectingParentCode may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenNonProtectingParentCodeEmpty() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/nonProtectingParentCode/empty.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("nonProtectingParentCode may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenNonProtectingParentCodeTooLong() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/nonProtectingParentCode/tooLong.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("nonProtectingParentCode Size must be 1"),
				is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void failsWhenNonProtectingParentCodeNotValidValue() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/nonProtectingParentCode/notValidValue.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("nonProtectingParentCode must be one of [U, P, Y, N]"),
				is(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void succeedsWhenNonProtectingParentCodeisU() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/valid/nonProtectingParentCode/U.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}

	@Test
	public void succeedsWhenNonProtectingParentCodeisP() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/valid/nonProtectingParentCode/P.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	
	@Test
	public void succeedsWhenNonProtectingParentCodeisY() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/valid/nonProtectingParentCode/Y.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	
	@Test
	public void succeedsWhenNonProtectingParentCodeisN() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/valid/nonProtectingParentCode/N.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}
	
	@Test
	public void succeedsWhenNonProtectingParentCodeIsLowerCase() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/valid/nonProtectingParentCode/lowerCase.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(204)));
	}

	/*
	 * staffPersonAddedInd tests
	 */
	@Test
	public void failsWhenStaffPersonAddedIndMissing() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/staffPersonAddedIndicator/missing.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenStaffPersonAddedIndNull() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/staffPersonAddedIndicator/null.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenStaffPersonAddedIndEmpty() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/staffPersonAddedIndicator/empty.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * fkClientT tests
	 */
	@Test
	public void failsFkClientTMissing() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/fkClientT/missing.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkClientT may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenFkClientTNull() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/fkClientT/null.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkClientT may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenFkClientTEmpty() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/fkClientT/empty.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkClientT may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenFkClientTTooLong() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/fkClientT/tooLong.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkClientT size must be between 1 and 10"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * fkClient0 tests
	 */
	@Test
	public void failsWhenFkClient0TooLong() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/fkClient0/tooLong.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkClient0 size must be between 0 and 10"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * fkReferralT tests
	 */
	@Test
	public void failsFkReferralTMissing() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/fkReferralT/missing.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkReferralT may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenFkReferralTNull() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/fkReferralT/null.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkReferralT may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenFkReferralTEmpty() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/fkReferralT/empty.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkReferralT may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenFkReferralTTooLong() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/fkReferralT/tooLong.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("fkReferralT size must be between 1 and 10"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * countySpecificCode tests
	 */
	@Test
	public void failsWhenCountySpecificCodeMissing() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/countySpecificCode/missing.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenCountySpecificCodeNull() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/countySpecificCode/null.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenCountySpecificCodeEmpty() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/countySpecificCode/empty.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenCountySpecificCodeTooLong() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/countySpecificCode/tooLong.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("countySpecificCode Size must be between 1 and 2"),
				is(greaterThanOrEqualTo(0)));
	}

	/*
	 * zippyCrestedInd tests
	 */
	@Test
	public void failsWhenZippyCrestedIndMissing() throws Exception {
		Allegation toCreate = MAPPER.readValue(
				fixture("fixtures/legacy/Allegation/invalid/zippyCrestedIndicator/missing.json"), Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("zippyCrestedIndicator may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenZippyCrestedIndNull() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/zippyCrestedIndicator/null.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("zippyCrestedIndicator may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	@Test
	public void failsWhenZippyCrestedIndEmpty() throws Exception {
		Allegation toCreate = MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/zippyCrestedIndicator/empty.json"),
				Allegation.class);
		Response response = resources.client().target(ROOT_RESOURCE).request()
				.accept(Api.Version.JSON_VERSION_1.getMediaType())
				.post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
		assertThat(response.getStatus(), is(equalTo(422)));
		assertThat(response.readEntity(String.class).indexOf("zippyCrestedIndicator may not be null"),
				is(greaterThanOrEqualTo(0)));
	}

	private Allegation validAllegation() {
		return new Allegation("Aaeae9r0F4", "1999-07-15", 2, "M", "Barber Shop", "1999-07-15", 0, 2180, "Fremont", "", false, "N", false, "AHooKwN0F4", "MKPFcB90F4", "8mu1E710F4", "19", false, 6574);
	}
}
