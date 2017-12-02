package gov.ca.cwds.rest.api.domain.investigation;


import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;
import static org.apache.commons.lang3.StringUtils.trim;

import java.util.Date;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"legacy_descriptor", "last_updated_by", "last_updated_at", "first_name",
    "middle_name", "last_name", "name_suffix", "gender", "date_of_birth", "ssn", "languages",
    "race_codes", "sensitive", "sealed", "phone_numbers", "roles", "addresses"})
public class Person extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("legacy_descriptor")
  private CmsRecordDescriptor cmsRecordDescriptor;

  @JsonProperty("last_updated_by")
  @ApiModelProperty(required = false, readOnly = false, value = "staff person Id")
  private String lastUpdatedBy;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainObject.TIMESTAMP_ISO8601_FORMAT,
      timezone = "UTC")
  @JsonProperty("last_updated_at")
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
      example = "2010-04-27T23:30:14.000Z")
  private Date lastUpdatedAt;

  @JsonProperty("first_name")
  @ApiModelProperty(required = true, readOnly = false, value = "first name", example = "Gerry")
  @Size(min = 1, max = 20)
  @NotNull
  private String firstName;

  @JsonProperty("middle_name")
  @ApiModelProperty(required = false, readOnly = false, value = "middle name", example = "M")
  @Size(min = 0, max = 50)
  private String middleName;

  @JsonProperty("last_name")
  @ApiModelProperty(required = true, readOnly = false, value = "last name", example = "Mitchell")
  @Size(min = 1, max = 50)
  @NotNull
  private String lastName;

  @JsonProperty("name_suffix")
  @ApiModelProperty(required = false, readOnly = false, value = "Suffix title", example = "jr")
  @Size(max = 4)
  private String nameSuffix;

  @JsonProperty("gender")
  @ApiModelProperty(required = true, readOnly = false, value = "gender code", example = "M")
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"M", "F", "U"}, ignoreCase = true, ignoreWhitespace = true)
  private String gender;

  @JsonFormat(shape = JsonFormat.Shape.STRING,
      pattern = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT)
  @JsonProperty("date_of_birth")
  @gov.ca.cwds.rest.validation.Date(format = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT,
      required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2012-04-01")
  private String dateOfBirth;

  @JsonProperty("ssn")
  @ApiModelProperty(required = true, readOnly = false, value = "social security number",
      example = "999551111")
  @Size(min = 0, max = 9)
  private String ssn;

  @Valid
  @JsonProperty("languages")
  // @JsonSerialize(using = SysIdSerializer.class)
  @ApiModelProperty(required = false, readOnly = false, value = "languages",
      dataType = "java.util.List", example = "['839', '840']")
  private Set<String> languages;

  @JsonProperty("race_codes")
  private RaceAndEthnicity raceAndEthnicity;

  @JsonProperty("sensitive")
  @ApiModelProperty(required = true, readOnly = false, example = "false",
      value = "person contains sensitive information")
  @NotNull
  private Boolean sensitive;

  @JsonProperty("sealed")
  @ApiModelProperty(required = true, readOnly = false, example = "false",
      value = "person contains sealed information")
  private Boolean sealed;

  @JsonProperty("phone_numbers")
  @ApiModelProperty(required = false, readOnly = false, value = "phone numbers",
      dataType = "java.util.List")
  private Set<PhoneNumber> phone;

  @JsonProperty("roles")
  @ApiModelProperty(required = false, readOnly = false, value = "Roles of person",
      dataType = "java.util.List", example = "['Non-mandated Reporter']")
  private Set<String> roles;

  @JsonProperty("addresses")
  private Set<InvestigationAddress> addresses;

  /**
   * Default constructor
   */
  public Person() {
    // default
  }

  /**
   * @param cmsRecordDescriptor - CMS record description
   * @param lastUpdatedBy - last updated by
   * @param lastUpdatedAt - last updated at
   * @param firstName - frist name
   * @param middleName - middle name
   * @param lastName - last name
   * @param nameSuffix - name suffix
   * @param gender - gender
   * @param dateOfBirth - date of birth
   * @param ssn - SSN
   * @param languages - list of languages
   * @param raceAndEthnicity - race/ethnicity
   * @param sensitive - sensitive data
   * @param sealed - sealed data
   * @param phone - phone information
   * @param roles - roles
   * @param addresses - address information
   */
  public Person(CmsRecordDescriptor cmsRecordDescriptor, String lastUpdatedBy, Date lastUpdatedAt,
      String firstName, String middleName, String lastName, String nameSuffix,
      @OneOf(value = {"M", "F", "U"}, ignoreCase = true, ignoreWhitespace = true) String gender,
      String dateOfBirth, String ssn, Set<String> languages, RaceAndEthnicity raceAndEthnicity,
      Boolean sensitive, Boolean sealed, Set<PhoneNumber> phone, Set<String> roles,
      Set<InvestigationAddress> addresses) {
    super();
    this.cmsRecordDescriptor = cmsRecordDescriptor;
    this.lastUpdatedBy = lastUpdatedBy;
    this.lastUpdatedAt = freshDate(lastUpdatedAt);
    this.firstName = trim(firstName);
    this.middleName = trim(middleName);
    this.lastName = trim(lastName);
    this.nameSuffix = trim(nameSuffix);
    this.gender = trim(gender);
    this.dateOfBirth = dateOfBirth;
    this.ssn = trim(ssn);
    this.languages = languages;
    this.raceAndEthnicity = raceAndEthnicity;
    this.sensitive = sensitive;
    this.sealed = sealed;
    this.phone = phone;
    this.roles = roles;
    this.addresses = addresses;
  }

  /**
   * Constructing Person object from Client (CMS)
   * 
   * @param client - client object
   * @param languages - list of languages
   * @param cmsRecordDescriptor - cmsRecordDescriptor
   * @param addresses - list of investigation address
   * @param phoneNumbers - list of client phone numbers
   * @param roles - list of roles
   * @param raceAndEthnicity - race/ethnicity
   */
  public Person(Client client, Set<String> languages, CmsRecordDescriptor cmsRecordDescriptor,
      Set<InvestigationAddress> addresses, Set<PhoneNumber> phoneNumbers, Set<String> roles,
      RaceAndEthnicity raceAndEthnicity) {
    this.lastUpdatedBy = client.getLastUpdatedId();
    this.lastUpdatedAt = client.getLastUpdatedTime() != null ? client.getLastUpdatedTime() : null;

    this.firstName = trim(client.getFirstName());
    this.lastName = trim(client.getLastName());
    this.middleName = trim(client.getMiddleName());
    this.nameSuffix = trim(client.getNameSuffix());
    this.gender = client.getGender();
    this.dateOfBirth =
        client.getBirthDate() != null ? DomainChef.cookDate(client.getBirthDate()) : null;
    this.ssn = client.getSsn();
    this.languages = languages;
    this.sealed = StringUtils.equals(client.getSensitivityIndicator(), "R");
    this.sensitive = StringUtils.equals(client.getSensitivityIndicator(), "S");
    this.cmsRecordDescriptor = cmsRecordDescriptor;
    this.addresses = addresses;
    this.phone = phoneNumbers;
    this.roles = roles;
    this.raceAndEthnicity = raceAndEthnicity;
  }

  /**
   * Constructing Person object from reporter(CMS)
   * 
   * @param reporter - reporter object
   * @param languages - list of languages
   * @param cmsRecordDescriptor - cmsRecordDescriptor
   * @param address - list of investigation address
   * @param phoneNumbers - list of client phone numbers
   * @param roles - list of person roles
   */
  public Person(Reporter reporter, Set<String> languages, CmsRecordDescriptor cmsRecordDescriptor,
      Set<InvestigationAddress> address, Set<PhoneNumber> phoneNumbers, Set<String> roles) {
    this.lastUpdatedBy = reporter.getLastUpdatedId();
    this.lastUpdatedAt =
        reporter.getLastUpdatedTime() != null ? reporter.getLastUpdatedTime() : null;

    this.firstName = trim(reporter.getFirstName());
    this.lastName = trim(reporter.getLastName());
    this.middleName = trim(reporter.getMiddleName());
    this.nameSuffix = trim(reporter.getNameSuffix());
    this.gender = trim(reporter.getGender());
    this.ssn = reporter.getSsn();
    this.dateOfBirth = null;
    this.languages = languages;
    this.sealed = Boolean.FALSE;
    this.sensitive = Boolean.FALSE;
    this.cmsRecordDescriptor = cmsRecordDescriptor;
    this.addresses = address;
    this.phone = phoneNumbers;
    this.roles = roles;
  }

  /**
   * @return CMS record description
   */
  public CmsRecordDescriptor getCmsRecordDescriptor() {
    return cmsRecordDescriptor;
  }

  /**
   * @return last updated by staff Id
   */
  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  /**
   * @return last updated date/time
   */
  public Date getLastUpdatedAt() {
    return freshDate(lastUpdatedAt);
  }

  /**
   * @return first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return - middle name
   */
  public String getMiddleName() {
    return middleName;
  }

  /**
   * @return - last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return - suffix
   */
  public String getNameSuffix() {
    return nameSuffix;
  }

  /**
   * @return - gender code
   */
  public String getGender() {
    return gender;
  }

  /**
   * @return - date of birth
   */
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * @return - ssn
   */
  public String getSsn() {
    return ssn;
  }

  /**
   * @return - list of language codes
   */
  public Set<String> getLanguages() {
    return languages;
  }

  /**
   * @return - list of race/ethnicity codes
   */
  public RaceAndEthnicity getRaceAndEthnicity() {
    return raceAndEthnicity;
  }

  /**
   * @return - contains sensitive information
   */
  public Boolean getSensitive() {
    return sensitive;
  }

  /**
   * @return - contains sealed information
   */
  public Boolean getSealed() {
    return sealed;
  }

  /**
   * @return - list of phone numbers
   */
  public Set<PhoneNumber> getPhone() {
    return phone;
  }

  /**
   * @return - list of roles
   */
  public Set<String> getRoles() {
    return roles;
  }

  /**
   * @return - list of address information
   */
  public Set<InvestigationAddress> getAddresses() {
    return addresses;
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
