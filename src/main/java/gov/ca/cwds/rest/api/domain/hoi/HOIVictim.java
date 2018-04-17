package gov.ca.cwds.rest.api.domain.hoi;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;

/**
 * Victim (child) person.
 * 
 * @author CWDS API Team
 */
public class HOIVictim extends HOIPerson {

  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("limited_access_code")
  private LimitedAccessType limitedAccessType;

  /**
   * No-argument constructor
   */
  public HOIVictim() {
    super();
  }

  /**
   * @param id - id
   * @param firstName - firstName
   * @param lastName - lastName
   * @param legacyDescriptor - legacyDescriptor
   */
  public HOIVictim(String id, String firstName, String lastName, String nameSuffix, LegacyDescriptor legacyDescriptor) {
    super(id, firstName, lastName, nameSuffix, legacyDescriptor);
  }

  /**
   * @return the limitedAccessType
   */
  public LimitedAccessType getLimitedAccessType() {
    return limitedAccessType;
  }

  /**
   * @param limitedAccessType - limitedAccessType
   */
  public void setLimitedAccessType(LimitedAccessType limitedAccessType) {
    this.limitedAccessType = limitedAccessType;
  }
}

