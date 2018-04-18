package gov.ca.cwds.config;

/**
 * This class provide all the values of the url's to get the token.
 * 
 * @author CWDS TPT4 Team
 *
 */
public class TokenCredentials {

  private String baseUrl;

  private String perryLoginUrl;

  private String authLoginUrl;

  private String tokenUrl;

  private String callBackUrl;

  private String logOutUrl;

  private String validateUrl;

  /**
   * getBaseUrl.
   * 
   * @return the baseUrl
   */
  public String getBaseUrl() {
    return baseUrl;
  }

  /**
   * getPerryLoginUrl.
   * 
   * @return the perryLoginUrl
   */
  public String getPerryLoginUrl() {
    return perryLoginUrl;
  }

  /**
   * getAuthLoginUrl.
   * 
   * @return the authLoginUrl
   */
  public String getAuthLoginUrl() {
    return authLoginUrl;
  }

  /**
   * getTokenUrl.
   * 
   * @return the tokenUrl
   */
  public String getTokenUrl() {
    return tokenUrl;
  }

  /**
   * getCallBackUrl.
   * 
   * @return the callBackUrl
   */
  public String getCallBackUrl() {
    return callBackUrl;
  }

  /**
   * getLogOutUrl.
   * 
   * @return the logOutUrl
   */
  public String getLogOutUrl() {
    return logOutUrl;
  }

  /**
   * baseUrl.
   * 
   * @param baseUrl - baseUrl
   */
  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  /**
   * perryLoginUrl.
   * 
   * @param perryLoginUrl - perryLoginUrl
   */
  public void setPerryLoginUrl(String perryLoginUrl) {
    this.perryLoginUrl = perryLoginUrl;
  }

  /**
   * authLoginUrl.
   * 
   * @param authLoginUrl - authLoginUrl
   */
  public void setAuthLoginUrl(String authLoginUrl) {
    this.authLoginUrl = authLoginUrl;
  }

  /**
   * tokenUrl.
   * 
   * @param tokenUrl - tokenUrl
   */
  public void setTokenUrl(String tokenUrl) {
    this.tokenUrl = tokenUrl;
  }

  /**
   * callBackUrl.
   * 
   * @param callBackUrl - callBackUrl
   */
  public void setCallBackUrl(String callBackUrl) {
    this.callBackUrl = callBackUrl;
  }

  /**
   * logOutUrl.
   * 
   * @param logOutUrl - logOutUrl
   */
  public void setLogOutUrl(String logOutUrl) {
    this.logOutUrl = logOutUrl;
  }

  /**
   * getValidateUrl.
   * 
   * @return the validateUrl
   */
  public String getValidateUrl() {
    return validateUrl;
  }

  /**
   * validateUrl.
   * 
   * @param validateUrl - validateUrl
   */
  public void setValidateUrl(String validateUrl) {
    this.validateUrl = validateUrl;
  }

}
