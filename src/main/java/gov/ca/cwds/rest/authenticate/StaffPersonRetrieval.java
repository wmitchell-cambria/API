package gov.ca.cwds.rest.authenticate;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is used to get the staffId and incident county of the person based on the token we
 * obtained.
 * 
 * @author CWDS TPT-4 Team
 *
 */
public class StaffPersonRetrieval extends HttpClientBuild {

  private static final Logger LOGGER = LoggerFactory.getLogger(StaffPersonRetrieval.class);

  private String validateUrl;

  /**
   * Constructor.
   * 
   * @param validateUrl - validateUrl
   */
  public StaffPersonRetrieval(String validateUrl) {
    this.validateUrl = validateUrl;
  }

  /**
   * getStaffPersonInfo is a user extraction using the validate url.
   * 
   * @param token - token
   * @return the staff Person info of the related token
   */
  public UserInfo getStaffPersonInfo(String token) {
    UserInfo userInfo = new UserInfo();
    try {
      LOGGER.info(validateUrl);
      LOGGER.info("GET: {}", validateUrl);
      postParams.add(new BasicNameValuePair("token", token));
      HttpGet httpGet = new HttpGet(validateUrl);
      URI uri = new URIBuilder(validateUrl).addParameters(postParams).build();
      httpGet.setURI(uri);
      HttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
      String json = EntityUtils.toString(httpResponse.getEntity());
      byte[] jsonArray = json.getBytes();
      ObjectMapper mapper = new ObjectMapper();
      JsonNode rootNode = mapper.readTree(jsonArray);
      userInfo.setStaffId(rootNode.path("staffId").asText());
      userInfo.setIncidentCounty(rootNode.path("county_code").asText());
    } catch (Exception e) {
      LOGGER.error("Unable to get the user json", e);
    }
    return userInfo;
  }

}

