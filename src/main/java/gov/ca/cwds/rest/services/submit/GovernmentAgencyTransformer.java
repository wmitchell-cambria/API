package gov.ca.cwds.rest.services.submit;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.GovernmentAgency;
import gov.ca.cwds.rest.api.domain.GovernmentAgencyIntake;

/**
 * @author CWDS API Team
 *
 */
public class GovernmentAgencyTransformer {

  /**
   * @param governmentAgencyIntakes - governmentAgencyIntakes
   * @return the governmentAgencies
   */
  public Set<GovernmentAgency> transform(Set<GovernmentAgencyIntake> governmentAgencyIntakes) {
    Set<GovernmentAgency> governmentAgencies = new HashSet<>();
    for (GovernmentAgencyIntake governmentAgencyIntake : governmentAgencyIntakes) {
      governmentAgencies.add(
          new GovernmentAgency(governmentAgencyIntake.getCode(), governmentAgencyIntake.getType()));
    }
    return governmentAgencies;

  }

}
