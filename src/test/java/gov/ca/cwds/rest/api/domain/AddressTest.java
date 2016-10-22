package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.resources.AddressResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class AddressTest {

  private static final String ROOT_RESOURCE = "/address/";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final AddressResource mockedAddressResource = mock(AddressResource.class);

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedAddressResource).build();


  /*
   * Serialization and deserialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(new Address("123 Main", "Sacramento", "CA", 95757));

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/address/valid/valid.json"), Address.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    Address expected = new Address("123 Main", "Sacramento", "CA", 95757);
    Address serialized =
        MAPPER.readValue(fixture("fixtures/domain/address/valid/valid.json"), Address.class);
    assertThat(serialized, is(expected));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Address.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * zip test - invalid format
   */
  // NOTE : we aren't validating zip right now
  // @Test
  // public void failsWhenInvalidZip() throws Exception {
  // Person serialized =
  // MAPPER.readValue(fixture("fixtures/domain/address/invalid/zip/invalid.json"), Person.class);
  // Response response =
  // resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
  // .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
  // assertThat(response.getStatus(), is(equalTo(422)));
  // assertThat(response.readEntity(String.class).indexOf("must be in the format of"),
  // is(greaterThanOrEqualTo(0)));
  // }


}
