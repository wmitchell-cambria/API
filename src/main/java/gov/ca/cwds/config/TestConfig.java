package gov.ca.cwds.config;

public class TestConfig {
  private String authenticationMode;
  private TestUrl testUrl;

  public TestUrl getTestUrl() {
    return testUrl;
  }

  public void setTestUrl(TestUrl testUrl) {
    this.testUrl = testUrl;
  }

  public String getAuthenticationMode() {
    return authenticationMode;
  }

  public void setAuthenticationMode(String authenticationMode) {
    this.authenticationMode = authenticationMode;
  }
}
