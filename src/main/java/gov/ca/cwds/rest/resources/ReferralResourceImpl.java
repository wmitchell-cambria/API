package gov.ca.cwds.rest.resources;

import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.api.persistence.Referral;
import gov.ca.cwds.rest.services.ReferralService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.setup.ServiceEnvironment;

import java.net.URI;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link ReferralResource} delegating work to {@link ReferralService}
 * 
 * @author CWDS API Team
 */
public class ReferralResourceImpl extends BaseVersionedResource<ReferralService> implements ReferralResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReferralResourceImpl.class);
	
	@Context
    UriInfo uriInfo;
	
	public ReferralResourceImpl(ServiceEnvironment serviceEnvironment) {
		super(serviceEnvironment, ReferralService.class);
	}
	
	@Override
	public Response getReferralSummary(String id, String acceptHeader) {
		ReferralService referralService = super.versionedService(acceptHeader);
		if(referralService == null) {
			//TODO : Test this
			//check out - text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8 
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(null).build();
		}
		ReferralSummary referralSummary = referralService.findReferralSummary(id);
		if( referralSummary != null ) {
			return Response.ok(referralSummary).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(null).build();
		}
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.ReferralResource#getReferral(long)
	 */
	@Override
	public Response get(String id, String acceptHeader) {
		ReferralService referralService = super.versionedService(acceptHeader);
		if(referralService == null) {
			//TODO : Test this
			//check out - text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8 
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(null).build();
		}
		Referral referral = referralService.find(id);
		if( referral != null ) {
			return Response.ok(referral).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(null).build();
		}
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.ReferralResource#deleteReferral(long)
	 */
	@Override
	public Response delete(String id, String acceptHeader) {
		ReferralService referralService = super.versionedService(acceptHeader);
		if(referralService == null) {
			//TODO : Test this
			//check out - text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8 
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(null).build();
		}
		Referral referral = referralService.delete(id);
		if( referral != null ) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity(null).build();
		}
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.ReferralResource#createReferral(gov.ca.cwds.rest.api.persistence.Referral)
	 */
	@Override
	public Response create(Referral referral, String acceptHeader) {
		ReferralService referralService = super.versionedService(acceptHeader);
		if(referralService == null) {
			//TODO : Test this
			//check out - text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8 
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(null).build();
		}
		try {
			referral = referralService.create(referral);
			
			 UriBuilder ub = uriInfo.getAbsolutePathBuilder();
	            URI referralUri = ub.
	                    path(referral.getId()).
	                    build();
			return Response.status(Response.Status.NO_CONTENT).header("Location", referralUri.toASCIIString()).build();
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
	 * @see gov.ca.cwds.rest.resources.ReferralResource#putReferral(gov.ca.cwds.rest.api.persistence.Referral)
	 */
	@Override
	public Response update(Referral referral, String acceptHeader) {
		ReferralService referralService = super.versionedService(acceptHeader);
		if(referralService == null) {
			//TODO : Test this
			//check out - text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8 
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(null).build();
		}
		try {
			referral = referralService.update(referral);
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
