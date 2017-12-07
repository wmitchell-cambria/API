package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;

import gov.ca.cwds.inject.ReferralHoiServiceBackedResource;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author CWDS API Team
 *
 */
@Api(value = RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT)
@Path(value = RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReferralHistoryOfInvolvementResource {

  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate - resourceDelegate
   */
  @Inject
  public ReferralHistoryOfInvolvementResource(
      @ReferralHoiServiceBackedResource ResourceDelegate resourceDelegate) {
    super();
    this.resourceDelegate = resourceDelegate;
  }

  /**
   * 
   * @param id the id
   * 
   * @return the response
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find referrals history of involvement by clientId",
      response = HOIReferral.class, code = 200)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the client to find") String id) {
    return resourceDelegate.get(id);
  }

}
