package gov.ca.cwds.rest.api.domain.investigation;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.util.CmsRecordUtils;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an AllegationPerson
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class AllegationPerson implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "First Name")
  private String firstName;

  @JsonProperty("last_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Last Name")
  private String lastName;

  @JsonProperty("middle_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Middle Name")
  private String middleName;

  @JsonProperty("name_suffix")
  @ApiModelProperty(required = false, readOnly = false, value = "Suffix")
  private String suffixTitle;

  @JsonFormat(shape = JsonFormat.Shape.STRING,
      pattern = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT)
  @JsonProperty("date_of_birth")
  @gov.ca.cwds.rest.validation.Date(format = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT,
      required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2012-04-01")
  private String dateOfBirth;

  @JsonProperty("legacy_descriptor")
  private CmsRecordDescriptor legacyDescriptor;


  /**
   * empty constructor
   */
  public AllegationPerson() {
    super();
  }

  /**
   * @param firstName - first name
   * @param lastName - last name
   * @param middleName - middle name
   * @param suffixTitle - suffix
   * @param dateOfBirth - birth date
   * @param legacyDescriptor - description of CMS record
   */
  public AllegationPerson(String firstName, String lastName, String middleName, String suffixTitle,
      String dateOfBirth, CmsRecordDescriptor legacyDescriptor) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
    this.suffixTitle = suffixTitle;
    this.dateOfBirth = dateOfBirth;
    this.legacyDescriptor = legacyDescriptor;
  }

  /**
   * constructing new Allegation Object
   * 
   * @param persistedClient - persisted Client object
   */
  public AllegationPerson(Client persistedClient) {

    this.firstName = StringUtils.trim(persistedClient.getFirstName());
    this.lastName = StringUtils.trim(persistedClient.getLastName());
    this.middleName = StringUtils.trim(persistedClient.getMiddleName());
    this.suffixTitle = StringUtils.trim(persistedClient.getNameSuffix());
    this.dateOfBirth = persistedClient.getBirthDate() != null
        ? DomainChef.cookDate(persistedClient.getBirthDate()) : null;
    this.legacyDescriptor =
        CmsRecordUtils.createLegacyDescriptor(persistedClient.getId(), LegacyTable.CLIENT);
  }

  /**
   * @return - first name of person
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName - first name of person
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return - last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName - last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return - middle name
   */
  public String getMiddleName() {
    return middleName;
  }

  /**
   * @param middleName - middle name
   */
  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  /**
   * @return - suffix
   */
  public String getSuffixTitle() {
    return suffixTitle;
  }

  /**
   * @param suffixTitle - suffix
   */
  public void setSuffixTitle(String suffixTitle) {
    this.suffixTitle = suffixTitle;
  }

  /**
   * @return - date of birth
   */
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * @param dateOfBirth - date of birth
   */
  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  /**
   * @return - CMS record descriptor
   */
  public CmsRecordDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  /**
   * @param legacyDescriptor - CMS record descriptor
   */
  public void setLegacyDescriptor(CmsRecordDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
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
