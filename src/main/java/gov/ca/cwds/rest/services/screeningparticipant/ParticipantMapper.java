package gov.ca.cwds.rest.services.screeningparticipant;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;

/**
 * @author CWDS API Team
 *
 */
public interface ParticipantMapper {

  /**
   * @param object
   * @return
   */
  ParticipantIntakeApi tranform(CmsPersistentObject object);

}
