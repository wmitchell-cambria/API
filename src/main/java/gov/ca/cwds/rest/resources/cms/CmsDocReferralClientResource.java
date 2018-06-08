package gov.ca.cwds.rest.resources.cms;

import static gov.ca.cwds.rest.core.Api.DATASOURCE_XA_CMS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_CMS_DOC_REFRRAL_CLIENT;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
import gov.ca.cwds.inject.CmsDocumentReferralClientServiceBackedResource;
import gov.ca.cwds.rest.api.domain.cms.CmsDocReferralClient;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link CmsDocReferralClient}. It delegates functions
 * to {@link ResourceDelegate}. It decorates the {@link ResourceDelegate} not in functionality but
 * with @see <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_CMS_DOC_REFRRAL_CLIENT)
@Path(value = RESOURCE_CMS_DOC_REFRRAL_CLIENT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CmsDocReferralClientResource {

  private TypedResourceDelegate<String, CmsDocReferralClient> typedResourceDelegate;

  /**
   * Constructor
   * 
   * @param typedResourceDelegate The typedResourceDelegate to delegate to.
   */
  @Inject
  public CmsDocReferralClientResource(
      @CmsDocumentReferralClientServiceBackedResource TypedResourceDelegate<String, CmsDocReferralClient> typedResourceDelegate) {
    this.typedResourceDelegate = typedResourceDelegate;
  }

  /**
   * Finds a document by id (doc_handle).
   * 
   * @param id the id
   * @return the response
   */
  @XAUnitOfWork(value = DATASOURCE_XA_CMS, readOnly = true)
  @GET
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find document by id (doc_handle)", response = CmsDocReferralClient.class,
      code = 200)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id (doc_handle) of the Document to find") String id) {
    return typedResourceDelegate.get(id);
  }

  /**
   * Delete a document by id.
   * 
   * @param id The id of the {@link CmsDocReferralClient}
   * @return {@link Response}
   */
  @XAUnitOfWork(DATASOURCE_XA_CMS)
  @DELETE
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized")})
  @ApiOperation(hidden = true, value = "Delete Document - not currently implemented",
      code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(
      @PathParam("id") @ApiParam(required = true, value = "id of Document to delete") String id) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

  /**
   * Create an {@link CmsDocReferralClient}.
   * 
   * @param doc The {@link CmsDocReferralClient}
   * @return The {@link CmsDocReferralClient}
   */
  @XAUnitOfWork(DATASOURCE_XA_CMS)
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Document")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Document", code = HttpStatus.SC_CREATED,
      response = CmsDocReferralClient.class)
  public Response create(@Valid @ApiParam(hidden = false,
      required = true) gov.ca.cwds.rest.api.domain.cms.CmsDocReferralClient doc) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

  /**
   * Update an {@link CmsDocReferralClient}.
   * 
   * @param id the id
   * @param doc {@link CmsDocReferralClient}
   * @param acceptHeader The accept header.
   * @return The {@link Response}
   */
  @XAUnitOfWork(DATASOURCE_XA_CMS)
  @PUT
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate Document")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(hidden = true, value = "Update Document", code = HttpStatus.SC_NO_CONTENT,
      response = Object.class)
  public Response update(
      @PathParam("id") @ApiParam(required = true, name = "id",
          value = "The id of the Document to update") String id,
      @ApiParam(hidden = true) CmsDocReferralClient doc,
      @HeaderParam("Accept") @ApiParam(hidden = true) String acceptHeader) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

}
