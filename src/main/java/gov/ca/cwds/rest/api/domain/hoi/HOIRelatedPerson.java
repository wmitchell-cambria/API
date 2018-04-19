package gov.ca.cwds.rest.api.domain.hoi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import java.util.Objects;

/**
 * Related person.
 *
 * @author CWDS API Team
 */
@JsonPropertyOrder({"id", "first_name", "last_name", "name_suffix", "legacy_descriptor",
    "relationship"})
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
   * @param id id
   * @param firstName first name
   * @param lastName last name
   * @param nameSuffix nameSuffix
   * @param legacyDescriptor legacy descriptor
   * @param relationship relationship
   * @param limitedAccessType limited access type
   */
  public HOIRelatedPerson(
      String id,
      String firstName,
      String lastName,
      String nameSuffix,
      LegacyDescriptor legacyDescriptor,
      SystemCodeDescriptor relationship,
      LimitedAccessType limitedAccessType) {
    super(id, firstName, lastName, nameSuffix, legacyDescriptor);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    HOIRelatedPerson that = (HOIRelatedPerson) o;
    return (limitedAccessType == that.limitedAccessType) && Objects
        .equals(relationship, that.relationship);
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), relationship, limitedAccessType);
  }
}

