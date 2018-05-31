package gov.ca.cwds.rest.services.submit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.fixture.ParticipantIntakeApiResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ParticipantsTransformerTest {

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();

  ParticipantIntakeApi nsParticipant;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    nsParticipant = new ParticipantIntakeApiResourceBuilder().setLegacyDescriptor(null).setRaces("")
        .setEthnicity("").build();
  }

  @Test
  public void transformConvertsParticipantsIntakeApiToParticipants() {
    Participant participant = new ParticipantResourceBuilder().setCsecs(new ArrayList<>())
        .setRaceAndEthnicity(new RaceAndEthnicity()).setAddresses(new HashSet<>())
        .createParticipant();
    Set<ParticipantIntakeApi> nsParticipants = Stream.of(nsParticipant).collect(Collectors.toSet());
    Set<Participant> expected = Stream.of(participant).collect(Collectors.toSet());
    Set<Participant> actual = new ParticipantsTransformer().transform(nsParticipants);
    assertEquals(actual, expected);
  }

  @Test
  public void testToConvertOnlyPrimartLanaguageIsPresent() {
    ParticipantIntakeApi participantIntakeApi =
        new ParticipantIntakeApiResourceBuilder().setLanguages(Arrays.asList("English"))
            .setLegacyDescriptor(null).setRaces("").setEthnicity("").build();
    Participant participant = new ParticipantResourceBuilder().setSecondaryLanguage((short) 0)
        .setCsecs(new ArrayList<>()).setRaceAndEthnicity(new RaceAndEthnicity())
        .setAddresses(new HashSet<>()).createParticipant();
    Set<ParticipantIntakeApi> nsParticipants =
        Stream.of(participantIntakeApi).collect(Collectors.toSet());
    Set<Participant> expected = Stream.of(participant).collect(Collectors.toSet());
    Set<Participant> actual = new ParticipantsTransformer().transform(nsParticipants);
    assertEquals(actual, expected);
  }

  @Test
  public void testToConvertSealed() {
    ParticipantIntakeApi participantIntakeApi = new ParticipantIntakeApiResourceBuilder()
        .setSealed(true).setLegacyDescriptor(null).setRaces("").setEthnicity("").build();
    Participant participant = new ParticipantResourceBuilder().setSensitivityIndicator("R")
        .setCsecs(new ArrayList<>()).setRaceAndEthnicity(new RaceAndEthnicity())
        .setAddresses(new HashSet<>()).createParticipant();
    Set<ParticipantIntakeApi> nsParticipants =
        Stream.of(participantIntakeApi).collect(Collectors.toSet());
    Set<Participant> expected = Stream.of(participant).collect(Collectors.toSet());
    Set<Participant> actual = new ParticipantsTransformer().transform(nsParticipants);
    assertEquals(actual, expected);
  }

  @Test
  public void testToConvertSensitiveIndicator() {
    ParticipantIntakeApi participantIntakeApi = new ParticipantIntakeApiResourceBuilder()
        .setSensitive(true).setLegacyDescriptor(null).setRaces("").setEthnicity("").build();
    Participant participant = new ParticipantResourceBuilder().setSensitivityIndicator("S")
        .setCsecs(new ArrayList<>()).setRaceAndEthnicity(new RaceAndEthnicity())
        .setAddresses(new HashSet<>()).createParticipant();
    Set<ParticipantIntakeApi> nsParticipants =
        Stream.of(participantIntakeApi).collect(Collectors.toSet());
    Set<Participant> expected = Stream.of(participant).collect(Collectors.toSet());
    Set<Participant> actual = new ParticipantsTransformer().transform(nsParticipants);
    assertEquals(actual, expected);
  }

}
