package gov.ca.cwds.config;

/**
 * username and password credentials to login based on the userType.
 * 
 * @author CWDS TPT-4 Team
 *
 */
public class User {

  private String userType;
  private String username;
  private String password;

  /**
   * getUsername.
   * 
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * username.
   * 
   * @param username - username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * getPassword.
   * 
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * password.
   * 
   * @param password - password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * getUserType.
   * 
   * @return the userType
   */
  public String getUserType() {
    return userType;
  }

  /**
   * userType.
   * 
   * @param userType - userType
   */
  public void setUserType(String userType) {
    this.userType = userType;
  }
}
