package gov.ca.cwds.rest.api.domain.hoi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import io.swagger.annotations.ApiModelProperty;

/**
 * Basic person.
 * 
 * @author CWDS API Team
 */
public class PersonHOI extends ApiObjectIdentity implements ApiTypedIdentifier<String> {

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
  public PersonHOI() {}

  /**
   * @param id - id
   * @param firstName - firstName
   * @param lastName - lastName
   * @param legacyDescriptor - legacyDescriptor
   */
  public PersonHOI(String id, String firstName, String lastName,
      LegacyDescriptor legacyDescriptor) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.legacyDescriptor = legacyDescriptor;
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

}

