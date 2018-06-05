package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.AddressesDao;
import gov.ca.cwds.data.ns.AllegationDao;
import gov.ca.cwds.data.ns.CsecDao;
import gov.ca.cwds.data.ns.LegacyDescriptorDao;
import gov.ca.cwds.data.ns.ParticipantAddressesDao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.ParticipantPhoneNumbersDao;
import gov.ca.cwds.data.ns.PhoneNumbersDao;
import gov.ca.cwds.data.ns.SafelySurrenderedBabiesDao;
import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.data.persistence.ns.Allegation;
import gov.ca.cwds.data.persistence.ns.CsecEntity;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantAddresses;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantPhoneNumbers;
import gov.ca.cwds.data.persistence.ns.PhoneNumbers;
import gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.SafelySurenderedBabies;
import gov.ca.cwds.rest.services.mapper.CsecMapper;
import gov.ca.cwds.rest.services.mapper.SafelySurrenderedBabiesMapper;

/**
 * Business layer object to work on {@link ParticipantIntakeApi}
 *
 * @author Intake Team 4
 */
public class ParticipantIntakeApiService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantIntakeApiService.class);

  @Inject
  private ParticipantDao participantDao;
  @Inject
  private AllegationDao allegationDao;
  @Inject
  private LegacyDescriptorDao legacyDescriptorDao;
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
  @Inject
  private CsecDao csecDao;
  @Inject
  private CsecMapper csecMapper;
  @Inject
  private SafelySurrenderedBabiesDao safelySurrenderedBabiesDao;
  @Inject
  private SafelySurrenderedBabiesMapper safelySurrenderedBabiesMapper;


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

    participantIntakeApi.setCsecs(csecMapper.toDomain(participantEntity.getCsecs()));
    participantIntakeApi.setSafelySurenderedBabies(
        safelySurrenderedBabiesMapper.map(participantEntity.getSafelySurrenderedBabies()));

    // Get it's legacy descriptor
    LegacyDescriptorEntity legacyDescriptorEntity =
        legacyDescriptorDao.findParticipantLegacyDescriptor(participantEntity.getId());
    if (legacyDescriptorEntity != null) {
      participantIntakeApi.setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntity));
    }

    // Get it's Addresses and PhoneNumbers
    for (Addresses addresses : addressesDao.findByParticipant(participantEntity.getId())) {
      AddressIntakeApi addressIntakeApi = new AddressIntakeApi(addresses);
      participantIntakeApi.getAddresses().add(addressIntakeApi);

      // Get it's legacy descriptor
      legacyDescriptorEntity = legacyDescriptorDao.findAddressLegacyDescriptor(addresses.getId());
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
  public ParticipantIntakeApi delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    ParticipantEntity participantEntity = participantDao.find(primaryKey);
    if (participantEntity == null) {
      return null;
    }
    participantDao.delete(primaryKey);

    // Delete all allegations for this participant
    allegationDao.deleteByIdList(allegationDao.findByVictimOrPerpetratorId((String) primaryKey)
        .stream().map(Allegation::getId).collect(Collectors.toList()));

    // Delete Participant Addresses & PhoneNumbers
    participantAddressesDao.findByParticipantId((String) primaryKey).forEach(
        participantAddresses -> participantAddressesDao.delete(participantAddresses.getId()));

    participantPhoneNumbersDao.findByParticipantId((String) primaryKey)
        .forEach(participantPhoneNumbers -> participantPhoneNumbersDao
            .delete(participantPhoneNumbers.getId()));

    // Delete legacy descriptor
    LegacyDescriptorEntity legacyDescriptorEntity =
        legacyDescriptorDao.findParticipantLegacyDescriptor((String) primaryKey);
    if (legacyDescriptorEntity != null) {
      legacyDescriptorDao.delete(legacyDescriptorEntity.getId());
    }

    return new ParticipantIntakeApi(participantEntity);
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public ParticipantIntakeApi create(Request request) {
    assert request instanceof ParticipantIntakeApi;
    ParticipantIntakeApi participantIntakeApi = (ParticipantIntakeApi) request;

    ParticipantEntity participantEntityManaged =
        participantDao.create(new ParticipantEntity(participantIntakeApi));

    createOrUpdateCsecs(participantIntakeApi, participantEntityManaged);
    createOrUpdateSafelySurrenderedBabies(participantIntakeApi, participantEntityManaged);

    // Create Participant Addresses & PhoneNumbers
    Set<AddressIntakeApi> addressIntakeApiSet =
        createParticipantAddresses(participantIntakeApi.getAddresses(), participantEntityManaged);

    Set<PhoneNumber> phoneNumberSet = createParticipantPhoneNumbers(
        participantIntakeApi.getPhoneNumbers(), participantEntityManaged);

    ParticipantIntakeApi participantIntakeApiPosted =
        new ParticipantIntakeApi(participantEntityManaged);
    participantIntakeApiPosted.addAddresses(addressIntakeApiSet);
    participantIntakeApiPosted.addPhoneNumbers(phoneNumberSet);
    participantIntakeApiPosted.setCsecs(csecMapper.toDomain(participantEntityManaged.getCsecs()));

    SafelySurenderedBabies createdSsb =
        safelySurrenderedBabiesMapper.map(participantEntityManaged.getSafelySurrenderedBabies());
    participantIntakeApiPosted.setSafelySurenderedBabies(createdSsb);



    // Save legacy descriptor entity
    if (participantIntakeApi.getLegacyDescriptor() != null) {
      LegacyDescriptorEntity legacyDescriptorEntityManaged = legacyDescriptorDao
          .create(new LegacyDescriptorEntity(participantIntakeApi.getLegacyDescriptor(),
              LegacyDescriptorEntity.DESCRIBABLE_TYPE_PARTICIPANT,
              Long.valueOf(participantEntityManaged.getId())));
      participantIntakeApiPosted
          .setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntityManaged));
    }
    return participantIntakeApiPosted;
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#update(Serializable, gov.ca.cwds.rest.api.Request)
   */
  @Override
  public ParticipantIntakeApi update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    assert request instanceof ParticipantIntakeApi;
    ParticipantIntakeApi participantIntakeApi = (ParticipantIntakeApi) request;
    participantIntakeApi.setId((String) primaryKey);
    ParticipantEntity participantEntityManaged = participantDao.find(primaryKey);
    if (participantEntityManaged == null) {
      LOGGER.info("participant not found : {}", participantIntakeApi);
      throw new ServiceException(new EntityNotFoundException(
          "Entity ParticipantEntity with id = [" + primaryKey + "] was not found."));
    }

    participantEntityManaged =
        participantDao.update(participantEntityManaged.updateFrom(participantIntakeApi));

    createOrUpdateCsecs(participantIntakeApi, participantEntityManaged);
    createOrUpdateSafelySurrenderedBabies(participantIntakeApi, participantEntityManaged);

    // Update Participant Addresses & PhoneNumbers
    Set<AddressIntakeApi> addressIntakeApiSet =
        updateParticipantAddresses(participantIntakeApi.getAddresses(), participantEntityManaged);

    Set<PhoneNumber> phoneNumberSet = updateParticipantPhoneNumbers(
        participantIntakeApi.getPhoneNumbers(), participantEntityManaged);

    ParticipantIntakeApi participantIntakeApiPosted =
        new ParticipantIntakeApi(participantEntityManaged);
    participantIntakeApiPosted.addAddresses(addressIntakeApiSet);
    participantIntakeApiPosted.addPhoneNumbers(phoneNumberSet);
    participantIntakeApiPosted.setCsecs(csecMapper.toDomain(participantEntityManaged.getCsecs()));
    participantIntakeApiPosted.setSafelySurenderedBabies(
        safelySurrenderedBabiesMapper.map(participantEntityManaged.getSafelySurrenderedBabies()));
    return participantIntakeApiPosted;


  }

  private Set<AddressIntakeApi> createParticipantAddresses(
      Set<AddressIntakeApi> addressIntakeApiSet, ParticipantEntity participantEntityManaged) {
    Set<AddressIntakeApi> addressIntakeApiSetPosted = new HashSet<>();
    addressIntakeApiSet.stream().map(this::createAddresses).forEach(addressesWrapper -> {
      addressIntakeApiSetPosted.add(addressesWrapper.addressIntakeApi);
      participantAddressesDao
          .create(new ParticipantAddresses(participantEntityManaged, addressesWrapper.addresses));
    });
    return addressIntakeApiSetPosted;
  }

  private Set<AddressIntakeApi> updateParticipantAddresses(
      Set<AddressIntakeApi> addressIntakeApiSet, ParticipantEntity participantEntityManaged) {
    Set<AddressIntakeApi> addressIntakeApiSetPosted = new HashSet<>();

    Map<String, ParticipantAddresses> participantAddressesOldMap = new HashMap<>();
    participantAddressesDao.findByParticipantId(participantEntityManaged.getId())
        .forEach(participantAddresses -> participantAddressesOldMap
            .put(participantAddresses.getAddress().getId(), participantAddresses));

    addressIntakeApiSet.stream().map(this::createAddresses).forEach(addressesWrapper -> {
      addressIntakeApiSetPosted.add(addressesWrapper.addressIntakeApi);

      // See if we had this ParticipantAddresses entity before. Otherwise create
      Addresses addressesEntityManaged = addressesWrapper.addresses;
      ParticipantAddresses participantAddresses =
          participantAddressesOldMap.get(addressesEntityManaged.getId());
      if (participantAddresses != null) {
        // Remove from the Old map
        participantAddressesOldMap.remove(addressesEntityManaged.getId());
        participantAddressesDao.update(participantAddresses);
      } else {
        participantAddressesDao
            .create(new ParticipantAddresses(participantEntityManaged, addressesEntityManaged));
      }
    });

    // Delete old ones that are not in the new.
    participantAddressesOldMap.values().forEach(
        participantAddresses -> participantAddressesDao.delete(participantAddresses.getId()));

    return addressIntakeApiSetPosted;
  }

  private void createOrUpdateCsecs(ParticipantIntakeApi participantIntakeApi,
      ParticipantEntity participantEntityManaged) {
    List<CsecEntity> csecEnities = new ArrayList<>(participantIntakeApi.getCsecs().size());
    for (Csec csec : participantIntakeApi.getCsecs()) {
      String participantId = participantEntityManaged.getId();
      CsecEntity csecEntity = csecMapper.map(csec);
      csecEntity.setParticipantId(participantId);
      if (csecEntity.getId() == null) {
        CsecEntity createdCsecEntity = csecDao.create(csecEntity);
        csec.setId(String.valueOf(createdCsecEntity.getId()));
        csecEnities.add(createdCsecEntity);
      } else {
        CsecEntity managedCsecEntity = csecDao.find(csecEntity.getId());
        if (managedCsecEntity == null) {
          throw new ServiceException(
              "Cannot update CSEC that doesn't exist. id = " + csecEntity.getId());
        }
        csecDao.getSessionFactory().getCurrentSession().detach(managedCsecEntity);
        csecEnities.add(csecDao.update(csecEntity));
      }
    }
    participantEntityManaged.setCsecs(csecEnities);
  }

  private void createOrUpdateSafelySurrenderedBabies(ParticipantIntakeApi participantIntakeApi,
      ParticipantEntity participantEntityManaged) {


    SafelySurenderedBabies safelySurenderedBabies =
        participantIntakeApi.getSafelySurenderedBabies();

    if (safelySurenderedBabies != null) {
      String participantId = participantEntityManaged.getId();

      SafelySurrenderedBabiesEntity existingSafelySurrenderedBabiesEntity =
          safelySurrenderedBabiesDao.find(participantId);
      SafelySurrenderedBabiesEntity createdOrUpdatedSafelySurrenderedBabiesEntity = null;

      if (existingSafelySurrenderedBabiesEntity == null) {
        SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity =
            safelySurrenderedBabiesMapper.map(safelySurenderedBabies);
        safelySurrenderedBabiesEntity.setParticipantId(participantId);
        createdOrUpdatedSafelySurrenderedBabiesEntity =
            safelySurrenderedBabiesDao.create(safelySurrenderedBabiesEntity);
        safelySurenderedBabies.setParticipantId(
            String.valueOf(createdOrUpdatedSafelySurrenderedBabiesEntity.getParticipantId()));
      } else {
        SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity = safelySurrenderedBabiesMapper
            .map(safelySurenderedBabies, existingSafelySurrenderedBabiesEntity);
        safelySurrenderedBabiesEntity.setParticipantId(participantId);
        createdOrUpdatedSafelySurrenderedBabiesEntity =
            safelySurrenderedBabiesDao.update(safelySurrenderedBabiesEntity);
      }
      participantEntityManaged
          .setSafelySurrenderedBabies(createdOrUpdatedSafelySurrenderedBabiesEntity);
    }

  }

  public List<ParticipantEntity> getByScreeningId(String screeningId) {
    return participantDao.getByScreeningId(screeningId);
  }

  private static class AddressesWrapper {

    private AddressIntakeApi addressIntakeApi;
    private Addresses addresses;

    AddressesWrapper(AddressIntakeApi addressIntakeApi, Addresses addresses) {
      this.addressIntakeApi = addressIntakeApi;
      this.addresses = addresses;
    }
  }

  private AddressesWrapper createAddresses(AddressIntakeApi addressIntakeApi) {
    Addresses addressesEntityManaged =
        addressIntakeApi.getId() == null ? null : addressesDao.find(addressIntakeApi.getId());
    if (addressesEntityManaged == null
        || !addressIntakeApi.equals(new AddressIntakeApi(addressesEntityManaged))) {
      // Create only those that don't exist or differs (were changed) from existing ones
      addressIntakeApi.setId(null);
      addressesEntityManaged = addressesDao.create(new Addresses(addressIntakeApi));
      LegacyDescriptor legacyDescriptor = addressIntakeApi.getLegacyDescriptor();
      addressIntakeApi = new AddressIntakeApi(addressesEntityManaged);
      addressIntakeApi.setLegacyDescriptor(addressIntakeApiService
          .saveLegacyDescriptor(legacyDescriptor, addressesEntityManaged.getId()));
    }
    return new AddressesWrapper(addressIntakeApi, addressesEntityManaged);
  }

  private Set<PhoneNumber> createParticipantPhoneNumbers(Set<PhoneNumber> phoneNumberSet,
      ParticipantEntity participantEntityManaged) {
    Set<PhoneNumber> phoneNumberSetPosted = new HashSet<>();

    for (PhoneNumber phoneNumber : phoneNumberSet) {

      PhoneNumbers phoneNumbersEntityManaged = phoneNumber.getId() == null ? null
          : phoneNumbersDao.find(String.valueOf(phoneNumber.getId()));

      if (phoneNumbersEntityManaged == null
          || !phoneNumber.equals(new PhoneNumber(phoneNumbersEntityManaged))) {
        // Create only those that don't exist or differs (were changed) from existing ones
        phoneNumber.setId(null);
        phoneNumbersEntityManaged = phoneNumbersDao.create(new PhoneNumbers(phoneNumber));
        phoneNumber = new PhoneNumber(phoneNumbersEntityManaged);
      }
      phoneNumberSetPosted.add(phoneNumber);

      ParticipantPhoneNumbers participantPhoneNumbers =
          new ParticipantPhoneNumbers(participantEntityManaged, phoneNumbersEntityManaged);
      participantPhoneNumbers.setCreatedAt(new Date());
      participantPhoneNumbers.setUpdatedAt(participantPhoneNumbers.getCreatedAt());
      participantPhoneNumbersDao.create(participantPhoneNumbers);

    }
    return phoneNumberSetPosted;
  }

  private Set<PhoneNumber> updateParticipantPhoneNumbers(Set<PhoneNumber> phoneNumberSet,
      ParticipantEntity participantEntityManaged) {
    Set<PhoneNumber> phoneNumberSetPosted = new HashSet<>();

    Map<String, ParticipantPhoneNumbers> participantPhoneNumbersOldMap = new HashMap<>();
    participantPhoneNumbersDao.findByParticipantId(participantEntityManaged.getId())
        .forEach(participantPhoneNumbers -> participantPhoneNumbersOldMap
            .put(participantPhoneNumbers.getPhoneNumber().getId(), participantPhoneNumbers));

    for (PhoneNumber phoneNumber : phoneNumberSet) {

      PhoneNumbers phoneNumbersEntityManaged = phoneNumber.getId() == null ? null
          : phoneNumbersDao.find(String.valueOf(phoneNumber.getId()));

      if (phoneNumbersEntityManaged == null
          || !phoneNumber.equals(new PhoneNumber(phoneNumbersEntityManaged))) {
        // Create only those that don't exist or differs (were changed) from existing ones
        phoneNumber.setId(null);
        phoneNumbersEntityManaged = phoneNumbersDao.create(new PhoneNumbers(phoneNumber));
        phoneNumber = new PhoneNumber(phoneNumbersEntityManaged);
      }

      phoneNumberSetPosted.add(phoneNumber);

      // See if we had this ParticipantPhoneNumber entity before. Otherwise create
      ParticipantPhoneNumbers participantPhoneNumbers =
          participantPhoneNumbersOldMap.get(phoneNumbersEntityManaged.getId());
      if (participantPhoneNumbers != null) {
        // Remove from the Old map
        participantPhoneNumbersOldMap.remove(phoneNumbersEntityManaged.getId());
        participantPhoneNumbersDao.update(participantPhoneNumbers);
      } else {
        participantPhoneNumbersDao.create(
            new ParticipantPhoneNumbers(participantEntityManaged, phoneNumbersEntityManaged));
      }
    }
    // Delete old ones that are not in the new.
    participantPhoneNumbersOldMap.values()
        .forEach(participantPhoneNumbers -> participantPhoneNumbersDao
            .delete(participantPhoneNumbers.getId()));

    return phoneNumberSetPosted;
  }

  void setCsecMapper(CsecMapper csecMapper) {
    this.csecMapper = csecMapper;
  }

  void setSafelySurrenderedBabiesMapper(
      SafelySurrenderedBabiesMapper safelySurrenderedBabiesMapper) {
    this.safelySurrenderedBabiesMapper = safelySurrenderedBabiesMapper;
  }
}
