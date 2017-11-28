package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.PersonResourceBuilder;

@SuppressWarnings("javadoc")
public class PersonPhoneTest {
  private PersonPhone personPhone;
  private PersonPhone otherPersonPhone;
  private PhoneNumber phoneNumber;
  private Person person;
  private gov.ca.cwds.rest.api.domain.Person domainPerson;
  private String userId = "0X5";
  private Long id = (long) 12345;

  @Before
  public void setup() {
    personPhone = new PersonPhone();
    otherPersonPhone = new PersonPhone();
    domainPerson = new PersonResourceBuilder().build();
    person = new Person(domainPerson, userId, userId);
    phoneNumber = new PhoneNumber(id, "1112223333", "home");
  }

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(PersonPhone.class.newInstance(), is(notNullValue()));

  }

  @Test
  public void testConstructor() throws Exception {
    PersonPhone personPhone = new PersonPhone(person, phoneNumber);
    assertEquals(personPhone.getPerson(), person);
    assertEquals(personPhone.getPhoneNumber(), phoneNumber);
  }

  @Test
  public void shouldSetPhoneNumber() throws Exception {
    String newNumber = "2223334444";
    String newType = "cell";
    PhoneNumber newPhoneNumber = new PhoneNumber(id, newNumber, newType);
    PersonPhone personPhone = new PersonPhone(person, phoneNumber);
    personPhone.setPhoneNumber(newPhoneNumber);
    assertEquals(personPhone.getPhoneNumber().getNumber(), newNumber);
    assertEquals(personPhone.getPhoneNumber().getType(), newType);
  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    assertTrue(personPhone.equals(personPhone));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsNull() throws Exception {
    assertFalse(personPhone.equals(null));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsADifferentClass() throws Exception {
    assertFalse(personPhone.equals("A Different Class"));
  }

  @Test
  public void equalsShouldBeFalseWhenThisObjectIsNotEqualsOtherObject() {
    otherPersonPhone.setPerson(new Person());
    personPhone.setPerson(new Person());
    assertFalse(personPhone.equals(otherPersonPhone));
  }

  @Test
  public void equalsShouldBeTrueWhenThisObjectEqualsOtherObject() {
    Person person = new Person();
    otherPersonPhone.setPerson(person);
    personPhone.setPerson(person);
    assertTrue(personPhone.equals(otherPersonPhone));
  }

}
