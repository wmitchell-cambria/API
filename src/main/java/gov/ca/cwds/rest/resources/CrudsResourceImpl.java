package gov.ca.cwds.rest.resources;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.ApiResponse;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * An implementation of the {@link CrudsResource}
 * 
 * @author CWDS API Team
 *
 * @param <T> The {@link DomainObject} to perform CRUDS on
 */
public final class CrudsResourceImpl<T extends DomainObject> implements CrudsResource<T> {
  private static final Logger LOGGER = LoggerFactory.getLogger(CrudsResourceImpl.class);

  private CrudsService<T> service;

  /**
   * Constructor
   * 
   * @param crudsService The crudsService for this resource.
   */
  public CrudsResourceImpl(CrudsService<T> crudsService) {
    this.service = crudsService;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.resources.CrudsResource#get(java.lang.String, java.lang.String)
   */
  @Override
  public Response get(String id, String acceptHeader) {
    if (!acceptHeaderContainsKnownMediaType(acceptHeader)) {
      return buildUnsupportedResponse();
    }
    T domainObject = (T) service.find(id);
    if (domainObject != null) {
      return Response.ok(domainObject).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity(null).build();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.resources.CrudsResource#delete(java.lang.String, java.lang.String)
   */
  @Override
  public Response delete(String id, String acceptHeader) {
    if (!acceptHeaderContainsKnownMediaType(acceptHeader)) {
      return buildUnsupportedResponse();
    }
    T domainObject = (T) service.delete(id);
    if (domainObject != null) {
      return Response.status(Response.Status.OK).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity(null).build();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.resources.CrudsResource#create(gov.ca.cwds.rest.api. domain.DomainObject,
   * java.lang.String, javax.ws.rs.core.UriInfo, javax.servlet.http.HttpServletResponse)
   */
  @Override
  public ApiResponse<T> create(T domainObject, String acceptHeader, UriInfo uriInfo,
      final HttpServletResponse response) {
    ApiResponse<T> retval = null;

    try {
      if (!acceptHeaderContainsKnownMediaType(acceptHeader)) {
        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
      } else {
        Serializable primaryKey = service.create(domainObject);
        retval = new ApiResponse<T>(primaryKey.toString(), domainObject);

        // TODO : abstract out the location header creation to something
        // which can be reused for our domain services
        // maybe follow the model of InjectLinks
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI uri = ub.path(primaryKey.toString()).build();

        response.addHeader("Location", uri.toString());
        response.setStatus(HttpServletResponse.SC_CREATED);
      }
    } catch (ServiceException e) {
      if (e.getCause() instanceof EntityExistsException) {
        response.setStatus(HttpServletResponse.SC_CONFLICT);
      } else {
        LOGGER.error("Unable to handle request", e);
        response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
      }
    } finally {
      try {
        response.flushBuffer();
      } catch (Exception e) {
      }
    }
    return retval;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.resources.CrudsResource#update(gov.ca.cwds.rest.api. domain.DomainObject,
   * java.lang.String)
   */
  @Override
  public Response update(T domainObject, String acceptHeader) {
    if (!acceptHeaderContainsKnownMediaType(acceptHeader)) {
      return buildUnsupportedResponse();
    }
    try {
      service.update(domainObject);
      return Response.status(Response.Status.NO_CONTENT).build();
    } catch (ServiceException e) {
      if (e.getCause() instanceof EntityNotFoundException) {
        return Response.status(Response.Status.NOT_FOUND).entity(null).build();
      } else {
        LOGGER.error("Unable to handle request", e);
        return Response.status(HttpStatus.SC_SERVICE_UNAVAILABLE).entity(null).build();
      }
    }
  }

  private boolean acceptHeaderContainsKnownMediaType(String acceptHeader) {
    return Arrays.asList(acceptHeader.split(",")).contains(MediaType.APPLICATION_JSON);
  }

  private Response buildUnsupportedResponse() {
    return Response.status(Response.Status.NOT_ACCEPTABLE).entity(null).build();
  }

}
