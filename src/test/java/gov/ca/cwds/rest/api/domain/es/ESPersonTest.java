package gov.ca.cwds.rest.api.domain.es;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.search.SearchHit;
import org.junit.Test;

import gov.ca.cwds.fixture.PersonEntityBuilder;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Ethnicity;
import gov.ca.cwds.rest.api.domain.Language;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.Race;
import gov.ca.cwds.rest.api.domain.es.ESPerson.ESColumn;

public class ESPersonTest {

  private String id = "1234567ABC";
  private String firstName = "firstname";
  private String middleName = "middlename";
  private String lastName = "lastename";
  private String suffix = "jr";
  private String gender = "M";
  private String birthDate = "2001-09-01";
  private String ssn = "123456789";
  private LegacyDescriptor legacyDescriptor = new LegacyDescriptor();
  private Address address =
      new Address("", "", "123 Main", "Sacramento", 1828, "95757", 32, legacyDescriptor);
  private Set<Address> addresses = new HashSet<>();
  private PhoneNumber phoneNumber = new PhoneNumber("408-641-0287", "cell");
  private Set<PhoneNumber> phoneNumbers = new HashSet<>();
  private Language language = new Language("English");
  private Set<Language> languages = new HashSet<>();
  private Race race = new Race("White", "European");
  private Set<Race> races = new HashSet<>();
  private Ethnicity ethnicity = new Ethnicity("Unknow", "South American");
  private Set<Ethnicity> ethnicities = new HashSet<>();
  private String sourceType = "gov.ca.cwds.rest.api.persistence.cms.OtherClientName";
  private String sourceJson = "test Json";

  @Test
  public void shouldCreateESPerson() throws Exception {
    ESPerson esPerson = new ESPerson(id, firstName, middleName, lastName, suffix, gender, birthDate,
        ssn, addresses, phoneNumbers, languages, races, ethnicities);
    assertThat(esPerson.getId(), is(equalTo(id)));
    assertThat(esPerson.getFirstName(), is(equalTo(firstName)));
    assertThat(esPerson.getMiddleName(), is(equalTo(middleName)));
    assertThat(esPerson.getLastName(), is(equalTo(lastName)));
    assertThat(esPerson.getNameSuffix(), is(equalTo(suffix)));
    assertThat(esPerson.getGender(), is(equalTo(gender)));
    assertThat(esPerson.getBirthDate(), is(equalTo(birthDate)));
    assertThat(esPerson.getSsn(), is(equalTo(ssn)));
  }

  @Test
  public void shouldCreateESPersonFromOverloadConstructor() throws Exception {
    ESPerson esPerson = new ESPerson(id, firstName, middleName, lastName, suffix, gender, birthDate,
        ssn, sourceType, sourceJson, addresses, phoneNumbers, languages, races, ethnicities);
    assertThat(esPerson.getSourceType(), is(equalTo(sourceType)));
    assertThat(esPerson.getSourceJson(), is(equalTo(sourceJson)));
  }

  @Test
  public void shouldCreateESPersonFromPerson() throws Exception {
    gov.ca.cwds.data.persistence.ns.Person person = new PersonEntityBuilder().build();
    ESPerson esPerson = new ESPerson(person);
    assertThat(esPerson.getId(), is(equalTo(person.getId().toString())));
  }

  @Test
  public void type() throws Exception {
    assertThat(ESPerson.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    String id = null;
    String firstName = null;
    String middleName = null;
    String lastName = null;
    String nameSuffix = null;
    String gender = null;
    String birthDate = null;
    String ssn = null;
    Set<Address> address = null;
    Set<PhoneNumber> phoneNumber = null;
    Set<Language> language = null;
    Set<Race> race = null;
    Set<Ethnicity> ethnicity = null;
    ESPerson target = new ESPerson(id, firstName, middleName, lastName, nameSuffix, gender,
        birthDate, ssn, address, phoneNumber, language, race, ethnicity);
    assertThat(target, notNullValue());
  }

  @Test
  public void makeESPerson_A$SearchHit() throws Exception {
    SearchHit hit = mock(SearchHit.class);
    ESPerson actual = ESPerson.makeESPerson(hit);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void pullCol_A$Map$Object() throws Exception {
    final Map<String, Object> m = new HashMap<String, Object>();
    final ESColumn f = ESColumn.FIRST_NAME;
    Object actual = ESPerson.pullCol(m, f);
    Object expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void trim_A$String() throws Exception {
    String s = null;
    String actual = ESPerson.trim(s);
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_A$() throws Exception {
    String id = null;
    String firstName = null;
    String middleName = null;
    String lastName = null;
    String nameSuffix = null;
    String gender = null;
    String birthDate = null;
    String ssn = null;
    Set<Address> address = null;
    Set<PhoneNumber> phoneNumber = null;
    Set<Language> language = null;
    Set<Race> race = null;
    Set<Ethnicity> ethnicity = null;
    ESPerson target = new ESPerson(id, firstName, middleName, lastName, nameSuffix, gender,
        birthDate, ssn, address, phoneNumber, language, race, ethnicity);
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSourceType_A$() throws Exception {
    String id = null;
    String firstName = null;
    String middleName = null;
    String lastName = null;
    String nameSuffix = null;
    String gender = null;
    String birthDate = null;
    String ssn = null;
    Set<Address> address = null;
    Set<PhoneNumber> phoneNumber = null;
    Set<Language> language = null;
    Set<Race> race = null;
    Set<Ethnicity> ethnicity = null;
    ESPerson target = new ESPerson(id, firstName, middleName, lastName, nameSuffix, gender,
        birthDate, ssn, address, phoneNumber, language, race, ethnicity);
    String actual = target.getSourceType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSourceJson_A$() throws Exception {
    String id = null;
    String firstName = null;
    String middleName = null;
    String lastName = null;
    String nameSuffix = null;
    String gender = null;
    String birthDate = null;
    String ssn = null;
    Set<Address> address = null;
    Set<PhoneNumber> phoneNumber = null;
    Set<Language> language = null;
    Set<Race> race = null;
    Set<Ethnicity> ethnicity = null;
    ESPerson target = new ESPerson(id, firstName, middleName, lastName, nameSuffix, gender,
        birthDate, ssn, address, phoneNumber, language, race, ethnicity);
    String actual = target.getSourceJson();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSourceObj_A$() throws Exception {
    String id = null;
    String firstName = null;
    String middleName = null;
    String lastName = null;
    String nameSuffix = null;
    String gender = null;
    String birthDate = null;
    String ssn = null;
    Set<Address> address = null;
    Set<PhoneNumber> phoneNumber = null;
    Set<Language> language = null;
    Set<Race> race = null;
    Set<Ethnicity> ethnicity = null;
    ESPerson target = new ESPerson(id, firstName, middleName, lastName, nameSuffix, gender,
        birthDate, ssn, address, phoneNumber, language, race, ethnicity);
    Object actual = target.getSourceObj();
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
