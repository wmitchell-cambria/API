package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.inject.ScreeningParticipantServiceBackedResource;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {{@link ParticipantIntakeApi}. It delegates
 * functions to {@link TypedResourceDelegate}. It decorates the {@link TypedResourceDelegate} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_SCREENINGS, tags = {RESOURCE_SCREENINGS})
@Path(value = RESOURCE_SCREENINGS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScreeningParicipantResource {

  private TypedResourceDelegate<String, ParticipantIntakeApi> typedResourceDelegate;

  /**
   * @param typedResourceDelegate - typedResourceDelegate
   */
  @Inject
  public ScreeningParicipantResource(
      @ScreeningParticipantServiceBackedResource TypedResourceDelegate<String, ParticipantIntakeApi> typedResourceDelegate) {
    this.typedResourceDelegate = typedResourceDelegate;
  }

  /**
   * Create an {@link ParticipantIntakeApi}.
   * 
   * @param id - screening id
   * @param participant - The participant to create
   * @return - The {@link Response}
   */
  @UnitOfWork(value = "cms")
  @POST
  @Path("/{id}/participant")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Participant")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Participant with the Screening Id", code = HttpStatus.SC_CREATED,
      response = ParticipantIntakeApi.class)
  public Response create(
      @PathParam("id") @ApiParam(required = true, name = "id", value = "The Screenig id") String id,
      @Valid @ApiParam(required = true,
          value = "Participant JSON object") ParticipantIntakeApi participant) {
    return typedResourceDelegate.create(participant);
  }

}
