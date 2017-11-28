package gov.ca.cwds.data.persistence.ns;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import gov.ca.cwds.fixture.ParticipantResourceBuilder;

@SuppressWarnings("javadoc")
public class ParticipantTest {

  private String userId = "0X5";
  private Long id = 12345L;
  private String firstName = "John";
  private String middleName = "";
  private String lastName = "Smitties";
  private String gender = "M";
  private Date birthDate = new Date();
  private String ssn = "222331111";
  private Set<PersonAddress> personAddress = new HashSet<>();
  private Set<PersonPhone> personPhone = new HashSet<>();
  private Set<PersonLanguage> personLanguage = new HashSet<>();
  private Set<PersonEthnicity> personEthnicity = new HashSet<>();
  private Set<PersonRace> personRace = new HashSet<>();

  private long personId = 23456;
  private long screeningId = 23445;


  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(Participant.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void testConstructor() {
    Participant participant = new Participant(personId, screeningId);
    assertEquals(participant.getPersonId(), personId);
    assertEquals(participant.getHotlineContactId(), screeningId);
  }

  @Test
  public void testConstructorUsingDomain() {
    gov.ca.cwds.rest.api.domain.Participant domain =
        new ParticipantResourceBuilder().createParticipant();
    Participant participant = new Participant(domain, userId, userId);
    assertEquals(participant.getPrimaryKey(), participant.getId());
    assertEquals(participant.getHotlineContactId(), domain.getScreeningId());
  }

  @Test
  public void testConstructorUsingDomainWithPerson() {
    gov.ca.cwds.rest.api.domain.Participant domain =
        new ParticipantResourceBuilder().createParticipant();
    Person person = new Person(id, firstName, middleName, lastName, gender, birthDate, ssn,
        personAddress, personPhone, personLanguage, personRace, personEthnicity);

    Participant participant = new Participant(domain, userId, userId, person);
    assertEquals(participant.getHotlineContactId(), domain.getScreeningId());
    assertEquals(participant.getPerson(), person);
  }
}
