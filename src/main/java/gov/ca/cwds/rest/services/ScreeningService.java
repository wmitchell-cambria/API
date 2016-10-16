package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.NotImplementedException;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningReference;
import gov.ca.cwds.rest.api.domain.ScreeningResponse;
import gov.ca.cwds.rest.api.domain.ScreeningResponseCreated;

/**
 * Business layer object to work on {@link Screening}
 * 
 * @author CWDS API Team
 */

public class ScreeningService implements CrudsService {

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    if (new Long(123).equals(primaryKey)) {
      Address address = new Address("10 main st", "Sacramento", "CA", 95814);
      ImmutableList.Builder<Person> builder = ImmutableList.builder();
      ImmutableList<Person> people = builder
          .add(new Person("Bart", "Simpson", "M", "04/01/1990", "123456789", address))
          .add(new Person("Maggie", "Simpson", "M", "05/21/1991", "123456789", address)).build();
      return new ScreeningResponse("X5HNJK", "2016-10-13", "Amador", "2016-10-13", "Home", "email",
          "First screening", "immediate", "accept_for_investigation", "10/11/2016",
          "first narrative", address, people);
    } else {
      return null;
    }
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
    if (!(request instanceof ScreeningReference)) {
      throw new ServiceException(MessageFormat.format("Unable to create screening service with {0}",
          request.getClass().getName()));
    }
    ScreeningReference screeningReference = (ScreeningReference) request;
    if ("success".equals(screeningReference.getReference())) {
      return new ScreeningResponseCreated(123, screeningReference.getReference());
    } else {
      throw new ServiceException(new EntityExistsException());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   * gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(Serializable primaryKey, Request request) {
    if (new Long(123).equals(primaryKey)) {
      Address address = new Address("10 main st", "Sacramento", "CA", 95814);
      ImmutableList.Builder<Person> builder = ImmutableList.builder();
      ImmutableList<Person> people = builder
          .add(new Person("Bart", "Simpson", "M", "04/01/1990", "123456789", address))
          .add(new Person("Maggie", "Simpson", "M", "05/21/1991", "123456789", address)).build();
      return new ScreeningResponse("X5HNJK", "2016-10-13", "Amador", "2016-10-13", "Home", "email",
          "First screening", "immediate", "accept_for_investigation", "10/11/2016",
          "first narrative", address, people);
    } else {
      throw new ServiceException(new EntityNotFoundException());
    }
  }
}
