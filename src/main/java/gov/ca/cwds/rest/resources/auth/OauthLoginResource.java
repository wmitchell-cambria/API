package gov.ca.cwds.rest.resources.auth;

import static gov.ca.cwds.rest.core.Api.RESOURCE_OAUTH_CWDS;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = RESOURCE_OAUTH_CWDS)
@Path(value = RESOURCE_OAUTH_CWDS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OauthLoginResource {

  private static final Logger LOG = LoggerFactory.getLogger(OauthLoginResource.class);

  private static final String THIS_PROTOCOL = "http://";
  private static final String THIS_HOST = "54.70.247.41";
  private static final String THIS_PATH = "/cwds/";
  private static final String BASE_URL = THIS_PROTOCOL + THIS_HOST + THIS_PATH;

  private static final String SAF_BASE = "https://sectest.dss.ca.gov/web1/dss_saf/auth/v2/";
  private static final String SAF_AUTH_URL = SAF_BASE + "oauth2/authorize";
  private static final String SAF_TOKEN_URL = SAF_BASE + "token";
  private static final String SAF_VALIDATE_URL =
      "https://sectest.dss.ca.gov/web1/dss_saf/data/v2/api/client/41/auth/validatetoken";

  // TODO: Don't store credentials in plain text or unprotected memory.
  private static final String SAF_CLIENT_ID = "ec591707-be4e-4de5-8fc7-7cff83b04f65";
  private static final String SAF_CLIENT_SECRET = "5Gp7dQ6Fh7BMs0HlkiQ99";

  // Registered with SAF.
  private static final String SAF_CALLBACK = BASE_URL + "callback";
  private static final String reponseType = "code";
  private static final String basicProfile = "basic_profile";

  // Jersey client.
  // final Client client = new
  // JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration()).build(getName());

  private Client client;
  private ResourceDelegate resourceDelegate;


  // /**
  // * Constructor
  // *
  // * @param client Jersey client
  // * @param resourceDelegate The resourceDelegate to delegate to.
  // */
  // public OauthLoginResource(final Client client, final ResourceDelegate resourceDelegate) {
  // this.client = client;
  // this.resourceDelegate = resourceDelegate;
  // }

  /**
   * Constructor
   * 
   * @param client Jersey client
   */
  public OauthLoginResource(final Client client) {
    this.client = client;
  }

  /**
   * key = "state" sent to SAF.
   */
  protected Map<String, SAFAuthBean> pendingAuthByState =
      new ConcurrentHashMap<String, SAFAuthBean>();

  /**
   * key = code from SAF callback.
   */
  protected Map<String, SAFAuthBean> pendingAuthByCode =
      new ConcurrentHashMap<String, SAFAuthBean>();

  /**
   * key = access token.
   */
  protected Map<String, SAFAuthBean> authByAccessToken =
      new ConcurrentHashMap<String, SAFAuthBean>();

  /**
   * 
   * @return unique "state" value, used to track OAuth2 authentication requests.
   */
  protected String getUniqueId() {
    return UUID.randomUUID().toString();
  }

  /**
   * Validate an unknown access token with SAF.
   * 
   * @param token bearer token to validate
   * @return true = token is valid
   */
  protected boolean validateToken(String token) {
    Response response = client.target(SAF_VALIDATE_URL).request()
        .header(HttpHeaders.AUTHORIZATION, "bearer " + token)
        .post(Entity.entity("bearer " + token, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

    if (response.getStatus() != (Status.OK.getStatusCode())) {
      LOG.info("TOKEN VALIDATION FAILED! " + response);
      return false;
    }

    return true;
  }

  /**
   * Finds a document by id (doc_handle).
   * 
   * @param id the id
   * 
   * @return the response
   */
  @UnitOfWork(value = "cms")
  // @GET
  // @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id (doc_handle) of the Document to find") String id) {
    return resourceDelegate.get(id);
  }



  /**
   * Delete a document by id.
   * 
   * @param id The id of the {@link CmsDocument}
   * 
   * @return {@link Response}
   */
  // @DELETE
  // @Path("/{id}")
  @ApiOperation(hidden = true, value = "Delete Document - not currently implemented",
      code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(
      @PathParam("id") @ApiParam(required = true, value = "id of Document to delete") String id) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

  /**
   * Create an {@link CmsDocument}
   * 
   * @param doc The {@link CmsDocument}
   * 
   * @return The {@link CmsDocument}
   */
  @UnitOfWork(value = "cms")
  // @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Document")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Document", code = HttpStatus.SC_CREATED,
      response = CmsDocument.class)
  public Response create(@Valid @ApiParam(hidden = false,
      required = true) gov.ca.cwds.rest.api.domain.cms.CmsDocument doc) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

  /**
   * Update an {@link CmsDocument}
   * 
   * @param id the id
   * @param doc {@link CmsDocument}
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = "cms")
  // @PUT
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 404, message = "not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate Document")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(hidden = true, value = "Update Document", code = HttpStatus.SC_NO_CONTENT,
      response = Object.class)
  public Response update(
      @PathParam("id") @ApiParam(required = true, name = "id",
          value = "The id of the Document to update") String id,
      @ApiParam(hidden = true) CmsDocument doc) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
  }

  // @GET
  // @Path("/login")
  public Response login1(final @Context HttpServletResponse response) {
    // Track this login request.
    final String state = getUniqueId();
    SAFAuthBean auth = new SAFAuthBean();
    auth.setState(state);
    pendingAuthByState.put(state, auth);

    String fullUrl =
        SAF_AUTH_URL + "?" + "client_id=" + SAF_CLIENT_ID + "&response_type=" + reponseType
            + "&redirect_uri=" + SAF_CALLBACK + "&scope=" + basicProfile + "&state=" + state;
    try {
      response.sendRedirect(fullUrl);
    } catch (IOException e) {
      throw new RuntimeException("unalbe to redirect to the url:" + fullUrl, e);
    }

    return Response.ok("fred").build();
  }

  // @GET
  // @Path("/callback")
  // @Produces({MediaType.APPLICATION_FORM_URLENCODED})
  // public Response safCallback(HttpServletResponse response,
  public Response safCallback(@Context HttpServletRequest req,
      @Context HttpServletResponse response, @QueryParam(value = "code") String code,
      @QueryParam(value = "state") String state) {

    // Response retval = null;
    String retval = "Unauthorized";
    LOG.info("safCallback(): ENTER: code=" + code + ", state=" + state);

    if (code.isEmpty() || state.isEmpty() || !pendingAuthByState.containsKey(state)) {
      LOG.info("safCallback(): INTRUDER ALERT!");
      return Response.status(Status.BAD_REQUEST).entity(retval).build();
    }

    // Track callback.
    SAFAuthBean auth = pendingAuthByState.get(state);
    auth.setCode(code);
    auth.setCalledCallbackAt(new Date().getTime());
    pendingAuthByCode.put(code, auth);

    LOG.info("SAF_TOKEN_URL=" + SAF_TOKEN_URL);
    WebTarget target = client.target(SAF_TOKEN_URL);
    Form form = new Form();
    form.param("client_id", SAF_CLIENT_ID);
    form.param("client_secret", SAF_CLIENT_SECRET);
    form.param("code", code);
    form.param("grant_type", "authorization_code");
    form.param("redirect_uri", SAF_CALLBACK);

    final OauthResponse oauth = target.request(MediaType.APPLICATION_JSON_TYPE)
        .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), OauthResponse.class);

    if (StringUtils.isBlank(oauth.getAccessToken())) {
      LOG.error("NO ACCESS TOKEN! state=" + state + ", code=" + code);
      return Response.status(Status.UNAUTHORIZED).entity("Unauthorized").build();
    }

    LOG.warn("ACCESS GRANT! state=" + state + ", code=" + code + ", access token="
        + oauth.getAccessToken());

    auth.setValidatedAt(new Date().getTime());
    auth.setAccessToken(oauth.getAccessToken());
    auth.setRefreshToken(oauth.getRefreshToken());
    auth.setExpiresIn(oauth.getExpiresIn());
    auth.setExpiresOn(oauth.getExpiresOn());
    auth.setTokenType(oauth.getTokenType());

    // SAF DOES NOT AUTOMATICALLY REDIRECT THE USER TO GIVEN REDIRECT_URL!
    // Send user to landing page.
    try {
      LOG.info("safCallback(): Success! Redirect to landing page!");
      final String authHeader = oauth.getTokenType() + " " + oauth.getAccessToken();
      LOG.info("safCallback(): CWDS_AUTH: " + authHeader);
      response.setHeader("CWDS_AUTH", authHeader);

      pendingAuthByCode.remove(code);
      pendingAuthByState.remove(state);
      authByAccessToken.put(auth.getAccessToken(), auth);

      // ANSWER: SAF does not obey redirects.
      // Instead, return HTML to the browser with redirect instructions.

      // Absolute URL:
      // response.sendRedirect(INTAKE_REDIRECT_URL);

      // Relative URI:
      // LOGGER.info("redirect to *relative* URI ...");
      // final String landingPage = response.encodeURL("landing");
      // response.setCharacterEncoding("UTF-8");
      // response.sendRedirect(landingPage);

      // final int status = response.getStatus();
      // LOGGER.info("After redirect ... status=" + status);

      // retval =
      // Response.seeOther(UriBuilder.fromPath("landing").build()) .header("CWDS_AUTH",
      // authHeader).build();
      retval = redirectToLanding(req, response);
      return Response.ok(retval).build();
    } catch (Exception e) {
      LOG.error("EXCEPTION: " + e.getMessage(), e);
      return Response.status(Status.UNAUTHORIZED).entity("Unauthorized").build();
    }

  }

  protected String redirectToLanding(HttpServletRequest req, HttpServletResponse resp) {
    // req.getProtocol()
    // req.getRemoteHost()
    // return "<!DOCTYPE HTML><html lang=\"en-US\"><head>"
    // + "<meta charset=\"UTF-8\">"
    // + "<meta http-equiv=\"refresh\"
    // content=\"5;url=http://54.70.247.41/cwds/landing\">"
    // + "<script type=\"text/javascript\">"
    // + " window.location.href = \"http://54.70.247.41/cwds/landing\""
    // + "</script><title>Page Redirection</title></head>"
    // + "<body>If you are not redirected automatically, follow the <a
    // href='http://54.70.247.41/cwds/landing'>link to example</a>"
    // + "<p/>" + new Date() + "</body></html>";
    return "<!DOCTYPE HTML><html lang=\"en-US\"><head></head><body>CWDS Landing<p/>" + new Date()
        + "</body></html>";
  }
}
