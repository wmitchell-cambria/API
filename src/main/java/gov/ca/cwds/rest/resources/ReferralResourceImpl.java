package gov.ca.cwds.rest.resources;

import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.api.persistence.Referral;
import gov.ca.cwds.rest.services.ReferralService;
import gov.ca.cwds.rest.setup.ServiceEnvironment;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link ReferralResource} delegating work to {@link ReferralService}
 * 
 * @author CWDS API Team
 */
public class ReferralResourceImpl extends BaseVersionedResource<ReferralService> implements ReferralResource {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ReferralResourceImpl.class);
	
	public ReferralResourceImpl(ServiceEnvironment serviceEnvironment) {
		super(serviceEnvironment, ReferralService.class);
	}
	
	@Override
	public Response getReferralSummary(String id, String acceptHeader) {
		ReferralService referralService = super.versionedService(acceptHeader);
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
	public Response createReferral(Referral referral) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.resources.ReferralResource#putReferral(gov.ca.cwds.rest.api.persistence.Referral)
	 */
	@Override
	public Response putReferral(Referral referral) {
		// TODO Auto-generated method stub
		return null;
	}
}
