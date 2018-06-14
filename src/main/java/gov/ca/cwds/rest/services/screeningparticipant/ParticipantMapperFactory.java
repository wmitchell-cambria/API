package gov.ca.cwds.rest.services.screeningparticipant;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;

/**
 * @author CWDS API Team
 * 
 * @param <P> - CmsPersistentObject
 *
 */
@FunctionalInterface
public interface ParticipantMapperFactory<P extends CmsPersistentObject> {

  /**
   * @param tableName - tableName
   * @return the tableName
   */
  ParticipantMapper<P> create(String tableName);

}
