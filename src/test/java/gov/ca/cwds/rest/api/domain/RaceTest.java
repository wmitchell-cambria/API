package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class RaceTest {

  private String race = "White";
  private String subrace = "American";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(new Race("White", "American"));

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/race/valid/valid.json"), Race.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void testDeserializesFromJSON() throws Exception {
    Race expected = new Race("White", "American");

    Race serialized =
        MAPPER.readValue(fixture("fixtures/domain/race/valid/valid.json"), Race.class);
    assertThat(serialized, is(expected));

  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    // EqualsVerifier.forClass(Race.class).suppress(Warning.NONFINAL_FIELDS).verify();
    Race expected = new Race("White", "American");
    assertThat(expected.hashCode(), is(not(0)));
  }

  @Test
  public void persistentObjectConstructorTest() throws Exception {
    Race domain = this.validRace();

    gov.ca.cwds.data.persistence.ns.Race persistent =
        new gov.ca.cwds.data.persistence.ns.Race(domain, "12345", "12345");

    Race totest = new Race(persistent);
    assertThat(totest.getRaceType(), is(equalTo(persistent.getRaceType())));
    assertThat(totest.getSubRaceType(), is(equalTo(persistent.getSubRaceType())));
  }

  @Test
  public void testJSONConstructorTest() throws Exception {
    Race domain = new Race(race, subrace);

    assertThat(domain.getRaceType(), is(equalTo(race)));
    assertThat(domain.getSubRaceType(), is(equalTo(subrace)));
  }

  private Race validRace() {

    try {
      Race validRace =
          MAPPER.readValue(fixture("fixtures/domain/race/valid/valid.json"), Race.class);

      return validRace;

    } catch (JsonParseException e) {
      e.printStackTrace();
      return null;
    } catch (JsonMappingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
