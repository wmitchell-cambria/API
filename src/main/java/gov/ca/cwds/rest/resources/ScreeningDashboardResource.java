package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.xa.XAUnitOfWork;
import gov.ca.cwds.rest.api.domain.ScreeningDashboard;
import gov.ca.cwds.rest.resources.converter.ResponseConverter;
import gov.ca.cwds.rest.services.ScreeningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link Resource}. It delegates functions to
 * {@link ScreeningService}.
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href= "https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 *
 * @author CWDS API Team
 */
@Api(value = RESOURCE_SCREENINGS, tags = {RESOURCE_SCREENINGS})
@Path(value = RESOURCE_SCREENINGS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScreeningDashboardResource {

  private ScreeningService screeningService;

  /**
   * Constructor
   *
   * @param screeningService The service to delegate to.
   */
  @Inject
  public ScreeningDashboardResource(ScreeningService screeningService) {
    this.screeningService = screeningService;
  }

  /**
   * Get list of Screenings (dashboard).
   * 
   * @return the {@link Response}
   */
  @XAUnitOfWork(readOnly = true)
  @GET
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate Screening")})
  @ApiOperation(value = "Returns screening summary", code = HttpStatus.SC_OK,
      response = ScreeningDashboard.class)
  public Response getScreenings() {
    gov.ca.cwds.rest.api.Response screenings = screeningService.findScreeningDashboard();
    return new ResponseConverter().withDataResponse(screenings);
  }

}
