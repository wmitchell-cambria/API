package gov.ca.cwds.rest.services.submit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
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

  private Map<String, IntakeLov> nsCodeToNsLovMap;
  ParticipantIntakeApi nsParticipant;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    IntakeLov intakeLovEnglish = mock(IntakeLov.class);
    when(intakeLovEnglish.getLegacySystemCodeId()).thenReturn(new Long(1253));
    IntakeLov intakeLovRussian = mock(IntakeLov.class);
    when(intakeLovRussian.getLegacySystemCodeId()).thenReturn(new Long(1271));
    IntakeLov intakeLovStateCa = mock(IntakeLov.class);
    when(intakeLovStateCa.getLegacySystemCodeId()).thenReturn(new Long(1828));
    nsCodeToNsLovMap = new HashMap<String, IntakeLov>();
    nsCodeToNsLovMap.put("English", intakeLovEnglish);
    nsCodeToNsLovMap.put("Russian", intakeLovRussian);
    nsCodeToNsLovMap.put("CA", intakeLovStateCa);
    nsParticipant = new ParticipantIntakeApiResourceBuilder().setLegacyDescriptor(null).setRaces("")
        .setEthnicity("").build();
  }

  @Test
  public void transformConvertsParticipantsIntakeApiToParticipants() {
    Participant participant =
        new ParticipantResourceBuilder().setRaceAndEthnicity(new RaceAndEthnicity())
            .setAddresses(new HashSet<>()).createParticipant();
    Set<ParticipantIntakeApi> nsParticipants = Stream.of(nsParticipant).collect(Collectors.toSet());
    Set<Participant> expected = Stream.of(participant).collect(Collectors.toSet());
    Set<Participant> actual =
        new ParticipantsTransformer().transform(nsParticipants, nsCodeToNsLovMap);
    assertEquals(actual, expected);
  }


}
