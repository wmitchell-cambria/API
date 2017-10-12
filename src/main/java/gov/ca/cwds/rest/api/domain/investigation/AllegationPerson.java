package gov.ca.cwds.rest.api.domain.investigation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.util.LegacyRecordUtils;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an AllegationPerson
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class AllegationPerson {
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

  @JsonProperty("suffix_title")
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

    this.firstName = persistedClient.getFirstName();
    this.lastName = persistedClient.getLastName();
    this.middleName = persistedClient.getMiddleName();
    this.suffixTitle = persistedClient.getNameSuffix();
    this.dateOfBirth =
        persistedClient.getBirthDate() != null ? String.valueOf(persistedClient.getBirthDate())
            : null;
    this.legacyDescriptor =
        LegacyRecordUtils.createLegacyDescriptor(persistedClient.getId(), LegacyTable.CLIENT);
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((legacyDescriptor == null) ? 0 : legacyDescriptor.hashCode());
    result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
    result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
    result = prime * result + ((suffixTitle == null) ? 0 : suffixTitle.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AllegationPerson other = (AllegationPerson) obj;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (legacyDescriptor == null) {
      if (other.legacyDescriptor != null)
        return false;
    } else if (!legacyDescriptor.equals(other.legacyDescriptor))
      return false;
    if (middleName == null) {
      if (other.middleName != null)
        return false;
    } else if (!middleName.equals(other.middleName))
      return false;
    if (dateOfBirth == null) {
      if (other.dateOfBirth != null)
        return false;
    } else if (!dateOfBirth.equals(other.dateOfBirth))
      return false;
    if (suffixTitle == null) {
      if (other.suffixTitle != null)
        return false;
    } else if (!suffixTitle.equals(other.suffixTitle))
      return false;
    return true;
  }



}
