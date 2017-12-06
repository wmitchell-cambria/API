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
import gov.ca.cwds.rest.api.domain.hoi.ReferralHOI;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link ReferralHOI}. It delegates functions to
 * {@link TypedResourceDelegate}. It decorates the {@link TypedResourceDelegate} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT)
@Path(value = RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReferralHOIResource {

  private TypedResourceDelegate<String, ReferralHOI> typedResourceDelegate;

  /**
   * Constructor
   * 
   * @param typedResourceDelegate - typedResourceDelegate
   */
  @Inject
  public ReferralHOIResource(
      @ReferralHoiServiceBackedResource TypedResourceDelegate<String, ReferralHOI> typedResourceDelegate) {
    super();
    this.typedResourceDelegate = typedResourceDelegate;
  }

  /**
   * Finds an referrals HOI by client id.
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
      response = ReferralHOI.class, code = 200)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the client to find") String id) {
    return typedResourceDelegate.get(id);
  }

}
