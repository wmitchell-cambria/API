package gov.ca.cwds.rest.authenticate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.authenticate.config.YmlLoader;

/**
 * This class is used to generate the token using json for Perry dev mode, and handles all the
 * redirect from clicking the login to the end to get the token.
 * 
 * @author CWDS TPT-4 Team
 *
 */
public class CwdsDevAuthenticationClient extends HttpClientBuild implements CwdsClientCommon {

  private static final Logger LOGGER = LoggerFactory.getLogger(CwdsDevAuthenticationClient.class);

  private static final int GET_ONLY_ACCESS_CODE_VALUE = 11;
  private static final String ACCESS_CODE = "accessCode";
  private static final String LOCATION = "Location";
  private static final String NEW_REQUEST_TO_BEGIN = "================START Login=========================";
  private static final String REQUEST_STEP= "{}=========================================";
  private static final String NEW_REQUEST_TO_END = "==================END Login=======================";

  private String authLoginUrl;
  private String tokenUrl;
  private String perryLoginUrl;
  private String callBackUrl;

  private HttpGet httpGet;
  private String redirectUrl;
  private String userName;

  /**
   * This constructor is to used to initialize the yaml and used over the class.
   * 
   * @param ymlLoader - ymlLoader
   * @param userName - userName
   */
  public CwdsDevAuthenticationClient(YmlLoader ymlLoader, String userName) {
    this.userName = userName;
    this.authLoginUrl = ymlLoader.readConfig().getTestUrl().getAuthLoginUrl();
    this.tokenUrl = ymlLoader.readConfig().getTestUrl().getTokenUrl();
    this.perryLoginUrl = ymlLoader.readConfig().getTestUrl().getPerryLoginUrl();
    this.callBackUrl = ymlLoader.readConfig().getTestUrl().getCallBackUrl();
  }

  /**
   * Default method to get the token.
   * 
   * @return the valid token
   */
  @Override
  public String getToken() {
    String token = null;
    try {
      ArrayList<NameValuePair> postParams = new ArrayList<>();
      LOGGER.info(NEW_REQUEST_TO_BEGIN);
      LOGGER.info("GET: {}", authLoginUrl);
      postParams.add(new BasicNameValuePair("callback", callBackUrl));
      postParams.add(new BasicNameValuePair("sp_id", null));
      HttpGet httpGet = new HttpGet(authLoginUrl);
      URI uri = new URIBuilder(authLoginUrl).addParameters(postParams).build();
      httpGet.setURI(uri);
      CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
      httpResponse.getFirstHeader(LOCATION).getValue();
      httpGet.releaseConnection();

      redirectUrl = giveUsernameCredentials();
      token = requestToken(redirectUrl);


    } catch (Exception e) {
      LOGGER.error("Unable to create the token", e);
    } finally {
      LOGGER.info(NEW_REQUEST_TO_END);
    }
    return token;
  }

  private String requestToken(String redirectUrl) throws IOException, URISyntaxException {
    ArrayList<NameValuePair> postParams = new ArrayList<>();
    LOGGER.info(REQUEST_STEP, 2);
    LOGGER.info("GET ACCESS CODE: {}", redirectUrl);
    httpGet = new HttpGet(redirectUrl);
    HttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
    redirectUrl = httpResponse.getFirstHeader(LOCATION).getValue();
    String accessCodeParm = redirectUrl.substring(redirectUrl.indexOf(ACCESS_CODE));
    int startIndex = accessCodeParm.indexOf(ACCESS_CODE) + GET_ONLY_ACCESS_CODE_VALUE;
    String accessCode = accessCodeParm.substring(startIndex);

    LOGGER.info(REQUEST_STEP, 3);
    LOGGER.info("GET TOKEN: {}", tokenUrl);
    postParams.clear();
    postParams.add(new BasicNameValuePair(ACCESS_CODE, accessCode));
    httpGet = new HttpGet(tokenUrl);
    URI uri = new URIBuilder(tokenUrl).addParameters(postParams).build();
    httpGet.setURI(uri);
    httpResponse = httpClient.execute(httpGet, httpContext);
    String token = EntityUtils.toString(httpResponse.getEntity());
    LOGGER.info("TOKEN: {}", token);
    return token;
  }

  private String giveUsernameCredentials() throws IOException {
    ArrayList<NameValuePair> postParams = new ArrayList<>();
    LOGGER.info(REQUEST_STEP, 1);
    LOGGER.info("POST: {}", perryLoginUrl);
    HttpPost httpPost = new HttpPost(perryLoginUrl);
    postParams.clear();
    postParams.add(new BasicNameValuePair("username", userName));
    httpPost.setEntity(new UrlEncodedFormEntity(postParams));
    HttpResponse httpResponse = httpClient.execute(httpPost, httpContext);
    LOGGER.info("Status: {}", httpResponse.getStatusLine());
    redirectUrl = httpResponse.getFirstHeader(LOCATION).getValue();
    LOGGER.info("Redirect URL: {}", redirectUrl);
    return redirectUrl;
  }

}
