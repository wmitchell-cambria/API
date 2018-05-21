package gov.ca.cwds.rest.services.submit;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumerated types for Access Restrictions / Limited Access Code
 *
 * @author CWDS API Team
 */
public enum AccessRestrictions {

  /**
   * None
   */
  NONE("N", "none"),

  /**
   * Sensitive
   */
  SENSITIVE("S", "sensitive"),

  /**
   * Sealed
   */
  SEALED("R", "sealed");

  private static final Map<String, AccessRestrictions> mapByNsDescription = new HashMap<>();

  private final String cmsDescription;
  private final String nsDescription;

  private AccessRestrictions(String cmsDescription, String nsDescription) {
    this.nsDescription = nsDescription;
    this.cmsDescription = cmsDescription;
  }

  public String getCmsDescription() {
    return cmsDescription;
  }

  public static AccessRestrictions findByNsDescription(String nsDescription) {
    return mapByNsDescription.containsKey(nsDescription) ? mapByNsDescription.get(nsDescription)
        : AccessRestrictions.NONE;
  }

  static {
    for (AccessRestrictions e : AccessRestrictions.values()) {
      mapByNsDescription.put(e.nsDescription, e);
    }
  }

}

