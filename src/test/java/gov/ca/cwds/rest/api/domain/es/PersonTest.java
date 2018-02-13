package gov.ca.cwds.rest.api.domain.es;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class PersonTest {
	private String id = "1234567ABC";
	  private String firstName = "firstname";
	  private String lastName = "lastename";
	  private String gender = "M";
	  private String birthDate = "2001-09-01";
	  private String ssn = "123456789";
	  private String sourceType = "gov.ca.cwds.rest.api.persistence.cms.OtherClientName";
	  private String sourceJson = "test Json";

@Test
public void shouldCreatePerson() {
	Person person = new Person(id, firstName, lastName, gender, birthDate, ssn, sourceType, sourceJson);
	assertThat(person.getId(), is(equalTo(id)));
	assertThat(person.getFirstName(), is(equalTo(firstName)));
	assertThat(person.getLastName(), is(equalTo(lastName)));
	assertThat(person.getGender(), is(equalTo(gender)));
	assertThat(person.getDateOfBirth(), is(equalTo(birthDate)));
	assertThat(person.getSsn(), is(equalTo(ssn)));
	assertThat(person.getType(), is(equalTo(sourceType)));
	assertThat(person.getSource(), is(equalTo(sourceJson)));	
	
			
}

@Test
public void shouldCreatePersonFromDefaultConstructor() throws Exception {
    assertThat(Person.class.newInstance(), is(notNullValue()));

}
}
