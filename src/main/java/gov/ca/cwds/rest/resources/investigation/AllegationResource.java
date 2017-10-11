package gov.ca.cwds.rest.resources.investigation;

import static gov.ca.cwds.rest.core.Api.RESOURCE_INVESTIGATIONS;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.inject.InvestigationAllegationServiceBackedResource;
import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.api.domain.investigation.AllegationList;
import gov.ca.cwds.rest.api.domain.investigation.Investigation;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {{@link Investigation}. It delegates functions to
 * {@link TypedResourceDelegate}. It decorates the {@link TypedResourceDelegate} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_INVESTIGATIONS)
@Path(value = RESOURCE_INVESTIGATIONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AllegationResource {
  private TypedResourceDelegate<String, AllegationList> typedResourceDelegate;
  private TypedResourceDelegate<String, Allegation> typedResourceDelegateForUpdate;

  /**
   * Constructor
   *
   * @param typedResourceDelegate The typedResourceDelegate to delegate to.
   */
  @Inject
  public AllegationResource(
      @InvestigationAllegationServiceBackedResource TypedResourceDelegate<String, AllegationList> typedResourceDelegate) {
    this.typedResourceDelegate = typedResourceDelegate;
  }

  /**
   * Create an {@link AllegationList}.
   *
   * @param id - CMS iId of the Referral or Case the Allegations are for
   * @param allegations - The allegations to create
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @POST
  @Path("/{id}/allegations")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate allegation")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create allegations", code = HttpStatus.SC_CREATED,
      response = AllegationList.class)
  public Response create(
      @PathParam("id") @ApiParam(required = true, name = "id",
          value = "The CMS id of the Referral or Case") String id,
      @Valid @ApiParam(hidden = false, required = true) AllegationList allegations) {
    return typedResourceDelegate.create(allegations);
  }

  /**
   * Find {@link Allegation}.
   *
   * @param id - CMS Id of Case or Referral
   * @return - allegations of investigation
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/{id}/allegations")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists")})
  @ApiOperation(value = "Find allegations of investigation", code = HttpStatus.SC_OK,
      response = AllegationList.class)
  public Response find(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The CMS Id of the Case or Referral") String id) {
    return typedResourceDelegate.get(id);

  }

  /**
   * Update {@link Allegation}.
   *
   * @param id - CMS Id of Case or Referral
   * @param allegationId - CMS Id of Allegation
   * @param allegationToUpdate - allegations to update
   * @return - updated allegations
   */
  @UnitOfWork(value = "cms")
  @PUT
  @Path("/{id}/allegations/{allegation_id}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate allegation")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Update allegations", code = HttpStatus.SC_OK, response = Allegation.class)
  public Response update(
      @PathParam("id") @ApiParam(required = true, name = "id",
          value = "The CMS Id of the Referral or Case ") String id,
      @PathParam("allegation_id") @ApiParam(required = true, name = "allegation_id",
          value = "The CMS Id of the Allegation") String allegationId,
      @Valid @ApiParam(hidden = false, required = true) Allegation allegationToUpdate) {
    return typedResourceDelegateForUpdate.update(id, allegationToUpdate);
  }
}
