package gov.ca.cwds.rest.api.persistence.legacy;

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
        assertThat(MAPPER.readValue(fixture("fixtures/legacy/Referral/valid/valid.json"), Referral.class), is(equalTo(validReferral())));
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
	* anonymousReporter Tests
	*/
	@Test
	public void failsWhenAnonymousReporterMissing() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/anonymousReporter/missing.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("anonymousReporter may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenAnonymousReporterNull() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/anonymousReporter/null.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("anonymousReporter may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenAnonymousReporterEmpty() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/anonymousReporter/empty.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("anonymousReporter may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenAnonymousReporterTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/anonymousReporter/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("anonymousReporter size must be 1"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	* applicationForPetition Tests
	*/
	@Test
	public void failsWhenApplicationForPetitionMissing() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/applicationForPetition/missing.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("applicationForPetition may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenApplicationForPetitionNull() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/applicationForPetition/null.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("applicationForPetition may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenApplicationForPetitionEmpty() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/applicationForPetition/empty.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("applicationForPetition may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenApplicationForPetitionTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/applicationForPetition/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("applicationForPetition size must be 1"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	* approvalNumber Tests
	*/
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
	public void failsWhenDrmsInvestigationDocTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/drmsInvestigationDoc/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("drmsInvestigationDoc size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	* filedSuspectedChildAbuseReporttoLawEnforcement Tests
	*/
	@Test
	public void failsWhenFiledSuspectedChildAbuseReporttoLawEnforcementMissing() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/filedSuspectedChildAbuseReporttoLawEnforcement/missing.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("filedSuspectedChildAbuseReporttoLawEnforcement may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenFiledSuspectedChildAbuseReporttoLawEnforcementNull() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/filedSuspectedChildAbuseReporttoLawEnforcement/null.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("filedSuspectedChildAbuseReporttoLawEnforcement may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenFiledSuspectedChildAbuseReporttoLawEnforcementEmpty() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/filedSuspectedChildAbuseReporttoLawEnforcement/empty.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("filedSuspectedChildAbuseReporttoLawEnforcement may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenFiledSuspectedChildAbuseReporttoLawEnforcementTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/filedSuspectedChildAbuseReporttoLawEnforcement/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("filedSuspectedChildAbuseReporttoLawEnforcement size must be 1"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	* familyAwarenessIndicator Tests
	*/
	@Test
	public void failsWhenFamilyAwarenessIndicatorMissing() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyAwarenessIndicator/missing.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("familyAwarenessIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenFamilyAwarenessIndicatorNull() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyAwarenessIndicator/null.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("familyAwarenessIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenFamilyAwarenessIndicatorEmpty() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyAwarenessIndicator/empty.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("familyAwarenessIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenFamilyAwarenessIndicatorTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyAwarenessIndicator/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("familyAwarenessIndicator size must be 1"), is(greaterThanOrEqualTo(0)));
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
	    assertThat(response.readEntity(String.class).indexOf("legalRightsNoticeIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenLegalRightsNoticeIndicatorNull() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/legalRightsNoticeIndicator/null.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("legalRightsNoticeIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenLegalRightsNoticeIndicatorEmpty() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/legalRightsNoticeIndicator/empty.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("legalRightsNoticeIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenLegalRightsNoticeIndicatorTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/legalRightsNoticeIndicator/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("legalRightsNoticeIndicator size must be 1"), is(greaterThanOrEqualTo(0)));
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
	
	/*
	* mandatedCrossReportReceivedDate Tests
	*/
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
	* lastUpdateId Tests
	*/
	@Test
	public void failsWhenLastUpdateIdMissing() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/lastUpdateId/missing.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("lastUpdatedId may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenLastUpdateIdNull() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/lastUpdateId/null.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("lastUpdatedId may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenLastUpdateIdEmpty() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/lastUpdateId/empty.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("lastUpdatedId may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenLastUpdateIdTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/lastUpdateId/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("lastUpdatedId size must be between 1 and 3"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	* lastUpdateTimeStamp Tests
	*/
	@Test
	public void failsWhenLastUpdateTimeStampMissing() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/lastUpdateTimeStamp/missing.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("lastUpdatedTime must be in the format of yyyy-MM-dd-HH.mm.ss.SSS"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenLastUpdateTimeStampNull() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/lastUpdateTimeStamp/null.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("lastUpdatedTime must be in the format of yyyy-MM-dd-HH.mm.ss.SSS"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenLastUpdateTimeStampWrongFormat() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/lastUpdateTimeStamp/wrongFormat.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("lastUpdatedTime must be in the format of yyyy-MM-dd-HH.mm.ss.SSS"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	* foreignKeyFromReferral Tests
	*/
	@Test
	public void failsWhenForeignKeyFromReferralTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/foreignKeyFromReferral/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("foreignKeyFromReferral size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	* fkAddrsT Tests
	*/
	@Test
	public void failsWhenFkAddrsTTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/fkAddrsT/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("fkAddrsT size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	* fkStaffPerso Tests
	*/
	@Test
	public void failsWhenFkStaffPersoTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/fkStaffPerso/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("fkStaffPerso size must be between 0 and 3"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	* foreignKeyStaffPerson Tests
	*/
	@Test
	public void failsWhenForeignKeyStaffPersonMissing() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/foreignKeyStaffPerson/missing.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("foreignKeyStaffPerson may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenForeignKeyStaffPersonNull() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/foreignKeyStaffPerson/null.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("foreignKeyStaffPerson may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenForeignKeyStaffPersonEmpty() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/foreignKeyStaffPerson/empty.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("foreignKeyStaffPerson may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenForeignKeyStaffPersonTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/foreignKeyStaffPerson/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("foreignKeyStaffPerson size must be between 1 and 3"), is(greaterThanOrEqualTo(0)));
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
	    assertThat(response.readEntity(String.class).indexOf("specialProjectReferralIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenSpecialProjectReferralIndicatorNull() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/specialProjectReferralIndicator/null.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("specialProjectReferralIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenSpecialProjectReferralIndicatorEmpty() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/specialProjectReferralIndicator/empty.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("specialProjectReferralIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenSpecialProjectReferralIndicatorTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/specialProjectReferralIndicator/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("specialProjectReferralIndicator size must be 1"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	* zippyCreatedIndicator Tests
	*/
	@Test
	public void failsWhenZippyCreatedIndicatorMissing() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/zippyCreatedIndicator/missing.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenZippyCreatedIndicatorNull() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/zippyCreatedIndicator/null.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenZippyCreatedIndicatorEmpty() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/zippyCreatedIndicator/empty.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenZippyCreatedIndicatorTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/zippyCreatedIndicator/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator size must be 1"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	* homelessIndicator Tests
	*/
	@Test
	public void failsWhenHomelessIndicatorMissing() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/homelessIndicator/missing.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("homelessIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenHomelessIndicatorNull() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/homelessIndicator/null.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("homelessIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenHomelessIndicatorEmpty() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/homelessIndicator/empty.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("homelessIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenHomelessIndicatorTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/homelessIndicator/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("homelessIndicator size must be 1"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	* familyRefusedServicesIndicator Tests
	*/
	@Test
	public void failsWhenFamilyRefusedServicesIndicatorMissing() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyRefusedServicesIndicator/missing.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("familyRefusedServicesIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenFamilyRefusedServicesIndicatorNull() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyRefusedServicesIndicator/null.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("familyRefusedServicesIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenFamilyRefusedServicesIndicatorEmpty() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyRefusedServicesIndicator/empty.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("familyRefusedServicesIndicator may not be empty"), is(greaterThanOrEqualTo(0)));
	}
	@Test
	public void failsWhenFamilyRefusedServicesIndicatorTooLong() throws Exception {
	    Referral toCreate = MAPPER.readValue(fixture("fixtures/legacy/Referral/invalid/familyRefusedServicesIndicator/tooLong.json"), Referral.class);
	    Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
	    assertThat(response.getStatus(), is(equalTo(422)));
	    assertThat(response.readEntity(String.class).indexOf("familyRefusedServicesIndicator size must be 1"), is(greaterThanOrEqualTo(0)));
	}
	
	/*
	* firstEvaluatedOutApprovalDate Tests
	*/
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
	
	/*
	* limitedAccessGovtAgencyType Tests
	*/

	/*
	* limitedAccessDate Tests
	*/
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
	
	/*
	* originalClosureDate Tests
	*/
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
		    		 "N",
		    		 "N",
		    		 "A3CDEOm0Ab",
		    		 new Short((short)122),
		    		 "A",
		    		 "2000-03-03",
		    		 new Short((short)409),
		    		 "current",
		    		 "abcdefg",
		    		 "A3B7sSC0Ab",
		    		 "efghijk",
		    		 "N",
		    		 "N",
		    		 new Short((short)1118),
		    		 "A",
		    		 "N",
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
		    		 "0Ab",
		    		 "2016-08-07-16.41.49.214",
		    		 "sdfghj",
		    		 "kjhgfdl",
		    		 "0Ab",
		    		 "0Ab",
		    		 "51",
		    		 "N",
		    		 "N",
		    		 "N",
		    		 "N",
		    		 "2000-05-05",
		    		 "C",
		    		 new Short((short)1234),
		    		 "2000-05-05",
		    		 "thjkl",
		    		 "2000-05-05"
    		);
    }
}