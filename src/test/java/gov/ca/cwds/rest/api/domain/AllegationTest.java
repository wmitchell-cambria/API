package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.AllegationResourceBuilder;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class AllegationTest {

  private long victimPersonId = 1;
  private long perpetratorPersonId = 2;
  private Short type = 2179;
  private String county = "Sacramento";
  private String legacySourceTable = "ALLGTN_T";
  private String legacyId = "1234567ABC";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Validator validator;

  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  private Short abuseCode = (short) 2179;

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(
        new Allegation("ALLGTN_T", "1234567ABC", 5432, 2, abuseCode, "Sacramento"));

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/Allegation/valid/valid.json"), Allegation.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void testDeserializesFromJSON() throws Exception {
    Allegation expected =
        new Allegation("ALLGTN_T", "1234567ABC", 5432, 2, abuseCode, "Sacramento");

    Allegation serialized =
        MAPPER.readValue(fixture("fixtures/domain/Allegation/valid/valid.json"), Allegation.class);
    assertThat(serialized, is(expected));
  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    Allegation domain = new Allegation(legacySourceTable, legacyId, victimPersonId,
        perpetratorPersonId, type, county);
    assertThat(domain.hashCode(), is(not(0)));
  }

  @Test
  public void testDomainConstructorTest() throws Exception {
    Allegation domain = new Allegation(legacySourceTable, legacyId, victimPersonId,
        perpetratorPersonId, type, county);

    assertThat(domain.getVictimPersonId(), is(equalTo(victimPersonId)));
    assertThat(domain.getPerpetratorPersonId(), is(equalTo(perpetratorPersonId)));
    assertThat(domain.getType(), is(equalTo(type)));
    assertThat(domain.getCounty(), is(equalTo(county)));
    assertThat(domain.getLegacyId(), is(equalTo(legacyId)));
    assertThat(domain.getLegacySourceTable(), is(equalTo(legacySourceTable)));
  }

  @Test
  public void testBlankLegacySourceTableSuccess() throws Exception {
    Allegation toValidate =
        new AllegationResourceBuilder().setLegacySourceTable("").createAllegation();

    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testNullLegacySourceTableSuccess() throws Exception {
    Allegation toValidate =
        new AllegationResourceBuilder().setLegacySourceTable(null).createAllegation();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testMissingLegacySourceTableSuccess() throws Exception {
    Allegation toValidate =
        MAPPER.readValue(fixture("fixtures/domain/Allegation/valid/missingLegacySourceTable.json"),
            Allegation.class);
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testBlankLegacyIdSuccess() throws Exception {
    Allegation toValidate =
        new AllegationResourceBuilder().setLegacySourceTable("").createAllegation();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testNullLegacyIdFail() throws Exception {
    Allegation toValidate =
        new AllegationResourceBuilder().setLegacySourceTable(null).createAllegation();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());

  }

  @Test
  public void testMissingLegacyIdSuccess() throws Exception {
    Allegation toValidate = MAPPER.readValue(
        fixture("fixtures/domain/Allegation/valid/missingLegacyId.json"), Allegation.class);
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testLegacyIdTooLongFail() throws Exception {
    Allegation toValidate =
        new AllegationResourceBuilder().setLegacyId("09OkH321LKh").createAllegation();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("size must be between 0 and 10",
        constraintViolations.iterator().next().getMessage());
  }

  @SuppressWarnings("unused")
  private Allegation validAllegation() {
    try {
      return MAPPER.readValue(fixture("fixtures/domain/Allegation/valid/valid.json"),
          Allegation.class);
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
