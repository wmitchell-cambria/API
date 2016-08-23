package gov.ca.cwds.rest.resources;

import java.net.URI;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.Service;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.setup.ServiceEnvironment;

/**
 * An implementation of the {@link CrudsResource}
 * 
 * @author CWDS API Team
 *
 * @param <T>	The {@link DomainObject} to perform CRUDS on
 * @param <S>	The root {@link Service} interface that will handle the CRUDS from a business layer.  We want the root interface because have different implementations of the interfaces for each version of the API. 
 */
public final class CrudsResourceImpl<T extends DomainObject, S extends Service> extends BaseResource<S> implements CrudsResource<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(CrudsResourceImpl.class);
	
	/**
	 * Constructor 
	 * 
	 * @param serviceEnvironment  The environment for this resource.
	 * @param clazz	The class represented by type S
	 */
	public CrudsResourceImpl(ServiceEnvironment serviceEnvironment, Class<S> clazz) {
		super(serviceEnvironment, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#get(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Response get(String id, String acceptHeader) {
		CrudsService service = (CrudsService)super.versionedService(acceptHeader);
		if(service == null) {
			//TODO : Test this
			//check out - text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8 
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(null).build();
		}
		T domainObject = (T)service.find(id);
		if( domainObject != null ) {
			return Response.ok(domainObject).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(null).build();
		}
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#delete(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Response delete(String id, String acceptHeader) {
		CrudsService service = (CrudsService)super.versionedService(acceptHeader);
		if(service == null) {
			//TODO : Test this
			//check out - text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8 
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(null).build();
		}
		T domainObject = (T)service.delete(id);
		if( domainObject != null ) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(null).build();
		}
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#create(gov.ca.cwds.rest.api.domain.DomainObject, java.lang.String, javax.ws.rs.core.UriInfo)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Response create(T domainObject, String acceptHeader, UriInfo uriInfo) {
		CrudsService service = (CrudsService)super.versionedService(acceptHeader);
		if(service == null) {
			//TODO : Test this
			//check out - text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8 
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(null).build();
		}
		try {
			String id = service.create(domainObject);
			
			UriBuilder ub = uriInfo.getAbsolutePathBuilder();
	        URI referralUri = ub.
	                    path(id).
	                    build();
			return Response.status(Response.Status.CREATED).header("Location", referralUri.toASCIIString()).build();
		} catch (ServiceException e) {
			if( e.getCause() instanceof EntityExistsException ) {
				return Response.status(Response.Status.CONFLICT).entity(null).build();
			} else {
				LOGGER.error("Unable to handle request", e);
				return Response.status(HttpStatus.SC_SERVICE_UNAVAILABLE).entity(null).build();
			}
		}
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.CrudsResource#update(gov.ca.cwds.rest.api.domain.DomainObject, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Response update(T domainObject, String acceptHeader) {
		CrudsService  service = (CrudsService)super.versionedService(acceptHeader);
		if(service == null) {
			//TODO : Test this
			//check out - text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8 
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(null).build();
		}
		try {
			service.update(domainObject);
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (ServiceException e) {
			if( e.getCause() instanceof EntityNotFoundException ) {
				return Response.status(Response.Status.NOT_FOUND).entity(null).build();
			} else {
				LOGGER.error("Unable to handle request", e);
				return Response.status(HttpStatus.SC_SERVICE_UNAVAILABLE).entity(null).build();
			}
		}
	}
	
}
