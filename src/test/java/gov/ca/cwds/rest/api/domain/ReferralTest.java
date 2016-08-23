package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.ReferralResourceImpl;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class ReferralTest {

	private static final String ROOT_RESOURCE = "/referrals/";
	
	private static final ReferralResourceImpl mockedReferralResource = mock(ReferralResourceImpl.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(mockedReferralResource).build();

	
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Referral validReferral = validReferral();

	@Before
	public void setup() {
		when(mockedReferralResource.create(eq(validReferral), eq(Api.Version.JSON_VERSION_1.getMediaType()), any(UriInfo.class))).thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
	}
	

	@Test
    public void serializesToJSON() throws Exception {
        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/legacy/Referral/valid/valid.json"), Referral.class));

        assertThat(MAPPER.writeValueAsString(validReferral()), is(equalTo(expected)));
    }
    
    @Test
    public void deserializesFromJSON() throws Exception {
        Referral deserialized = MAPPER.readValue(fixture("fixtures/legacy/Referral/valid/valid.json"), Referral.class);
        Referral valid = validReferral();
    	assertThat(deserialized, is(equalTo(valid)));
    }
    
	/*
	 * Successful Tests
	 */
	@Test
	public void successfulWithValid() throws Exception {
		Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/valid/valid.json"), Referral.class);
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
	}

	@Test
	public void successfulWithOptionalsNotIncluded() throws Exception {
		Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/valid/optionalsNotIncluded.json"), Referral.class);
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
	}

    /*
    * id Tests
    */
    @Test
    public void failsWhenIdMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/id/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("id may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenIdNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/id/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("id may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenIdEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/id/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("id may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenIdTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/id/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("id size must be between 1 and 10"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * additionalInfoIncludedCode Tests
    */
    @Test
    public void failsWhenAdditionalInfoIncludedCodeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/additionalInfoIncludedCode/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("additionalInfoIncludedCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenAdditionalInfoIncludedCodeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/additionalInfoIncludedCode/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("additionalInfoIncludedCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenAdditionalInfoIncludedCodeEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/additionalInfoIncludedCode/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("additionalInfoIncludedCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenAdditionalInfoIncludedCodeTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/additionalInfoIncludedCode/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("additionalInfoIncludedCode size must be 1"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * anonymousReporterIndicator Tests
    */
    @Test
    public void failsWhenAnonymousReporterIndicatorMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/anonymousReporterIndicator/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("anonymousReporterIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenAnonymousReporterIndicatorNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/anonymousReporterIndicator/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("anonymousReporterIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenAnonymousReporterIndicatorEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/anonymousReporterIndicator/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("anonymousReporterIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenAnonymousReporterIndicatorAllWhitespace() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/anonymousReporterIndicator/allWhitespace.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("anonymousReporterIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * applicationForPetitionIndicator Tests
    */
    @Test
    public void failsWhenApplicationForPetitionIndicatorMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/applicationForPetitionIndicator/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("applicationForPetitionIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenApplicationForPetitionIndicatorNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/applicationForPetitionIndicator/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("applicationForPetitionIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenApplicationForPetitionIndicatorEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/applicationForPetitionIndicator/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("applicationForPetitionIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenApplicationForPetitionIndicatorAllWhitespace() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/applicationForPetitionIndicator/allWhitespace.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("applicationForPetitionIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * approvalNumber Tests
    */
    @Test
    public void successWhenApprovalNumberEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/approvalNumber/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenApprovalNumberNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/approvalNumber/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenApprovalNumberTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/approvalNumber/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("approvalNumber size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * approvalStatusType Tests
    */
    @Test
    public void failsWhenApprovalStatusTypeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/approvalStatusType/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("approvalStatusType may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenApprovalStatusTypeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/approvalStatusType/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("approvalStatusType may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * caretakersPerpetratorCode Tests
    */
    @Test
    public void failsWhenCaretakersPerpetratorCodeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/caretakersPerpetratorCode/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("caretakersPerpetratorCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenCaretakersPerpetratorCodeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/caretakersPerpetratorCode/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("caretakersPerpetratorCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenCaretakersPerpetratorCodeEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/caretakersPerpetratorCode/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("caretakersPerpetratorCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenCaretakersPerpetratorCodeTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/caretakersPerpetratorCode/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("caretakersPerpetratorCode size must be 1"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * closureDate Tests
    */
    @Test
    public void successWhenClosureDateEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/closureDate/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenClosureDateNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/closureDate/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenClosureDateWrongFormat() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/closureDate/wrongFormat.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("closureDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * communicationMethodType Tests
    */
    @Test
    public void failsWhenCommunicationMethodTypeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/communicationMethodType/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("communicationMethodType may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenCommunicationMethodTypeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/communicationMethodType/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("communicationMethodType may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * currentLocationOfChildren Tests
    */
    @Test
    public void successWhenCurrentLocationOfChildrenEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/currentLocationOfChildren/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenCurrentLocationOfChildrenNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/currentLocationOfChildren/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenCurrentLocationOfChildrenTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/currentLocationOfChildren/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("currentLocationOfChildren size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * drmsAllegationDescriptionDoc Tests
    */
    @Test
    public void successWhenDrmsAllegationDescriptionDocEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/drmsAllegationDescriptionDoc/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenDrmsAllegationDescriptionDocNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/drmsAllegationDescriptionDoc/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenDrmsAllegationDescriptionDocTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/drmsAllegationDescriptionDoc/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("drmsAllegationDescriptionDoc size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * drmsErReferralDoc Tests
    */
    @Test
    public void successWhenDrmsErReferralDocEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/drmsErReferralDoc/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenDrmsErReferralDocNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/drmsErReferralDoc/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenDrmsErReferralDocTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/drmsErReferralDoc/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("drmsErReferralDoc size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * drmsInvestigationDoc Tests
    */
    @Test
    public void successWhenDrmsInvestigationDocEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/drmsInvestigationDoc/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenDrmsInvestigationDocNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/drmsInvestigationDoc/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenDrmsInvestigationDocTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/drmsInvestigationDoc/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("drmsInvestigationDoc size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * filedSuspectedChildAbuseReporttoLawEnforcementIndicator Tests
    */
    @Test
    public void failsWhenFiledSuspectedChildAbuseReporttoLawEnforcementIndicatorMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/filedSuspectedChildAbuseReporttoLawEnforcementIndicator/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("filedSuspectedChildAbuseReporttoLawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenFiledSuspectedChildAbuseReporttoLawEnforcementIndicatorNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/filedSuspectedChildAbuseReporttoLawEnforcementIndicator/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("filedSuspectedChildAbuseReporttoLawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenFiledSuspectedChildAbuseReporttoLawEnforcementIndicatorEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/filedSuspectedChildAbuseReporttoLawEnforcementIndicator/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("filedSuspectedChildAbuseReporttoLawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenFiledSuspectedChildAbuseReporttoLawEnforcementIndicatorAllWhitespace() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/filedSuspectedChildAbuseReporttoLawEnforcementIndicator/allWhitespace.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("filedSuspectedChildAbuseReporttoLawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * familyAwarenessIndicator Tests
    */
    @Test
    public void failsWhenFamilyAwarenessIndicatorMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyAwarenessIndicator/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("familyAwarenessIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenFamilyAwarenessIndicatorNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyAwarenessIndicator/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("familyAwarenessIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenFamilyAwarenessIndicatorEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyAwarenessIndicator/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("familyAwarenessIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenFamilyAwarenessIndicatorAllWhitespace() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyAwarenessIndicator/allWhitespace.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("familyAwarenessIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * govtEntityType Tests
    */
    @Test
    public void failsWhenGovtEntityTypeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/govtEntityType/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("govtEntityType may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenGovtEntityTypeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/govtEntityType/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("govtEntityType may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * legalDefinitionCode Tests
    */
    @Test
    public void failsWhenLegalDefinitionCodeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/legalDefinitionCode/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("legalDefinitionCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenLegalDefinitionCodeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/legalDefinitionCode/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("legalDefinitionCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenLegalDefinitionCodeEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/legalDefinitionCode/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("legalDefinitionCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenLegalDefinitionCodeTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/legalDefinitionCode/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("legalDefinitionCode size must be 1"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * legalRightsNoticeIndicator Tests
    */
    @Test
    public void failsWhenLegalRightsNoticeIndicatorMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/legalRightsNoticeIndicator/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("legalRightsNoticeIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenLegalRightsNoticeIndicatorNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/legalRightsNoticeIndicator/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("legalRightsNoticeIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenLegalRightsNoticeIndicatorEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/legalRightsNoticeIndicator/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("legalRightsNoticeIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenLegalRightsNoticeIndicatorAllWhitespace() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/legalRightsNoticeIndicator/allWhitespace.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("legalRightsNoticeIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * limitedAccessCode Tests
    */
    @Test
    public void failsWhenLimitedAccessCodeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessCode/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("limitedAccessCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenLimitedAccessCodeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessCode/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("limitedAccessCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenLimitedAccessCodeEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessCode/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("limitedAccessCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenLimitedAccessCodeTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessCode/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("limitedAccessCode size must be 1"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenLimitedAccessCodeNotValidValue() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessCode/notValidValue.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("limitedAccessCode must be one of [S, R, N]"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void successWhenLimitedAccessCodeIsS() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessCode/S.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenLimitedAccessCodeIsR() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessCode/R.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenLimitedAccessCodeIsN() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessCode/N.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    /*
    * mandatedCrossReportReceivedDate Tests
    */
    @Test
    public void successWhenMandatedCrossReportReceivedDateEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/mandatedCrossReportReceivedDate/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenMandatedCrossReportReceivedDateNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/mandatedCrossReportReceivedDate/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenMandatedCrossReportReceivedDateWrongFormat() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/mandatedCrossReportReceivedDate/wrongFormat.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("mandatedCrossReportReceivedDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * referralName Tests
    */
    @Test
    public void failsWhenReferralNameMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/referralName/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referralName may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReferralNameNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/referralName/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referralName may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReferralNameEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/referralName/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referralName may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReferralNameTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/referralName/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referralName size must be between 1 and 35"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * openAdequateCaseCode Tests
    */
    @Test
    public void failsWhenOpenAdequateCaseCodeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/openAdequateCaseCode/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("openAdequateCaseCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenOpenAdequateCaseCodeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/openAdequateCaseCode/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("openAdequateCaseCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenOpenAdequateCaseCodeEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/openAdequateCaseCode/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("openAdequateCaseCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenOpenAdequateCaseCodeTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/openAdequateCaseCode/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("openAdequateCaseCode size must be 1"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * receivedDate Tests
    */
    @Test
    public void failsWhenReceivedDateMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/receivedDate/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("receivedDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReceivedDateNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/receivedDate/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("receivedDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReceivedDateWrongFormat() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/receivedDate/wrongFormat.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("receivedDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * receivedTime Tests
    */
    @Test
    public void failsWhenReceivedTimeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/receivedTime/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("receivedTime must be in the format of yyyy-MM-dd-HH.mm.ss.SSS"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReceivedTimeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/receivedTime/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("receivedTime must be in the format of yyyy-MM-dd-HH.mm.ss.SSS"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReceivedTimeWrongFormat() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/receivedTime/wrongFormat.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("receivedTime must be in the format of yyyy-MM-dd-HH.mm.ss.SSS"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * referralResponseType Tests
    */
    @Test
    public void failsWhenReferralResponseTypeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/referralResponseType/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referralResponseType may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReferralResponseTypeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/referralResponseType/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referralResponseType may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * referredToResourceType Tests
    */
    @Test
    public void failsWhenReferredToResourceTypeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/referredToResourceType/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referredToResourceType may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReferredToResourceTypeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/referredToResourceType/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referredToResourceType may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * responseDeterminationDate Tests
    */
    @Test
    public void successWhenResponseDeterminationDateEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responseDeterminationDate/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenResponseDeterminationDateNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responseDeterminationDate/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenResponseDeterminationDateWrongFormat() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responseDeterminationDate/wrongFormat.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("responseDeterminationDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * responseDeterminationTime Tests
    */
    @Test
    public void successWhenResponseDeterminationTimeEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responseDeterminationTime/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenResponseDeterminationTimeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responseDeterminationTime/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenResponseDeterminationTimeWrongFormat() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responseDeterminationTime/wrongFormat.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("responseDeterminationTime must be in the format of yyyy-MM-dd-HH.mm.ss.SSS"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * responseRationaleText Tests
    */
    @Test
    public void successWhenResponseRationaleTextEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responseRationaleText/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenResponseRationaleTextNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responseRationaleText/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenResponseRationaleTextTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responseRationaleText/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("responseRationaleText size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * screenerNoteText Tests
    */
    @Test
    public void successWhenScreenerNoteTextEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/screenerNoteText/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenScreenerNoteTextNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/screenerNoteText/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenScreenerNoteTextTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/screenerNoteText/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("screenerNoteText size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * specificsIncludedCode Tests
    */
    @Test
    public void failsWhenSpecificsIncludedCodeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/specificsIncludedCode/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("specificsIncludedCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenSpecificsIncludedCodeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/specificsIncludedCode/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("specificsIncludedCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenSpecificsIncludedCodeEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/specificsIncludedCode/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("specificsIncludedCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenSpecificsIncludedCodeTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/specificsIncludedCode/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("specificsIncludedCode size must be 1"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * sufficientInformationCode Tests
    */
    @Test
    public void failsWhenSufficientInformationCodeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/sufficientInformationCode/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("sufficientInformationCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenSufficientInformationCodeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/sufficientInformationCode/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("sufficientInformationCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenSufficientInformationCodeEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/sufficientInformationCode/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("sufficientInformationCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenSufficientInformationCodeTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/sufficientInformationCode/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("sufficientInformationCode size must be 1"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * unfoundedSeriesCode Tests
    */
    @Test
    public void failsWhenUnfoundedSeriesCodeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/unfoundedSeriesCode/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("unfoundedSeriesCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenUnfoundedSeriesCodeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/unfoundedSeriesCode/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("unfoundedSeriesCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenUnfoundedSeriesCodeEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/unfoundedSeriesCode/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("unfoundedSeriesCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenUnfoundedSeriesCodeTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/unfoundedSeriesCode/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("unfoundedSeriesCode size must be 1"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * linkToPrimaryReferralId Tests
    */
    @Test
    public void successWhenLinkToPrimaryReferralIdEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/linkToPrimaryReferralId/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenLinkToPrimaryReferralIdNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/linkToPrimaryReferralId/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenLinkToPrimaryReferralIdTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/linkToPrimaryReferralId/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("linkToPrimaryReferralId size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * allegesAbuseOccurredAtAddressId Tests
    */
    @Test
    public void successWhenAllegesAbuseOccurredAtAddressIdEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/allegesAbuseOccurredAtAddressId/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenAllegesAbuseOccurredAtAddressIdNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/allegesAbuseOccurredAtAddressId/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenAllegesAbuseOccurredAtAddressIdTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/allegesAbuseOccurredAtAddressId/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("allegesAbuseOccurredAtAddressId size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * firstResponseDeterminedByStaffPersonId Tests
    */
    @Test
    public void successWhenFirstResponseDeterminedByStaffPersonIdEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/firstResponseDeterminedByStaffPersonId/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenFirstResponseDeterminedByStaffPersonIdNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/firstResponseDeterminedByStaffPersonId/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenFirstResponseDeterminedByStaffPersonIdTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/firstResponseDeterminedByStaffPersonId/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("firstResponseDeterminedByStaffPersonId size must be between 0 and 3"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * primaryContactStaffPersonId Tests
    */
    @Test
    public void failsWhenPrimaryContactStaffPersonIdMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/primaryContactStaffPersonId/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("primaryContactStaffPersonId may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenPrimaryContactStaffPersonIdNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/primaryContactStaffPersonId/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("primaryContactStaffPersonId may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenPrimaryContactStaffPersonIdEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/primaryContactStaffPersonId/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("primaryContactStaffPersonId may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenPrimaryContactStaffPersonIdTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/primaryContactStaffPersonId/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("primaryContactStaffPersonId size must be between 1 and 3"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * countySpecificCode Tests
    */
    @Test
    public void failsWhenCountySpecificCodeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/countySpecificCode/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenCountySpecificCodeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/countySpecificCode/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenCountySpecificCodeEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/countySpecificCode/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenCountySpecificCodeTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/countySpecificCode/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("countySpecificCode size must be between 1 and 2"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * specialProjectReferralIndicator Tests
    */
    @Test
    public void failsWhenSpecialProjectReferralIndicatorMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/specialProjectReferralIndicator/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("specialProjectReferralIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenSpecialProjectReferralIndicatorNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/specialProjectReferralIndicator/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("specialProjectReferralIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenSpecialProjectReferralIndicatorEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/specialProjectReferralIndicator/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("specialProjectReferralIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenSpecialProjectReferralIndicatorAllWhitespace() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/specialProjectReferralIndicator/allWhitespace.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("specialProjectReferralIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * zippyCreatedIndicator Tests
    */
    @Test
    public void failsWhenZippyCreatedIndicatorMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/zippyCreatedIndicator/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenZippyCreatedIndicatorNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/zippyCreatedIndicator/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenZippyCreatedIndicatorEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/zippyCreatedIndicator/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenZippyCreatedIndicatorAllWhitespace() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/zippyCreatedIndicator/allWhitespace.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * homelessIndicator Tests
    */
    @Test
    public void failsWhenHomelessIndicatorMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/homelessIndicator/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("homelessIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenHomelessIndicatorNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/homelessIndicator/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("homelessIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenHomelessIndicatorEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/homelessIndicator/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("homelessIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenHomelessIndicatorAllWhitespace() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/homelessIndicator/allWhitespace.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("homelessIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * familyRefusedServicesIndicator Tests
    */
    @Test
    public void failsWhenFamilyRefusedServicesIndicatorMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyRefusedServicesIndicator/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("familyRefusedServicesIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenFamilyRefusedServicesIndicatorNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyRefusedServicesIndicator/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("familyRefusedServicesIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenFamilyRefusedServicesIndicatorEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyRefusedServicesIndicator/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("familyRefusedServicesIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenFamilyRefusedServicesIndicatorAllWhitespace() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyRefusedServicesIndicator/allWhitespace.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("familyRefusedServicesIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * firstEvaluatedOutApprovalDate Tests
    */
    @Test
    public void successWhenFirstEvaluatedOutApprovalDateEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/firstEvaluatedOutApprovalDate/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenFirstEvaluatedOutApprovalDateNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/firstEvaluatedOutApprovalDate/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenFirstEvaluatedOutApprovalDateWrongFormat() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/firstEvaluatedOutApprovalDate/wrongFormat.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("firstEvaluatedOutApprovalDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * responsibleAgencyCode Tests
    */
    @Test
    public void failsWhenResponsibleAgencyCodeMissing() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responsibleAgencyCode/missing.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("responsibleAgencyCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenResponsibleAgencyCodeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responsibleAgencyCode/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("responsibleAgencyCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenResponsibleAgencyCodeEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responsibleAgencyCode/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("responsibleAgencyCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenResponsibleAgencyCodeTooLong() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responsibleAgencyCode/tooLong.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("responsibleAgencyCode size must be 1"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenResponsibleAgencyCodeNotValidValue() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responsibleAgencyCode/notValidValue.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("responsibleAgencyCode must be one of [C, P, O, A, S, I, K, M]"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void successWhenResponsibleAgencyCodeIsC() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responsibleAgencyCode/C.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenResponsibleAgencyCodeIsP() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responsibleAgencyCode/P.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenResponsibleAgencyCodeIsO() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responsibleAgencyCode/O.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenResponsibleAgencyCodeIsA() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responsibleAgencyCode/A.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenResponsibleAgencyCodeIsS() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responsibleAgencyCode/S.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenResponsibleAgencyCodeIsI() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responsibleAgencyCode/I.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenResponsibleAgencyCodeIsK() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responsibleAgencyCode/K.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenResponsibleAgencyCodeIsM() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/responsibleAgencyCode/M.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }

    /*
    * limitedAccessGovtAgencyType Tests
    */
    @Test
    public void successWhenLimitedAccessGovtAgencyTypeEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessGovtAgencyType/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenLimitedAccessGovtAgencyTypeNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessGovtAgencyType/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }

    /*
    * limitedAccessDate Tests
    */
    @Test
    public void successWhenLimitedAccessDateEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessDate/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenLimitedAccessDateNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessDate/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenLimitedAccessDateWrongFormat() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessDate/wrongFormat.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("limitedAccessDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * limitedAccessDesc Tests
    */
    @Test
    public void successWhenLimitedAccessDescEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessDesc/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenLimitedAccessDescNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/limitedAccessDesc/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }

    /*
    * originalClosureDate Tests
    */
    @Test
    public void successWhenOriginalClosureDateEmpty() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/originalClosureDate/empty.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenOriginalClosureDateNull() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/originalClosureDate/null.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenOriginalClosureDateWrongFormat() throws Exception {
        Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/originalClosureDate/wrongFormat.json"), Referral.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("originalClosureDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
    }

	/*
	 * Utils
	 */
    private Referral validReferral() {
    	return new Referral("DEF",
		    		 "A",
		    		 false,
		    		 false,
		    		 "A3CDEOm0Ab",
		    		 new Short((short)122),
		    		 "A",
		    		 "2000-03-03",
		    		 new Short((short)409),
		    		 "current",
		    		 "abcdefg",
		    		 "A3B7sSC0Ab",
		    		 "efghijk",
		    		 false,
		    		 false,
		    		 new Short((short)1118),
		    		 "A",
		    		 false,
		    		 "N",
		    		 "2000-01-31",
		    		 "Verification (R3)                  ",
		    		 "A",
		    		 "2000-01-01",
		    		 "2016-08-07-16.41.49.214",
		    		 new Short((short)1520),
		    		 new Short((short)0),
		    		 "2000-01-31",
		    		 "2016-08-07-16.41.49.214",
		    		 "lmnopq",
		    		 "rstuvw",
		    		 "A",
		    		 "A",
		    		 "A",
		    		 "sdfghj",
		    		 "kjhgfdl",
		    		 "0Ab",
		    		 "0Ab",
		    		 "51",
		    		 false,
		    		 false,
		    		 false,
		    		 false,
		    		 "2000-05-05",
		    		 "C",
		    		 new Short((short)1234),
		    		 "2000-05-05",
		    		 "thjkl",
		    		 "2000-05-05"
    		);
    }
}
