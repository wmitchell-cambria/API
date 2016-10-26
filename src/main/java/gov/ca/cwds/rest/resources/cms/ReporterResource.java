package gov.ca.cwds.rest.resources.cms;

import static gov.ca.cwds.rest.core.Api.RESOURCE_REPORTER;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import gov.ca.cwds.rest.api.domain.legacy.Reporter;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link Reporter}. It delegates functions to
 * {@link ResourceDelegate}. It decorates the {@link ResourceDelegate} not in functionality but
 * with @see <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_REPORTER)
@Path(value = RESOURCE_REPORTER)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReporterResource {
  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  public ReporterResource(ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Finds an reporter by id.
   * 
   * @param id the id
   * 
   * @return the response
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find reporter by id", response = Reporter.class, code = 200)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Reporter to find") String id) {
    return resourceDelegate.get(id);
  }

  /**
   * Delete an reporter by id.
   * 
   * @param id The id of the {@link Reporter}
   * 
   * @return {@link Response}
   */
  @UnitOfWork(value = "cms")
  @DELETE
  @Path("/{id}")
  @ApiOperation(value = "Delete Reporter", code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(
      @PathParam("id") @ApiParam(required = true, value = "id of Reporter to delete") String id) {
    return resourceDelegate.delete(id);
  }

  /**
   * Create an {@link Reporter}
   * 
   * @param reporter The {@link Reporter}
   * 
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Reporter")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Reporter", code = HttpStatus.SC_CREATED, response = Reporter.class)
  public Response create(@Valid @ApiParam(hidden = false, required = true) Reporter reporter) {
    return resourceDelegate.create(reporter);
  }

  /**
   * Update an {@link Reporter}
   * 
   * @param id the id
   * @param reporter {@link Reporter}
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @PUT
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate Reporter")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Update Reporter", code = HttpStatus.SC_NO_CONTENT, response = Object.class)
  public Response update(
      @PathParam("id") @ApiParam(required = true, name = "id",
          value = "The id of the Reporter to update") String id,
      @Valid @ApiParam(hidden = false, required = true) Reporter reporter) {
    return resourceDelegate.update(id, reporter);
  }
}
