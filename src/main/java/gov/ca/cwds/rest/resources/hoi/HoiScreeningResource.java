package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_HOI_SCREENINGS;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.xa.XAUnitOfWork;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningResponse;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import gov.ca.cwds.rest.resources.converter.ResponseConverter;
import gov.ca.cwds.rest.services.hoi.HOIScreeningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link HOIScreening}. It delegates functions to
 * {@link TypedResourceDelegate}. It decorates the {@link TypedResourceDelegate} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 *
 * @author CWDS API Team
 */
@Api(value = RESOURCE_HOI_SCREENINGS)
@Path(value = RESOURCE_HOI_SCREENINGS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HoiScreeningResource {

  private HOIScreeningService hoiScreeningService;

  /**
   * Constructor.
   *
   * @param hoiScreeningService - hoiScreeningService
   */
  @Inject
  public HoiScreeningResource(HOIScreeningService hoiScreeningService) {
    this.hoiScreeningService = hoiScreeningService;
  }

  /**
   * Finds history of involvement by screening id.
   *
   * @param clientIds - clientIds
   * @return the hoi screenings
   */
  @XAUnitOfWork(readOnly = true, transactional = false)
  @GET
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find history of involvement by screening id",
      response = HOIScreeningResponse.class)
  public Response get(@QueryParam("clientIds") @ApiParam(required = true, name = "clientIds",
      value = "List of Client Id-s") List<String> clientIds) {
    gov.ca.cwds.rest.api.Response clients =
        hoiScreeningService.handleFind(new HOIRequest(clientIds));
    return new ResponseConverter().withDataResponse(clients);
  }

}
