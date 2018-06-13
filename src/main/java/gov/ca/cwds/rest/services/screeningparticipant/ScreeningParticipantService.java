package gov.ca.cwds.rest.services.screeningparticipant;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.services.ParticipantIntakeApiService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * Business layer object to work on ParticipantIntakeApi
 * 
 * @author CWDS API Team
 */
public class ScreeningParticipantService
    implements TypedCrudsService<String, ParticipantIntakeApi, ParticipantIntakeApi> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningParticipantService.class);

  @Inject
  private ScreeningDao screeningDao;

  @Inject
  private ParticipantIntakeApiService participantIntakeApiService;

  @Inject
  private ParticipantDaoFactoryImpl participantDaoFactory;

  @Inject
  private ParticipantMapperFactoryImpl participantMapperFactoryImpl;

  @Override
  @UnitOfWork(value = "cms")
  public ParticipantIntakeApi create(ParticipantIntakeApi incomingParticipantIntakeApi) {
    if (StringUtils.isBlank(incomingParticipantIntakeApi.getScreeningId())) {
      LOGGER.error("Screening is required to create the particpant {}",
          incomingParticipantIntakeApi.getScreeningId());
      throw new ServiceException();
    }
    isScreeningExists(incomingParticipantIntakeApi);
    ParticipantIntakeApi participantIntakeApi = null;
    LegacyDescriptor legacyDescriptor = incomingParticipantIntakeApi.getLegacyDescriptor();

    if (legacyDescriptor != null && StringUtils.isNotBlank(legacyDescriptor.getId())
        && StringUtils.isNotBlank(legacyDescriptor.getTableName())) {
      participantIntakeApi =
          createParticipant(legacyDescriptor.getId(), legacyDescriptor.getTableName());
      participantIntakeApi.setScreeningId(incomingParticipantIntakeApi.getScreeningId());
      return participantIntakeApiService.create(participantIntakeApi);
    } else {
      return participantIntakeApiService.create(incomingParticipantIntakeApi);
    }
  }

  private ParticipantIntakeApi createParticipant(String id, String tableName) {
    CmsPersistentObject persistentObject;
    ParticipantMapper participantMapper;
    CrudsDao<CmsPersistentObject> crudsDaoObject = participantDaoFactory.create(tableName);
    if ((persistentObject = crudsDaoObject.find(id)) != null) {
      participantMapper = participantMapperFactoryImpl.create(tableName);
      return participantMapper.tranform(persistentObject);
    } else {
      LOGGER.error("Object is not found with the given identifier {}", id);
      throw new ServiceException();
    }
  }

  private void isScreeningExists(ParticipantIntakeApi participantIntakeApi) {
    if (screeningDao.find(participantIntakeApi.getScreeningId()) == null) {
      LOGGER.error("Screening not found {}", participantIntakeApi.getScreeningId());
      throw new EntityNotFoundException();
    }
  }

  @Override
  public ParticipantIntakeApi delete(String id) {
    return null;
  }

  @Override
  public ParticipantIntakeApi find(String id) {
    return null;
  }

  @Override
  public ParticipantIntakeApi update(String id, ParticipantIntakeApi request) {
    return null;
  }

  /**
   * @param screeningDao - screeningDao
   */
  public void setScreeningDao(ScreeningDao screeningDao) {
    this.screeningDao = screeningDao;
  }

  /**
   * @param participantIntakeApiService - participantIntakeApiService
   */
  public void setParticipantIntakeApiService(
      ParticipantIntakeApiService participantIntakeApiService) {
    this.participantIntakeApiService = participantIntakeApiService;
  }

  /**
   * @param participantDaoFactory - participantDaoFactory
   */
  public void setParticipantDaoFactory(ParticipantDaoFactoryImpl participantDaoFactory) {
    this.participantDaoFactory = participantDaoFactory;
  }

  /**
   * @param participantMapperFactoryImpl - participantMapperFactoryImpl
   */
  public void setParticipantMapperFactoryImpl(
      ParticipantMapperFactoryImpl participantMapperFactoryImpl) {
    this.participantMapperFactoryImpl = participantMapperFactoryImpl;
  }

}
