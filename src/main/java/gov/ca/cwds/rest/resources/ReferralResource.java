package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.REFERRAL;
import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.api.persistence.Referral;
import gov.ca.cwds.rest.core.MediaType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * A resource providing a RESTful interface for {@link Referral}.
 * 
 * 
 * @author CWDS API Team
 */
@Api(value = REFERRAL, consumes = MediaType.APPLICATION_JSON_V1, produces = MediaType.APPLICATION_JSON_V1)
@Path(REFERRAL)
@Produces(MediaType.APPLICATION_JSON_V1)
public interface ReferralResource {
	
	/**
	 * Gets a {@link ReferralSummary} based on the given id.
	 * 
	 * @param id	The id of the summarized {@link Referral}
	 * 
	 * @return	The {@link Response} with the summarized {@link Referral}
	 */
	@GET
	@Path("/{id}/summary")
	@ApiOperation(value = "Find ReferralSummary by Referral id", response = ReferralSummary.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "ReferralSummary not found") })
	public Response getReferralSummary(@PathParam("id") String id);

}
