package gov.ca.cwds.rest.api.domain.hoi;

/**
 * Reporter person.
 * 
 * @author CWDS API Team
 */
public class Reporter extends Person {

  /**
   * Default serialization version.
   */
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
  public Reporter() {
    super();
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}

