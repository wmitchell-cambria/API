package gov.ca.cwds.rest.api.domain.hoi;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;

/**
 * Reporter person.
 * 
 * @author CWDS API Team
 */
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
  }

  private Role role;

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
  public HOIReporter(String id, String firstName, String lastName,
      LegacyDescriptor legacyDescriptor) {
    super(id, firstName, lastName, legacyDescriptor);
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

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
