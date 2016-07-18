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
import io.swagger.annotations.ResponseHeader;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

/**
 * A resource providing a RESTful interface for {@link Referral}.
 * 
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_REFERRAL)
@Path(RESOURCE_REFERRAL)
@Produces(gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
@Consumes(gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
public interface ReferralResource {

	/**
	 * Gets a {@link ReferralSummary} based on the given id.
	 * 
	 * @param id
	 *            The id of the summarized {@link Referral}
	 * @param acceptHeader
	 *            The accept header. Used to determine version of API,
	 *            corresponds to a value in {@link ApiVersion}
	 * 
	 * @return {@link Response} with the summarized {@link Referral}
	 */
	@GET
	@UnitOfWork
	@Path("/{id}/summary")
	@ApiOperation(value = "Find ReferralSummary by Referral id", response = ReferralSummary.class)
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "ReferralSummary not found"),
			@ApiResponse(code = 406, message = "Content Type not supported") })
	public Response getReferralSummary(
			@PathParam("id") @ApiParam(required = true, value = "the id") String id,
			@HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader);

	/**
	 * Gets a {@link Referral} based on the given id.
	 * 
	 * @param id
	 *            The id of the {@link Referral}
	 * @param acceptHeader
	 *            The accept header. Used to determine version of API,
	 *            corresponds to a value in {@link ApiVersion}
	 * 
	 * @return {@link Response} with the {@link Referral}
	 */
	@GET
	@UnitOfWork
	@Path("/{id}")
	@ApiOperation(value = "Find by id", response = Referral.class)
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 406, message = "Accept Header/Version not supported") })
	public Response get(
			@PathParam("id") @ApiParam(required = true, value = "id of object to get") String id,
			@HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader);

	/**
	 * Delete a {@link Referral}
	 * 
	 * @param referral
	 *            The {@link Referral}
	 * @param acceptHeader
	 *            The accept header. Used to determine version of API,
	 *            corresponds to a value in {@link ApiVersion}
	 * 
	 * @return {@link Response} with the {@link Referral}
	 */
	@DELETE
	@UnitOfWork
	@Path("/{id}")
	@ApiOperation(value = "Delete a referral", code = HttpStatus.SC_NO_CONTENT)
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 406, message = "Accept Header/Version not supported") })
	public Response delete(
			@PathParam("id") @ApiParam(required = true, value = "id of object to delete") String id,
			@HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader);

	/**
	 * Create a {@link Referral}
	 * 
	 * @param referral
	 *            The {@link Referral}
	 * @param acceptHeader
	 *            The accept header. Used to determine version of API,
	 *            corresponds to a value in {@link ApiVersion}
	 * 
	 * @return {@link Response} with the {@link Referral}
	 */
	@POST
	@UnitOfWork
	@ApiOperation(value = "Create a referral", 
					response = Referral.class, 
					code = 201, 
					responseHeaders = @ResponseHeader(name = "Location", description = "Link to the newly created object", response = Object.class))
	@ApiResponses(value = {
			@ApiResponse(code = 406, message = "Accept Header/Version not supported"),
			@ApiResponse(code = 409, message = "Conflict - already exists") })
	public Response create(
			@ApiParam(required = true, value = "Object to be created") Referral referral,
			@HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader);
	
	 /**
	 * Update a {@link Referral}
	 *
	 * @param referral The {@link Referral}
	 * @param acceptHeader The accept header. Used to determine version of
	 API, corresponds to a value in {@link ApiVersion}
	 *
	 * @return {@link Response} with the {@link Referral}
	 */
	@PUT
	@UnitOfWork
	@ApiOperation(value = "Update a referral", response = Referral.class, produces = gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "updated"),
			@ApiResponse(code = 404, message = "not found"),
			@ApiResponse(code = 406, message = "Accept Header/Version not supported") })
	public Response update(
			@ApiParam(required = true, value = "the referral") Referral referral,
			@HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader);
}
