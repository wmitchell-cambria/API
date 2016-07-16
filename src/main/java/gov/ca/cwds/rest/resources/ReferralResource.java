package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.REFERRAL;
import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.core.MediaType;
import io.swagger.annotations.Api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Api(REFERRAL)
@Path(REFERRAL)
@Produces(MediaType.APPLICATION_JSON_V1)
public interface ReferralResource {
	
	@GET
	@Path("/{id}/summary")
	public ReferralSummary getReferralSummary(@PathParam("id") String id);

}
