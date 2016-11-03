package gov.ca.cwds.rest.resources.cms;

import static gov.ca.cwds.rest.core.Api.RESOURCE_REFERRAL_CLIENT;

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

import gov.ca.cwds.rest.api.domain.legacy.ReferralClient;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Implementation of {@link ReferralClientResource} delegating work to 
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_REFERRAL_CLIENT)
@Path(value = RESOURCE_REFERRAL_CLIENT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)


public class ReferralClientResource {

  private ResourceDelegate resourceDelegate;

  /**
   * Constructor
   * 
   * @param resourceDelegate - the resourceDelegate to delegate to.
   */
  public ReferralClientResource(ResourceDelegate resourceDelegate) {
    this.resourceDelegate = resourceDelegate;

  }
  // @SuppressWarnings("unused")
  // private static final Logger LOGGER = LoggerFactory.getLogger(ReferralClientResourceImpl.class);
  //
  // private CrudsResource<ReferralClient> crudsResource;
  //
  // public ReferralClientResourceImpl(ServiceEnvironment serviceEnvironment,
  // CrudsResource<ReferralClient> crudsResource) {
  // super(serviceEnvironment, ReferralClientService.class);
  // this.crudsResource = crudsResource;
  // }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.resources.CrudsResource#get(java.lang.String, java.lang.String)
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/id")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not fournd"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find ReferralClient by composite id of referralId and clientId",
      response = ReferralClient.class)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "ReferralClient has a composite key of referralId and clientId") String id) {
    // public Response get(@ApiParam(required = true, allowMultiple=true, value = "ReferralClient
    // has a composite key of referralId and clientId",
    // example="referralId=abcdefgh,clientId=td89slaz") String id, String acceptHeader) {
    return resourceDelegate.get(id);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.resources.CrudsResource#delete(java.lang.String, java.lang.String)
   */
  @UnitOfWork(value = "cms")
  @DELETE
  @Path("/{id}")
  @ApiOperation(value = "Delete ReferralClient by composite id of referralId and clientId",
      code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(@ApiParam(required = true, allowMultiple = true,
      value = "ReferralClient has a composite key of referralId and clientId",
      example = "referralId=abcdefgh,clientId=td89slaz") String id) {
    return resourceDelegate.delete(id);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.resources.CrudsResource#update(gov.ca.cwds.rest.api.domain.DomainObject,
   * java.lang.String)
   */

  @UnitOfWork(value = "cms")
  @PUT
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate ReferralClient")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Update ReferralClient", code = HttpStatus.SC_NO_CONTENT,
      response = Object.class)
  public Response update(
      @PathParam("id") @ApiParam(required = true, name = "id",
          value = "The id of ReferralClient to update") String id,
      @Valid @ApiParam(hidden = false, required = true) ReferralClient referralClient) {
    return resourceDelegate.update(id, referralClient);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.resources.CrudsResource#create(gov.ca.cwds.rest.api.domain.DomainObject,
   * java.lang.String, javax.ws.rs.core.UriInfo)
   */
  @UnitOfWork(value = "cms")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Referral")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Referral", code = HttpStatus.SC_CREATED,
      response = ReferralClient.class)
  public Response create(
      @Valid @ApiParam(hidden = false, required = true) ReferralClient referral) {
    return resourceDelegate.create(referral);
  }
}
