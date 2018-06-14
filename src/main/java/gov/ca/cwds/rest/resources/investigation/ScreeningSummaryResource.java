package gov.ca.cwds.rest.resources.investigation;

import static gov.ca.cwds.rest.core.Api.RESOURCE_INVESTIGATIONS;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.xa.XAUnitOfWork;
import gov.ca.cwds.inject.ScreeningSummaryServiceBackedResource;
import gov.ca.cwds.rest.api.domain.investigation.ScreeningSummary;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link ScreeningSummary}. It delegates functions to
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
public class ScreeningSummaryResource {

  private TypedResourceDelegate<String, ScreeningSummary> typedResourceDelegate;

  /**
   * Constructor
   *
   * @param typedResourceDelegate The typedResourceDelegate to delegate to.
   */
  @Inject
  public ScreeningSummaryResource(
      @ScreeningSummaryServiceBackedResource TypedResourceDelegate<String, ScreeningSummary> typedResourceDelegate) {
    this.typedResourceDelegate = typedResourceDelegate;
  }


  /**
   * Find an {@link ScreeningSummary}.
   *
   * @param id The Referral id that this Screening Summary is for
   * @return The {@link Response}
   */
  @XAUnitOfWork
  @GET
  @Path("/{id}/screening")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists")})
  @ApiOperation(value = "Find Screening Summary by Referral Id", code = HttpStatus.SC_OK,
      response = ScreeningSummary.class)
  public Response find(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Referral ") String id) {
    return typedResourceDelegate.get(id);
  }

}
