package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.ObjectMapperUtils;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class PhoneNumberTest {

  String number = "408 672-5583";
  String type = "Home";
  Long id = 1234L;
  Long newId = 234L;

  private static final ObjectMapper MAPPER = ObjectMapperUtils.createObjectMapper();

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(new PhoneNumber("408 690-1234", "Cell"));

    String serialized = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/domain/phoneNumber/valid/valid.json"), PhoneNumber.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void testDeserializesFromJSON() throws Exception {
    PhoneNumber expected = new PhoneNumber("408 690-1234", "Cell");

    PhoneNumber serialized = MAPPER
        .readValue(fixture("fixtures/domain/phoneNumber/valid/valid.json"), PhoneNumber.class);
    assertThat(serialized, is(expected));
  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    // EqualsVerifier.forClass(PhoneNumber.class).suppress(Warning.NONFINAL_FIELDS).verify();
    PhoneNumber serialized = MAPPER
        .readValue(fixture("fixtures/domain/phoneNumber/valid/valid.json"), PhoneNumber.class);
    assertThat(serialized.hashCode(), is(not(0)));
  }

  @Test
  public void persistentObjectConstructorWithLastUpdatedTest() throws Exception {
    PhoneNumber domain = this.validPhoneNumber();

    gov.ca.cwds.data.persistence.ns.PhoneNumber persistent =
        new gov.ca.cwds.data.persistence.ns.PhoneNumber(domain, "LastUpdateId", "CreatedId");

    PhoneNumber totest = new PhoneNumber(persistent);

    assertThat(totest.getNumber(), is(equalTo(persistent.getNumber())));
    assertThat(totest.getType(), is(equalTo(persistent.getType())));
  }

  @Test
  public void testConstructorWithPersistentObject() throws Exception {
    gov.ca.cwds.data.persistence.ns.PhoneNumber persistent =
        new gov.ca.cwds.data.persistence.ns.PhoneNumber(id, number, type);
    PhoneNumber domain = new PhoneNumber(persistent);
    assertThat(domain.getId(), is(equalTo(persistent.getId())));
    assertThat(domain.getNumber(), is(equalTo(persistent.getNumber())));
    assertThat(domain.getType(), is(equalTo(persistent.getType())));
    
  }
  
  @Test
  public void testSetters() throws Exception {
    PhoneNumber domain = new PhoneNumber(number, type);
    domain.setId(newId);
    assertThat(domain.getId(), is(equalTo(newId)));
  }
  
  @Test
  public void testJSONConstructorTest() throws Exception {
    PhoneNumber domain = new PhoneNumber(number, type);

    assertThat(domain.getNumber(), is(equalTo(number)));
    assertThat(domain.getType(), is(equalTo(type)));
  }

  private PhoneNumber validPhoneNumber() {
    try {
      PhoneNumber validPhoneNumber = MAPPER
          .readValue(fixture("fixtures/domain/phoneNumber/valid/valid.json"), PhoneNumber.class);
      return validPhoneNumber;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

}
