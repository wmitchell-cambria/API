package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.SCREENING_RELATIONSHIPS;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.xa.XAUnitOfWork;
import gov.ca.cwds.inject.ScreeningRelationshipServiceBackedResource;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link ScreeningRelationshipResource}. It delegates
 * functions to {@link ServiceBackedResourceDelegate}. It decorates the
 * {@link ServiceBackedResourceDelegate} not in functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 *
 * @author CWDS API Team
 */
@Api(value = SCREENING_RELATIONSHIPS, tags = {SCREENING_RELATIONSHIPS})
@Path(value = SCREENING_RELATIONSHIPS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScreeningRelationshipResource {

  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   *
   * @param resourceDelegate The resourceDelegate to delegate to.
   */
  @Inject
  public ScreeningRelationshipResource(
      @ScreeningRelationshipServiceBackedResource ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * Create an {@link ScreeningRelationship}.
   *
   * @param screeningRelationship The {@link ScreeningRelationship}
   * @return The {@link Response}
   */
  @XAUnitOfWork
  @POST
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate ScreeningToReferral")})
  @ApiOperation(value = "Create Screening Relationship", code = HttpStatus.SC_CREATED,
      response = ScreeningRelationship.class)
  public Response create(@Valid @ApiParam(hidden = false,
      required = true) ScreeningRelationship screeningRelationship) {
    return resourceDelegate.create(screeningRelationship);
  }

}
