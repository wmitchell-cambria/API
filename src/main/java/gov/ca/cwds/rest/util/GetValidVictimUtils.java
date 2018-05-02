package gov.ca.cwds.rest.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.Role;

/**
 * This static class is used to get the valid victim's from the participants, later we can add more
 * methods to get the remaining like perpetrator, reporters.
 * 
 * @author CWDS API team
 *
 */
public class GetValidVictimUtils {

  /**
   * 
   */
  private GetValidVictimUtils() {
    // no-opt
  }

  /**
   * @param participants - participants
   * @return the valid victims
   */
  public static Collection<Participant> getVictims(Collection<Participant> participants) {
    List<Participant> victims = new ArrayList<>();
    if (participants != null) {
      return participants.stream()
          .filter(value -> value.getRoles().contains(Role.VICTIM_ROLE.getType()))
          .collect(Collectors.toList());
    }
    return victims;
  }

}
