package gov.ca.cwds.rest.resources.auth;

import static gov.ca.cwds.rest.core.Api.DATASOURCE_XA_CMS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_AUTHORIZE;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.xa.XAUnitOfWork;
import gov.ca.cwds.inject.AuthorizationServiceBackedResource;
import gov.ca.cwds.rest.api.domain.auth.AuthorizationRequest;
import gov.ca.cwds.rest.api.domain.auth.AuthorizationResponse;
import gov.ca.cwds.rest.resources.SimpleResourceDelegate;
import gov.ca.cwds.rest.services.auth.AuthorizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource to determine if the logged-in staff person is authorized to access given clients.
 *
 * @author CWDS API Team
 */
@Api(value = RESOURCE_AUTHORIZE, tags = {RESOURCE_AUTHORIZE})
@Path(value = RESOURCE_AUTHORIZE)
public class AuthorizationResource {

  private SimpleResourceDelegate<String, AuthorizationRequest, AuthorizationResponse, AuthorizationService> simpleResourceDelegate;

  /**
   * Constructor
   * 
   * @param simpleResourceDelegate - simpleResourceDelegate
   */
  @Inject
  public AuthorizationResource(
      @AuthorizationServiceBackedResource SimpleResourceDelegate<String, AuthorizationRequest, AuthorizationResponse, AuthorizationService> simpleResourceDelegate) {
    super();
    this.simpleResourceDelegate = simpleResourceDelegate;
  }

  /**
   * Determine if the logged-in user is authorized to access the client identified by given id.
   *
   * @param id the id
   * @return the response
   */
  @XAUnitOfWork(DATASOURCE_XA_CMS)
  @GET
  @Path("/client/{id}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 403, message = "Forbidden"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Determine if client is authorized", response = Response.class, code = 200)
  public Response authorizeClientAccess(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "ID of the client to authorize") String id) {
    simpleResourceDelegate.handle(new AuthorizationRequest(id));
    return Response.status(Response.Status.OK).build();
  }

}
