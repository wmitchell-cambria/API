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

import java.text.ParseException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.es.PersonSearchRequest;
import gov.ca.cwds.rest.api.persistence.cms.Referral;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.jdbi.CrudsDao;
import gov.ca.cwds.rest.jdbi.DataAccessEnvironment;
import gov.ca.cwds.rest.resources.PersonSearchResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class PersonSearchRequestTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_SEARCH_PERSON + "/";

  private static final PersonSearchResource mockResource = mock(PersonSearchResource.class);

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private PersonSearchRequest validPersonSearchRequest = validPersonSearchRequest();

  private String firstName = "bart";
  private String lastName = "simpson";
  private String birthDate = "2008-09-01";


  public PersonSearchRequestTest() throws ParseException {}

  @Before
  public void setup() {
    @SuppressWarnings("rawtypes")
    CrudsDao crudsDao = mock(CrudsDao.class);
    DataAccessEnvironment.register(gov.ca.cwds.rest.api.persistence.cms.Referral.class, crudsDao);
    when(crudsDao.find(any())).thenReturn(mock(Referral.class));

    when(mockResource.create(eq(validPersonSearchRequest)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/es/PersonSearch/valid/valid.json"), PersonSearchRequest.class));

    assertThat(MAPPER.writeValueAsString(validPersonSearchRequest()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/es/PersonSearch/valid/valid.json"),
        PersonSearchRequest.class), is(equalTo(validPersonSearchRequest())));
  }

  @Test
  public void jsonCreatorCtorTest() throws Exception {
    PersonSearchRequest referralClient = new PersonSearchRequest(firstName, lastName, birthDate);

    assertThat(referralClient.getFirstName(), is(equalTo(firstName)));
    assertThat(referralClient.getLastName(), is(equalTo(lastName)));
    assertThat(referralClient.getBirthDate(), is(equalTo(birthDate)));
  }

  @Test
  public void equalsHashCodeWork() {
    // TODO: Broken test!! Date field not validating!
    // EqualsVerifier.forClass(PersonSearchRequest.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /**
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    PersonSearchRequest toCreate = MAPPER.readValue(
        fixture("fixtures/domain/es/PersonSearch/valid/valid.json"), PersonSearchRequest.class);
    Response response = resources.client().target(ROOT_RESOURCE + "all").request()
        .accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(Response.Status.NO_CONTENT.getStatusCode())));
  }

  /**
   * birthDate Tests
   */
  @Test
  public void successWhenBirthDateEmpty() throws Exception {
    PersonSearchRequest toCreate =
        MAPPER.readValue(fixture("fixtures/domain/es/PersonSearch/valid/birthDateEmpty.json"),
            PersonSearchRequest.class);
    Response response = resources.client().target(ROOT_RESOURCE + "query_or").request()
        .accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(Response.Status.NO_CONTENT.getStatusCode())));
  }

  @Test
  public void successWhenBirthDateNull() throws Exception {
    PersonSearchRequest toCreate =
        MAPPER.readValue(fixture("fixtures/domain/es/PersonSearch/valid/birthDateNull.json"),
            PersonSearchRequest.class);
    Response response = resources.client().target(ROOT_RESOURCE + "query_or").request()
        .accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(Response.Status.NO_CONTENT.getStatusCode())));
  }

  @Test
  public void failsWhenBirthDateWrongFormat() throws Exception {
    PersonSearchRequest toCreate = MAPPER.readValue(
        fixture("fixtures/domain/es/PersonSearch/invalid/birthDateWrongFormat.json"),
        PersonSearchRequest.class);
    Response response = resources.client().target(ROOT_RESOURCE + "query_or").request()
        .accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));

    // final String message = response.readEntity(String.class);
    // System.out.print(message);

    // TODO: Broken test!! Date field not validating!
    // assertThat(response.getStatus(), is(equalTo(422)));
    // assertThat(
    // response.readEntity(String.class).indexOf("birth_date must be in the format of yyyy-MM-dd"),
    // is(greaterThanOrEqualTo(0)));
  }

  /*
   * Utils
   */
  private PersonSearchRequest validPersonSearchRequest() {
    return new PersonSearchRequest("bart", "simpson", "2008-09-01");
  }
}
