package gov.ca.cwds.rest.business.rules.doctool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.business.rules.R02265ChildClientExists;

/**
 * @author CWDS API Team
 *
 */
public class R02265ChildClientExistsTest {


  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /*
   * Load system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @SuppressWarnings("javadoc")
  @Test
  public void shouldDetermineThatClientIsChild() throws Exception {
    Participant participant =
        new ParticipantResourceBuilder().setDateOfBirth("2016-01-01").createParticipant();
    String dateStarted = "2017-01-01";
    R02265ChildClientExists r02265ChildClientExists =
        new R02265ChildClientExists(participant, dateStarted);
    assertThat(r02265ChildClientExists.isValid(), is(equalTo(Boolean.TRUE)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldDetermineThatClientIsNotChild() throws Exception {
    Participant participant =
        new ParticipantResourceBuilder().setDateOfBirth("1990-01-01").createParticipant();
    String dateStarted = "2017-01-01";
    R02265ChildClientExists r02265ChildClientExists =
        new R02265ChildClientExists(participant, dateStarted);
    assertThat(r02265ChildClientExists.isValid(), is(equalTo(Boolean.FALSE)));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldSetClientChildIndicatorTrue() throws Exception {
    Participant participant =
        new ParticipantResourceBuilder().setDateOfBirth("2016-01-01").createParticipant();
    String dateStarted = "2017-01-01";
    Boolean childClient = new R02265ChildClientExists(participant, dateStarted).isValid();
    Client client =
        Client.createWithDefaults(participant, dateStarted, "M", (short) 0, childClient);
    assertThat(client.getChildClientIndicatorVar(), is(equalTo(Boolean.TRUE)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldSetClientChildIndicatorFalse() throws Exception {
    Participant participant =
        new ParticipantResourceBuilder().setDateOfBirth("1990-01-01").createParticipant();
    String dateStarted = "2017-01-01";
    Boolean childClient = new R02265ChildClientExists(participant, dateStarted).isValid();
    Client client =
        Client.createWithDefaults(participant, dateStarted, "M", (short) 0, childClient);
    assertThat(client.getChildClientIndicatorVar(), is(equalTo(Boolean.FALSE)));
  }

  @Test
  public void shouldDetermineThatClientIsNotChildWhenDOBNull() throws Exception {
	    Participant participant =
	            new ParticipantResourceBuilder().setDateOfBirth(null).createParticipant();
	        String dateStarted = "2017-01-01";
	        R02265ChildClientExists r02265ChildClientExists =
	            new R02265ChildClientExists(participant, dateStarted);
	        assertThat(r02265ChildClientExists.isValid(), is(equalTo(Boolean.FALSE)));
  }
}
