package gov.ca.cwds.rest.services;

import com.google.inject.Inject;
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

  @Inject
  private ParticipantDao participantDao;
  @Inject
  private AllegationDao allegationDao;
  @Inject
  private LegacyDescriptorDao legacyDescriptorDao;
  @Inject
  private ScreeningDao screeningDao;
  @Inject
  private AddressesDao addressesDao;
  @Inject
  private ParticipantAddressesDao participantAddressesDao;
  @Inject
  private AddressIntakeApiService addressIntakeApiService;
  @Inject
  private PhoneNumbersDao phoneNumbersDao;
  @Inject
  private ParticipantPhoneNumbersDao participantPhoneNumbersDao;


  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#find(Serializable)
   */
  @Override
  public ParticipantIntakeApi find(Serializable primaryKey) {
    assert primaryKey instanceof String;
    ParticipantEntity participantEntity = participantDao.find(primaryKey);
    if (participantEntity == null) {
      return null;
    }
    ParticipantIntakeApi participantIntakeApi = new ParticipantIntakeApi(participantEntity);

    //Get it's legacy descriptor
    LegacyDescriptorEntity legacyDescriptorEntity = participantDao
        .findParticipantLegacyDescriptor(participantEntity.getId());
    if (legacyDescriptorEntity != null) {
      participantIntakeApi.setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntity));
    }

    //Get it's Addresses and PhoneNumbers
    for (Addresses addresses : addressesDao.findByParticipant(participantEntity.getId())) {
      AddressIntakeApi addressIntakeApi = new AddressIntakeApi(addresses);
      participantIntakeApi.getAddresses().add(addressIntakeApi);

      //Get it's legacy descriptor
      legacyDescriptorEntity = addressesDao.findAddressLegacyDescriptor(addresses.getId());
      if (legacyDescriptorEntity != null) {
        addressIntakeApi.setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntity));
      }

    }
    for (PhoneNumbers phoneNumbers : phoneNumbersDao.findByParticipant(participantEntity.getId())) {
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
    LegacyDescriptorEntity legacyDescriptorEntity = participantDao
        .findParticipantLegacyDescriptor((String) primaryKey);
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
    assert request instanceof ParticipantIntakeApi;
    ParticipantIntakeApi participantIntakeApi = (ParticipantIntakeApi) request;

    ScreeningEntity screeningEntity = participantIntakeApi.getScreeningId() == null ?
        null : screeningDao.find(String.valueOf(participantIntakeApi.getScreeningId()));

    ParticipantEntity participantEntityManaged = participantDao.create(
        new ParticipantEntity(participantIntakeApi, screeningEntity));

    //Create Participant Addresses & PhoneNumbers
    Set<AddressIntakeApi> addressIntakeApiSet = createParticipantAddresses(
        participantIntakeApi.getAddresses(),
        participantEntityManaged);

    Set<PhoneNumber> phoneNumberSet = createParticipantPhoneNumbers(
        participantIntakeApi.getPhoneNumbers(),
        participantEntityManaged);

    ParticipantIntakeApi participantIntakeApiPosted = new ParticipantIntakeApi(
        participantEntityManaged);
    participantIntakeApiPosted.addAddresses(addressIntakeApiSet);
    participantIntakeApiPosted.addPhoneNumbers(phoneNumberSet);

    //Save legacy descriptor entity
    if (participantIntakeApi.getLegacyDescriptor() != null) {
      LegacyDescriptorEntity legacyDescriptorEntityManaged = legacyDescriptorDao
          .create(new LegacyDescriptorEntity(
              participantIntakeApi.getLegacyDescriptor(),
              LegacyDescriptorEntity.DESCRIBABLE_TYPE_PARTICIPANT,
              Long.valueOf(participantEntityManaged.getId())));
      participantIntakeApiPosted
          .setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntityManaged));
    }
    return participantIntakeApiPosted;
  }

  private Set<AddressIntakeApi> createParticipantAddresses(
      Set<AddressIntakeApi> addressIntakeApiSet, ParticipantEntity participantEntityManaged) {
    Set<AddressIntakeApi> addressIntakeApiSetPosted = new HashSet<>();
    for (AddressIntakeApi addressIntakeApi : addressIntakeApiSet) {

      Addresses addressesEntityManaged = addressIntakeApi.getId() == null ?
          null : addressesDao.find(addressIntakeApi.getId());

      if (addressesEntityManaged == null || !addressIntakeApi
          .equals(new AddressIntakeApi(addressesEntityManaged))) {
        //Create only those that don't exist or differs (were changed) from existing ones
        addressIntakeApi.setId(null);
        addressesEntityManaged = addressesDao.create(new Addresses(addressIntakeApi));
        addressIntakeApi = new AddressIntakeApi(addressesEntityManaged);
        addressIntakeApi.setLegacyDescriptor(
            addressIntakeApiService.saveLegacyDescriptor(addressIntakeApi.getLegacyDescriptor(),
                addressesEntityManaged.getId()));
      }
      addressIntakeApiSetPosted.add(addressIntakeApi);

      participantAddressesDao
          .create(new ParticipantAddresses(participantEntityManaged, addressesEntityManaged));
    }
    return addressIntakeApiSetPosted;
  }

  private Set<PhoneNumber> createParticipantPhoneNumbers(Set<PhoneNumber> phoneNumberSet,
      ParticipantEntity participantEntityManaged) {
    Set<PhoneNumber> phoneNumberSetPosted = new HashSet<>();

    for (PhoneNumber phoneNumber : phoneNumberSet) {

      PhoneNumbers phoneNumbersEntityManaged = phoneNumber.getId() == null ?
          null : phoneNumbersDao.find(String.valueOf(phoneNumber.getId()));

      if (phoneNumbersEntityManaged == null || !phoneNumber
          .equals(new PhoneNumber(phoneNumbersEntityManaged))) {
        //Create only those that don't exist or differs (were changed) from existing ones
        phoneNumber.setId(null);
        phoneNumbersEntityManaged = phoneNumbersDao.create(new PhoneNumbers(phoneNumber));
        phoneNumber = new PhoneNumber(phoneNumbersEntityManaged);
      }
      phoneNumberSetPosted.add(phoneNumber);

      participantPhoneNumbersDao
          .create(new ParticipantPhoneNumbers(participantEntityManaged, phoneNumbersEntityManaged));

    }
    return phoneNumberSetPosted;
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
