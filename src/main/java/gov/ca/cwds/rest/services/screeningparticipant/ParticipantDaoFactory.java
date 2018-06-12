package gov.ca.cwds.rest.services.screeningparticipant;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;

/**
 * @author CWDS API Team
 *
 */
@FunctionalInterface
public interface ParticipantDaoFactory {

  /**
   * @param tableName - tableName
   * @return the table dao
   */
  CrudsDao<CmsPersistentObject> create(String tableName);

}
