package gov.ca.cwds.rest.api.domain.investigation;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.util.CmsRecordUtils;
import gov.ca.cwds.rest.validation.Date;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Relationship
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"id", "date_of_birth", "first_name", "middle_name", "last_name", "name_suffix",
    "gender", "date_of_death", "sensitive", "sealed", "legacy_descriptor", "relationship_to"})
public final class Relationship extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(required = true, readOnly = false, value = "CWS/CMS identifier",
      example = "ABC1234567")
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  private String id;

  @JsonProperty("date_of_birth")
  @gov.ca.cwds.rest.validation.Date(format = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT,
      required = false)
  @ApiModelProperty(required = true, readOnly = false, value = "date of birth",
      example = "1999-10-01")
  private String dateOfBirth;

  @JsonProperty("first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "first name", example = "joe")
  @Size(min = 1, max = 20)
  private String firstName;

  @JsonProperty("middle_name")
  @Size(min = 0, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "middle name", example = "w")
  private String middleName;

  @JsonProperty("last_name")
  @NotBlank
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "last name", example = "sufer")
  private String lastName;

  @JsonProperty("name_suffix")
  @NotNull
  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "Suffix Title Description",
      example = "")
  private String suffixName;

  @JsonProperty("gender")
  @NotNull
  @Size(max = 1)
  @ApiModelProperty(required = true, readOnly = false, value = "Gender Code", example = "M")
  @OneOf(value = {"M", "F", "I", "U"}, ignoreCase = false, ignoreWhitespace = true)
  private String gender;

  @JsonProperty("date_of_death")
  @gov.ca.cwds.rest.validation.Date(format = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT,
      required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "date of death",
      example = "2010-10-01")
  private String dateOfDeath;

  @JsonProperty("sensitive")
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "sensitive", example = "false")
  private Boolean sensitive;

  @JsonProperty("sealed")
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "sealed", example = "false")
  private Boolean sealed;

  @JsonProperty("legacy_descriptor")
  private CmsRecordDescriptor cmsRecordDescriptor;

  @JsonProperty("relationship_to")
  private Set<RelationshipTo> relatedTo;

  /**
   * 
   */
  public Relationship() {
    super();
  }

  /**
   * @param id - id
   * @param dateOfBirth - date of birth
   * @param firstName - first name
   * @param middleName - middle name
   * @param lastName - last name
   * @param suffixName - suffix
   * @param gender - gender code
   * @param dateOfDeath - date of death
   * @param sensitive - sensitive data
   * @param sealed - sealed data
   * @param cmsRecordDescriptor - CMS record description
   * @param relatedTo - people related to this person
   */
  public Relationship(String id, @Date(format = "yyyy-MM-dd", required = false) String dateOfBirth,
      String firstName, String middleName, String lastName, String suffixName, String gender,
      @Date(format = "yyyy-MM-dd", required = false) String dateOfDeath, Boolean sensitive,
      Boolean sealed, CmsRecordDescriptor cmsRecordDescriptor, Set<RelationshipTo> relatedTo) {
    super();
    this.id = id;
    this.dateOfBirth = dateOfBirth;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.suffixName = suffixName;
    this.gender = gender;
    this.dateOfDeath = dateOfDeath;
    this.sensitive = sensitive;
    this.sealed = sealed;
    this.cmsRecordDescriptor = cmsRecordDescriptor;
    this.relatedTo = relatedTo;
  }

  /**
   * Constructing relationship object
   * 
   * @param client - client object
   * @param relationships - set of relationship
   */
  public Relationship(Client client, Set<RelationshipTo> relationships) {
    this.id = client.getId();
    this.dateOfBirth =
        client.getBirthDate() != null ? DomainChef.cookDate(client.getBirthDate()) : null;
    this.firstName = client.getFirstName();
    this.middleName = client.getMiddleName();
    this.lastName = client.getLastName();
    this.suffixName = client.getNameSuffix();
    this.gender = client.getGender();
    this.dateOfDeath = DomainChef.cookDate(client.getDeathDate());
    this.sealed =
        StringUtils.equalsAnyIgnoreCase(client.getSensitivityIndicator(), "R") ? Boolean.TRUE
            : Boolean.FALSE;
    this.sensitive =
        StringUtils.equalsAnyIgnoreCase(client.getSensitivityIndicator(), "S") ? Boolean.TRUE
            : Boolean.FALSE;
    this.cmsRecordDescriptor =
        CmsRecordUtils.createLegacyDescriptor(client.getId(), LegacyTable.CLIENT);
    this.relatedTo = relationships;
  }

  /**
   * @return id
   */
  public String getId() {
    return id;
  }

  /**
   * @return date of birth
   */
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * @return first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return middle name
   */
  public String getMiddleName() {
    return middleName;
  }

  /**
   * @return last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return name suffix
   */
  public String getSuffixName() {
    return suffixName;
  }

  /**
   * @return the gender
   */
  public String getGender() {
    return gender;
  }

  /**
   * @return the dateOfDeath
   */
  public String getDateOfDeath() {
    return dateOfDeath;
  }

  /**
   * @return sensitive data
   */
  public Boolean getSensitive() {
    return sensitive;
  }

  /**
   * @return sealed data
   */
  public Boolean getSealed() {
    return sealed;
  }

  /**
   * @return CMS record description
   */
  public CmsRecordDescriptor getCmsRecordDescriptor() {
    return cmsRecordDescriptor;
  }

  /**
   * @return people related to this person
   */
  public Set<RelationshipTo> getRelatedTo() {
    return relatedTo;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
