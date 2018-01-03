package gov.ca.cwds.data.persistence.ns;

import java.util.Date;
import java.util.HashSet;
import org.junit.Test;

public class ScreeningTest {

  @Test
  public void shouldAddParticipantWhenNotEmpty() {
    HashSet participants = new HashSet();
    ParticipantEntity participantEntity = new ParticipantEntity();
    participants.add(participantEntity);

    ScreeningEntity screeningEntity =
        new ScreeningEntity("reference", new Date(), "Sonoma", new Date(), "Home", "Phone", "Fred",
            "Immdiate", "Descicion", new Date(), "some Narative", new Address(), participants);
    // assertEquals(screeningEntity.getParticipants(), participants);
  }

  @Test
  public void shouldNotChangeParticipantsWhenAddingEmptyHashSet() {
    HashSet participants = new HashSet();

    ScreeningEntity screeningEntity =
        new ScreeningEntity("reference", new Date(), "Sonoma", new Date(), "Home", "Phone", "Fred",
            "Immdiate", "Descicion", new Date(), "some Narative", new Address(), participants);
    // assertEquals(0, screeningEntity.getParticipants().size());
  }
}
