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

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.legacy.Referral;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.jdbi.CrudsDao;
import gov.ca.cwds.rest.jdbi.DataAccessEnvironment;
import gov.ca.cwds.rest.resources.legacy.AllegationResourceImpl;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class AllegationTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_ALLEGATION + "/";
  private static final AllegationResourceImpl mockedAllegationResource =
      mock(AllegationResourceImpl.class);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(mockedAllegationResource).build();


  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Allegation validAllegation = validAllegation();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "a";
  private String abuseEndDate = "1973-11-22";
  private String abuseStartDate = "2006-09-12";
  private Short abuseFrequency = 1;
  private String abuseFrequencyPeriodCode = "b";
  private String abuseLocationDescription = "c";
  private Short allegationDispositionType = 2;
  private Short allegationType = 3;
  private String dispositionDescription = "d";
  private String dispositionDate = "1963-11-22";
  private Boolean injuryHarmDetailIndicator = Boolean.TRUE;
  private String nonProtectingParentCode = "e";
  private Boolean staffPersonAddedIndicator = Boolean.FALSE;
  private String victimClientId = "f";
  private String perpetratorClientId = "g";
  private String referralId = "h";
  private String countySpecificCode = "i";
  private Boolean zippyCreatedIndicator = Boolean.TRUE;
  private Short placementFacilityType = 4;

  @Before
  public void setup() {
    @SuppressWarnings("rawtypes")
    CrudsDao crudsDao = mock(CrudsDao.class);
    DataAccessEnvironment.register(gov.ca.cwds.rest.api.persistence.legacy.Referral.class, crudsDao);
    when(crudsDao.find(any())).thenReturn(mock(Referral.class));

    when(mockedAllegationResource.create(eq(validAllegation), eq(Api.Version.JSON_VERSION_1.getMediaType()),
            any(UriInfo.class))).thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    Allegation domain =
        new Allegation(id, abuseEndDate, abuseFrequency, abuseFrequencyPeriodCode,
            abuseLocationDescription, abuseStartDate, allegationDispositionType, allegationType,
            dispositionDescription, dispositionDate, injuryHarmDetailIndicator,
            nonProtectingParentCode, staffPersonAddedIndicator, victimClientId, perpetratorClientId, referralId,
            countySpecificCode, zippyCreatedIndicator, placementFacilityType);
    gov.ca.cwds.rest.api.persistence.legacy.Allegation persistent =
        new gov.ca.cwds.rest.api.persistence.legacy.Allegation(domain, "lastUpdatedId");

    Allegation totest = new Allegation(persistent);
    assertThat(totest.getId(), is(equalTo(persistent.getId())));
    assertThat(totest.getAbuseEndDate(), is(equalTo(df.format(persistent.getAbuseEndDate()))));
    assertThat(totest.getAbuseFrequency(), is(equalTo(persistent.getAbuseFrequency())));
    assertThat(totest.getAbuseFrequencyPeriodCode(),
        is(equalTo(persistent.getAbuseFrequencyPeriodCode())));
    assertThat(totest.getAbuseLocationDescription(),
        is(equalTo(persistent.getAbuseLocationDescription())));
    assertThat(totest.getAbuseStartDate(), is(equalTo(df.format(persistent.getAbuseStartDate()))));
    assertThat(totest.getAllegationDispositionType(),
        is(equalTo(persistent.getAllegationDispositionType())));
    assertThat(totest.getAllegationType(), is(equalTo(persistent.getAllegationType())));
    assertThat(totest.getDispositionDescription(),
        is(equalTo(persistent.getDispositionDescription())));
    assertThat(totest.getDispositionDate(), is(equalTo(df.format(persistent.getDispositionDate()))));
    assertThat(totest.getInjuryHarmDetailIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getInjuryHarmDetailIndicator()))));
    assertThat(totest.getNonProtectingParentCode(),
        is(equalTo(persistent.getNonProtectingParentCode())));
    assertThat(totest.getStaffPersonAddedIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getStaffPersonAddedIndicator()))));
    assertThat(totest.getVictimClientId(), is(equalTo(persistent.getVictimClientId())));
    assertThat(totest.getPerpetratorClientId(), is(equalTo(persistent.getPerpetratorClientId())));
    assertThat(totest.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(totest.getZippyCreatedIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getZippyCreatedIndicator()))));
    assertThat(totest.getPlacementFacilityType(),
        is(equalTo(persistent.getPlacementFacilityType())));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Allegation domain =
        new Allegation(id, abuseEndDate, abuseFrequency, abuseFrequencyPeriodCode,
            abuseLocationDescription, abuseStartDate, allegationDispositionType, allegationType,
            dispositionDescription, dispositionDate, injuryHarmDetailIndicator,
            nonProtectingParentCode, staffPersonAddedIndicator, victimClientId, perpetratorClientId, referralId ,
            countySpecificCode, zippyCreatedIndicator, placementFacilityType);

    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getAbuseEndDate(), is(equalTo(abuseEndDate)));
    assertThat(domain.getAbuseFrequency(), is(equalTo(abuseFrequency)));
    assertThat(domain.getAbuseFrequencyPeriodCode(), is(equalTo(abuseFrequencyPeriodCode)));
    assertThat(domain.getAbuseLocationDescription(), is(equalTo(abuseLocationDescription)));
    assertThat(domain.getAbuseStartDate(), is(equalTo(abuseStartDate)));
    assertThat(domain.getAllegationDispositionType(), is(equalTo(allegationDispositionType)));
    assertThat(domain.getAllegationType(), is(equalTo(allegationType)));
    assertThat(domain.getDispositionDescription(), is(equalTo(dispositionDescription)));
    assertThat(domain.getDispositionDate(), is(equalTo(dispositionDate)));
    assertThat(domain.getInjuryHarmDetailIndicator(), is(equalTo(injuryHarmDetailIndicator)));
    assertThat(domain.getNonProtectingParentCode(), is(equalTo(nonProtectingParentCode)));
    assertThat(domain.getStaffPersonAddedIndicator(), is(equalTo(staffPersonAddedIndicator)));
    assertThat(domain.getVictimClientId(), is(equalTo(victimClientId)));
    assertThat(domain.getPerpetratorClientId(), is(equalTo(perpetratorClientId)));
    assertThat(domain.getReferralId(), is(equalTo(referralId)));
    assertThat(domain.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(domain.getZippyCreatedIndicator(), is(equalTo(zippyCreatedIndicator)));
    assertThat(domain.getPlacementFacilityType(), is(equalTo(placementFacilityType)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected =
        MAPPER.writeValueAsString(MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/valid/valid.json"), Allegation.class));

    assertThat(MAPPER.writeValueAsString(validAllegation()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/valid.json"), Allegation.class),
        is(equalTo(validAllegation())));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/valid.json"), Allegation.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/optionalsNotIncluded.json"),
            Allegation.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
  }

  /*
   * id Tests
   */
  @Test
  public void failsWhenIdMissing() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/id/missing.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("id may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenIdNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/id/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("id may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenIdEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/id/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("id may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenIdTooLong() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/id/tooLong.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("id size must be between 1 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * abuseEndDate Tests
   */
  @Test
  public void successWhenAbuseEndDateEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/abuseEndDate/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenAbuseEndDateNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/abuseEndDate/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenAbuseEndDateWrongFormat() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/abuseEndDate/wrongFormat.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "abuseEndDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * abuseFrequency Tests
   */
  @Test
  public void failsWhenAbuseFrequencyMissing() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/abuseFrequency/missing.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("abuseFrequency may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAbuseFrequencyNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/abuseFrequency/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("abuseFrequency may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * abuseFrequencyPeriodCode Tests
   */
  @Test
  public void failsWhenAbuseFrequencyPeriodCodeMissing() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/abuseFrequencyPeriodCode/missing.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("abuseFrequencyPeriodCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAbuseFrequencyPeriodCodeNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/abuseFrequencyPeriodCode/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("abuseFrequencyPeriodCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAbuseFrequencyPeriodCodeEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/abuseFrequencyPeriodCode/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("abuseFrequencyPeriodCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAbuseFrequencyPeriodCodeTooLong() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/abuseFrequencyPeriodCode/tooLong.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("abuseFrequencyPeriodCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAbuseFrequencyPeriodCodeNotValidValue() throws Exception {
    Allegation toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/Allegation/invalid/abuseFrequencyPeriodCode/notValidValue.json"),
                Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "abuseFrequencyPeriodCode must be one of [D, M, W, Y]"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenAbuseFrequencyPeriodCodeIsD() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/valid/abuseFrequencyPeriodCode/D.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenAbuseFrequencyPeriodCodeIsM() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/valid/abuseFrequencyPeriodCode/M.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenAbuseFrequencyPeriodCodeIsW() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/valid/abuseFrequencyPeriodCode/W.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenAbuseFrequencyPeriodCodeIsY() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/valid/abuseFrequencyPeriodCode/Y.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenAbuseFrequencyPeriodCodeIsLowerCase() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/valid/abuseFrequencyPeriodCode/lowerCase.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * abuseLocationDescription Tests
   */
  @Test
  public void failsWhenAbuseLocationDescriptionMissing() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/abuseLocationDescription/missing.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("abuseLocationDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAbuseLocationDescriptionNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/abuseLocationDescription/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("abuseLocationDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAbuseLocationDescriptionEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/abuseLocationDescription/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("abuseLocationDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAbuseLocationDescriptionTooLong() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/abuseLocationDescription/tooLong.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "abuseLocationDescription size must be between 1 and 75"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * abuseStartDate Tests
   */
  @Test
  public void successWhenAbuseStartDateEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/abuseStartDate/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenAbuseStartDateNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/abuseStartDate/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenAbuseStartDateWrongFormat() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/abuseStartDate/wrongFormat.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "abuseStartDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * allegationDispositionType Tests
   */
  @Test
  public void failsWhenAllegationDispositionTypeMissing() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/allegationDispositionType/missing.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("allegationDispositionType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAllegationDispositionTypeNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/allegationDispositionType/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("allegationDispositionType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAllegationDispositionTypeEmplty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/allegationDispositionType/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("allegationDispositionType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * allegationType Tests
   */
  @Test
  public void failsWhenAllegationTypeMissing() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/allegationType/missing.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("allegationType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAllegationTypeNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/allegationType/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("allegationType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAllegationTypeEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/allegationType/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("allegationType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * dispositionDescription Tests
   */
  @Test
  public void failsWhenDispositionDescriptionMissing() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/dispositionDescription/missing.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionDescriptionNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/dispositionDescription/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionDescriptionEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/dispositionDescription/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * dispositionDate Tests
   */
  @Test
  public void successWhenDispositionDateEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/dispositionDate/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenDispositionDateNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/dispositionDate/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenDispositionDateWrongFormat() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/dispositionDate/wrongFormat.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "dispositionDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * injuryHarmDetailIndicator Tests
   */
  @Test
  public void failsWhenInjuryHarmDetailIndicatorMissing() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/injuryHarmDetailIndicator/missing.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("injuryHarmDetailIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenInjuryHarmDetailIndicatorNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/injuryHarmDetailIndicator/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("injuryHarmDetailIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenInjuryHarmDetailIndicatorEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/injuryHarmDetailIndicator/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("injuryHarmDetailIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenInjuryHarmDetailIndicatorAllWhitespace() throws Exception {
    Allegation toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/Allegation/invalid/injuryHarmDetailIndicator/allWhitespace.json"),
                Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("injuryHarmDetailIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * nonProtectingParentCode Tests
   */
  @Test
  public void failsWhenNonProtectingParentCodeMissing() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/nonProtectingParentCode/missing.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("nonProtectingParentCode may not be empty"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenNonProtectingParentCodeNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/nonProtectingParentCode/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("nonProtectingParentCode may not be empty"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenNonProtectingParentCodeEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/nonProtectingParentCode/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("nonProtectingParentCode may not be empty"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenNonProtectingParentCodeTooLong() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/nonProtectingParentCode/tooLong.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("nonProtectingParentCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenNonProtectingParentCodeNotValidValue() throws Exception {
    Allegation toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/Allegation/invalid/nonProtectingParentCode/notValidValue.json"),
                Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "nonProtectingParentCode must be one of [U, P, Y, N]"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenNonProtectingParentCodeIsU() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/valid/nonProtectingParentCode/U.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenNonProtectingParentCodeIsP() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/valid/nonProtectingParentCode/P.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenNonProtectingParentCodeIsY() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/valid/nonProtectingParentCode/Y.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenNonProtectingParentCodeIsN() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/valid/nonProtectingParentCode/N.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenNonProtectingParentCodeIsLowerCase() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/valid/nonProtectingParentCode/lowerCase.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * staffPersonAddedIndicator Tests
   */
  @Test
  public void failsWhenStaffPersonAddedIndicatorMissing() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/staffPersonAddedIndicator/missing.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStaffPersonAddedIndicatorNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/staffPersonAddedIndicator/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStaffPersonAddedIndicatorEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/staffPersonAddedIndicator/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStaffPersonAddedIndicatorAllWhitespace() throws Exception {
    Allegation toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/Allegation/invalid/staffPersonAddedIndicator/allWhitespace.json"),
                Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * victimClientId Tests
   */
  @Test
  public void failsWhenVictimClientIdMissing() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/victimClientId/missing.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("victimClientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenVictimClientIdNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/victimClientId/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("victimClientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenVictimClientIdEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/victimClientId/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("victimClientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenVictimClientIdTooLong() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/victimClientId/tooLong.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("victimClientId size must be between 1 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * perpetratorClientId Tests
   */
  @Test
  public void successWhenPerpetratorClientIdEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/perpetratorClientId/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenPerpetratorClientIdNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/valid/perpetratorClientId/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenPerpetratorClientIdTooLong() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/perpetratorClientId/tooLong.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("perpetratorClientId size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * referralId Tests
   */
  @Test
  public void failsWhenReferralIdMissing() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/referralId/missing.json"),
            Allegation.class);
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
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/referralId/null.json"),
            Allegation.class);
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
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/referralId/empty.json"),
            Allegation.class);
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
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/Allegation/invalid/referralId/tooLong.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("referralId size must be between 1 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * countySpecificCode Tests
   */
  @Test
  public void failsWhenCountySpecificCodeMissing() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/countySpecificCode/missing.json"),
            Allegation.class);
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
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/countySpecificCode/null.json"),
            Allegation.class);
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
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/countySpecificCode/empty.json"),
            Allegation.class);
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
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/countySpecificCode/tooLong.json"),
            Allegation.class);
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
   * zippyCreatedIndicator Tests
   */
  @Test
  public void failsWhenZippyCreatedIndicatorMissing() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/zippyCreatedIndicator/missing.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenZippyCreatedIndicatorNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/zippyCreatedIndicator/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenZippyCreatedIndicatorEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/zippyCreatedIndicator/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenZippyCreatedIndicatorAllWhitespace() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/invalid/zippyCreatedIndicator/allWhitespace.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * placementFacilityType Tests
   */
  @Test
  public void successWhenPlacementFacilityTypeEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/valid/placementFacilityType/empty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenPlacementFacilityTypeNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/Allegation/valid/placementFacilityType/null.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * Utils
   */
  private Allegation validAllegation() {
    return new Allegation("Aaeae9r0F4", "1999-07-15", (short) 2, "M", "Barber Shop", "1999-07-15",
        (short) 0, (short) 2180, "Fremont", "", false, "N", false, "AHooKwN0F4", "MKPFcB90F4",
        "AbiQCgu0Hj", "19", false, (short) 6574);
  }
}
