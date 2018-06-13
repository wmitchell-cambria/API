package gov.ca.cwds.rest.services.screeningparticipant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 * 
 * @param <P> - CmsPersistentObject
 *
 */
public class ParticipantMapperFactoryImpl<P extends CmsPersistentObject>
    implements ParticipantMapperFactory<P> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantMapperFactoryImpl.class);

  private static final String SOURCE_PACKAGE = "gov.ca.cwds.rest.services.screeningparticipant.";

  @Inject
  private Injector injector;

  @Override
  public ParticipantMapper<P> create(String tableName) {
    ParticipantMapper<P> mapper;
    LegacyDaoMapperEnum legacyDaoMapperEnum = LegacyDaoMapperEnum.findByTableName(tableName);
    if (legacyDaoMapperEnum == null) {
      LOGGER.error("Dao is not found with the given {}", tableName);
      throw new ServiceException();
    }

    String name = SOURCE_PACKAGE + legacyDaoMapperEnum.getTranformerName();
    try {
      @SuppressWarnings("unchecked")
      Class<ParticipantMapper<P>> participantMapper =
          (Class<ParticipantMapper<P>>) Class.forName(name);
      mapper = injector.getInstance(participantMapper);
    } catch (ClassNotFoundException e) {
      LOGGER.error("Unable to load the class {}", name);
      throw new ServiceException(e);
    }
    return mapper;
  }

  /**
   * @param injector - injector
   */
  public void setInjector(Injector injector) {
    this.injector = injector;
  }

}
