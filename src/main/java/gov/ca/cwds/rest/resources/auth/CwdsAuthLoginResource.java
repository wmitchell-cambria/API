package gov.ca.cwds.rest.resources.auth;

import static gov.ca.cwds.rest.core.Api.RESOURCE_OAUTH_CWDS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = RESOURCE_OAUTH_CWDS)
@Path(value = RESOURCE_OAUTH_CWDS)
@Produces(MediaType.APPLICATION_JSON)
public class CwdsAuthLoginResource {
  private static Map<String, String> TOKEN_MAP = new HashMap<>();

  private Client client;

  /**
   * Constructor
   * 
   * @param client Jersey client
   */
  public CwdsAuthLoginResource(final Client client) {
    this.client = client;
  }

  protected boolean validateToken(String token) {

    if (TOKEN_MAP.isEmpty() || TOKEN_MAP.get("token") == null
        || !token.equalsIgnoreCase(TOKEN_MAP.get("token"))) {
      return false;
    }

    return true;
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 401, message = "unauthirized")})
  @ApiOperation(value = "access token:", response = String.class, code = 200)
  public Response getToken(@FormParam("username") String username,
      @FormParam("password") String password) throws JsonProcessingException {

    Form formData = new Form();
    formData.param("username", username);
    formData.param("password", password);
    String url = "http://localhost:9080/cwds/password_type_token";
    Response response = client.target(url).request().post(Entity.form(formData));

    System.out.println(response.getStatus());
    String token = response.readEntity(String.class);
    TOKEN_MAP.put("token", token);

    ObjectMapper mapper = new ObjectMapper();

    return Response.ok(mapper.writeValueAsString(TOKEN_MAP)).build();
  }

  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  public Response get(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
    boolean flag = validateToken(token);
    String payload = "";
    if (flag) {
      Child child1 = new Child();
      child1.setName("cherry");
      child1.setAge(2);

      Child child2 = new Child();
      child2.setName("charan");
      child2.setAge(3);
      ObjectMapper mapper = new ObjectMapper();
      try {
        payload = mapper.writeValueAsString(Arrays.asList(child1, child2));
      } catch (JsonProcessingException e) {
        throw new RuntimeException("unable to parse to json");
      }
    } else {
      return Response.status(Status.UNAUTHORIZED).build();
    }
    return Response.ok(payload).build();
  }

}


