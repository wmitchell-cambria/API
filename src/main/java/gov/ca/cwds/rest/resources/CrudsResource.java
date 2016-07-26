package gov.ca.cwds.rest.resources;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import gov.ca.cwds.rest.core.Api;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Interface for Resources which provide CRUDS endpoints.
 *  
 * @author CWDS API Team
 *
 * @param <T>	The {@link PersistentObject} the CRUDS endpoints work on
 */
public interface CrudsResource<T extends PersistentObject> extends Resource {
	
	/**
	 * Gets a {@link PersistentObject} based on the given id.
	 * 
	 * @param id
	 *            The id of the {@link PersistentObject}
	 * @param acceptHeader
	 *            The accept header. Used to determine version of API,
	 *            corresponds to a value in {@link Api.Version}
	 * 
	 * @return {@link Response} with the {@link PersistentObject}
	 */
	@GET
	@UnitOfWork
	@Path("/{id}")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 406, message = "Accept Header/Version not supported") })
	public Response get(
			@PathParam("id") @ApiParam(required = true, value = "id of object to get") String id,
			@HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader);

	/**
	 * Delete a {@link PersistentObject}
	 * 
	 * @param id
	 *            The id of the {@link PersistentObject}
	 * @param acceptHeader
	 *            The accept header. Used to determine version of API,
	 *            corresponds to a value in {@link Api.Version}
	 * 
	 * @return {@link Response} with the {@link PersistentObject}
	 */
	@DELETE
	@UnitOfWork
	@Path("/{id}")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 406, message = "Accept Header/Version not supported") })
	public Response delete(
			@PathParam("id") @ApiParam(required = true, value = "id of object to delete") String id,
			@HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader);

	/**
	 * Create a {@link PersistentObject}
	 * 
	 * @param persistentObject
	 *            The {@link PersistentObject}
	 * @param acceptHeader
	 *            The accept header. Used to determine version of API,
	 *            corresponds to a value in {@link Api.Version}
	 * @param uriInfo	The {@link UriInfo}           
	 * 
	 * @return {@link Response} with the {@link PersistentObject}
	 */
	@POST
	@UnitOfWork
	@ApiResponses(value = {
			@ApiResponse(code = 406, message = "Accept Header/Version not supported"),
			@ApiResponse(code = 409, message = "Conflict - already exists") })
	public Response create(
			@ApiParam(required = true, value = "Object to be created") T persistentObject,
			@HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader,
			@Context UriInfo uriInfo);
	
	/**
	 * Update a {@link PersistentObject}
	 *
	 * @param persistentObject The {@link PersistentObject}
	 * @param acceptHeader The accept header. Used to determine version of API, corresponds to a value in {@link Api.Version}
	 *
	 * @return {@link Response} with the {@link PersistentObject}
	 */
	@PUT
	@UnitOfWork
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "not found"),
			@ApiResponse(code = 406, message = "Accept Header/Version not supported") })
	public Response update(
			@ApiParam(required = true, value = "the object to be updated") T persistentObject,
			@HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader);
}
