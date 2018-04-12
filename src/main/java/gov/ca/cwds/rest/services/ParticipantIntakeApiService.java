package gov.ca.cwds.rest.services;

import com.google.inject.Inject;
import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.ns.AddressesDao;
import gov.ca.cwds.data.ns.AllegationDao;
import gov.ca.cwds.data.ns.LegacyDescriptorDao;
import gov.ca.cwds.data.ns.ParticipantAddressesDao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.ParticipantPhoneNumbersDao;
import gov.ca.cwds.data.ns.PhoneNumbersDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.data.persistence.ns.Allegation;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantAddresses;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantPhoneNumbers;
import gov.ca.cwds.data.persistence.ns.PhoneNumbers;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
  private ParticipantAddressesDao participantAddressesDao;
  private AddressIntakeApiService addressIntakeApiService;
  private PhoneNumbersDao phoneNumbersDao;
  private ParticipantPhoneNumbersDao participantPhoneNumbersDao;

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
      AddressIntakeApiService addressIntakeApiService, ParticipantAddressesDao participantAddressesDao, ParticipantPhoneNumbersDao participantPhoneNumbersDao) {
    this.participantDao = participantDao;
    this.allegationDao = allegationDao;
    this.legacyDescriptorDao = legacyDescriptorDao;
    this.screeningDao = screeningDao;
    this.addressesDao = addressesDao;
    this.participantAddressesDao = participantAddressesDao;
    this.phoneNumbersDao = phoneNumbersDao;
    this.participantPhoneNumbersDao = participantPhoneNumbersDao;
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

    //Get it's Addresses and PhoneNumbers
    for (Addresses addresses : addressesDao.findByParticipant(participantEntity.getId())){
      AddressIntakeApi addressIntakeApi = new AddressIntakeApi(addresses);
      participantIntakeApi.getAddresses().add(addressIntakeApi);

      //Get it's legacy descriptor
      legacyDescriptorEntity = addressesDao.findAddressLegacyDescriptor(addresses.getId());
      if (legacyDescriptorEntity != null){
        addressIntakeApi.setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntity));
      }

    }
    for (PhoneNumbers phoneNumbers : phoneNumbersDao.findByParticipant(participantEntity.getId())){
      participantIntakeApi.getPhoneNumbers().add(new PhoneNumber(phoneNumbers));
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
    ParticipantEntity participantEntity = new ParticipantEntity(participantIntakeApi, screeningEntity);
    participantEntity = participantDao.create(participantEntity);

    //Create Participant Addresses & PhoneNumbers
    Set<AddressIntakeApi> addressIntakeApiSet = new HashSet<>();
    for (AddressIntakeApi addressIntakeApi : participantIntakeApi.getAddresses()){
      Addresses addressesEntity = addressesDao.find(addressIntakeApi.getId());
      if(addressesEntity == null || !addressIntakeApi.equals(new AddressIntakeApi(addressesEntity))){
        //Create only those that don't exist or differs (were changed) from existing ones
        addressIntakeApi.setId(null);
        addressesEntity = addressesDao.create(new Addresses(addressIntakeApi));
        addressIntakeApi = new AddressIntakeApi(addressesEntity);
        addressIntakeApi.setLegacyDescriptor(
            addressIntakeApiService.saveLegacyDescriptor(addressIntakeApi.getLegacyDescriptor(), addressesEntity.getId()));
      }
      addressIntakeApiSet.add(addressIntakeApi);

      participantAddressesDao.create(new ParticipantAddresses(participantEntity, addressesEntity));
    }
    Set<PhoneNumber> phoneNumberSet = new HashSet<>();
    for (PhoneNumber phoneNumber : participantIntakeApi.getPhoneNumbers()){
      PhoneNumbers phoneNumbersEntity = phoneNumbersDao.find(phoneNumber.getId());
      if (phoneNumbersEntity == null || phoneNumber.equals(new PhoneNumber(phoneNumbersEntity))){
        //Create only those that don't exist or differs (were changed) from existing ones
        phoneNumber.setId(null);
        phoneNumbersEntity = phoneNumbersDao.create(new PhoneNumbers(phoneNumber));
        phoneNumber = new PhoneNumber(phoneNumbersEntity);
      }
      phoneNumberSet.add(phoneNumber);

      participantPhoneNumbersDao.create(new ParticipantPhoneNumbers(participantEntity, phoneNumbersEntity));

    }

    //Save legacy descriptor entity
    LegacyDescriptorEntity legacyDescriptorEntity = new LegacyDescriptorEntity(
        participantIntakeApi.getLegacyDescriptor(), LegacyDescriptorEntity.DESCRIBABLE_TYPE_PARTICIPANT, Long.valueOf(participantEntity.getId()));
    legacyDescriptorEntity = legacyDescriptorDao.create(legacyDescriptorEntity);

    participantIntakeApi = new ParticipantIntakeApi(participantEntity);
    participantIntakeApi.setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntity));
    participantIntakeApi.getAddresses().addAll(addressIntakeApiSet);

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
