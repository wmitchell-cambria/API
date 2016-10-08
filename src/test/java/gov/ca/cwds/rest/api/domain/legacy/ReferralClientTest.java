package gov.ca.cwds.rest.api.domain.legacy;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.legacy.Referral;
import gov.ca.cwds.rest.core.ApiPoc;
import gov.ca.cwds.rest.jdbi.CrudsDao;
import gov.ca.cwds.rest.jdbi.DataAccessEnvironment;
import gov.ca.cwds.rest.resources.legacy.ReferralClientResourceImpl;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class ReferralClientTest {

  private static final String ROOT_RESOURCE = "/" + ApiPoc.RESOURCE_REFERRAL_CLIENT + "/";

  private static final ReferralClientResourceImpl mockedReferralClientResource =
      mock(ReferralClientResourceImpl.class);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(mockedReferralClientResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private ReferralClient validReferralClient = validReferralClient();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String referralId = "a";
  private String clientId = "b";
  private String approvalNumber = "c";
  private Short approvalStatusType = 1;
  private Short dispositionClosureReasonType = 2;
  private String dispositionCode = "d";
  private String dispositionDate = "1973-11-22";
  private Boolean selfReportedIndicator = Boolean.TRUE;
  private Boolean staffPersonAddedIndicator = Boolean.FALSE;
  private String dispositionClosureDescription = "e";
  private Short ageNumber = 3;
  private String agePeriodCode = "f";
  private String countySpecificCode = "g";
  private Boolean mentalHealthIssuesIndicator = Boolean.TRUE;
  private Boolean alcoholIndicator = null;
  private Boolean drugIndicator = Boolean.FALSE;

  public ReferralClientTest() throws ParseException {}

  @Before
  public void setup() {
    @SuppressWarnings("rawtypes")
    CrudsDao crudsDao = mock(CrudsDao.class);
    DataAccessEnvironment.register(gov.ca.cwds.rest.api.persistence.legacy.Referral.class, crudsDao);
    when(crudsDao.find(any())).thenReturn(mock(Referral.class));

    when(mockedReferralClientResource.create(eq(validReferralClient), eq(ApiPoc.Version.JSON_VERSION_1.getMediaType()),
            any(UriInfo.class))).thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected =
        MAPPER.writeValueAsString(MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/valid/valid.json"), ReferralClient.class));

    assertThat(MAPPER.writeValueAsString(validReferralClient()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/valid/valid.json"),
        ReferralClient.class), is(equalTo(validReferralClient())));
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    ReferralClient domain =
        new ReferralClient(approvalNumber, approvalStatusType, dispositionClosureReasonType,
            dispositionCode, dispositionDate, selfReportedIndicator, staffPersonAddedIndicator,
            referralId, clientId, dispositionClosureDescription, ageNumber, agePeriodCode,
            countySpecificCode, mentalHealthIssuesIndicator, alcoholIndicator, drugIndicator);
    gov.ca.cwds.rest.api.persistence.legacy.ReferralClient persistent =
        new gov.ca.cwds.rest.api.persistence.legacy.ReferralClient(domain, "lastUpdatedId");

    ReferralClient totest = new ReferralClient(persistent);
    assertThat(totest.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(totest.getClientId(), is(equalTo(persistent.getClientId())));
    assertThat(totest.getApprovalNumber(), is(equalTo(persistent.getApprovalNumber())));
    assertThat(totest.getApprovalStatusType(), is(equalTo(persistent.getApprovalStatusType())));
    assertThat(totest.getDispositionClosureReasonType(),
        is(equalTo(persistent.getDispositionClosureReasonType())));
    assertThat(totest.getDispositionCode(), is(equalTo(persistent.getDispositionCode())));
    assertThat(totest.getDispositionDate(), is(equalTo(df.format(persistent.getDispositionDate()))));
    assertThat(totest.getSelfReportedIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getSelfReportedIndicator()))));
    assertThat(totest.getStaffPersonAddedIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getStaffPersonAddedIndicator()))));
    assertThat(totest.getDispositionClosureDescription(),
        is(equalTo(persistent.getDispositionClosureDescription())));
    assertThat(totest.getAgeNumber(), is(equalTo(persistent.getAgeNumber())));
    assertThat(totest.getAgePeriodCode(), is(equalTo(persistent.getAgePeriodCode())));
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(totest.getMentalHealthIssuesIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getMentalHealthIssuesIndicator()))));
    assertThat(totest.getAlcoholIndicator(), is(nullValue()));
    assertThat(totest.getDrugIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getDrugIndicator()))));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    ReferralClient referralClient =
        new ReferralClient(approvalNumber, approvalStatusType, dispositionClosureReasonType,
            dispositionCode, dispositionDate, selfReportedIndicator, staffPersonAddedIndicator,
            referralId, clientId, dispositionClosureDescription, ageNumber, agePeriodCode,
            countySpecificCode, mentalHealthIssuesIndicator, alcoholIndicator, drugIndicator);

    assertThat(referralClient.getReferralId(), is(equalTo(referralId)));
    assertThat(referralClient.getClientId(), is(equalTo(clientId)));
    assertThat(referralClient.getApprovalNumber(), is(equalTo(approvalNumber)));
    assertThat(referralClient.getApprovalStatusType(), is(equalTo(approvalStatusType)));
    assertThat(referralClient.getDispositionClosureReasonType(),
        is(equalTo(dispositionClosureReasonType)));
    assertThat(referralClient.getDispositionCode(), is(equalTo(dispositionCode)));
    assertThat(referralClient.getDispositionDate(), is(equalTo(dispositionDate)));
    assertThat(referralClient.getSelfReportedIndicator(), is(equalTo(Boolean.TRUE)));
    assertThat(referralClient.getStaffPersonAddedIndicator(), is(equalTo(Boolean.FALSE)));
    assertThat(referralClient.getDispositionClosureDescription(),
        is(equalTo(dispositionClosureDescription)));
    assertThat(referralClient.getAgeNumber(), is(equalTo(ageNumber)));
    assertThat(referralClient.getAgePeriodCode(), is(equalTo(agePeriodCode)));
    assertThat(referralClient.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(referralClient.getMentalHealthIssuesIndicator(), is(equalTo(Boolean.TRUE)));
    assertThat(referralClient.getAlcoholIndicator(), is(nullValue()));
    assertThat(referralClient.getDrugIndicator(), is(equalTo(Boolean.FALSE)));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/valid/valid.json"),
            ReferralClient.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/valid/optionalsNotIncluded.json"),
            ReferralClient.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
  }

  /*
   * approvalNumber Tests
   */
  @Test
  public void successWhenApprovalNumberEmpty() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/approvalNumber/empty.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenApprovalNumberNull() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/approvalNumber/null.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenApprovalNumberTooLong() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/approvalNumber/tooLong.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("approvalNumber size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * dispositionClosureReasonType Tests
   */
  @Test
  public void failsWhenDispositionClosureReasonTypeMissing() throws Exception {
    ReferralClient toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/ReferralClient/invalid/dispositionClosureReasonType/missing.json"),
                ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionClosureReasonType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionClosureReasonTypeNull() throws Exception {
    ReferralClient toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/ReferralClient/invalid/dispositionClosureReasonType/null.json"),
                ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionClosureReasonType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * approvalStatusType Tests
   */
  @Test
  public void failsWhenApprovalStatusTypeMissing() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/approvalStatusType/missing.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("approvalStatusType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenApprovalStatusTypeNull() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/approvalStatusType/null.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("approvalStatusType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * dispositionCode Tests
   */
  @Test
  public void failsWhenDispositionCodeMissing() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/dispositionCode/missing.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dispositionCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionCodeNull() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/dispositionCode/null.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dispositionCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionCodeEmpty() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/dispositionCode/empty.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dispositionCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionCodeTooLong() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/dispositionCode/tooLong.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dispositionCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * dispositionDate Tests
   */
  @Test
  public void successWhenDispositionDateEmpty() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/dispositionDate/empty.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenDispositionDateNull() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/dispositionDate/null.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenDispositionDateWrongFormat() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/dispositionDate/wrongFormat.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "dispositionDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * selfReportedIndicator Tests
   */
  @Test
  public void failsWhenSelfReportedIndicatorMissing() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/selfReportedIndicator/missing.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("selfReportedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSelfReportedIndicatorNull() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/selfReportedIndicator/null.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("selfReportedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSelfReportedIndicatorEmpty() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/selfReportedIndicator/empty.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("selfReportedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSelfReportedIndicatorAllWhitespace() throws Exception {
    ReferralClient toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/ReferralClient/invalid/selfReportedIndicator/allWhitespace.json"),
                ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("selfReportedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * staffPersonAddedIndicator Tests
   */
  @Test
  public void failsWhenStaffPersonAddedIndicatorMissing() throws Exception {
    ReferralClient toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/ReferralClient/invalid/staffPersonAddedIndicator/missing.json"),
                ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStaffPersonAddedIndicatorNull() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/staffPersonAddedIndicator/null.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStaffPersonAddedIndicatorEmpty() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/staffPersonAddedIndicator/empty.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStaffPersonAddedIndicatorAllWhitespace() throws Exception {
    ReferralClient toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/ReferralClient/invalid/staffPersonAddedIndicator/allWhitespace.json"),
                ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * referralId Tests
   */
  @Test
  public void failsWhenReferralIdMissing() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/invalid/referralId/missing.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdNull() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/invalid/referralId/null.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdEmpty() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/invalid/referralId/empty.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdTooLong() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/invalid/referralId/tooLong.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("referralId size must be between 1 and 10"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * clientId Tests
   */
  @Test
  public void failsWhenClientIdMissing() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/invalid/clientId/missing.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("clientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenClientIdNull() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/invalid/clientId/null.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("clientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenClientIdEmpty() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/invalid/clientId/empty.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("clientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenClientIdTooLong() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/invalid/clientId/tooLong.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("clientId size must be between 1 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * dispositionClosureDescription Tests
   */
  @Test
  public void failsWhenDispositionClosureDescriptionMissing() throws Exception {
    ReferralClient toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/ReferralClient/invalid/dispositionClosureDescription/missing.json"),
                ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionClosureDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionClosureDescriptionNull() throws Exception {
    ReferralClient toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/ReferralClient/invalid/dispositionClosureDescription/null.json"),
                ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionClosureDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionClosureDescriptionEmpty() throws Exception {
    ReferralClient toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/ReferralClient/invalid/dispositionClosureDescription/empty.json"),
                ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionClosureDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * ageNumber Tests
   */
  @Test
  public void failsWhenAgeNumberMissing() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/invalid/ageNumber/missing.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("ageNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAgeNumberNull() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/invalid/ageNumber/null.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("ageNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * agePeriodCode Tests
   */
  @Test
  public void failsWhenAgePeriodCodeMissing() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/agePeriodCode/missing.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("agePeriodCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAgePeriodCodeNull() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/invalid/agePeriodCode/null.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("agePeriodCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAgePeriodCodeEmpty() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/agePeriodCode/empty.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("agePeriodCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAgePeriodCodeTooLong() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/agePeriodCode/tooLong.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("agePeriodCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * countySpecificCode Tests
   */
  @Test
  public void failsWhenCountySpecificCodeMissing() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/countySpecificCode/missing.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeNull() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/countySpecificCode/null.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeEmpty() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/countySpecificCode/empty.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeTooLong() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/countySpecificCode/tooLong.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("countySpecificCode size must be between 1 and 2"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * mentalHealthIssuesIndicator Tests
   */
  @Test
  public void successWhenMentalHealthIssuesIndicatorEmpty() throws Exception {
    ReferralClient toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/ReferralClient/invalid/mentalHealthIssuesIndicator/empty.json"),
                ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenMentalHealthIssuesIndicatorNull() throws Exception {
    ReferralClient toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/ReferralClient/invalid/mentalHealthIssuesIndicator/null.json"),
                ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * alcoholIndicator Tests
   */
  @Test
  public void successWhenAlcoholIndicatorEmpty() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/alcoholIndicator/empty.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenAlcoholIndicatorNull() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/alcoholIndicator/null.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * drugIndicator Tests
   */
  @Test
  public void successWhenDrugIndicatorEmpty() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/ReferralClient/invalid/drugIndicator/empty.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenDrugIndicatorNull() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/ReferralClient/invalid/drugIndicator/null.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(ApiPoc.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, ApiPoc.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * Utils
   */
  private ReferralClient validReferralClient() {
    return new ReferralClient("A123", new Short((short) 123), new Short((short) 123), "A",
        "2000-01-01", false, false, "AbiQCgu0Hj", "abc", "description abc", new Short((short) 12), "M",
        "AB", false, false, false);
  }
}