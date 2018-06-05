package gov.ca.cwds.rest.api.domain.investigation;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
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
import gov.ca.cwds.fixture.investigation.AllegationPersonEntityBuilder;
import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class AllegationTest {

  private ObjectMapper MAPPER = new ObjectMapper();
  private Short injuryHarmType1 = 1372;
  private Short injuryHarmType2 = 1372;
  private Short injuryHarmSubType1 = 6;
  private Short injuryHarmSubType2 = 7;

  private Short otherAllegationType = 2177;

  private AllegationSubType allegationSubType1 =
      new AllegationSubType(injuryHarmType1, injuryHarmSubType1);
  private AllegationSubType allegationSubType2 =
      new AllegationSubType(injuryHarmType2, injuryHarmSubType2);
  private Set<AllegationSubType> allegationSubTypes = new HashSet<>();

  protected Short allegationType = 2179;
  protected Boolean createdByScreener = false;
  protected AllegationSubType allegationSubType;
  protected Short dispositionType = 46;
  protected String rational = "disposistion reason explained";
  private CmsRecordDescriptor legacyDescriptor = new CmsRecordDescriptorEntityBuilder().build();
  private AllegationPerson victim = new AllegationPersonEntityBuilder().build();
  private AllegationPerson perpetrator = new AllegationPersonEntityBuilder().setFirstName("Jack")
      .setLastName("Jones").setDateOfBirth("2001-09-30").build();
  private Validator validator;

  @Before
  public void setup() {
    allegationSubTypes.add(allegationSubType1);
    allegationSubTypes.add(allegationSubType2);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  @Test
  public void shouldCreateObjectWithDefaultConstructor() {
    Allegation allegation = new Allegation();
    assertNotNull(allegation);
  }

  @Test
  public void domainConstructorTest() {

    Allegation domain = new Allegation(allegationType, createdByScreener, allegationSubTypes,
        dispositionType, rational, legacyDescriptor, victim, perpetrator);
    assertThat(domain.getAllegationType(), is(equalTo(allegationType)));
    assertThat(domain.getCreatedByScreener(), is(equalTo(createdByScreener)));
    assertThat(domain.getAllegationSubType(), is(equalTo(allegationSubTypes)));
    assertThat(domain.getDispositionType(), is(equalTo(dispositionType)));
    assertThat(domain.getRationale(), is(equalTo(rational)));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
    assertThat(domain.getVictim(), is(equalTo(victim)));
    assertThat(domain.getPerpetrator(), is(equalTo(perpetrator)));

  }

  @Test
  public void testPersistentConstructor() {
    gov.ca.cwds.data.persistence.cms.Allegation persistentAllegation =
        new gov.ca.cwds.fixture.AllegationEntityBuilder().build();
    Allegation allegation = new Allegation(persistentAllegation, allegationSubTypes);
    AllegationPerson victim = new AllegationPerson(persistentAllegation.getVictim());
    AllegationPerson perpetrator =
        new AllegationPerson(persistentAllegation.getPerpetrator());
    assertThat(allegation.getAllegationType(),
        is(equalTo(persistentAllegation.getAllegationType())));
    assertThat(allegation.getDispositionType(),
        is(equalTo(persistentAllegation.getAllegationDispositionType())));
    assertThat(allegation.getVictim(), is(equalTo(victim)));
    assertThat(allegation.getPerpetrator(), is(equalTo(perpetrator)));
    assertThat(allegation.getAllegationSubType(), is(equalTo(allegationSubTypes)));
  }

  @Test
  public void testNullPerpetratorClient() {
    gov.ca.cwds.data.persistence.cms.Allegation persistentAllegation =
        new gov.ca.cwds.fixture.AllegationEntityBuilder().setPerpetrator(null).build();
    Allegation allegation = new Allegation(persistentAllegation, allegationSubTypes);
    assertThat(allegation.getPerpetrator(), is(equalTo(null)));
  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    Allegation allegation = new Allegation(allegationType, createdByScreener, allegationSubTypes,
        dispositionType, rational, legacyDescriptor, victim, perpetrator);
    Allegation otherAllegation = new Allegation(allegationType, createdByScreener,
        allegationSubTypes, dispositionType, rational, legacyDescriptor, victim, perpetrator);
    assertEquals(allegation, otherAllegation);

  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    Allegation allegation = new AllegationEntityBuilder().build();
    Allegation otherAllegation =
        new AllegationEntityBuilder().setAllegationType(otherAllegationType).build();
    assertThat(allegation, is(not(equals(otherAllegation))));
  }

  @Test
  public void shouldFindSingleItemInHashSetWhenMultipleItemsAddedWithSameValue() {
    Allegation allegation = new AllegationEntityBuilder().build();
    Allegation otherAllegation = new AllegationEntityBuilder().build();
    Set<Allegation> items = new HashSet<>();
    items.add(allegation);
    items.add(otherAllegation);

    assertTrue(items.contains(allegation));
    assertTrue(items.contains(otherAllegation));
    assertEquals(1, items.size());

  }

  @Test
  public void shouldFindMultipleItemInHashSetWhenItemsHaveWithDifferentValue() {
    Allegation allegation = new AllegationEntityBuilder().build();
    Allegation otherAllegation =
        new AllegationEntityBuilder().setAllegationType(otherAllegationType).build();
    Set<Allegation> items = new HashSet<>();
    items.add(allegation);
    items.add(otherAllegation);

    assertTrue(items.contains(allegation));
    assertTrue(items.contains(otherAllegation));
    assertEquals(2, items.size());
  }

  @Test
  public void shouldAllowNullDispositionType() {
    Allegation allegation = new AllegationEntityBuilder().setDispositionType(null).build();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(allegation);
    Iterator<ConstraintViolation<Allegation>> itr = constraintViolations.iterator();
    // while (itr.hasNext()) {
    // ConstraintViolation<Allegation> cv = itr.next();
    // System.out.println(cv.getMessage());
    // }
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldNotAllowNullAllegationType() {
    Allegation allegation = new AllegationEntityBuilder().setAllegationType(null).build();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(allegation);
    assertEquals(1, constraintViolations.size());
  }

  @Test
  public void shouldAllowNullCreatedByScreener() {
    Allegation allegation = new AllegationEntityBuilder().setCreatedByScreener(null).build();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(allegation);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldAllowEmptyAllegationSubType() {
    Set<AllegationSubType> allegationSubTypes = new HashSet<>();
    Allegation allegation =
        new AllegationEntityBuilder().setAllegationSubTypes(allegationSubTypes).build();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(allegation);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldAllowNullRational() {
    Allegation allegation = new AllegationEntityBuilder().setRational(null).build();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(allegation);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldNotAllowTooLongRational() {

    String longRational = new String(new char[255]).replace('\0', ' ');
    Allegation allegation = new AllegationEntityBuilder().setRational(longRational).build();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(allegation);
    assertEquals(1, constraintViolations.size());
  }

  @Test
  public void shouldNotAllowEmptyVictim() {
    Allegation allegation = new AllegationEntityBuilder().setVictim(null).build();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(allegation);
    assertEquals(1, constraintViolations.size());

  }

  @Test
  public void showAllowEmptyPerpetrator() {
    Allegation allegation = new AllegationEntityBuilder().setPerpetrator(null).build();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(allegation);
    assertEquals(0, constraintViolations.size());

  }

  @Test
  public void shouldAllowNullCmsRecordDescriptor() {
    Allegation allegation = new AllegationEntityBuilder().setLegacyDescriptor(null).build();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(allegation);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  // @Ignore
  public void testSerializedOutput()
      throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
    Allegation allegation = new AllegationEntityBuilder().build();
    final String expected = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(allegation);
    // System.out.println(expected);
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Allegation.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    Allegation allegation = new AllegationEntityBuilder().build();
    assertThat(MAPPER.readValue(fixture("fixtures/domain/investigation/allegation/valid.json"),
        Allegation.class), is(equalTo(allegation)));
  }

}
