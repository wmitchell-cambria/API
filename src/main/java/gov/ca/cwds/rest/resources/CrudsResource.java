package gov.ca.cwds.rest.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;

import gov.ca.cwds.rest.api.domain.DomainObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

/**
 * Interface for Resources which provide CRUDS end points. The implementation of
 * this is not meant to be exposed as an actual end point though. It is meant to
 * be delegated to by an exposed resource.
 * 
 * @author CWDS API Team
 *
 * @param <T>
 *            The {@link DomainObject} the CRUDS end points work on.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CrudsResource<T extends DomainObject> extends Resource {

	/**
	 * Gets a {@link DomainObject} based on the given id.
	 * 
	 * @param id
	 *            The id of the {@link DomainObject}
	 * @param acceptHeader
	 *            The accept header.
	 * 
	 * @return {@link Response} with the {@link DomainObject}
	 */
	@GET
	@Path("/{id}")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 406, message = "Accept Header not supported") })
	@ApiOperation(hidden = true, value = "Find DomainObject by id", response = DomainObject.class)
	public Response get(@PathParam("id") @ApiParam(required = true, value = "id of object to get") String id,
			@HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader);

	/**
	 * Delete a {@link DomainObject}
	 * 
	 * @param id
	 *            The id of the {@link DomainObject}
	 * @param acceptHeader
	 *            The accept header.
	 * 
	 * @return {@link Response} with the {@link DomainObject}
	 */
	@DELETE
	@Path("/{id}")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 406, message = "Accept Header not supported") })
	@ApiOperation(hidden = true, value = "Delete DomainObject", code = HttpStatus.SC_OK, response = Object.class)
	public Response delete(@PathParam("id") @ApiParam(required = true, value = "id of object to delete") String id,
			@HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader);

	/**
	 * Create a {@link DomainObject}
	 * 
	 * @param domainObject
	 *            The {@link DomainObject}
	 * @param acceptHeader
	 *            The accept header.
	 * @param uriInfo
	 *            The {@link UriInfo}
	 * @param response
	 *            The servlet response
	 * 
	 * @return {@link gov.ca.cwds.rest.api.domain.ApiResponse} an
	 *         {@link gov.ca.cwds.rest.api.domain.ApiResponse} for the newly
	 *         create {@link DomainObject}
	 */
	@POST
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Unable to process JSON"),
			@ApiResponse(code = 406, message = "Accept Header not supported"),
			@ApiResponse(code = 409, message = "Conflict - already exists"),
			@ApiResponse(code = 422, message = "Unable to process entity") })
	@Consumes(value = MediaType.APPLICATION_JSON)
	@ApiOperation(hidden = true, value = "Create DomainObject", response = Object.class, code = HttpStatus.SC_CREATED, responseHeaders = @ResponseHeader(name = "Location", description = "Link to the newly created object", response = Object.class))
	public gov.ca.cwds.rest.api.domain.ApiResponse<T> create(
			@ApiParam(required = true, value = "Object to be created") @Valid T domainObject,
			@HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader, @Context UriInfo uriInfo,
			@Context HttpServletResponse response);

	/**
	 * Update a {@link DomainObject}
	 *
	 * @param domainObject
	 *            The {@link DomainObject}
	 * @param acceptHeader
	 *            The accept header.
	 *
	 * @return {@link Response} with a {@link DomainObject}
	 */
	@PUT
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Unable to process JSON"),
			@ApiResponse(code = 404, message = "not found"),
			@ApiResponse(code = 406, message = "Accept Header not supported"),
			@ApiResponse(code = 422, message = "Unable to validate entity") })
	@Consumes(value = MediaType.APPLICATION_JSON)
	@ApiOperation(hidden = true, value = "Update DomainObject", code = HttpStatus.SC_NO_CONTENT, response = Object.class)
	public Response update(@ApiParam(required = true, value = "the object to be updated") T domainObject,
			@HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader);
}
