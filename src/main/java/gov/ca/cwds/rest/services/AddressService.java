package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.ns.AddressDao;
import gov.ca.cwds.data.ns.XaNsAddressDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.PostedAddress;
import gov.ca.cwds.rest.filters.RequestExecutionContext;

/**
 * Business layer object to work on Postgres {@link Address}.
 * 
 * @author CWDS API Team
 */
public class AddressService implements CrudsService {

  private AddressDao addressDao;
  private XaNsAddressDao xaAddressDao;

  /**
   * Constructor
   * 
   * @param addressDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.ns.Address}
   *        objects.
   */
  @Inject
  public AddressService(AddressDao addressDao, XaNsAddressDao xaAddressDao) {
    this.addressDao = addressDao;
    this.xaAddressDao = xaAddressDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Address find(Serializable primaryKey) {
    assert primaryKey instanceof Long;

    gov.ca.cwds.data.persistence.ns.Address persistedAddress = addressDao.find(primaryKey);
    if (persistedAddress != null) {
      return new Address(persistedAddress);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof Long;
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedAddress create(Request request) {
    assert request instanceof Address;

    final Address address = (Address) request;
    gov.ca.cwds.data.persistence.ns.Address managed =
        new gov.ca.cwds.data.persistence.ns.Address(address, null, null);

    managed = addressDao.create(managed);
    return new PostedAddress(managed);
  }

  /**
   * {@inheritDoc}
   * 
   * <p>
   * Update NS and CMS with XA transaction.
   * </p>
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof Long;
    assert request instanceof Address;
    final Address address = (Address) request;
    final RequestExecutionContext ctx = RequestExecutionContext.instance();
    final String staffId = ctx.getStaffId();
    return new PostedAddress(
        xaAddressDao.update(new gov.ca.cwds.data.persistence.ns.Address(address, staffId, null)));
  }

}
