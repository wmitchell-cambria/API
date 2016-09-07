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

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.legacy.ReporterResourceImpl;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class ReporterTest {

  private static final String ROOT_RESOURCE = "/reporters/";

  private static final ReporterResourceImpl mockedReporterResource =
      mock(ReporterResourceImpl.class);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(mockedReporterResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private Reporter validReporter = validReporter();

  private String referralId = "a";
  private String badgeNumber = "b";
  private String cityName = "c";
  private Short colltrClientRptrReltnshpType = 1;
  private Short communicationMethodType = 2;
  private Boolean confidentialWaiverIndicator = Boolean.TRUE;
  private String drmsMandatedRprtrFeedback = "d";
  private String employerName = "e";
  private String feedbackDate = "1973-11-22";
  private Boolean feedbackRequiredIndicator = Boolean.FALSE;
  private String firstName = "f";
  private String lastName = "g";
  private Boolean mandatedReporterIndicator = Boolean.TRUE;
  private int messagePhoneExtensionNumber = 3;
  private BigDecimal messagePhoneNumber = new BigDecimal(4);
  private String middleInitialName = "h";
  private String namePrefixDescription = "i";
  private BigDecimal primaryPhoneNumber = new BigDecimal(5);;
  private int primaryPhoneExtensionNumber = 6;
  private Short stateCodeType = 7;
  private String streetName = "j";
  private String streetNumber = "k";
  private String suffixTitleDescription = "l";
  private int zipNumber = 8;
  private String lawEnforcementId = "m";
  private Short zipSuffixNumber = 9;
  private String countySpecificCode = "n";

  @Before
  public void setup() {
    when(
        mockedReporterResource.create(eq(validReporter),
            eq(Api.Version.JSON_VERSION_1.getMediaType()), any(UriInfo.class))).thenReturn(
        Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    Reporter domain =
        new Reporter(badgeNumber, cityName, colltrClientRptrReltnshpType, communicationMethodType,
            confidentialWaiverIndicator, drmsMandatedRprtrFeedback, employerName, feedbackDate,
            feedbackRequiredIndicator, firstName, lastName, mandatedReporterIndicator,
            messagePhoneExtensionNumber, messagePhoneNumber, middleInitialName,
            namePrefixDescription, primaryPhoneNumber, primaryPhoneExtensionNumber, stateCodeType,
            streetName, streetNumber, suffixTitleDescription, zipNumber, referralId,
            lawEnforcementId, zipSuffixNumber, countySpecificCode);
    gov.ca.cwds.rest.api.persistence.legacy.Reporter persistent =
        new gov.ca.cwds.rest.api.persistence.legacy.Reporter(domain, "lastUpdatedId");

    Reporter totest = new Reporter(persistent);
    assertThat(totest.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(totest.getBadgeNumber(), is(equalTo(persistent.getBadgeNumber())));
    assertThat(totest.getCityName(), is(equalTo(persistent.getCityName())));
    assertThat(totest.getColltrClientRptrReltnshpType(),
        is(equalTo(persistent.getColltrClientRptrReltnshpType())));
    assertThat(totest.getCommunicationMethodType(),
        is(equalTo(persistent.getCommunicationMethodType())));
    assertThat(totest.getConfidentialWaiverIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getConfidentialWaiverIndicator()))));
    assertThat(totest.getDrmsMandatedRprtrFeedback(),
        is(equalTo(persistent.getDrmsMandatedRprtrFeedback())));
    assertThat(totest.getEmployerName(), is(equalTo(persistent.getEmployerName())));
    assertThat(totest.getFeedbackDate(), is(equalTo(df.format(persistent.getFeedbackDate()))));
    assertThat(totest.getFeedbackRequiredIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getFeedbackRequiredIndicator()))));
    assertThat(totest.getFirstName(), is(equalTo(persistent.getFirstName())));
    assertThat(totest.getLastName(), is(equalTo(persistent.getLastName())));
    assertThat(totest.getMandatedReporterIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getMandatedReporterIndicator()))));
    assertThat(totest.getMessagePhoneExtensionNumber(),
        is(equalTo(persistent.getMessagePhoneExtensionNumber())));
    assertThat(totest.getMessagePhoneNumber(), is(equalTo(persistent.getMessagePhoneNumber())));
    assertThat(totest.getMiddleInitialName(), is(equalTo(persistent.getMiddleInitialName())));
    assertThat(totest.getNamePrefixDescription(),
        is(equalTo(persistent.getNamePrefixDescription())));
    assertThat(totest.getPrimaryPhoneNumber(), is(equalTo(persistent.getPrimaryPhoneNumber())));
    assertThat(totest.getPrimaryPhoneExtensionNumber(),
        is(equalTo(persistent.getPrimaryPhoneExtensionNumber())));
    assertThat(totest.getStateCodeType(), is(equalTo(persistent.getStateCodeType())));
    assertThat(totest.getStreetName(), is(equalTo(persistent.getStreetName())));
    assertThat(totest.getStreetNumber(), is(equalTo(persistent.getStreetNumber())));
    assertThat(totest.getSuffixTitleDescription(),
        is(equalTo(persistent.getSuffixTitleDescription())));
    assertThat(totest.getZipNumber(), is(equalTo(persistent.getZipNumber())));
    assertThat(totest.getLawEnforcementId(), is(equalTo(persistent.getLawEnforcementId())));
    assertThat(totest.getZipSuffixNumber(), is(equalTo(persistent.getZipSuffixNumber())));
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));

  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Reporter reporter =
        new Reporter(badgeNumber, cityName, colltrClientRptrReltnshpType, communicationMethodType,
            confidentialWaiverIndicator, drmsMandatedRprtrFeedback, employerName, feedbackDate,
            feedbackRequiredIndicator, firstName, lastName, mandatedReporterIndicator,
            messagePhoneExtensionNumber, messagePhoneNumber, middleInitialName,
            namePrefixDescription, primaryPhoneNumber, primaryPhoneExtensionNumber, stateCodeType,
            streetName, streetNumber, suffixTitleDescription, zipNumber, referralId,
            lawEnforcementId, zipSuffixNumber, countySpecificCode);

    assertThat(reporter.getReferralId(), is(equalTo(referralId)));
    assertThat(reporter.getBadgeNumber(), is(equalTo(badgeNumber)));
    assertThat(reporter.getCityName(), is(equalTo(cityName)));
    assertThat(reporter.getColltrClientRptrReltnshpType(),
        is(equalTo(colltrClientRptrReltnshpType)));
    assertThat(reporter.getCommunicationMethodType(), is(equalTo(communicationMethodType)));
    assertThat(reporter.getConfidentialWaiverIndicator(), is(equalTo(confidentialWaiverIndicator)));
    assertThat(reporter.getDrmsMandatedRprtrFeedback(), is(equalTo(drmsMandatedRprtrFeedback)));
    assertThat(reporter.getEmployerName(), is(equalTo(employerName)));
    assertThat(reporter.getFeedbackDate(), is(equalTo(feedbackDate)));
    assertThat(reporter.getFeedbackRequiredIndicator(), is(equalTo(feedbackRequiredIndicator)));
    assertThat(reporter.getFirstName(), is(equalTo(firstName)));
    assertThat(reporter.getLastName(), is(equalTo(lastName)));
    assertThat(reporter.getMandatedReporterIndicator(), is(equalTo(mandatedReporterIndicator)));
    assertThat(reporter.getMessagePhoneExtensionNumber(), is(equalTo(messagePhoneExtensionNumber)));
    assertThat(reporter.getMessagePhoneNumber(), is(equalTo(messagePhoneNumber)));
    assertThat(reporter.getMiddleInitialName(), is(equalTo(middleInitialName)));
    assertThat(reporter.getNamePrefixDescription(), is(equalTo(namePrefixDescription)));
    assertThat(reporter.getPrimaryPhoneNumber(), is(equalTo(primaryPhoneNumber)));
    assertThat(reporter.getPrimaryPhoneExtensionNumber(), is(equalTo(primaryPhoneExtensionNumber)));
    assertThat(reporter.getStateCodeType(), is(equalTo(stateCodeType)));
    assertThat(reporter.getStreetName(), is(equalTo(streetName)));
    assertThat(reporter.getStreetNumber(), is(equalTo(streetNumber)));
    assertThat(reporter.getSuffixTitleDescription(), is(equalTo(suffixTitleDescription)));
    assertThat(reporter.getZipNumber(), is(equalTo(zipNumber)));
    assertThat(reporter.getLawEnforcementId(), is(equalTo(lawEnforcementId)));
    assertThat(reporter.getZipSuffixNumber(), is(equalTo(zipSuffixNumber)));
    assertThat(reporter.getCountySpecificCode(), is(equalTo(countySpecificCode)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected =
        MAPPER.writeValueAsString(MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/valid/valid.json"), Reporter.class));

    assertThat(MAPPER.writeValueAsString(validReporter()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/valid/valid.json"), Reporter.class),
        is(equalTo(validReporter())));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/valid/valid.json"), Reporter.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/valid/optionalsNotIncluded.json"),
            Reporter.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
  }

  /*
   * badgeNumber Tests
   */
  @Test
  public void failsWhenBadgeNumberMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/badgeNumber/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("badgeNumber may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenBadgeNumberNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/badgeNumber/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("badgeNumber may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenBadgeNumberEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/badgeNumber/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("badgeNumber may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenBadgeNumberTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/badgeNumber/tooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("badgeNumber size must be between 1 and 6"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * cityName Tests
   */
  @Test
  public void failsWhenCityNameMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/cityName/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cityName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCityNameNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/cityName/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cityName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCityNameEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/cityName/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cityName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCityNameTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/cityName/tooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cityName size must be between 1 and 20"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * colltrClientRptrReltnshpType Tests
   */
  @Test
  public void failsWhenColltrClientRptrReltnshpTypeMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/colltrClientRptrReltnshpType/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("colltrClientRptrReltnshpType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenColltrClientRptrReltnshpTypeNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/colltrClientRptrReltnshpType/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("colltrClientRptrReltnshpType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * communicationMethodType Tests
   */
  @Test
  public void failsWhenCommunicationMethodTypeMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/communicationMethodType/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("communicationMethodType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCommunicationMethodTypeNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/communicationMethodType/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("communicationMethodType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * confidentialWaiverIndicator Tests
   */
  @Test
  public void failsWhenConfidentialWaiverIndicatorMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/confidentialWaiverIndicator/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("confidentialWaiverIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenConfidentialWaiverIndicatorNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/confidentialWaiverIndicator/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("confidentialWaiverIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenConfidentialWaiverIndicatorEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/confidentialWaiverIndicator/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("confidentialWaiverIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenConfidentialWaiverIndicatorAllWhitespace() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/Reporter/invalid/confidentialWaiverIndicator/allWhitespace.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("confidentialWaiverIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * drmsMandatedRprtrFeedback Tests
   */
  @Test
  public void successWhenDrmsMandatedRprtrFeedbackEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/drmsMandatedRprtrFeedback/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenDrmsMandatedRprtrFeedbackNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/drmsMandatedRprtrFeedback/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenDrmsMandatedRprtrFeedbackTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/drmsMandatedRprtrFeedback/tooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "drmsMandatedRprtrFeedback size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * employerName Tests
   */
  @Test
  public void failsWhenEmployerNameMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/employerName/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("employerName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenEmployerNameNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/employerName/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("employerName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenEmployerNameEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/employerName/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("employerName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenEmployerNameTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/employerName/tooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("employerName size must be between 1 and 35"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * feedbackDate Tests
   */
  @Test
  public void successWhenFeedbackDateEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/feedbackDate/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenFeedbackDateNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/feedbackDate/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenFeedbackDateWrongFormat() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/feedbackDate/wrongFormat.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "feedbackDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * feedbackRequiredIndicator Tests
   */
  @Test
  public void failsWhenFeedbackRequiredIndicatorMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/feedbackRequiredIndicator/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("feedbackRequiredIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFeedbackRequiredIndicatorNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/feedbackRequiredIndicator/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("feedbackRequiredIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFeedbackRequiredIndicatorEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/feedbackRequiredIndicator/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("feedbackRequiredIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFeedbackRequiredIndicatorAllWhitespace() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/Reporter/invalid/feedbackRequiredIndicator/allWhitespace.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("feedbackRequiredIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * firstName Tests
   */
  @Test
  public void failsWhenFirstNameMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/firstName/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("firstName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFirstNameNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/firstName/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("firstName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFirstNameEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/firstName/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("firstName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFirstNameTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/firstName/tooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("firstName size must be between 1 and 20"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * lastName Tests
   */
  @Test
  public void failsWhenLastNameMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/lastName/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lastName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLastNameNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/lastName/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lastName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLastNameEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/lastName/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lastName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLastNameTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/lastName/tooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lastName size must be between 1 and 25"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * mandatedReporterIndicator Tests
   */
  @Test
  public void failsWhenMandatedReporterIndicatorMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/mandatedReporterIndicator/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("mandatedReporterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMandatedReporterIndicatorNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/mandatedReporterIndicator/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("mandatedReporterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMandatedReporterIndicatorEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/mandatedReporterIndicator/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("mandatedReporterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMandatedReporterIndicatorAllWhitespace() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/Reporter/invalid/mandatedReporterIndicator/allWhitespace.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("mandatedReporterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * messagePhoneExtensionNumber Tests
   */
  @Test
  public void failsWhenMessagePhoneExtensionNumberMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/messagePhoneExtensionNumber/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("messagePhoneExtensionNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMessagePhoneExtensionNumberNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/messagePhoneExtensionNumber/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("messagePhoneExtensionNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * messagePhoneNumber Tests
   */
  @Test
  public void failsWhenMessagePhoneNumberMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/messagePhoneNumber/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("messagePhoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMessagePhoneNumberNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/messagePhoneNumber/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("messagePhoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * middleInitialName Tests
   */
  @Test
  public void failsWhenMiddleInitialNameMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/middleInitialName/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("middleInitialName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMiddleInitialNameNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/middleInitialName/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("middleInitialName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMiddleInitialNameEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/middleInitialName/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("middleInitialName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMiddleInitialNameTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/middleInitialName/tooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("middleInitialName size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * namePrefixDescription Tests
   */
  @Test
  public void failsWhenNamePrefixDescriptionMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/namePrefixDescription/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("namePrefixDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenNamePrefixDescriptionNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/namePrefixDescription/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("namePrefixDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenNamePrefixDescriptionEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/namePrefixDescription/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("namePrefixDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenNamePrefixDescriptionTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/namePrefixDescription/tooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "namePrefixDescription size must be between 1 and 6"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * primaryPhoneNumber Tests
   */
  @Test
  public void failsWhenPrimaryPhoneNumberMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/primaryPhoneNumber/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("primaryPhoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPrimaryPhoneNumberNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/primaryPhoneNumber/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("primaryPhoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * primaryPhoneExtensionNumber Tests
   */
  @Test
  public void failsWhenPrimaryPhoneExtensionNumberMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/primaryPhoneExtensionNumber/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("primaryPhoneExtensionNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPrimaryPhoneExtensionNumberNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/primaryPhoneExtensionNumber/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("primaryPhoneExtensionNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * stateCodeType Tests
   */
  @Test
  public void failsWhenStateCodeTypeMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/stateCodeType/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("stateCodeType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStateCodeTypeNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/stateCodeType/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("stateCodeType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * streetName Tests
   */
  @Test
  public void failsWhenStreetNameMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/streetName/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("streetName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStreetNameNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/streetName/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("streetName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStreetNameEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/streetName/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("streetName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStreetNameTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/streetName/tooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("streetName size must be between 1 and 40"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * streetNumber Tests
   */
  @Test
  public void failsWhenStreetNumberMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/streetNumber/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("streetNumber may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStreetNumberNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/streetNumber/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("streetNumber may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStreetNumberEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/streetNumber/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("streetNumber may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStreetNumberTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/streetNumber/tooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("streetNumber size must be between 1 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * suffixTitleDescription Tests
   */
  @Test
  public void failsWhenSuffixTitleDescriptionMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/suffixTitleDescription/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("suffixTitleDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSuffixTitleDescriptionNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/suffixTitleDescription/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("suffixTitleDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSuffixTitleDescriptionEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/suffixTitleDescription/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("suffixTitleDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSuffixTitleDescriptionTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/suffixTitleDescription/tooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "suffixTitleDescription size must be between 1 and 4"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * zipNumber Tests
   */
  @Test
  public void failsWhenZipNumberMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/zipNumber/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zipNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenZipNumberNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/zipNumber/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zipNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * referralId Tests
   */
  @Test
  public void failsWhenReferralIdMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/referralId/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/referralId/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/referralId/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/referralId/tooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("referralId size must be between 1 and 10"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * lawEnforcementId Tests
   */
  @Test
  public void successWhenLawEnforcementIdEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/lawEnforcementId/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenLawEnforcementIdNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/lawEnforcementId/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenLawEnforcementIdTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/lawEnforcementId/tooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("lawEnforcementId size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * zipSuffixNumber Tests
   */
  @Test
  public void failsWhenZipSuffixNumberMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/zipSuffixNumber/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zipSuffixNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenZipSuffixNumberNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/zipSuffixNumber/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zipSuffixNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * countySpecificCode Tests
   */
  @Test
  public void failsWhenCountySpecificCodeMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/countySpecificCode/missing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/countySpecificCode/null.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Reporter/invalid/countySpecificCode/empty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Reporter/invalid/countySpecificCode/tooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("countySpecificCode size must be between 1 and 2"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * Utils
   */
  private Reporter validReporter() {
    return new Reporter("A123", "ABC", new Short((short) 12), new Short((short) 34), false,
        "ABC123", "DEF", "2000-01-01", false, "John", "Smith", false, 123, new BigDecimal(1234567),
        "A", "ABC123", new BigDecimal(1234567), 123, new Short((short) 1234), "ABC STREET", "123",
        "AB", 12345, "DEF", "DEF", new Short((short) 1234), "AB");
  }
}