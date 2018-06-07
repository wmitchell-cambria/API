package gov.ca.cwds.rest.api.services.screeningparticipant;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;

/**
 * @author CWDS API Team
 *
 */
public interface ParticipantDaoFactory {

  /**
   * @param tableName
   * @return
   */
  CrudsDao<CmsPersistentObject> create(String tableName);

}
