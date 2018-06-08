package gov.ca.cwds.rest.resources.investigation;

import static gov.ca.cwds.rest.core.Api.DATASOURCE_XA_CMS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_INVESTIGATIONS;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.xa.XAUnitOfWork;
import gov.ca.cwds.inject.PeopleServiceBackedResource;
import gov.ca.cwds.rest.api.domain.investigation.Investigation;
import gov.ca.cwds.rest.api.domain.investigation.People;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link Investigation}. It delegates functions to
 * {@link TypedResourceDelegate}. It decorates the {@link TypedResourceDelegate} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_INVESTIGATIONS)
@Path(value = RESOURCE_INVESTIGATIONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class PeopleResource {

  private TypedResourceDelegate<String, People> typedResourceDelegate;

  /**
   * Constructor
   *
   * @param typedResourceDelegate The typedResourceDelegate to delegate to.
   */
  @Inject
  public PeopleResource(
      @PeopleServiceBackedResource TypedResourceDelegate<String, People> typedResourceDelegate) {
    this.typedResourceDelegate = typedResourceDelegate;
  }

  /**
   * Find an {@link Investigation}.
   *
   * @param id - CMS Id of Case or Referral
   * @return - people of investigation
   */
  @XAUnitOfWork(value = DATASOURCE_XA_CMS, readOnly = true)
  @GET
  @Path("/{id}/people")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists")})
  @ApiOperation(value = "Find people of investigation", code = HttpStatus.SC_OK,
      response = People.class)
  public Response find(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The CMS Id of the Case or Referral ") String id) {
    return typedResourceDelegate.get(id);
  }

}
