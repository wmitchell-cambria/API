package gov.ca.cwds.rest.resources.auth;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.services.CrudsService;

// @Path("/cwds")
public class OauthLoginService implements CrudsService {

  private static final Logger LOG = LoggerFactory.getLogger(OauthLoginService.class);

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

  private Client client;

  public OauthLoginService(final Client client) {
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
   * @param token token to validate
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

  // @GET
  // @Path("/login")
  public void login(final @Context HttpServletResponse response) {

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

  @Override
  public gov.ca.cwds.rest.api.Response find(Serializable primaryKey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public gov.ca.cwds.rest.api.Response delete(Serializable primaryKey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public gov.ca.cwds.rest.api.Response create(Request request) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public gov.ca.cwds.rest.api.Response update(Serializable primaryKey, Request request) {
    // TODO Auto-generated method stub
    return null;
  }
}
