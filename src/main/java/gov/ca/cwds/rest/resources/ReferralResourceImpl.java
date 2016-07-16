package gov.ca.cwds.rest.resources;

import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.api.persistence.Referral;
import gov.ca.cwds.rest.services.ReferralService;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link Referral} delegating work to {@link ReferralService}
 * 
 * @author CWDS API Team
 */
public class ReferralResourceImpl implements ReferralResource {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ReferralResourceImpl.class);
	
	private ReferralService referralService;
	
	public ReferralResourceImpl(ReferralService referralService) {
		this.referralService = referralService;
	}
	
	@Override
	public Response getReferralSummary(String id) {
		ReferralSummary referralSummary = referralService.findReferralSummary(id);
		if( referralSummary != null ) {
			return Response.ok(referralSummary).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

}
