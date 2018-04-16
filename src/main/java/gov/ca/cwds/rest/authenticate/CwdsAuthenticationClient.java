package gov.ca.cwds.rest.authenticate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.authenticate.config.YmlLoader;

/**
 * This class is used to generate the token using username and password with Perry, and handles all
 * the redirect from clicking the login to the end to get the token.
 * 
 * @author CWDS TPT-4 Team
 *
 */
public class CwdsAuthenticationClient extends HttpClientBuild implements CwdsClientCommon {

  private static final Logger LOGGER = LoggerFactory.getLogger(CwdsAuthenticationClient.class);

  private static final int GET_STATE_VALUE = 4;
  private static final int GET_RESPONE_TYPE_VALUE = 3;
  private static final int GET_SCOPE_VALUE = 2;
  private static final int GET_REDIRECT_URL_VALUE = 1;
  private static final int GET_CLIENT_ID_VALUE = 0;
  private static final int GET_ONLY_ACCESS_CODE_VALUE = 11;
  private static final int GET_ONLY_THE_VALUE = 7;
  private static final String RANDOM_VALUE = "123456";

  private static final String REDIRECT_URL_LOGGER = "Redirect URL: {}";
  private static final String POST_LOGGER = "POST : {}{}";

  private static final String SUBMIT_CONTINUE = "submit.Continue";
  private static final String STATE = "state";
  private static final String SCOPE = "scope";
  private static final String CLIENT_ID = "client_id";
  private static final String SUBMIT_VALIDATE = "submit.Validate";
  private static final String SELECTED_CONTACT = "selectedContact";
  private static final String GET_LOGGER = "GET: {}";
  private static final String ACCESS_CODE = "accessCode";
  private static final String EMAIL_CONTACT = "emailContact";
  private static final String VIEW = "View";
  private static final String SUBMIT_SIGNIN_RACF = "submit.Signin.RACF";
  private static final String USERNAMEFIELD = "Username";
  private static final String LOCATIONFIELD = "Location";
  private static final String DEVICE_LOG_ID = "deviceLogID";
  private static final String VALUE = "value=";
  private static final String REQUEST_VERIFICATION_TOKEN = "__RequestVerificationToken";

  private static final String NEW_REQUEST_TO_BEGIN = "=========================================";
  private static final String AUTH_LOGIN_URL = "https://web.integration.cwds.io/perry/authn/login";
  private static final String TOKEN_URL = "https://web.integration.cwds.io/perry/authn/token";
  private static final String BASE_URL = "https://sectest.dss.ca.gov";
  private static final String CALL_BACK_URL = "https://ferbapi.integration.cwds.io/swagger";

  private String userName;
  private String password;

  private String token = null;
  private HttpGet httpGet;
  private URI uri;
  private HttpResponse httpResponse;
  private HttpPost httpPost;
  private String location;
  private YmlLoader ymlLoader;

  /**
   * This constructor is to used to initialize the yaml and used over the class.
   * 
   * @param ymlLoader - ymlLoader
   * @param userName - userName
   * @param password - password
   */
  public CwdsAuthenticationClient(YmlLoader ymlLoader, String userName, String password) {
    this.ymlLoader = ymlLoader;
    this.userName = userName;
    this.password = password;
  }

  /**
   * Default method to get the token.
   * 
   * @return the valid token
   */
  @Override
  public String getToken() {
    try {
      String redirectUrl;
      loginIntoSwagger();
      String response = EntityUtils.toString(httpResponse.getEntity());
      String requestVerificationToken = getRequestVerificationToken(response);

      httpResponse = loginWithCredentials(location, requestVerificationToken);
      response = EntityUtils.toString(httpResponse.getEntity());
      requestVerificationToken = getRequestVerificationToken(response);
      String emailContact = getEmailContact(response);

      httpResponse = sendAccessCode(requestVerificationToken, emailContact);
      response = EntityUtils.toString(httpResponse.getEntity());
      String deviceLogId = getDeviceLogicId(response);
      requestVerificationToken = getRequestVerificationToken(response);

      enterAccessCode(requestVerificationToken, emailContact, deviceLogId);
      redirectUrl = location.substring(location.indexOf('?') + 1);
      String[] values = redirectUrl.split("&");
      LOGGER.info(REDIRECT_URL_LOGGER, location);
      httpPost.releaseConnection();

      String accessCode = continueToCwsIntegration(location, values);
      token = requestToken(accessCode);

    } catch (Exception e) {
      LOGGER.error("Unable to create the token", e);
    } finally {
      this.httpGet.reset();
    }
    return token;

  }

  private void loginIntoSwagger() throws URISyntaxException, IOException {
    // Login in expect 302 with redirect:
    // https://web.xxxx.cwds.io/perry/login
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info(GET_LOGGER, AUTH_LOGIN_URL);
    postParams.add(new BasicNameValuePair("callback", CALL_BACK_URL));
    postParams.add(new BasicNameValuePair("sp_id", null));
    httpGet = new HttpGet(AUTH_LOGIN_URL);
    uri = new URIBuilder(AUTH_LOGIN_URL).addParameters(postParams).build();
    httpGet.setURI(uri);
    httpResponse = httpClient.execute(httpGet, httpContext);
    String redirectUrl = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();
    LOGGER.info(REDIRECT_URL_LOGGER, redirectUrl);

    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info(GET_LOGGER, redirectUrl);
    httpGet = new HttpGet(redirectUrl);
    httpResponse = httpClient.execute(httpGet, httpContext);
    location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();
    LOGGER.info(REDIRECT_URL_LOGGER, location);

    loginToPerryRedirect();
  }

  private void loginToPerryRedirect() throws IOException, URISyntaxException {
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info(GET_LOGGER, location);
    httpGet = new HttpGet(location);
    httpResponse = httpClient.execute(httpGet, httpContext);
    location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();
    LOGGER.info(REDIRECT_URL_LOGGER, location);

    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info("GET: {} {}", BASE_URL, location);
    httpGet = new HttpGet(BASE_URL);
    uri = new URIBuilder(BASE_URL).setPath(location).build();
    httpGet.setURI(uri);
    httpResponse = httpClient.execute(httpGet, httpContext);
  }

  private HttpResponse loginWithCredentials(String locationUrl, String requestVerificationToken)
      throws IOException {
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info(POST_LOGGER, BASE_URL, locationUrl);
    httpPost = new HttpPost(BASE_URL + locationUrl);
    postParams.clear();
    postParams.add(new BasicNameValuePair(REQUEST_VERIFICATION_TOKEN, requestVerificationToken));
    postParams.add(new BasicNameValuePair(USERNAMEFIELD, userName));
    postParams.add(new BasicNameValuePair("Password", password)); // Query Param field, not value
    postParams.add(new BasicNameValuePair(SUBMIT_SIGNIN_RACF, "RACF"));
    postParams.add(new BasicNameValuePair(VIEW, "None"));
    return requestCommonBuild();
  }

  private HttpResponse requestCommonBuild() throws IOException {
    httpPost.setEntity(new UrlEncodedFormEntity(postParams));
    httpResponse = httpClient.execute(httpPost, httpContext);

    location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();
    LOGGER.info(REDIRECT_URL_LOGGER, location);
    httpPost.releaseConnection();

    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info("GET : {}{}", BASE_URL, location);
    httpGet = new HttpGet(BASE_URL + location);
    httpResponse = httpClient.execute(httpGet, httpContext);
    return httpResponse;
  }

  private void enterAccessCode(String requestVerificationToken, String emailContact,
      String deviceLogId) throws IOException {
    // expect 302 with redirectUrl
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info(POST_LOGGER, BASE_URL, location);
    httpPost = new HttpPost(BASE_URL + location);
    postParams.clear();
    postParams.add(new BasicNameValuePair(REQUEST_VERIFICATION_TOKEN, requestVerificationToken));
    postParams.add(new BasicNameValuePair(SELECTED_CONTACT, emailContact));
    postParams.add(new BasicNameValuePair(ACCESS_CODE, RANDOM_VALUE));
    postParams.add(new BasicNameValuePair(DEVICE_LOG_ID, deviceLogId));
    postParams.add(new BasicNameValuePair(SUBMIT_VALIDATE, null));
    httpPost.setEntity(new UrlEncodedFormEntity(postParams));
    httpResponse = httpClient.execute(httpPost, httpContext);
    location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();
  }

  private HttpResponse sendAccessCode(String requestVerificationToken, String emailContact)
      throws IOException {
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info(POST_LOGGER, BASE_URL, location);
    httpPost = new HttpPost(BASE_URL + location);
    postParams.clear();
    postParams.add(new BasicNameValuePair(REQUEST_VERIFICATION_TOKEN, requestVerificationToken));
    postParams.add(new BasicNameValuePair(EMAIL_CONTACT, emailContact));
    postParams.add(new BasicNameValuePair(ACCESS_CODE, emailContact));
    postParams.add(new BasicNameValuePair("submit.SendAccessCode", null));
    return requestCommonBuild();
  }

  private String continueToCwsIntegration(String location, String[] values) throws IOException {
    // expect 302 with redirectUrl
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info("POST: {}", location);
    httpPost = new HttpPost(location);
    postParams
        .add(new BasicNameValuePair(CLIENT_ID, getRedirectUrlParam(values[GET_CLIENT_ID_VALUE])));
    postParams.add(new BasicNameValuePair("redirect_uri",
        getRedirectUrlParam(values[GET_REDIRECT_URL_VALUE])));
    postParams.add(new BasicNameValuePair(SCOPE, getRedirectUrlParam(values[GET_SCOPE_VALUE])));
    postParams.add(new BasicNameValuePair("response_type",
        getRedirectUrlParam(values[GET_RESPONE_TYPE_VALUE])));
    postParams.add(new BasicNameValuePair(STATE, getRedirectUrlParam(values[GET_STATE_VALUE])));
    postParams.add(new BasicNameValuePair(SUBMIT_CONTINUE, "Continue+to+CWDS+-+Integration"));
    httpPost.setEntity(new UrlEncodedFormEntity(postParams));
    httpResponse = httpClient.execute(httpPost, httpContext);
    location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();
    LOGGER.info(REDIRECT_URL_LOGGER, location);

    return requestAccessCode(location);
  }

  private String requestAccessCode(String location) throws IOException {
    // expect 302 with redirectUrl to:
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info("POST: {}", location);
    httpPost = new HttpPost(location);
    httpResponse = httpClient.execute(httpPost, httpContext);
    location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();
    LOGGER.info(REDIRECT_URL_LOGGER, location);

    // expect 302 with accessCode in the redirectUrl
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info("GET : {}", location);
    httpGet = new HttpGet(location);
    httpResponse = httpClient.execute(httpGet, httpContext);
    location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();
    String accessCode = getAccessCode(location);
    LOGGER.info(REDIRECT_URL_LOGGER, location);
    return accessCode;
  }

  private String requestToken(String accessCode) throws URISyntaxException, IOException {
    // expect status code 200 with token
    LOGGER.info(NEW_REQUEST_TO_BEGIN);
    LOGGER.info("GET TOKEN: {}", TOKEN_URL);
    postParams.clear();
    postParams.add(new BasicNameValuePair(ACCESS_CODE, accessCode));
    httpGet = new HttpGet(TOKEN_URL);
    uri = new URIBuilder(TOKEN_URL).addParameters(postParams).build();
    httpGet.setURI(uri);
    httpResponse = httpClient.execute(httpGet, httpContext);
    token = EntityUtils.toString(httpResponse.getEntity());
    LOGGER.info("TOKEN: {}", token);
    return token;
  }

  private String getEmailContact(String response) {
    String emailId = response.substring(response.indexOf("input id=\"emailContact\""));
    int startIndex = emailId.indexOf(VALUE) + GET_ONLY_THE_VALUE;
    int endIndex = emailId.indexOf('}') + 1;
    String actualEmailId = emailId.substring(startIndex, endIndex);
    return actualEmailId.replaceAll("&quot;", "\"");
  }

  private String getAccessCode(String location) {
    String accessCodeParm = location.substring(location.indexOf(ACCESS_CODE));
    int startIndex = accessCodeParm.indexOf(ACCESS_CODE) + GET_ONLY_ACCESS_CODE_VALUE;
    return accessCodeParm.substring(startIndex);
  }

  private String getRedirectUrlParam(String location) {
    return location.substring(location.indexOf('=') + 1, location.length());
  }

  private String getDeviceLogicId(String response) {
    String deviceId = response.substring(response.indexOf(DEVICE_LOG_ID));
    int startIndex = deviceId.indexOf(VALUE) + GET_ONLY_THE_VALUE;
    int endIndex = deviceId.indexOf("/>") - 2;
    return deviceId.substring(startIndex, endIndex);
  }

  private String getRequestVerificationToken(String response) {
    String verifiricationToken = response.substring(response.indexOf(REQUEST_VERIFICATION_TOKEN));
    int startIndex = verifiricationToken.indexOf(VALUE) + GET_ONLY_THE_VALUE;
    int endIndex = verifiricationToken.indexOf("/>") - 2;
    return verifiricationToken.substring(startIndex, endIndex);
  }

}
