package gov.ca.cwds.rest.services;

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
import gov.ca.cwds.rest.api.services.screeningparticipant.ParticipantDaoFactoryImpl;
import gov.ca.cwds.rest.api.services.screeningparticipant.ParticipantMapper;
import gov.ca.cwds.rest.api.services.screeningparticipant.ParticipantMapperFactoryImpl;

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
  public ParticipantIntakeApi create(ParticipantIntakeApi incomingParticipantIntakeApi) {
    if (StringUtils.isNotBlank(incomingParticipantIntakeApi.getScreeningId())) {
      // isScreeningExists(incomingParticipantIntakeApi);
      ParticipantIntakeApi participantIntakeApi = null;
      LegacyDescriptor legacyDescriptor = incomingParticipantIntakeApi.getLegacyDescriptor();

      if (legacyDescriptor != null && StringUtils.isNotBlank(legacyDescriptor.getId())
          && StringUtils.isNotBlank(legacyDescriptor.getTableName())) {
        participantIntakeApi =
            createParticipant(legacyDescriptor.getId(), legacyDescriptor.getTableName());
        participantIntakeApi.setScreeningId(incomingParticipantIntakeApi.getScreeningId());
        return participantIntakeApiService.create(participantIntakeApi);
      } else {
        return participantIntakeApiService.create(participantIntakeApi);
      }
    } else {
      LOGGER.error("Screening is required to create the particpant {}",
          incomingParticipantIntakeApi.getScreeningId());
      throw new ServiceException();
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
      LOGGER.error("Dao is not found with the given {}", tableName);
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
  public ParticipantIntakeApi delete(String arg0) {
    return null;
  }

  @Override
  public ParticipantIntakeApi find(String arg0) {
    return null;
  }

  @Override
  public ParticipantIntakeApi update(String arg0, ParticipantIntakeApi arg1) {
    return null;
  }

}
