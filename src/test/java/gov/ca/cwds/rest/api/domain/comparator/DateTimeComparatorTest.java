package gov.ca.cwds.rest.api.domain.comparator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.cms.Client;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class DateTimeComparatorTest {

  DateTimeComparator comparator;
  Participant participant;
  Client client;
  LegacyDescriptor legacyDescriptor;
  DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

  /**
   * 
   */
  @Before
  public void setup() {
    comparator = new DateTimeComparator();

  }

  @Test
  public void shouldCompareEqualDatesAsTrue() {
    participant = createParticipant("2016-11-25T14:32:23.123-0700");
    client = createClient("2016-11-25T14:32:23.123-0700");

    assertTrue(comparator.compare(participant.getLegacyDescriptor().getLastUpdated(),
        client.getLastUpdatedTime()));
  }

  @Test
  public void shouldCompareDatesWithSecondResolution() {
    participant = createParticipant("2016-11-25T14:32:00.999-0700");
    client = createClient("2016-11-25T14:32:00.001-0700");

    assertTrue(comparator.compare(participant.getLegacyDescriptor().getLastUpdated(),
        client.getLastUpdatedTime()));
  }

  @Test
  public void shouldNotEvaluateStringsWithDifferentSecondsAsEqual() {
    participant = createParticipant("2016-11-25T14:32:00.123-0700");
    client = createClient("2016-11-25T14:32:59.123-0700");

    assertFalse(comparator.compare(participant.getLegacyDescriptor().getLastUpdated(),
        client.getLastUpdatedTime()));
  }

  @Test
  public void shouldEvaluateDateWithDifferentTimeZoneAsEqual() {
    participant = createParticipant("2016-11-25T14:32:00.000-0700");
    client = createClient("2016-11-25T14:32:59.000-0700");

    assertFalse(comparator.compare(participant.getLegacyDescriptor().getLastUpdated(),
        client.getLastUpdatedTime()));
  }

  @Test
  public void shouldNotEvaluateStringsWithDifferentDayAsEqual() {
    participant = createParticipant("2016-11-02T14:32:00.123-0700");
    client = createClient("2016-11-01T14:32:00.123-0700");

    assertFalse(comparator.compare(participant.getLegacyDescriptor().getLastUpdated(),
        client.getLastUpdatedTime()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionIfDateCantBeParsed() {
    participant = createParticipant("Bad.Date");
    client = createClient("2016-11-01-14.32.00.123");

    assertFalse(comparator.compare(participant.getLegacyDescriptor().getLastUpdated(),
        client.getLastUpdatedTime()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIndexOutOfBoundsExceptionIfNoMillisecondsArefound() {
    participant = createParticipant("BadDate");
    client = createClient("2016-11-01-14.32.00.123");

    assertFalse(comparator.compare(participant.getLegacyDescriptor().getLastUpdated(),
        client.getLastUpdatedTime()));
  }

  private Client createClient(String clientDate) {
    DateTime date = dateTimeFormatter.parseDateTime(clientDate);
    return new ClientResourceBuilder().setLastUpdateTime(date).build();
  }

  private Participant createParticipant(String participantDate) {
    DateTime date = dateTimeFormatter.parseDateTime(participantDate);
    legacyDescriptor = new LegacyDescriptor("DFGHJYTR", "", date, "", "");
    return new ParticipantResourceBuilder().setLegacyDescriptor(legacyDescriptor)
        .createParticipant();
  }
}
