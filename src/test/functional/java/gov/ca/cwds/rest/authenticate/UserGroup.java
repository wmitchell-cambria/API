package gov.ca.cwds.rest.authenticate;

/**
 * Enums.
 * 
 * @author CWDS TPT-4 Team
 *
 */
public enum UserGroup {

  //
  // CHECKSTYLE:OFF
  //
  SOCIAL_WORKER("socialWorkerOnly"),

  COUNTY_SENSITIVE("countySensitivePrivilegeUser"),

  COUNTY_SEALED("countySealedPrivilegeUser"),

  STATE_SENSITIVE("stateSensitivePrivilegeUser"),

  STATE_SEALED("stateSealedPrivilegeUser");

  private final String name;

  private UserGroup(String name) {
    this.name = name;
  }

  /**
   * getName.
   * 
   * @return the name
   */
  public final String getName() {
    return name;
  }

}
