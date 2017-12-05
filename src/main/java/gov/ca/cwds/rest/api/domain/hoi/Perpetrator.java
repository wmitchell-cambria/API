package gov.ca.cwds.rest.api.domain.hoi;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.LimitedAccessType;

/**
 * Perpetrator person.
 * 
 * @author CWDS API Team
 */
public class Perpetrator extends PersonHOI {

  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("limited_access_code")
  private LimitedAccessType limitedAccessType;

  /**
   * No-argument constructor
   */
  public Perpetrator() {
    super();
  }

  public LimitedAccessType getLimitedAccessType() {
    return limitedAccessType;
  }

  public void setLimitedAccessType(LimitedAccessType limitedAccessType) {
    this.limitedAccessType = limitedAccessType;
  }
}

