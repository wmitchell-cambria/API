package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.CrossReportResourceImpl;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CrossReportTest {

	private static final String ROOT_RESOURCE = "/crossreports/";
	
	private static final CrossReportResourceImpl mockedCrossReportResource = mock(CrossReportResourceImpl.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(mockedCrossReportResource).build();

	
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private CrossReport validCrossReport = validCrossReport();

    private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private final static DateFormat tf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
    private String thirdId = "b";
    private Short crossReportMethodType = 1;
    private Boolean filedOutOfStateIndicator = Boolean.TRUE;
    private Boolean governmentOrgCrossRptIndicatorVar = Boolean.FALSE;
    private String informTime = "1970-01-01-16.41.49.000";
    private String recipientBadgeNumber = "d";
    private Integer recipientPhoneExtensionNumber = 2;
    private BigDecimal recipientPhoneNumber = new BigDecimal(3);
    private String informDate = "1973-11-22";
    private String recipientPositionTitleDesc = "e";
    private String referenceNumber = "f";
    private String referralId = "a";
    private String lawEnforcementId = "g";
    private String staffPersonId = "h";
    private String description = "i";
    private String recipientName = "j";
    private String outstateLawEnforcementAddr = "k";
    private String countySpecificCode = "l";
    private Boolean lawEnforcementIndicator = Boolean.TRUE;
    private Boolean outStateLawEnforcementIndicator = Boolean.FALSE;
    private Boolean satisfyCrossReportIndicator = Boolean.TRUE;
    
	@Before
	public void setup() {
		when(mockedCrossReportResource.create(eq(validCrossReport), eq(Api.Version.JSON_VERSION_1.getMediaType()), any(UriInfo.class))).thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
	}
	
	/*
	 * Constructor Tests
	 */
	@Test
	public void persistentObjectConstructorTest() throws Exception {
		CrossReport domain = new CrossReport(thirdId, crossReportMethodType, filedOutOfStateIndicator, 
				governmentOrgCrossRptIndicatorVar, informTime, recipientBadgeNumber, recipientPhoneExtensionNumber,
				recipientPhoneNumber, informDate, recipientPositionTitleDesc, referenceNumber, referralId,
				lawEnforcementId, staffPersonId, description, recipientName,outstateLawEnforcementAddr,
				countySpecificCode, lawEnforcementIndicator,outStateLawEnforcementIndicator,
				satisfyCrossReportIndicator);
		gov.ca.cwds.rest.api.persistence.legacy.CrossReport persistent = new gov.ca.cwds.rest.api.persistence.legacy.CrossReport(domain, "lastUpdatedId");

		CrossReport totest = new CrossReport(persistent);
		assertThat(totest.getThirdId(), is(equalTo(persistent.getThirdId())));
		assertThat(totest.getCrossReportMethodType(), is(equalTo(persistent.getCrossReportMethodType())));
		assertThat(totest.getFiledOutOfStateIndicator(), is(equalTo(DomainObject.uncookBooleanString(persistent.getFiledOutOfStateIndicator()))));
		assertThat(totest.getGovernmentOrgCrossRptIndicatorVar(), is(equalTo(DomainObject.uncookBooleanString(persistent.getGovernmentOrgCrossRptIndicatorVar()))));
		assertThat(totest.getInformTime(), is(equalTo(tf.format(persistent.getInformTime()))));
		assertThat(totest.getRecipientBadgeNumber(), is(equalTo(persistent.getRecipientBadgeNumber())));
		assertThat(totest.getRecipientPhoneExtensionNumber(), is(equalTo(persistent.getRecipientPhoneExtensionNumber())));
		assertThat(totest.getRecipientPhoneNumber(), is(equalTo(persistent.getRecipientPhoneNumber())));
		assertThat(totest.getInformDate(), is(equalTo(df.format(persistent.getInformDate()))));
		assertThat(totest.getRecipientPositionTitleDesc(), is(equalTo(persistent.getRecipientPositionTitleDesc())));
		assertThat(totest.getReferenceNumber(), is(equalTo(persistent.getReferenceNumber())));
		assertThat(totest.getReferralId(), is(equalTo(persistent.getReferralId())));
		assertThat(totest.getLawEnforcementId(), is(equalTo(persistent.getLawEnforcementId())));
		assertThat(totest.getStaffPersonId(), is(equalTo(persistent.getStaffPersonId())));
		assertThat(totest.getDescription(), is(equalTo(persistent.getDescription())));
		assertThat(totest.getRecipientName(), is(equalTo(persistent.getRecipientName())));
		assertThat(totest.getOutstateLawEnforcementAddr(), is(equalTo(persistent.getOutstateLawEnforcementAddr())));
		assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
		assertThat(totest.getLawEnforcementIndicator(), is(equalTo(DomainObject.uncookBooleanString(persistent.getLawEnforcementIndicator()))));
		assertThat(totest.getOutStateLawEnforcementIndicator(), is(equalTo(DomainObject.uncookBooleanString(persistent.getOutStateLawEnforcementIndicator()))));
		assertThat(totest.getSatisfyCrossReportIndicator(), is(equalTo(DomainObject.uncookBooleanString(persistent.getSatisfyCrossReportIndicator()))));
	}

	@Test
	public void jsonCreatorConstructorTest() throws Exception {
		CrossReport domain = new CrossReport(thirdId, crossReportMethodType, filedOutOfStateIndicator,
				governmentOrgCrossRptIndicatorVar, informTime, recipientBadgeNumber, recipientPhoneExtensionNumber,
				recipientPhoneNumber, informDate, recipientPositionTitleDesc, referenceNumber, referralId,
				lawEnforcementId, staffPersonId, description, recipientName, outstateLawEnforcementAddr,
				countySpecificCode, lawEnforcementIndicator, outStateLawEnforcementIndicator,
				satisfyCrossReportIndicator);

		assertThat(domain.getThirdId(), is(equalTo(thirdId)));
		assertThat(domain.getCrossReportMethodType(), is(equalTo(crossReportMethodType)));
		assertThat(domain.getFiledOutOfStateIndicator(), is(equalTo(filedOutOfStateIndicator)));
		assertThat(domain.getGovernmentOrgCrossRptIndicatorVar(), is(equalTo(governmentOrgCrossRptIndicatorVar)));
		assertThat(domain.getInformTime(), is(equalTo(informTime)));
		assertThat(domain.getRecipientBadgeNumber(), is(equalTo(recipientBadgeNumber)));
		assertThat(domain.getRecipientPhoneExtensionNumber(), is(equalTo(recipientPhoneExtensionNumber)));
		assertThat(domain.getRecipientPhoneNumber(), is(equalTo(recipientPhoneNumber)));
		assertThat(domain.getInformDate(), is(equalTo(informDate)));
		assertThat(domain.getRecipientPositionTitleDesc(), is(equalTo(recipientPositionTitleDesc)));
		assertThat(domain.getReferenceNumber(), is(equalTo(referenceNumber)));
		assertThat(domain.getReferralId(), is(equalTo(referralId)));
		assertThat(domain.getLawEnforcementId(), is(equalTo(lawEnforcementId)));
		assertThat(domain.getStaffPersonId(), is(equalTo(staffPersonId)));
		assertThat(domain.getDescription(), is(equalTo(description)));
		assertThat(domain.getRecipientName(), is(equalTo(recipientName)));
		assertThat(domain.getOutstateLawEnforcementAddr(), is(equalTo(outstateLawEnforcementAddr)));
		assertThat(domain.getCountySpecificCode(), is(equalTo(countySpecificCode)));
		assertThat(domain.getLawEnforcementIndicator(), is(equalTo(lawEnforcementIndicator)));
		assertThat(domain.getOutStateLawEnforcementIndicator(), is(equalTo(outStateLawEnforcementIndicator)));
		assertThat(domain.getSatisfyCrossReportIndicator(), is(equalTo(satisfyCrossReportIndicator)));
	}

    @Test
    public void serializesToJSON() throws Exception {
        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/legacy/CrossReport/valid/valid.json"), CrossReport.class));

        assertThat(MAPPER.writeValueAsString(validCrossReport()), is(equalTo(expected)));
    }
    
    @Test
    public void deserializesFromJSON() throws Exception {
        assertThat(MAPPER.readValue(fixture("fixtures/legacy/CrossReport/valid/valid.json"), CrossReport.class), is(equalTo(validCrossReport())));
    }

	/*
	 * Successful Tests
	 */
	@Test
	public void successfulWithValid() throws Exception {
		CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/valid/valid.json"), CrossReport.class);
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
	}

	@Test
	public void successfulWithOptionalsNotIncluded() throws Exception {
		CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/valid/optionalsNotIncluded.json"), CrossReport.class);
		assertThat(resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
	}

    /*
    * thirdId Tests
    */
    @Test
    public void failsWhenThirdIdMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/thirdId/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("thirdId may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenThirdIdNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/thirdId/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("thirdId may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenThirdIdEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/thirdId/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("thirdId may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenThirdIdTooLong() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/thirdId/tooLong.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("thirdId size must be between 1 and 10"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * crossReportMethodType Tests
    */
    @Test
    public void failsWhenCrossReportMethodTypeMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/crossReportMethodType/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("crossReportMethodType may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenCrossReportMethodTypeNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/crossReportMethodType/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("crossReportMethodType may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * filedOutOfStateIndicator Tests
    */
    @Test
    public void failsWhenFiledOutOfStateIndicatorMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/filedOutOfStateIndicator/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("filedOutOfStateIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenFiledOutOfStateIndicatorNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/filedOutOfStateIndicator/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("filedOutOfStateIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenFiledOutOfStateIndicatorEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/filedOutOfStateIndicator/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("filedOutOfStateIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenFiledOutOfStateIndicatorAllWhitespace() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/filedOutOfStateIndicator/allWhitespace.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("filedOutOfStateIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * governmentOrgCrossRptIndicatorVar Tests
    */
    @Test
    public void failsWhenGovernmentOrgCrossRptIndicatorVarMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/governmentOrgCrossRptIndicatorVar/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("governmentOrgCrossRptIndicatorVar may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenGovernmentOrgCrossRptIndicatorVarNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/governmentOrgCrossRptIndicatorVar/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("governmentOrgCrossRptIndicatorVar may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenGovernmentOrgCrossRptIndicatorVarEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/governmentOrgCrossRptIndicatorVar/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("governmentOrgCrossRptIndicatorVar may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenGovernmentOrgCrossRptIndicatorVarAllWhitespace() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/governmentOrgCrossRptIndicatorVar/allWhitespace.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("governmentOrgCrossRptIndicatorVar may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * informTime Tests
    */
    @Test
    public void successWhenInformTimeEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/informTime/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenInformTimeNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/informTime/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenInformTimeWrongFormat() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/informTime/wrongFormat.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("informTime must be in the format of yyyy-MM-dd-HH.mm.ss.SSS"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * recipientBadgeNumber Tests
    */
    @Test
    public void failsWhenRecipientBadgeNumberMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientBadgeNumber/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientBadgeNumber may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenRecipientBadgeNumberNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientBadgeNumber/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientBadgeNumber may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenRecipientBadgeNumberEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientBadgeNumber/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientBadgeNumber may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenRecipientBadgeNumberTooLong() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientBadgeNumber/tooLong.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientBadgeNumber size must be between 1 and 6"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * recipientPhoneExtensionNumber Tests
    */
    @Test
    public void failsWhenRecipientPhoneExtensionNumberMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientPhoneExtensionNumber/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientPhoneExtensionNumber may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenRecipientPhoneExtensionNumberNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientPhoneExtensionNumber/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientPhoneExtensionNumber may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * recipientPhoneNumber Tests
    */
    @Test
    public void failsWhenRecipientPhoneNumberMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientPhoneNumber/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientPhoneNumber may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenRecipientPhoneNumberNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientPhoneNumber/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientPhoneNumber may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * informDate Tests
    */
    @Test
    public void failsWhenInformDateMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/informDate/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("informDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenInformDateNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/informDate/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("informDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenInformDateWrongFormat() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/informDate/wrongFormat.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("informDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * recipientPositionTitleDesc Tests
    */
    @Test
    public void failsWhenRecipientPositionTitleDescMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientPositionTitleDesc/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientPositionTitleDesc may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenRecipientPositionTitleDescNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientPositionTitleDesc/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientPositionTitleDesc may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenRecipientPositionTitleDescEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientPositionTitleDesc/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientPositionTitleDesc may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenRecipientPositionTitleDescTooLong() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientPositionTitleDesc/tooLong.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientPositionTitleDesc size must be between 1 and 30"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * referenceNumber Tests
    */
    @Test
    public void failsWhenReferenceNumberMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/referenceNumber/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referenceNumber may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReferenceNumberNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/referenceNumber/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referenceNumber may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReferenceNumberEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/referenceNumber/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referenceNumber may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReferenceNumberTooLong() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/referenceNumber/tooLong.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referenceNumber size must be between 1 and 10"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * referralId Tests
    */
    @Test
    public void failsWhenReferralIdMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/referralId/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReferralIdNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/referralId/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReferralIdEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/referralId/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenReferralIdTooLong() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/referralId/tooLong.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("referralId size must be between 1 and 10"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * lawEnforcementId Tests
    */
    @Test
    public void successWhenLawEnforcementIdEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/lawEnforcementId/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void successWhenLawEnforcementIdNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/lawEnforcementId/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(204)));
    }
    @Test
    public void failsWhenLawEnforcementIdTooLong() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/lawEnforcementId/tooLong.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("lawEnforcementId size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * staffPersonId Tests
    */
    @Test
    public void failsWhenStaffPersonIdMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/staffPersonId/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("staffPersonId may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenStaffPersonIdNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/staffPersonId/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("staffPersonId may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenStaffPersonIdEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/staffPersonId/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("staffPersonId may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenStaffPersonIdTooLong() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/staffPersonId/tooLong.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("staffPersonId size must be between 1 and 3"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * description Tests
    */
    @Test
    public void failsWhenDescriptionMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/description/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("description may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenDescriptionNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/description/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("description may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenDescriptionEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/description/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("description may not be empty"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * recipientName Tests
    */
    @Test
    public void failsWhenRecipientNameMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientName/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientName may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenRecipientNameNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientName/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientName may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenRecipientNameEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientName/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientName may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenRecipientNameTooLong() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/recipientName/tooLong.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("recipientName size must be between 1 and 40"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * outstateLawEnforcementAddr Tests
    */
    @Test
    public void failsWhenOutstateLawEnforcementAddrMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/outstateLawEnforcementAddr/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("outstateLawEnforcementAddr may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenOutstateLawEnforcementAddrNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/outstateLawEnforcementAddr/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("outstateLawEnforcementAddr may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenOutstateLawEnforcementAddrEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/outstateLawEnforcementAddr/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("outstateLawEnforcementAddr may not be empty"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * countySpecificCode Tests
    */
    @Test
    public void failsWhenCountySpecificCodeMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/countySpecificCode/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenCountySpecificCodeNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/countySpecificCode/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenCountySpecificCodeEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/countySpecificCode/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenCountySpecificCodeTooLong() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/countySpecificCode/tooLong.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("countySpecificCode size must be between 1 and 2"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * lawEnforcementIndicator Tests
    */
    @Test
    public void failsWhenLawEnforcementIndicatorMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/lawEnforcementIndicator/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("lawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenLawEnforcementIndicatorNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/lawEnforcementIndicator/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("lawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenLawEnforcementIndicatorEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/lawEnforcementIndicator/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("lawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenLawEnforcementIndicatorAllWhitespace() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/lawEnforcementIndicator/allWhitespace.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("lawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * outStateLawEnforcementIndicator Tests
    */
    @Test
    public void failsWhenOutStateLawEnforcementIndicatorMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/outStateLawEnforcementIndicator/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("outStateLawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenOutStateLawEnforcementIndicatorNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/outStateLawEnforcementIndicator/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("outStateLawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenOutStateLawEnforcementIndicatorEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/outStateLawEnforcementIndicator/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("outStateLawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenOutStateLawEnforcementIndicatorAllWhitespace() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/outStateLawEnforcementIndicator/allWhitespace.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("outStateLawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }

    /*
    * satisfyCrossReportIndicator Tests
    */
    @Test
    public void failsWhenSatisfyCrossReportIndicatorMissing() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/satisfyCrossReportIndicator/missing.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("satisfyCrossReportIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenSatisfyCrossReportIndicatorNull() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/satisfyCrossReportIndicator/null.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("satisfyCrossReportIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenSatisfyCrossReportIndicatorEmpty() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/satisfyCrossReportIndicator/empty.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("satisfyCrossReportIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }
    @Test
    public void failsWhenSatisfyCrossReportIndicatorAllWhitespace() throws Exception {
        CrossReport toCreate = MAPPER.readValue(fixture("fixtures/legacy/CrossReport/invalid/satisfyCrossReportIndicator/allWhitespace.json"), CrossReport.class);
        Response response = resources.client().target(ROOT_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
        assertThat(response.getStatus(), is(equalTo(422)));
        assertThat(response.readEntity(String.class).indexOf("satisfyCrossReportIndicator may not be null"), is(greaterThanOrEqualTo(0)));
    }

	/*
	 * Utils
	 */
    private CrossReport validCrossReport() {
        return new CrossReport(
            "ABC123",
            (short)123,
            false,
            false,
            "2000-01-01-16.41.49.214",
            "AB123",
            234,
            new BigDecimal(1234567),
            "2000-01-01",
            "ABC23",
            "DE123",
            "DEF",
            "GHJ",
            "AD",
            "ABC DESC",
            "JOHN",
            "ABC STREET",
            "AB",
            false,
            false,
            false);
    }
}
