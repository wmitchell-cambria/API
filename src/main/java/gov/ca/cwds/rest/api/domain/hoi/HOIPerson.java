package gov.ca.cwds.rest.api.domain.hoi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.persistence.ns.Participant;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Basic person.
 *
 * @author CWDS API Team
 */
public class HOIPerson extends ApiObjectIdentity implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(example = "111111")
  private String id;

  @JsonProperty("first_name")
  @ApiModelProperty(example = "joe")
  private String firstName;

  @JsonProperty("last_name")
  @ApiModelProperty(example = "west")
  private String lastName;

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private LegacyDescriptor legacyDescriptor = new LegacyDescriptor();

  /**
   * No-argument constructor
   */
  public HOIPerson() {
    // default constructor
  }

  /**
   * @param id - id
   * @param firstName - firstName
   * @param lastName - lastName
   * @param legacyDescriptor - legacyDescriptor
   */
  public HOIPerson(String id, String firstName, String lastName,
      LegacyDescriptor legacyDescriptor) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.legacyDescriptor = legacyDescriptor;
  }

  /**
   * Construct from persistence class
   *
   * @param persistedParticipant persistence level participant object
   */
  public HOIPerson(Participant persistedParticipant) {
    this.id = persistedParticipant.getId();
    this.firstName = persistedParticipant.getFirstName();
    this.lastName = persistedParticipant.getLastName();
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }
}

