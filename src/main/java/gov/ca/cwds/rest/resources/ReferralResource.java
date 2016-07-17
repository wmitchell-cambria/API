package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_REFERRAL;
import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.api.persistence.Referral;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
@Api(value = RESOURCE_REFERRAL, consumes = gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1, produces = gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
@Path(RESOURCE_REFERRAL)
@Produces(gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
public interface ReferralResource {
	
	/**
	 * Gets a {@link ReferralSummary} based on the given id.
	 * 
	 * @param id	The id of the summarized {@link Referral}
	 * @param acceptHeader	The accept header.  Used to determine version of API, corresponds to a value in {@link ApiVersion}
	 * 
	 * @return	{@link Response} with the summarized {@link Referral}
	 */
	@GET
	@UnitOfWork
	@Path("/{id}/summary")
	@ApiOperation(value = "Find ReferralSummary by Referral id", response = ReferralSummary.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "ReferralSummary not found"),
							@ApiResponse(code = 406, message = "Accept Header/Version not supported")})
	public Response getReferralSummary(
			@PathParam("id") @ApiParam(required = true, value = "id of the referral") String id,
			@HeaderParam("Accept") String acceptHeader);

//	/**
//	 * Gets a {@link Referral} based on the given id.
//	 * 
//	 * @param id	The id of the {@link Referral}
//	 * @param acceptHeader	The accept header.  Used to determine version of API, corresponds to a value in {@link ApiVersion}
//	 * 
//	 * @return	{@link Response} with the {@link Referral}
//	 */
//	@GET
//	@UnitOfWork
//	@Path("/{id}")
//	@ApiOperation(value = "Find Referral", response = Referral.class)
//	@ApiResponses(value = { @ApiResponse(code = 404, message = "Referral not found"),
//							@ApiResponse(code = 406, message = "Accept Header/Version not supported")})
//	public Response getReferral(
//			@PathParam("id") @ApiParam(required = true, value = "id of the referral") long id);
//	
//	/**
//	 * Delete a {@link Referral}
//	 * 
//	 * @param referral	The {@link Referral}
//	 * @param acceptHeader	The accept header.  Used to determine version of API, corresponds to a value in {@link ApiVersion}
//	 * 
//	 * @return	{@link Response} with the {@link Referral}
//	 */
//	@DELETE
//	@UnitOfWork
//	@Path("/{id}")
//	@ApiOperation(value = "Delete a referral", response = Referral.class)
//	@ApiResponses(value = { @ApiResponse(code = 404, message = "Referral not found"),
//							@ApiResponse(code = 406, message = "Accept Header/Version not supported")})
//	public Response deleteReferral(@ApiParam(required=true, value="id of the referral") long referral) ;
//
//	/**
//	 * Create a {@link Referral}
//	 * 
//	 * @param referral	The {@link Referral}
//	 * @param acceptHeader	The accept header.  Used to determine version of API, corresponds to a value in {@link ApiVersion}
//	 * 
//	 * @return	{@link Response} with the {@link Referral}
//	 */
//	@DELETE
//	@UnitOfWork
//	@ApiOperation(value = "Find Referral by id", response = Referral.class)
//	@ApiResponses(value = { @ApiResponse(code = 404, message = "Referral not found"),
//							@ApiResponse(code = 406, message = "Accept Header/Version not supported")})
//	public Response createReferral(@ApiParam(required=true, value="the referral") Referral referral) ;
}

