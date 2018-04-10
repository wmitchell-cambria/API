package gov.ca.cwds.rest.services;

import com.google.inject.Inject;
import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.ns.AddressesDao;
import gov.ca.cwds.data.ns.AllegationDao;
import gov.ca.cwds.data.ns.LegacyDescriptorDao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.PhoneNumbersDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.data.persistence.ns.Allegation;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.PhoneNumbers;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Business layer object to work on {@link ParticipantIntakeApi}
 * 
 * @author Intake Team 4
 */
public class ParticipantIntakeApiService implements CrudsService {
  private ParticipantDao participantDao;
  private AllegationDao allegationDao;
  private LegacyDescriptorDao legacyDescriptorDao;
  private ScreeningDao screeningDao;
  private AddressesDao addressesDao;
  private AddressIntakeApiService addressIntakeApiService;
  private PhoneNumbersDao phoneNumbersDao;

  /**
   * Constructor
   *
   * @param participantDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.ns.ParticipantEntity}
   * @param allegationDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.ns.Allegation}
   *        objects.
   */
  @Inject
  public ParticipantIntakeApiService(ParticipantDao participantDao, AllegationDao allegationDao, LegacyDescriptorDao legacyDescriptorDao,
      ScreeningDao screeningDao, AddressesDao addressesDao, PhoneNumbersDao phoneNumbersDao,
      AddressIntakeApiService addressIntakeApiService) {
    this.participantDao = participantDao;
    this.allegationDao = allegationDao;
    this.legacyDescriptorDao = legacyDescriptorDao;
    this.screeningDao = screeningDao;
    this.addressesDao = addressesDao;
    this.phoneNumbersDao = phoneNumbersDao;
    this.addressIntakeApiService = addressIntakeApiService;
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#find(Serializable)
   */
  @Override
  public ParticipantIntakeApi find(Serializable primaryKey) {
    assert primaryKey instanceof String;
    ParticipantEntity participantEntity = participantDao.find(primaryKey);
    if(participantEntity == null){
      return null;
    }
    ParticipantIntakeApi participantIntakeApi =  new ParticipantIntakeApi(participantEntity);
    //Get it's legacy descriptor
    LegacyDescriptorEntity legacyDescriptorEntity = participantDao.findParticipantLegacyDescriptor(participantEntity.getId());
    if (legacyDescriptorEntity != null){
      participantIntakeApi.setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntity));
    }
    return participantIntakeApi;
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#delete(Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    participantDao.delete(primaryKey);
    //Delete all allegations for this participant
    allegationDao.deleteByIdList(
        allegationDao.findByVictimOrPerpetratorId((String) primaryKey)
            .stream().map(Allegation::getId).collect(Collectors.toList()));
    //Delete legacy descriptor
    LegacyDescriptorEntity legacyDescriptorEntity = participantDao.findParticipantLegacyDescriptor((String) primaryKey);
    if (legacyDescriptorEntity != null) {
      legacyDescriptorDao.delete(legacyDescriptorEntity.getId());
    }
    return null;
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response create(Request request) {
    ParticipantIntakeApi participantIntakeApi = (ParticipantIntakeApi)request;

    ScreeningEntity screeningEntity = screeningDao.find(String.valueOf(participantIntakeApi.getScreeningId()));
    Set<Addresses> addressesSet = participantIntakeApi.getAddresses().stream().map(Addresses::new).collect(Collectors.toCollection(HashSet::new));
//    Set<Addresses> addressesSet = participantIntakeApi.getAddresses().stream().map(addressIntakeApiService::create).map(Addresses::new).collect(Collectors.toCollection(HashSet::new));
    Set<PhoneNumbers> phoneNumbersSet = participantIntakeApi.getPhoneNumbers().stream().map(PhoneNumbers::new).collect(Collectors.toCollection(HashSet::new));
//    Set<PhoneNumbers> phoneNumbersSet = participantIntakeApi.getPhoneNumbers().stream().map(PhoneNumbers::new).map(phoneNumbersDao::create).collect(Collectors.toCollection(HashSet::new));

    ParticipantEntity participantEntity = new ParticipantEntity(participantIntakeApi, screeningEntity, addressesSet, phoneNumbersSet);
    participantEntity = participantDao.create(participantEntity);

    //Save legacy descriptor entity
    LegacyDescriptorEntity legacyDescriptorEntity = new LegacyDescriptorEntity(
        participantIntakeApi.getLegacyDescriptor(), LegacyDescriptorEntity.DESCRIBABLE_TYPE_PARTICIPANT, Long.valueOf(participantEntity.getId()));
    legacyDescriptorEntity = legacyDescriptorDao.create(legacyDescriptorEntity);

    participantIntakeApi = new ParticipantIntakeApi(participantEntity);
    participantIntakeApi.setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntity));

    return participantIntakeApi;
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#update(Serializable, gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(Serializable arg0, Request arg1) {
    return null;
  }

}
