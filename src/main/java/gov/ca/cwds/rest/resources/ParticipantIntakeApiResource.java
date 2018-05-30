package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.DATASOURCE_XA_NS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_PARTICIPANTS_INTAKE_API;

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

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.xa.XAUnitOfWork;
import gov.ca.cwds.inject.ParticipantIntakeApiServiceBackedResource;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.services.ParticipantIntakeApiService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link ParticipantIntakeApi}. It delegates functions
 * to {@link ParticipantIntakeApiService}.
 *
 * @author CWDS API Team
 */
@Api(value = RESOURCE_PARTICIPANTS_INTAKE_API, tags = RESOURCE_PARTICIPANTS_INTAKE_API)
@Path(value = RESOURCE_PARTICIPANTS_INTAKE_API)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParticipantIntakeApiResource {

  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   *
   * @param resourceDelegate The service.
   */
  @Inject
  public ParticipantIntakeApiResource(
      @ParticipantIntakeApiServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Finds an participant by id.
   *
   * @param id the id
   * @return the response
   */
  @XAUnitOfWork(value = DATASOURCE_XA_NS, readOnly = true)
  @GET
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find Participant", code = HttpStatus.SC_OK,
      response = ParticipantIntakeApi.class)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Participant to find") String id) {
    return resourceDelegate.get(id);
  }

  /**
   * Delete a participant
   *
   * @param id The id of the {@link ParticipantIntakeApi}
   * @return {@link Response}
   */
  @UnitOfWork(value = "ns")
  @DELETE
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found")})
  @ApiOperation(value = "Delete Participant", code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the Participant to delete") String id) {
    return resourceDelegate.delete(id);
  }

  /**
   * Create an {@link ParticipantIntakeApi}
   *
   * @param participant The {@link ParticipantIntakeApi}
   * @return The {@link Response}
   */
  @UnitOfWork(value = "ns")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Participant")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Participant", code = HttpStatus.SC_CREATED,
      response = ParticipantIntakeApi.class)
  public Response create(@Valid @ApiParam(required = true,
      value = "Participant JSON object") ParticipantIntakeApi participant) {
    return resourceDelegate.create(participant);
  }

  /**
   * Update an {@link ParticipantIntakeApi}
   *
   * @param id the id
   * @param participant {@link ParticipantIntakeApi}
   * @return The {@link Response}
   */
  @UnitOfWork(value = "ns")
  @PUT
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate Document")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Update Participant", code = HttpStatus.SC_NO_CONTENT,
      response = ParticipantIntakeApi.class)
  public Response update(
      @PathParam("id") @ApiParam(required = true, name = "id", value = "Participant id") String id,
      @Valid @ApiParam(required = true,
          value = "Participant JSON object") ParticipantIntakeApi participant) {
    return resourceDelegate.update(id, participant);
  }

}
