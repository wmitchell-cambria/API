package gov.ca.cwds.rest.api.domain.hoi;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;

/**
 * Victim (child) person.
 * 
 * @author CWDS API Team
 */
public class Victim extends PersonHOI {

  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("limited_access_code")
  private LimitedAccessType limitedAccessType;

  /**
   * No-argument constructor
   */
  public Victim() {
    super();
  }

  /**
   * @param id - id
   * @param firstName - firstName
   * @param lastName - lastName
   * @param legacyDescriptor - legacyDescriptor
   */
  public Victim(String id, String firstName, String lastName, LegacyDescriptor legacyDescriptor) {
    super(id, firstName, lastName, legacyDescriptor);
  }

  /**
   * @return
   */
  public LimitedAccessType getLimitedAccessType() {
    return limitedAccessType;
  }

  public void setLimitedAccessType(LimitedAccessType limitedAccessType) {
    this.limitedAccessType = limitedAccessType;
  }
}

