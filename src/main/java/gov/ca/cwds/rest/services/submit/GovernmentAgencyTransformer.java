package gov.ca.cwds.rest.services.submit;

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
  public GovernmentAgency transform(GovernmentAgencyIntake governmentAgencyIntakes) {
    return new GovernmentAgency(governmentAgencyIntakes.getCode(),
        governmentAgencyIntakes.getType());
  }

}
