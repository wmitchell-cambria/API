package gov.ca.cwds.rest.services.screeningparticipant;

/**
 * @author CWDS API Team
 *
 */
public interface ParticipantMapperFactory {

  /**
   * @param tableName - tableName
   * @return the tableName
   */
  ParticipantMapper create(String tableName);

}
