package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an PostedIndividualDeliveredSevice
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"legacy_descriptor", "first_name", "last_name", "relationship"})
public class IndividualDeliveredService extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @NotNull
  @JsonProperty("legacy_descriptor")
  private CmsRecordDescriptor legacyDescriptor;

  @NotEmpty
  @JsonProperty("first_name")
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "John")
  private String firstName;

  @NotEmpty
  @JsonProperty("middle_name")
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Bob")
  private String middleName;

  @NotEmpty
  @JsonProperty("last_name")
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Jones")
  private String lastName;

  @JsonProperty("name_suffix")
  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "SR.")
  private String suffixTitle;

  @JsonProperty("prefix_title")
  @Size(max = 6)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "MR.")
  private String prefixTitle;

  @NotEmpty
  @JsonProperty("relationship")
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Reporter")
  private String relationship;

  /**
   * @param legacyDescriptor the CmsRecordDescriptor
   * @param firstName first name
   * @param middleName middle name
   * @param lastName last name
   * @param suffixTitle The name suffix.
   * @param prefixTitle The prefix_title
   * @param relationship relationship
   */
  public IndividualDeliveredService(
      @JsonProperty("legacy_descriptor") CmsRecordDescriptor legacyDescriptor,
      @JsonProperty("first_name") String firstName, @JsonProperty("middle_name") String middleName,
      @JsonProperty("last_name") String lastName, @JsonProperty("suffix_title") String suffixTitle,
      @JsonProperty("prefix_title") String prefixTitle,
      @JsonProperty("relationship") String relationship) {
    super();
    this.legacyDescriptor = legacyDescriptor;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.suffixTitle = suffixTitle;
    this.prefixTitle = prefixTitle;
    this.relationship = relationship;
  }

  public IndividualDeliveredService() {
    // default
  }

  /**
   * @return the legacyDescriptor
   */
  public CmsRecordDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the relationship
   */
  public String getRelationship() {
    return relationship;
  }

  /**
   * @return the middleName
   */
  public String getMiddleName() {
    return middleName;
  }

  /**
   * @return the suffixTitle
   */
  public String getSuffixTitle() {
    return suffixTitle;
  }

  /**
   * @return the prefixTitle
   */
  public String getPrefixTitle() {
    return prefixTitle;
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
