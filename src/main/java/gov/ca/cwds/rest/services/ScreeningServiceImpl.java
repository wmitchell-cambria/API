package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningRequest;
import gov.ca.cwds.rest.api.domain.ScreeningResponseCreated;
import gov.ca.cwds.rest.jdbi.ns.AddressDao;
import gov.ca.cwds.rest.jdbi.ns.ScreeningDao;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;

/**
 * Business layer object to work on {@link Screening}
 * 
 * @author CWDS API Team
 */

public class ScreeningServiceImpl extends ScreeningService {

  private ScreeningDao screeningDao;
  private AddressDao addressDao;
  private PersonService personService;


  public ScreeningServiceImpl(ScreeningDao screeningDao, AddressDao addressDao,
      PersonService personService) {
    this.screeningDao = screeningDao;
    this.addressDao = addressDao;
    this.personService = personService;
  }


  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    SessionFactory sessionFactory = screeningDao.getSessionFactory();
    org.hibernate.Session session = sessionFactory.openSession();
    ManagedSessionContext.bind(session);
    gov.ca.cwds.rest.api.persistence.ns.Screening object = screeningDao.find(primaryKey);
    if (object != null) {
      Long addressPrimaryKey = object.getAddressId();
      gov.ca.cwds.rest.api.persistence.ns.Address address = null;
      if (addressPrimaryKey != null) {
        address = addressDao.find(addressPrimaryKey);
      }
      if (address != null) {
        return new ScreeningResponseCreated(object, address);
      }
      return new ScreeningResponseCreated(object, null);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    throw new NotImplementedException("Delete is not implemented");
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response create(Request request) {
    SessionFactory sessionFactory = screeningDao.getSessionFactory();
    org.hibernate.Session session = sessionFactory.openSession();
    ManagedSessionContext.bind(session);
    // Address address = ((ScreeningRequest) request).getAddress();
    ScreeningRequest screening = ((ScreeningRequest) request);
    // gov.ca.cwds.rest.api.persistence.ns.Address persistedAddress =
    // new gov.ca.cwds.rest.api.persistence.ns.Address(address, null);
    // persistedAddress = addressDao.create(persistedAddress);
    // session.flush();
    gov.ca.cwds.rest.api.persistence.ns.Screening persistedScreening =
        new gov.ca.cwds.rest.api.persistence.ns.Screening(screening, null);
    // Long id = persistedAddress.getId();
    // persistedScreening.setAddressId(id);
    persistedScreening = screeningDao.create(persistedScreening);
    session.flush();
    // return new ScreeningResponseCreated(persistedScreening, persistedAddress);
    return new ScreeningResponseCreated(persistedScreening, null);
  }


  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   * gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(Serializable primaryKey, Request request) {
    throw new NotImplementedException("Update is not implemented");
  }



}
