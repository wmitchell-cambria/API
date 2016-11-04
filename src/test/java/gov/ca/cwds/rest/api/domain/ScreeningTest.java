package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.resources.ScreeningResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class ScreeningTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final ScreeningResource mockedScreeningResource = mock(ScreeningResource.class);

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedScreeningResource).build();

  /*
   * Serialization and deserialization
   */
  @Test
  public void serializesToJSON() throws Exception {

    String expected = MAPPER.writeValueAsString(new Screening("screening reference", "10/19/2016",
        "Santa Clara", "10/19/2016", "school", "phone", "screening name", "24 hour",
        "accept_for_investigation", "2016-10-05", "test the narrative"));
    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/screening/valid/valid.json"), Screening.class));

    assertThat(serialized, is(expected));
  }

  // @Test
  // public void deserializesFromJSON() throws Exception {
  // Screening expected = new Screening("screening reference", "10/19/2016", "Santa Clara",
  // "10/19/2016", "school", "phone", "screening name", "24 hour", "accept_for_investigation",
  // "2016-10-05", "test the narrative");
  // Screening serialized =
  // MAPPER.readValue(fixture("fixtures/domain/screening/valid/valid.json"), Screening.class);
  // assertThat(serialized, is(expected));
  // }

  // @Test
  // public void equalsHashCodeWork() throws Exception {
  // EqualsVerifier.forClass(Screening.class).suppress(Warning.NONFINAL_FIELDS).verify();
  // Screening expected = new Screening("screening reference", "10/19/2016", "Santa Clara",
  // "10/19/2016", "school", "phone", "screening name", "24 hour", "accept_for_investigation",
  // "2016-10-05", "test the narrative");
  // Screening serialized =
  // MAPPER.readValue(fixture("fixtures/domain/screening/valid/valid.json"), Screening.class);
  // assertThat(serialized, is(expected));
  // }
}
