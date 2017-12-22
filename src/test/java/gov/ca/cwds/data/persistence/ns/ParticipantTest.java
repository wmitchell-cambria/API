package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

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
}
