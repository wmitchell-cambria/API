package gov.ca.cwds.rest.api.services.screeningparticipant;

/**
 * @author CWDS API Team
 *
 */
public interface ParticipantMapperFactory {

  /**
   * @param tableName
   * @return
   */
  ParticipantMapper create(String tableName);

}
