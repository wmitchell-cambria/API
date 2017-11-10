package gov.ca.cwds.rest.api.domain.investigation;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.investigation.AllegationPersonEntityBuilder;
import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.util.CmsRecordUtils;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


@SuppressWarnings("javadoc")
public class AllegationPersonTest {
  private ObjectMapper MAPPER = new ObjectMapper();
  private Validator validator;

  protected String firstName = "Joanna";
  protected String lastName = "Kenneson";
  protected String middleName = "";
  protected String suffixTitle = "phd";
  protected String dateOfBirth = "2012-10-01";
  private CmsRecordDescriptor legacyDescriptor = new CmsRecordDescriptorEntityBuilder().build();

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  @Test
  public void shouldCreateObjectWithDefaultConstructor() {
    AllegationPerson allegationPerson = new AllegationPerson();
    assertNotNull(allegationPerson);
  }

  @Test
  public void domainConstrutorTest() {
    AllegationPerson domain = new AllegationPerson(firstName, lastName, middleName, suffixTitle,
        dateOfBirth, legacyDescriptor);
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getMiddleName(), is(equalTo(middleName)));
    assertThat(domain.getSuffixTitle(), is(equalTo(suffixTitle)));
    assertThat(domain.getDateOfBirth(), is(equalTo(dateOfBirth)));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
  }

  @Test
  public void testClientConstructorSuccess() {
    Client client = new ClientEntityBuilder().build();
    CmsRecordDescriptor clientLegacyDescriptor =
        CmsRecordUtils.createLegacyDescriptor(client.getId(), LegacyTable.CLIENT);
    AllegationPerson domain = new AllegationPerson(client);
    assertThat(domain.getFirstName(), is(equalTo(client.getFirstName())));
    assertThat(domain.getLastName(), is(equalTo(client.getLastName())));
    assertThat(domain.getMiddleName(), is(equalTo(client.getMiddleName())));
    assertThat(domain.getSuffixTitle(), is(equalTo(client.getSuffixTitleDescription())));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(clientLegacyDescriptor)));
    assertThat(domain.getDateOfBirth(), is(equalTo(DomainChef.cookDate(client.getBirthDate()))));
  }

  @Test
  public void testClientConstructorWithNullDateOfBirthSuccess() {
    Client client = new ClientEntityBuilder().setBirthDate(null).build();
    AllegationPerson domain = new AllegationPerson(client);
    assertThat(domain.getDateOfBirth(), is(equalTo(null)));
  }

  @Test
  public void testInvalidDateOfBirthFormatFailure() {
    AllegationPerson allegationPerson = new AllegationPerson(firstName, lastName, middleName,
        suffixTitle, "04-01-2012", legacyDescriptor);
    Set<ConstraintViolation<AllegationPerson>> constraintViolations =
        validator.validate(allegationPerson);
    assertEquals(1, constraintViolations.size());
  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    AllegationPerson allegationPerson = new AllegationPersonEntityBuilder().build();
    AllegationPerson otherAllegationPerson =
        new AllegationPersonEntityBuilder().setFirstName("Jerry").build();
    assertThat(allegationPerson, is(not(equals(otherAllegationPerson))));
  }

  @Test
  // @Ignore
  public void testSerializedOutput()
      throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
    AllegationPerson allegationPerson = new AllegationPersonEntityBuilder().build();
    final String expected =
        MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(allegationPerson);
    // System.out.println(expected);
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(AllegationPerson.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    AllegationPerson allegationPerson = new AllegationPersonEntityBuilder().build();
    assertThat(
        MAPPER.readValue(fixture("fixtures/domain/investigation/allegationPerson/valid.json"),
            AllegationPerson.class),
        is(equalTo(allegationPerson)));
  }

}
