package gov.ca.cwds.rest.services;

import com.google.inject.Inject;
import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.ns.AddressesDao;
import gov.ca.cwds.data.ns.LegacyDescriptorDao;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import java.io.Serializable;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Business layer object to work on {@link Address}
 *
 * @author Intake Team 4
 */
public class AddressIntakeApiService implements CrudsService {

  private AddressesDao addressesDao;
  private LegacyDescriptorDao legacyDescriptorDao;

  /**
   * Constructor
   *
   * @param addressesDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.ns.Address}
   * objects.
   */
  @Inject
  public AddressIntakeApiService(AddressesDao addressesDao,
      LegacyDescriptorDao legacyDescriptorDao) {
    this.addressesDao = addressesDao;
    this.legacyDescriptorDao = legacyDescriptorDao;
  }

  /**
   * {@inheritDoc}
   *
   * @see CrudsService#find(Serializable)
   */
  @Override
  public AddressIntakeApi find(Serializable primaryKey) {
    assert primaryKey instanceof Long;

    gov.ca.cwds.data.persistence.ns.Addresses persistedAddress = addressesDao.find(primaryKey);
    if (persistedAddress == null) {
      return null;
    }
    AddressIntakeApi addressIntakeApi = new AddressIntakeApi(persistedAddress);
    //Get it's legacy descriptor
    LegacyDescriptorEntity legacyDescriptorEntity = addressesDao
        .findAddressLegacyDescriptor(persistedAddress.getId());
    if (legacyDescriptorEntity != null) {
      addressIntakeApi.setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntity));
    }

    return addressIntakeApi;

  }

  /**
   * {@inheritDoc}
   *
   * @see CrudsService#delete(Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof Long;
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * {@inheritDoc}
   *
   * @see CrudsService#create(Request)
   */
  @Override
  public AddressIntakeApi create(Request request) {
    assert request instanceof AddressIntakeApi;

    AddressIntakeApi address = (AddressIntakeApi) request;

    gov.ca.cwds.data.persistence.ns.Addresses managed =
        new gov.ca.cwds.data.persistence.ns.Addresses(address);

    managed = addressesDao.create(managed);

    AddressIntakeApi addressPosted = new AddressIntakeApi(managed);
    address.setLegacyDescriptor(
        saveLegacyDescriptor(address.getLegacyDescriptor(), managed.getId()));

    return addressPosted;
  }

  /**
   * {@inheritDoc}
   *
   * @see CrudsService#update(Serializable, Request)
   */
  @Override
  public Response update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof Long;
    throw new NotImplementedException("Update is not implemented");
  }


  public LegacyDescriptor saveLegacyDescriptor(LegacyDescriptor legacyDescriptor,
      String describableId) {
    if (legacyDescriptor == null || describableId == null) {
      return null;
    }
    //Save legacy descriptor entity
    LegacyDescriptorEntity legacyDescriptorEntity = new LegacyDescriptorEntity(
        legacyDescriptor, LegacyDescriptorEntity.DESCRIBABLE_TYPE_ADDRESS,
        Long.valueOf(describableId));
    legacyDescriptorEntity = legacyDescriptorDao.create(legacyDescriptorEntity);
    return new LegacyDescriptor(legacyDescriptorEntity);
  }
}
