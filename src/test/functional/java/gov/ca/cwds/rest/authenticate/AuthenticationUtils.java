package gov.ca.cwds.rest.authenticate;

import gov.ca.cwds.authenticate.config.YmlLoader;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class AuthenticationUtils {

  CwdsLoginType cwdsLoginType = null;
  private YmlLoader ymlLoader;

  /**
   * Constructor.
   * 
   * @param ymlLoader - ymlLoader
   */
  public AuthenticationUtils(YmlLoader ymlLoader) {
    this.ymlLoader = ymlLoader;
  }

  /**
   * This is used to get the token.
   * 
   * @param userType - Type of user to get the token {@link UserGroup}
   * @return the user based token
   */
  public String getToken(UserGroup userType) {
    cwdsLoginType = new CwdsLoginType(ymlLoader);
    return cwdsLoginType.login(userType);
  }

  /**
   * This method is used to get the user info based on the token.
   * 
   * @param token - token
   * @return the staff Person details
   */
  public UserInfo getStaffPersonDetails(String token) {
    StaffPersonRetrieval staffPersonRetreival =
        new StaffPersonRetrieval(ymlLoader.readConfig().getTestUrl().getValidateUrl());
    return staffPersonRetreival.getStaffPersonInfo(token);
  }
}
