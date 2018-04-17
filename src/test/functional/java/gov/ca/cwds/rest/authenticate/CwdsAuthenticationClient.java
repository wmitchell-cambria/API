package gov.ca.cwds.rest.authenticate;

import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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

  public static void main(String[] args) {
    String userName = "CWDSIntake+BRADYG@gmail.com";
    String password = "CWS1kids*";
    CwdsAuthenticationClient cwdsAuthenticationClient =
        new CwdsAuthenticationClient(null, userName, password);
    cwdsAuthenticationClient.getToken();
  }

  /**
   * Default method to get the token.
   * 
   * @return the valid token
   */
  @Override
  public String getToken() {
    try {

      ArrayList<NameValuePair> postParams = new ArrayList<>();
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

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info(GET_LOGGER, location);
      httpGet = new HttpGet(location);
      httpResponse = httpClient.execute(httpGet, httpContext);
      String response = EntityUtils.toString(httpResponse.getEntity());

      String csrfIndex = response.substring(response.indexOf("_csrf"));
      int startIndex = csrfIndex.indexOf(VALUE) + GET_ONLY_THE_VALUE;
      int endIndex = csrfIndex.indexOf("/>") - 1;
      String csrf = csrfIndex.substring(startIndex, endIndex);

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info(POST_LOGGER, location);
      httpPost = new HttpPost(location);
      postParams.clear();
      postParams.add(new BasicNameValuePair("_csrf", csrf));
      postParams.add(new BasicNameValuePair("password", password));
      postParams.add(new BasicNameValuePair("signInSubmitButton", "Sign+in"));
      postParams.add(new BasicNameValuePair("username", userName));
      httpPost.setEntity(new UrlEncodedFormEntity(postParams));
      httpResponse = httpClient.execute(httpPost, httpContext);
      location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info(GET_LOGGER, location);
      httpGet = new HttpGet(location);
      httpResponse = httpClient.execute(httpGet, httpContext);
      location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();

      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info(GET_LOGGER, location);
      httpGet = new HttpGet(location);
      httpResponse = httpClient.execute(httpGet, httpContext);
      location = httpResponse.getFirstHeader(LOCATIONFIELD).getValue();
      String accessCode = getAccessCode(location);
      LOGGER.info(REDIRECT_URL_LOGGER, accessCode);

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

    } catch (Exception e) {
      LOGGER.error("Unable to create the token", e);
    } finally {
      this.httpGet.reset();
    }
    return token;
  }

  private String getAccessCode(String location) {
    String accessCodeParm = location.substring(location.indexOf(ACCESS_CODE));
    int startIndex = accessCodeParm.indexOf(ACCESS_CODE) + GET_ONLY_ACCESS_CODE_VALUE;
    return accessCodeParm.substring(startIndex);
  }

}
