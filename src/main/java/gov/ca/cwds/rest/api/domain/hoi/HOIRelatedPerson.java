package gov.ca.cwds.rest.api.domain.hoi;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;

/**
 * Related person.
 * 
 * @author CWDS API Team
 */
public class HOIRelatedPerson extends HOIPerson {

  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("relationship")
  private SystemCodeDescriptor relationship;

  @JsonProperty("limited_access_code")
  private LimitedAccessType limitedAccessType;

  /**
   * No-argument constructor
   */
  public HOIRelatedPerson() {
    super();
  }

  public LimitedAccessType getLimitedAccessType() {
    return limitedAccessType;
  }

  public void setLimitedAccessType(LimitedAccessType limitedAccessType) {
    this.limitedAccessType = limitedAccessType;
  }

  public SystemCodeDescriptor getRelationship() {
    return relationship;
  }

  public void setRelationship(SystemCodeDescriptor relationship) {
    this.relationship = relationship;
  }
}

