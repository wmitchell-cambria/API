package gov.ca.cwds.rest.api.domain.hoi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;

/**
 * Related person.
 * 
 * @author CWDS API Team
 */
@JsonPropertyOrder({"id", "first_name", "last_name", "legacy_descriptor", "relationship"})
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

  /**
   * 
   * @param id id
   * @param firstName first name
   * @param lastName last name
   * @param legacyDescriptor legacy descriptor
   * @param relationship relationship
   * @param limitedAccessType limited access type
   */
  public HOIRelatedPerson(String id, String firstName, String lastName,
      LegacyDescriptor legacyDescriptor, SystemCodeDescriptor relationship,
      LimitedAccessType limitedAccessType) {
    super(id, firstName, lastName, legacyDescriptor);
    this.relationship = relationship;
    this.limitedAccessType = limitedAccessType;
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

