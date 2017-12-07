package gov.ca.cwds.rest.api.domain.hoi;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;

/**
 * Reporter person.
 * 
 * @author CWDS API Team
 */
@JsonPropertyOrder({"id", "first_name", "last_name", "role", "legacy_descriptor"})
public class HOIReporter extends HOIPerson {

  private static final long serialVersionUID = 1L;

  public enum Role {

    MANDATED_REPORTER("Mandated Reporter"),

    NON_MANDATED_REPORTER("Non-mandated Reporter"),

    ANONYMOUS_REPORTER("Anonymous Reporter"),

    SELF_REPORTER("Self Reported");

    private final String description;

    Role(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }

    @Override
    public String toString() {
      return this.getDescription();
    }
  }

  private Role role;
  private String reporterRole;

  /**
   * Default no-argument constructor
   */
  public HOIReporter() {
    super();
  }

  /**
   * @param id - id
   * @param firstName - firstName
   * @param lastName - lastName
   * @param legacyDescriptor - legacyDescriptor
   */
  public HOIReporter(Role role, String id, String firstName, String lastName,
      LegacyDescriptor legacyDescriptor) {
    super(id, firstName, lastName, legacyDescriptor);
    this.reporterRole = role.getDescription();
  }

  /**
   * @return the role
   */
  public Role getRole() {
    return role;
  }

  /**
   * @param role - role
   */
  public void setRole(Role role) {
    this.role = role;
  }


  public String getReporterRole() {
    return reporterRole;
  }

  public void setReporterRole(String reporterRole) {
    this.reporterRole = reporterRole;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
