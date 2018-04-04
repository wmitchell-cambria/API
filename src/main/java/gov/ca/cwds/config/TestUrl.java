package gov.ca.cwds.config;

public class TestUrl {
  private String baseUrl;
  private String authenticationBaseUrl;
  private String perryLoginUrl;
  private String authLoginUrl;
  private String tokenUrl;
  private String callBackUrl;
  private String logOutUrl;

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getAuthenticationBaseUrl() {
    return authenticationBaseUrl;
  }

  public void setAuthenticationBaseUrl(String safeUrl) {
    this.authenticationBaseUrl = safeUrl;
  }

  public String getPerryLoginUrl() {
    return perryLoginUrl;
  }

  public void setPerryLoginUrl(String perryLoginUrl) {
    this.perryLoginUrl = perryLoginUrl;
  }

  public String getAuthLoginUrl() {
    return authLoginUrl;
  }

  public void setAuthLoginUrl(String authLoginUrl) {
    this.authLoginUrl = authLoginUrl;
  }

  public String getTokenUrl() {
    return tokenUrl;
  }

  public void setTokenUrl(String tokenUrl) {
    this.tokenUrl = tokenUrl;
  }

  public String getCallBackUrl() {
    return callBackUrl;
  }

  public void setCallBackUrl(String callBackUrl) {
    this.callBackUrl = callBackUrl;
  }

  public String getLogOutUrl() {
    return logOutUrl;
  }

  public void setLogOutUrl(String logOutUrl) {
    this.logOutUrl = logOutUrl;
  }

}
