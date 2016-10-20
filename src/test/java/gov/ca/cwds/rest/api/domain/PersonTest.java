package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import gov.ca.cwds.rest.resources.PersonResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonTest {

  private static final String ROOT_RESOURCE = "/people/";
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final PersonResource mockedPersonResource = mock(PersonResource.class);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(mockedPersonResource).build();

  /*
   * Serialization and deserialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    Address address = new Address("123 Main", "Sacramento", "CA", 95757);
    String expected =
        MAPPER.writeValueAsString(new Person("firstname", "lastname", "F", "09/01/2001",
            "123456789", address));
    String serialized =
        MAPPER.writeValueAsString(MAPPER.readValue(
            fixture("fixtures/domain/person/valid/valid.json"), Person.class));
    assertThat(serialized, is(expected));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    Address address = new Address("123 Main", "Sacramento", "CA", 95757);
    Person expected = new Person("firstname", "lastname", "F", "09/01/2001", "123456789", address);
    Person serialized =
        MAPPER.readValue(fixture("fixtures/domain/person/valid/valid.json"), Person.class);
    assertThat(serialized, is(expected));
  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    EqualsVerifier.forClass(Person.class).suppress(Warning.NONFINAL_FIELDS).verify();
    Address address = new Address("123 Main", "Sacramento", "CA", 95757);
    Person expected = new Person("firstname", "lastname", "F", "09/01/2001", "123456789", address);
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
        MAPPER.readValue(fixture("fixtures/domain/person/valid/valid.json"), Person.class);
    Entity<Person> entity = Entity.entity(serialized, MediaType.APPLICATION_JSON);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(entity);
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * date of birth test - success
   */
  @Test
  public void successWhenValidDateOfBirth() throws Exception {
    Person serialized =
        MAPPER.readValue(fixture("fixtures/domain/person/valid/valid.json"), Person.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * date of birth test - invalid format
   */
  @Test
  public void failsWhenInvalidDateOfBirth() throws Exception {
    Person serialized =
        MAPPER.readValue(fixture("fixtures/domain/person/invalid/dob/wrongFormat.json"),
            Person.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("date_of_birth must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDateOfBirthInFuture() throws Exception {
    Person serialized =
        MAPPER.readValue(fixture("fixtures/domain/person/invalid/dob/future.json"), Person.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  /*
   * Ssn Tests
   */
  @Test
  public void failsWhenSsnTooLong() throws Exception {
    Person serialized =
        MAPPER.readValue(fixture("fixtures/domain/person/invalid/ssn/tooLong.json"), Person.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("ssn size must be between 0 and 9"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * Gender Tests
   */
  @Test
  public void failsWhenGenderTooLong() throws Exception {
    Person serialized =
        MAPPER.readValue(fixture("fixtures/domain/person/invalid/gender/tooLong.json"),
            Person.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("gender size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

}
