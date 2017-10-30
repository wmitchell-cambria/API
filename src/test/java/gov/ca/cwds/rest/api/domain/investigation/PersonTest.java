package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.fixture.investigation.InvestigationAddressEntityBuilder;
import gov.ca.cwds.fixture.investigation.PersonEntityBuilder;
import gov.ca.cwds.fixture.investigation.RaceAndEthnicityEntityBuilder;
improt gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class PersonTest {
  private ObjectMapper MAPPER = new ObjectMapper();
  private Validator validator;

  private CmsRecordDescriptor cmsRecordDescriptor;
  private String lastUpdatedBy = "0X5";
  private Date lastUpdatedAt =
      DomainChef.uncookStrictTimestampString("2016-04-27T23:30:14.000-0000");
  private String firstName = "Art";
  private String middleName = "Mike";
  private String lastName = "Griswald";
  private String suffixTitle = "bs";
  private String gender = "M";
  private String birthDate = "1998-10-30";
  private String ssn = "999667777";
  private Set<String> languages = new LinkedHashSet<>();

  private String primaryLanguage = "1253";
  private String secondaryLanguage = "1255";
  private RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicityEntityBuilder().build();
  private Boolean sensitive = false;
  private Boolean sealed = false;
  private Short phoneType = 1111;

  private BigDecimal phoneNumber = new BigDecimal(3219876);
  private CmsRecordDescriptor phoneCmsRecordDescriptor =
      new CmsRecordDescriptor("1234567ABC", "001-2000-3399-415790", "CLIENT_T", "Client");

  private PhoneNumber phone =
      new PhoneNumber(phoneNumber, 3322, phoneType, phoneCmsRecordDescriptor);
  private Set<PhoneNumber> phoneNumbers = new HashSet<PhoneNumber>();

  private Set<String> roles = new HashSet<>();

  private InvestigationAddress address = new InvestigationAddressEntityBuilder().build();
  private Set<InvestigationAddress> addresses = new HashSet<>();

  @Before
  public void setup() {
    roles.add("Mandated reporter");
    addresses.add(address);
    languages.add(primaryLanguage);
    languages.add(secondaryLanguage);
    cmsRecordDescriptor =
        new CmsRecordDescriptor("1234567ABC", "111-222-333-4444", "CLIENT_T", "Client");
    phoneNumbers.add(phone);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);

  }

  @Test
  public void testEmptyConstructorSuccess() {
    Person person = new Person();
    assertNotNull(person);
  }

  @Test
  public void testDomainConstructorSuccess() {
    Person person = new Person(cmsRecordDescriptor, lastUpdatedBy, lastUpdatedAt, firstName,
        middleName, lastName, suffixTitle, gender, birthDate, ssn, languages, raceAndEthnicity,
        sensitive, sealed, phoneNumbers, roles, addresses);
    assertThat(cmsRecordDescriptor, is(equalTo(person.getCmsRecordDescriptor())));
    assertThat(lastUpdatedBy, is(equalTo(person.getLastUpdatedBy())));
    assertThat(lastUpdatedAt, is(equalTo(person.getLastUpdatedAt())));
    assertThat(firstName, is(equalTo(person.getFirstName())));
    assertThat(middleName, is(equalTo(person.getMiddleName())));
    assertThat(lastName, is(equalTo(person.getLastName())));
    assertThat(suffixTitle, is(equalTo(person.getNameSuffix())));
    assertThat(gender, is(equalTo(person.getGender())));
    assertThat(birthDate, is(equalTo(person.getDateOfBirth())));
    assertThat(ssn, is(equalTo(person.getSsn())));
    assertThat(languages, is(equalTo(person.getLanguages())));
    assertThat(raceAndEthnicity, is(equalTo(person.getRaceAndEthnicity())));
    assertThat(sensitive, is(equalTo(person.getSensitive())));
    assertThat(sealed, is(equalTo(person.getSealed())));
    assertThat(phoneNumbers, is(equalTo(person.getPhone())));
    assertThat(roles, is(equalTo(person.getRoles())));
    assertThat(addresses, is(equalTo(person.getAddresses())));

  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    Person person = new PersonEntityBuilder().build();
    Person otherPerson = new PersonEntityBuilder().build();
    assertEquals(person, otherPerson);

  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    Person person = new PersonEntityBuilder().build();
    Person otherPerson = new PersonEntityBuilder().setBirthDate("2001-09-30").build();
    assertThat(person, is(not(equals(otherPerson))));
  }

  @Test
  public void shouldFindMultipleItemInHashSetWhenItemsHaveWithDifferentValue() {
    Person person = new PersonEntityBuilder().build();
    Person otherPerson = new PersonEntityBuilder().setBirthDate("2001-09-30").build();

    Set<Person> items = new HashSet<>();
    items.add(otherPerson);
    items.add(person);

    assertTrue(items.contains(otherPerson));
    assertTrue(items.contains(person));
    assertEquals(2, items.size());
  }

  @Test
  public void shouldFindSingleItemInHashSetWhenMultipleItemsAddedWithSameValue() {
    Person person = new PersonEntityBuilder().build();
    Person otherPerson = new PersonEntityBuilder().build();
    Set<Person> items = new HashSet<>();
    items.add(otherPerson);
    items.add(person);

    assertTrue(items.contains(person));
    assertTrue(items.contains(otherPerson));
    assertEquals(1, items.size());
  }

  @Test void testConstructorWithClientSuccess() {
    Client client = new ClientEntityBuilder().build();
    Set<String> languages = new HashSet<>();
    CmsRecordDescriptor cmsRecordDescriptor = new CmsRecordDescriptorEntityBuilder().setTableDescription("CLIENT_T").build();
    Set<InvestigationAddress> addresses = new HashSet<>();
    Set<PhoneNumber> phoneNumbers = new HashSet<>();
    Set<String> roles = new HashSet<>();
    RaceAndEthnicity raceAndEnthnicity = new RaceAndEthnicityEntityBuilder().build();
    
    languages.add("1253");
    InvestigationAddress address = new InvestigationAddressEntityBuilder().build();
    addresses.add(address);
  }
  
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Person.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }


  @Test
  public void shouldNotAllowBlankFirstName() {
    Person person = new PersonEntityBuilder().setFirstName("").build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    // Iterator<ConstraintViolation<Person>> itr = constraintViolations.iterator();
    // while (itr.hasNext()) {
    // ConstraintViolation<Person> cv = itr.next();
    // System.out.println(cv.getMessage());
    // }
    assertEquals(1, constraintViolations.size());
  }

  @Test
  public void sholdNotAllowNullFirstName() {
    Person person = new PersonEntityBuilder().setFirstName(null).build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(1, constraintViolations.size());

  }

  @Test
  public void shouldNotAllowTooLongFirstName() {
    String longFirstName = new String(new char[21]).replace('\0', ' ');
    Person person = new PersonEntityBuilder().setFirstName(longFirstName).build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(1, constraintViolations.size());
  }

  @Test
  public void shouldAllowBlankMiddleName() {
    Person person = new PersonEntityBuilder().setMiddleName("").build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(0, constraintViolations.size());

  }

  @Test
  public void shouldNotAllowBlankLastName() {
    Person person = new PersonEntityBuilder().setLastName("").build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(1, constraintViolations.size());
  }

  @Test
  public void shouldNotAllowNullLastName() {
    Person person = new PersonEntityBuilder().setLastName(null).build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(1, constraintViolations.size());
  }

  @Test
  public void shouldNotAllowToLongLastName() {
    String longLastName = new String(new char[51]).replace('\0', ' ');
    Person person = new PersonEntityBuilder().setLastName(longLastName).build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(1, constraintViolations.size());
  }

  @Test
  public void shouldNotAllowBlankNameSuffix() {
    Person person = new PersonEntityBuilder().setSuffixTitle("").build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldNotAllowTooLongNameSuffixt() {
    Person person = new PersonEntityBuilder().setSuffixTitle("12345").build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(1, constraintViolations.size());
  }

  @Test
  public void shouldAllowFemaleGender() {
    Person person = new PersonEntityBuilder().setGender("F").build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldAllowMaleGender() {
    Person person = new PersonEntityBuilder().setGender("M").build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldAllowUndefinedGender() {
    Person person = new PersonEntityBuilder().setGender("U").build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldNotAllowInvalidGender() {
    Person person = new PersonEntityBuilder().setGender("X").build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(1, constraintViolations.size());
  }

  @Test
  public void shouldAllowValidDateOfBirth() {
    Person person = new PersonEntityBuilder().setBirthDate("2004-01-30").build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldNotAllowValidDateOfBirth() {
    Person person = new PersonEntityBuilder().setBirthDate("01-30-2004").build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(1, constraintViolations.size());
  }

  @Test
  public void shouldAllowValidSsn() {
    Person person = new PersonEntityBuilder().setSsn("111223333").build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldNotAllowInvalidSsn() {
    Person person = new PersonEntityBuilder().setSsn("1112233334").build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(1, constraintViolations.size());
  }

  @Test
  public void shouldAllowEmptyLanguages() {
    Set<String> languages = new HashSet<>();
    Person person = new PersonEntityBuilder().setLanguages(languages).build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void shouldNotAllowNullSensitive() {
    Person person = new PersonEntityBuilder().setSensitive(null).build();
    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
    assertEquals(1, constraintViolations.size());
  }

  @Test
  @Ignore
  public void testSerilizedOutput()
      throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
    Person display = new PersonEntityBuilder().build();
    final String expected = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(display);
    System.out.println(expected);
  }
}
