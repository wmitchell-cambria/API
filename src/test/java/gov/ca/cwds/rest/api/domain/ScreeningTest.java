package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.resources.ScreeningResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

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
    Address address = new Address("10 main st", "Sacramento", "CA", 95814);
    ArrayList<Integer> involvedPersonIds = new ArrayList<Integer>();
    involvedPersonIds.add(1);
    involvedPersonIds.add(2);
    involvedPersonIds.add(3);
    String expected = MAPPER.writeValueAsString(
        new Screening((long) 2, "X5HNJK", "2016-10-13T01:07", "amador", "2016-10-13",
            "Relative's Home", "email", "first screening", "immediate", "accept_for_investigation",
            "2016-10-05T01:01", "first narrative", address, involvedPersonIds));

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/screening/valid/valid.json"), Screening.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    Address address = new Address("10 main st", "Sacramento", "CA", 95814);
    ArrayList<Integer> involvedPersonIds = new ArrayList<Integer>();
    involvedPersonIds.add(1);
    involvedPersonIds.add(2);
    involvedPersonIds.add(3);
    Screening expected =
        new Screening((long) 2, "X5HNJK", "2016-10-13T01:07", "amador", "2016-10-13",
            "Relative's Home", "email", "first screening", "immediate", "accept_for_investigation",
            "2016-10-05T01:01", "first narrative", address, involvedPersonIds);
    Screening serialized =
        MAPPER.readValue(fixture("fixtures/domain/screening/valid/valid.json"), Screening.class);
    assertThat(serialized, is(expected));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Screening.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * validation tests
   */
}
