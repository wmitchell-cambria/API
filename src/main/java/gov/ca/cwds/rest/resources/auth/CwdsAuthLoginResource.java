package gov.ca.cwds.rest.resources.auth;

import static gov.ca.cwds.rest.core.Api.RESOURCE_OAUTH_CWDS;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = RESOURCE_OAUTH_CWDS)
@Path(value = RESOURCE_OAUTH_CWDS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CwdsAuthLoginResource {

  private Client client;

  /**
   * Constructor
   * 
   * @param client Jersey client
   */
  public CwdsAuthLoginResource(final Client client) {
    this.client = client;
  }



  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public String getToken(@FormParam("username") String username,
      @FormParam("password") String password) {

    Form formData = new Form();
    formData.param("username", username);
    formData.param("password", password);
    String url = "http://localhost:8090/cwds/password_type_token";
    Response response = client.target(url).request().post(Entity.form(formData));

    if (response.getStatus() != (Status.OK.getStatusCode())) {
      return response.readEntity(String.class);
    }
    return response.readEntity(String.class);
  }

  @GET
  // @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  public Response get(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {

    String url = "http://localhost:8090/find-childrens";

    Response response =
        client.target(url).request().header(HttpHeaders.AUTHORIZATION, "bearer " + token).get();

    if (response.getStatus() != (Status.OK.getStatusCode())) {
      Response.Status.UNAUTHORIZED.getStatusCode();
    }
    return response;
  }
}
