package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.resources.PersonResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


public class PersonTest {

  private static final String ROOT_RESOURCE = "/people/";
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final PersonResource mockedPeopleResource = mock(PersonResource.class);

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedPeopleResource).build();

  /*
   * Serialization and deserialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
    String expected = MAPPER
        .writeValueAsString(new Person("Bart", "Simpson", "M", "04/01/1990", "123456789", address));

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/person/valid/valid.json"), Person.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
    Person expected = new Person("Bart", "Simpson", "M", "04/01/1990", "123456789", address);
    Person serialized =
        MAPPER.readValue(fixture("fixtures/domain/person/valid/valid.json"), Person.class);
    assertThat(serialized, is(expected));
  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    EqualsVerifier.forClass(Person.class).suppress(Warning.NONFINAL_FIELDS).verify();
    Address address = new Address("123 Main", "Sacramento", "CA", 95757);
    Person expected = new Person("firstname", "lastname", "F", "20010901", "123456789", address);
    Person serialized =
        MAPPER.readValue(fixture("fixtures/domain/person/valid/valid.json"), Person.class);
    assertThat(serialized, is(expected));
  }

  /*
   * success test
   */
  @Test
  public void successfulWithValid() throws Exception {
    Person serialized =
        MAPPER.readValue(fixture("fixtures/domain/people/valid/valid.json"), Person.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * date of birth test - success
   */
  @Test
  public void successWhenValidDateOfBirth() throws Exception {
    Person serialized =
        MAPPER.readValue(fixture("fixtures/domain/people/valid/valid.json"), Person.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
    // assertThat(response.readEntity(String.class).indexOf("must be in the format of"),
    // is(greaterThanOrEqualTo(0)));

  }

  /*
   * date of birth test - invalid format
   */
  @Test
  public void failsWhenInvalidDateOfBirth() throws Exception {
    Person serialized =
        MAPPER.readValue(fixture("fixtures/domain/people/invalid/dob/invalid.json"), Person.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * date of birth test - empty/null date
   */
  @Test
  public void failsWhenDateOfBirthNull() throws Exception {
    Person serialized = MAPPER
        .readValue(fixture("fixtures/domain/people/invalid/dobnull/invalid.json"), Person.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDateOfBirthInFuture() throws Exception {
    Person serialized = MAPPER.readValue(
        fixture("fixtures/domain/people/invalid/dobinfuture/invalid.json"), Person.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void failsWhenInvalidGender() throws Exception {
    Person serialized = MAPPER
        .readValue(fixture("fixtures/domain/people/invalid/gender/invalid.json"), Person.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void failedTest() {}
  // TODO : RDB implement tests
}
