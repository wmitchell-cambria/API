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
import gov.ca.cwds.rest.api.domain.legacy.StaffPerson;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.legacy.StaffPersonResourceImpl;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class StaffPersonTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_STAFF_PERSON + "/";

  private static final StaffPersonResourceImpl mockedStaffPersonResource =
      mock(StaffPersonResourceImpl.class);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(mockedStaffPersonResource).build();


  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private StaffPerson validStaffPerson = validStaffPerson();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "a";
  private String endDate = "1973-11-22";
  private String firstName = "b";
  private String jobTitle = "c";
  private String lastName = "d";
  private String middleInitial = "e";
  private String namePrefix = "f";
  private BigDecimal phoneNumber = new BigDecimal(1);
  private Integer phoneExt = 2;
  private String startDate = "2006-09-12";
  private String nameSuffix = "g";
  private Boolean telecommuterIndicator = Boolean.TRUE;
  private String cwsOffice = "h";
  private String availabilityAndLocationDescription = "i";
  private String ssrsLicensingWorkerId = "j";
  private String countyCode = "k";
  private Boolean dutyWorkerIndicator = Boolean.FALSE;
  private String cwsOfficeAddress = "l";
  private String emailAddress = "m";
  private String twitterName = "n";

  @Before
  public void setup() {
    when(
        mockedStaffPersonResource.create(eq(validStaffPerson),
            eq(Api.Version.JSON_VERSION_1.getMediaType()), any(UriInfo.class))).thenReturn(
        Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    StaffPerson domain =
        new StaffPerson(id, endDate, firstName, jobTitle, lastName, middleInitial, namePrefix,
            phoneNumber, phoneExt, startDate, nameSuffix, telecommuterIndicator, cwsOffice,
            availabilityAndLocationDescription, ssrsLicensingWorkerId, countyCode,
            dutyWorkerIndicator, cwsOfficeAddress, emailAddress, twitterName);
    gov.ca.cwds.rest.api.persistence.legacy.StaffPerson persistent =
        new gov.ca.cwds.rest.api.persistence.legacy.StaffPerson(domain, "lastUpdatedId");
    gov.ca.cwds.rest.api.persistence.ns.StaffPersonNS persistentNS =
        new gov.ca.cwds.rest.api.persistence.ns.StaffPersonNS(domain, "lastUpdatedId");

    StaffPerson totest = new StaffPerson(persistent, persistentNS);
    assertThat(totest.getId(), is(equalTo(persistent.getId())));
    assertThat(totest.getEndDate(), is(equalTo(df.format(persistent.getEndDate()))));
    assertThat(totest.getFirstName(), is(equalTo(persistent.getFirstName())));
    assertThat(totest.getJobTitle(), is(equalTo(persistent.getJobTitle())));
    assertThat(totest.getLastName(), is(equalTo(persistent.getLastName())));
    assertThat(totest.getMiddleInitial(), is(equalTo(persistent.getMiddleInitial())));
    assertThat(totest.getNamePrefix(), is(equalTo(persistent.getNamePrefix())));
    assertThat(totest.getPhoneNumber(), is(equalTo(persistent.getPhoneNumber())));
    assertThat(totest.getPhoneExt(), is(equalTo(persistent.getPhoneExt())));
    assertThat(totest.getStartDate(), is(equalTo(df.format(persistent.getStartDate()))));
    assertThat(totest.getNameSuffix(), is(equalTo(persistent.getNameSuffix())));
    assertThat(totest.getTelecommuterIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getTelecommuterIndicator()))));
    assertThat(totest.getCwsOffice(), is(equalTo(persistent.getCwsOffice())));
    assertThat(totest.getAvailabilityAndLocationDescription(),
        is(equalTo(persistent.getAvailabilityAndLocationDescription())));
    assertThat(totest.getSsrsLicensingWorkerId(),
        is(equalTo(persistent.getSsrsLicensingWorkerId())));
    assertThat(totest.getCountyCode(), is(equalTo(persistent.getCountyCode())));
    assertThat(totest.getDutyWorkerIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getDutyWorkerIndicator()))));
    assertThat(totest.getCwsOfficeAddress(), is(equalTo(persistent.getCwsOfficeAddress())));
    assertThat(totest.getEmailAddress(), is(equalTo(persistent.getEmailAddress())));
    assertThat(totest.getTwitterName(), is(equalTo(persistentNS.getTwitterName())));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    StaffPerson domain =
        new StaffPerson(id, endDate, firstName, jobTitle, lastName, middleInitial, namePrefix,
            phoneNumber, phoneExt, startDate, nameSuffix, telecommuterIndicator, cwsOffice,
            availabilityAndLocationDescription, ssrsLicensingWorkerId, countyCode,
            dutyWorkerIndicator, cwsOfficeAddress, emailAddress, twitterName);

    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getEndDate(), is(equalTo(endDate)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getJobTitle(), is(equalTo(jobTitle)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getMiddleInitial(), is(equalTo(middleInitial)));
    assertThat(domain.getNamePrefix(), is(equalTo(namePrefix)));
    assertThat(domain.getPhoneNumber(), is(equalTo(phoneNumber)));
    assertThat(domain.getPhoneExt(), is(equalTo(phoneExt)));
    assertThat(domain.getStartDate(), is(equalTo(startDate)));
    assertThat(domain.getNameSuffix(), is(equalTo(nameSuffix)));
    assertThat(domain.getTelecommuterIndicator(), is(equalTo(telecommuterIndicator)));
    assertThat(domain.getCwsOffice(), is(equalTo(cwsOffice)));
    assertThat(domain.getAvailabilityAndLocationDescription(),
        is(equalTo(availabilityAndLocationDescription)));
    assertThat(domain.getSsrsLicensingWorkerId(), is(equalTo(ssrsLicensingWorkerId)));
    assertThat(domain.getCountyCode(), is(equalTo(countyCode)));
    assertThat(domain.getDutyWorkerIndicator(), is(equalTo(dutyWorkerIndicator)));
    assertThat(domain.getCwsOfficeAddress(), is(equalTo(cwsOfficeAddress)));
    assertThat(domain.getEmailAddress(), is(equalTo(emailAddress)));
    assertThat(domain.getTwitterName(), is(equalTo(twitterName)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected =
        MAPPER.writeValueAsString(MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/valid/valid.json"), StaffPerson.class));

    assertThat(MAPPER.writeValueAsString(validStaffPerson()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/valid.json"),
        StaffPerson.class), is(equalTo(validStaffPerson())));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    StaffPerson toCreate =
        MAPPER
            .readValue(fixture("fixtures/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1)).getStatus(), is(equalTo(204)));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/optionalsNotIncluded.json"),
            StaffPerson.class);
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
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/id/missing.json"),
            StaffPerson.class);
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
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/id/empty.json"),
            StaffPerson.class);
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
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/id/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("id may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenIdTooShort() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/id/tooShort.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("id size must be 3"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenIdTooLong() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/id/tooLong.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("id size must be 3"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * endDate Tests
   */
  @Test
  public void successWhenEndDateEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/endDate/empty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenEndDateNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/endDate/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenEndDateWrongFormat() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/endDate/wrongFormat.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("endDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * firstName Tests
   */
  @Test
  public void failsWhenFirstNameMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/firstName/missing.json"),
            StaffPerson.class);
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
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/firstName/null.json"),
            StaffPerson.class);
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
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/firstName/empty.json"),
            StaffPerson.class);
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
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/firstName/tooLong.json"),
            StaffPerson.class);
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
   * jobTitle Tests
   */
  @Test
  public void failsWhenJobTitleMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/jobTitle/missing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("jobTitle may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenJobTitleNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/jobTitle/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("jobTitle may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenJobTitleEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/jobTitle/empty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("jobTitle may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenJobTitleTooLong() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/jobTitle/tooLong.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("jobTitle size must be between 1 and 30"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * lastName Tests
   */
  @Test
  public void failsWhenLastNameMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastName/missing.json"),
            StaffPerson.class);
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
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastName/null.json"),
            StaffPerson.class);
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
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastName/empty.json"),
            StaffPerson.class);
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
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/lastName/tooLong.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lastName size must be between 1 and 25"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * middleInitial Tests
   */
  @Test
  public void failsWhenMiddleInitialMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/middleInitial/missing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("middleInitial may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMiddleInitialNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/middleInitial/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("middleInitial may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMiddleInitialEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/middleInitial/empty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("middleInitial may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMiddleInitialTooLong() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/middleInitial/tooLong.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("middleInitial size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * namePrefix Tests
   */
  @Test
  public void failsWhenNamePrefixMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/namePrefix/missing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("namePrefix may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenNamePrefixNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/namePrefix/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("namePrefix may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenNamePrefixEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/namePrefix/empty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("namePrefix may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenNamePrefixTooLong() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/namePrefix/tooLong.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("namePrefix size must be between 1 and 6"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * phoneNumber Tests
   */
  @Test
  public void failsWhenPhoneNumberMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/phoneNumber/missing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("phoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPhoneNumberNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/phoneNumber/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("phoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPhoneNumberEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/phoneNumber/empty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("phoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * phoneExt Tests
   */
  @Test
  public void failsWhenPhoneExtMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/phoneExt/missing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("phoneExt may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPhoneExtNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/phoneExt/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("phoneExt may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPhoneExtEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/phoneExt/empty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("phoneExt may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * startDate Tests
   */
  @Test
  public void failsWhenStartDateMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/startDate/missing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("startDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStartDateNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/startDate/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("startDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStartDateWrongFormat() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/startDate/wrongFormat.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("startDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * nameSuffix Tests
   */
  @Test
  public void failsWhenNameSuffixMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/nameSuffix/missing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("nameSuffix may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenNameSuffixNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/nameSuffix/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("nameSuffix may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenNameSuffixEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/nameSuffix/empty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("nameSuffix may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenNameSuffixTooLong() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/nameSuffix/tooLong.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("nameSuffix size must be between 1 and 4"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * telecommuterIndicator Tests
   */
  @Test
  public void failsWhenTelecommuterIndicatorMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/invalid/telecommuterIndicator/missing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("telecommuterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenTelecommuterIndicatorNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/invalid/telecommuterIndicator/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("telecommuterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenTelecommuterIndicatorEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/invalid/telecommuterIndicator/empty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("telecommuterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenTelecommuterIndicatorAllWhitespace() throws Exception {
    StaffPerson toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/StaffPerson/invalid/telecommuterIndicator/allWhitespace.json"),
                StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("telecommuterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * cwsOffice Tests
   */
  @Test
  public void failsWhenCwsOfficeMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/cwsOffice/missing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOffice may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCwsOfficeNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/cwsOffice/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOffice may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCwsOfficeEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/cwsOffice/empty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOffice may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCwsOfficeTooLong() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/cwsOffice/tooLong.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("cwsOffice size must be between 1 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * availabilityAndLocationDescription Tests
   */
  @Test
  public void failsWhenAvailabilityAndLocationDescriptionMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/StaffPerson/invalid/availabilityAndLocationDescription/missing.json"),
                StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "availabilityAndLocationDescription may not be empty"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAvailabilityAndLocationDescriptionEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/StaffPerson/invalid/availabilityAndLocationDescription/empty.json"),
                StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "availabilityAndLocationDescription may not be empty"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAvailabilityAndLocationDescriptionNull() throws Exception {
    StaffPerson toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/StaffPerson/invalid/availabilityAndLocationDescription/null.json"),
                StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "availabilityAndLocationDescription may not be empty"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAvailabilityAndLocationDescriptionTooLong() throws Exception {
    StaffPerson toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/legacy/StaffPerson/invalid/availabilityAndLocationDescription/tooLong.json"),
                StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "availabilityAndLocationDescription size must be between 1 and 160"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * ssrsLicensingWorkerId Tests
   */
  @Test
  public void failsWhenSsrsLicensingWorkerIdMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/invalid/ssrsLicensingWorkerId/missing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("ssrsLicensingWorkerId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSsrsLicensingWorkerIdNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/invalid/ssrsLicensingWorkerId/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("ssrsLicensingWorkerId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSsrsLicensingWorkerIdEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/invalid/ssrsLicensingWorkerId/empty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("ssrsLicensingWorkerId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSsrsLicensingWorkerIdTooLong() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/invalid/ssrsLicensingWorkerId/tooLong.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "ssrsLicensingWorkerId size must be between 1 and 4"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * countyCode Tests
   */
  @Test
  public void failsWhenCountyCodeMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/countyCode/missing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countyCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountyCodeNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/countyCode/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countyCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountyCodeEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/countyCode/empty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countyCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountyCodeTooLong() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/countyCode/tooLong.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("countyCode size must be between 1 and 2"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * dutyWorkerIndicator Tests
   */
  @Test
  public void failsWhenDutyWorkerIndicatorMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/invalid/dutyWorkerIndicator/missing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dutyWorkerIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDutyWorkerIndicatorNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/invalid/dutyWorkerIndicator/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dutyWorkerIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDutyWorkerIndicatorEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/invalid/dutyWorkerIndicator/empty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dutyWorkerIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDutyWorkerIndicatorAllWhitespace() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/invalid/dutyWorkerIndicator/allWhitespace.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dutyWorkerIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * cwsOfficeAddress Tests
   */
  @Test
  public void failsWhenCwsOfficeAddressMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/invalid/cwsOfficeAddress/missing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOfficeAddress may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCwsOfficeAddressNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/cwsOfficeAddress/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOfficeAddress may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCwsOfficeAddressEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/invalid/cwsOfficeAddress/empty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOfficeAddress may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCwsOfficeAddressTooLong() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(
            fixture("fixtures/legacy/StaffPerson/invalid/cwsOfficeAddress/tooLong.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("cwsOfficeAddress size must be between 1 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * emailAddress Tests
   */
  @Test
  public void successWhenEmailAddressEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/emailAddress/empty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenEmailAddressNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/emailAddress/null.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenEmailAddressTooLong() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/invalid/emailAddress/tooLong.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request()
            .accept(Api.Version.JSON_VERSION_1.getMediaType())
            .post(Entity.entity(toCreate, Api.MEDIA_TYPE_JSON_V1));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("emailAddress size must be between 0 and 50"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * Utils
   */
  private StaffPerson validStaffPerson() {
    return new StaffPerson("ABC", "2016-08-07", "John", "CEO", "Doe", "C", "Mr", new BigDecimal(
        9165551212L), 22, "2001-01-02", "sufx", true, "MIZN02k11B", "abc", "def", "99", false,
        "3XPCP92b24", "john.doe@anyco.com", "john");
  }
}
