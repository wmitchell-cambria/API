package gov.ca.cwds.data.persistence.ns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.PersonEntityBuilder;

@SuppressWarnings("javadoc")
public class PersonRaceTest {
  private PersonRace personRace;
  private PersonRace otherPersonRace;
  private Person person;
  private Race race;
  private Long id;

  @Before
  public void setup() {
    id = (long) 123456;
    personRace = new PersonRace();
    otherPersonRace = new PersonRace();
    person = new PersonEntityBuilder().build();
    race = new Race(id, "12345", "23456");

  }

  @Test
  public void testConstructor() throws Exception {
    PersonRace pRace = new PersonRace(person, race);
    assertEquals(pRace.getPerson(), person);
    assertEquals(pRace.getRace(), race);

  }

  @Test
  public void shouldSetRace() throws Exception {
    String race1 = "23467";
    String race2 = "34567";
    Race newRace = new Race(id, race1, race2);
    PersonRace pRace = new PersonRace(person, race);
    pRace.setRace(newRace);

    assertEquals(pRace.getRace().getRaceType(), race1);
    assertEquals(pRace.getRace().getSubRaceType(), race2);
  }

  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    assertTrue(personRace.equals(personRace));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsNull() throws Exception {
    assertFalse(personRace.equals(null));
  }

  @Test
  public void equalsShouldBeFalseWhenOtherObjectIsADifferentClass() throws Exception {
    assertFalse(personRace.equals("A Different Class"));
  }

  @Test
  public void equalsShouldBeFalseWhenThisObjectIsNotEqualsOtherObject() {
    otherPersonRace.setPerson(new Person());
    personRace.setPerson(new Person());
    assertFalse(personRace.equals(otherPersonRace));
  }

  @Test
  public void equalsShouldBeTrueWhenThisObjectEqualsOtherObject() {
    Person person = new Person();
    otherPersonRace.setPerson(person);
    personRace.setPerson(person);
    assertTrue(personRace.equals(otherPersonRace));
  }
}
