package gov.ca.cwds.rest.services.screeningparticipant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;

import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 *
 */
public class ParticipantMapperFactoryImpl implements ParticipantMapperFactory {
  private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantMapperFactoryImpl.class);

  private static final String SOURCE_PACKAGE = "gov.ca.cwds.rest.services.screeningparticipant.";

  @Inject
  private Injector injector;

  @Override
  public ParticipantMapper create(String tableName) {
    ParticipantMapper mapper;
    LegacyDaoMapperEnum legacyDaoMapperEnum = LegacyDaoMapperEnum.findByTableName(tableName);
    if (legacyDaoMapperEnum == null) {
      LOGGER.error("Dao is not found with the given {}", tableName);
      throw new ServiceException();
    }

    String name = SOURCE_PACKAGE + legacyDaoMapperEnum.getTranformerName();
    try {
      @SuppressWarnings("unchecked")
      Class<ParticipantMapper> participantMapper = (Class<ParticipantMapper>) Class.forName(name);
      mapper = injector.getInstance(participantMapper);
    } catch (ClassNotFoundException e) {
      LOGGER.error("Unable to load the class {}", name);
      throw new ServiceException(e);
    }
    return mapper;
  }

}
