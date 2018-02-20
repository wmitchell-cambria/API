package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.PersonEntityBuilder;

public class PersonLanguageTest {

  PersonLanguage personLanguage;
  PersonLanguage otherPersonLanguage;
  Person person;
  Language language;
  private Long languageId = 1223L;
  private Long newLanguageId = 2334L;
  private String languageCodeId = "4321";
  private String newLanguageCodeId = "5432";

    @Before
    public void setup(){
      personLanguage = new PersonLanguage();
      otherPersonLanguage = new PersonLanguage();
      person = new PersonEntityBuilder().build();
      language = new Language(languageId, languageCodeId);

    }

	@Test
	public void testEmptyConstructor() throws Exception {
	    assertThat(PersonLanguage.class.newInstance(), is(notNullValue()));		
	}

	@Test
	public void testConstructor() throws Exception {
	  PersonLanguage personLanguage = new PersonLanguage(person, language);
	  assertThat(personLanguage.getPerson(), is(equalTo(person)));
	  assertThat(personLanguage.getLanguage(), is(equalTo(language)));
	}
	
	@Test
	public void shouldModifyValuesUsingSetters() throws Exception {
	  
	  Person newPerson = new PersonEntityBuilder().setLastName("new last").setFirstName("new first").build();
	  PersonLanguage personLanguage = new PersonLanguage(person, language);
	  
	  Language newLanguage = new Language(newLanguageId, newLanguageCodeId);
	  
	  personLanguage.setPerson(newPerson);
	  assertThat(personLanguage.getPerson().getFirstName(), is(equalTo("new first")));
	  assertThat(personLanguage.getPerson().getLastName(), is(equalTo("new last")));	  
	  personLanguage.setLanguage(newLanguage);
	  assertThat(personLanguage.getLanguage().getLanguageCodeId(), is(equalTo(newLanguageCodeId)));
	  assertThat(personLanguage.getLanguage().getLanguageId(), is(equalTo(newLanguageId)));
	}
	
    @Test
    public void equalsShouldBeTrueWhenSameObject() throws Exception {
      assertTrue(personLanguage.equals(personLanguage));
    }

    @Test
    public void equalsShouldBeFalseWhenOtherObjectIsNull() throws Exception {
      assertFalse(personLanguage.equals(null));
    }

    @Test
    public void equalsShouldBeFalseWhenOtherObjectIsADifferentClass() throws Exception {
      assertFalse(personLanguage.equals("A Different Class"));
    }

  @Test
  public void equalsShouldBeFalseWhenThisObjectIsNotEqualsOtherObject(){
    otherPersonLanguage.setPerson(new Person());
    personLanguage.setPerson(new Person());
    assertFalse(personLanguage.equals(otherPersonLanguage));
  }

    @Test
    public void equalsShouldBeTrueWhenThisObjectEqualsOtherObject(){
      Person person = new Person();
      otherPersonLanguage.setPerson(person);
      personLanguage.setPerson(person);
      assertTrue(personLanguage.equals(otherPersonLanguage));
    }
}