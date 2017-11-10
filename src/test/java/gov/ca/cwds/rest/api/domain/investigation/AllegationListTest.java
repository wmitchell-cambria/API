package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashSet;
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

import gov.ca.cwds.fixture.investigation.AllegationEntityBuilder;
import gov.ca.cwds.fixture.investigation.AllegationListEntityBuilder;
import gov.ca.cwds.fixture.investigation.AllegationPersonEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class AllegationListTest {
  private ObjectMapper MAPPER = new ObjectMapper();
  private Validator validator;

  protected Short allegationType = 2179;
  protected Boolean createdByScreener = false;
  protected AllegationSubType allegationSubType;
  protected Short dispositionType = 46;
  protected String rational = "disposistion reason explained";
  private AllegationPerson perpetrator = new AllegationPersonEntityBuilder().setFirstName("Jack")
      .setLastName("Jones").setDateOfBirth("2001-09-30").build();
  private Allegation allegation1 = new AllegationEntityBuilder().build();
  private Allegation allegation2 =
      new AllegationEntityBuilder().setPerpetrator(perpetrator).build();
  private Allegation allegation3 =
      new AllegationEntityBuilder().setCreatedByScreener(Boolean.TRUE).build();
  private Set<Allegation> allegations = new HashSet<>();
  private Set<Allegation> differentAllegations = new HashSet<>();

  @Before
  public void setup() {
    allegations.add(allegation1);
    allegations.add(allegation2);
    differentAllegations.add(allegation3);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  @Test
  public void shouldCreateObjectWithDefaultConstructor() {
    AllegationList allegationList = new AllegationList();
    assertNotNull(allegationList);
  }

  @Test
  public void domainConstructorTest() {
    AllegationList allegationList = new AllegationList(allegations);
    assertThat(allegationList.getAllegations(), is(equalTo(allegations)));
  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    AllegationList allegationList = new AllegationList(allegations);
    AllegationList allegationList1 = new AllegationList(allegations);
    assertEquals(allegationList, allegationList1);

  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    AllegationList allegationList = new AllegationList(allegations);
    AllegationList allegationList1 = new AllegationList(differentAllegations);
    assertThat(allegationList, is(not(equals(allegationList1))));
  }

  @Test
  public void shouldValidateAllegationList() {
    AllegationList allegationList = new AllegationList(allegations);
    Set<ConstraintViolation<AllegationList>> constraintViolations =
        validator.validate(allegationList);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldValidateEmptyAllegationList() {
    AllegationList allegationList = new AllegationList();
    Set<ConstraintViolation<AllegationList>> constraintViolations =
        validator.validate(allegationList);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  // @Ignore
  public void testSerializedOutput()
      throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
    AllegationList allegationList = new AllegationListEntityBuilder().build();
    final String expected =
        MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(allegationList);
    // System.out.println(expected);
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(AllegationList.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
