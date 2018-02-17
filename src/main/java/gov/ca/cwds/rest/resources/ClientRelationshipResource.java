package gov.ca.cwds.rest.resources;

import com.google.inject.Inject;
import gov.ca.cwds.inject.RelationshipServiceBackedResource;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CLIENT;

/**
 * A resource providing a RESTful interface for {@link Resource}. It delegates functions
 * to {@link ServiceBackedResourceDelegate}. It decorates the {@link ServiceBackedResourceDelegate}
 * not in functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 *
 * @author CWDS API Team
 */
@Api(value = RESOURCE_CLIENT, tags = {RESOURCE_CLIENT})
@Path(value = RESOURCE_CLIENT )
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientRelationshipResource {
    private TypedResourceDelegate<String, Relationship> resourceDelegate;

    /**
     * Constructor
     *
     * @param resourceDelegate The resourceDelegate to delegate to.
     */
    @Inject
    public ClientRelationshipResource(
            @RelationshipServiceBackedResource TypedResourceDelegate<String, Relationship> resourceDelegate) {
        this.resourceDelegate = resourceDelegate;
    }

    /**
     * Finds a Relationship for a Client id.
     *
     * @param id the id
     *
     * @return the response
     */
    @UnitOfWork(value = "cms")
    @GET
    @Path("/{id}/relationships" )
    @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 406, message = "Accept Header not supported")})

    @ApiOperation(value = "Find referral by id", response = Relationship.class, code = 200)

    public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
            value = "The id of the client to find relationships for") String id) {
        return resourceDelegate.get(id);
    }
}
