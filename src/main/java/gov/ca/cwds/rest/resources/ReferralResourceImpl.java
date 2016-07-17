package gov.ca.cwds.rest.resources;

import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.core.Api;
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
		ReferralService referralService = super.versionedService(Api.Version.findByMediaType(acceptHeader));
		ReferralSummary referralSummary = referralService.findReferralSummary(id);
		if( referralSummary != null ) {
			return Response.ok(referralSummary).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("ReferralSummary not found").build();
		}
	}
}
